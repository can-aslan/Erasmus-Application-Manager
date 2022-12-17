package com.beam.beamBackend.service.form;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.PreApprovalForm;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.model.WishlistItem;
import com.beam.beamBackend.repository.IStudentRepository;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FileGenerator {
    public File generatePreApprovalForm(PreApprovalForm preApprovalForm, Image signature) throws BadElementException, MalformedURLException, IOException{
        Document document = new Document();

        // Get the owner student of the pre-approval form
        Student student = preApprovalForm.getStudent();

        // Get wishlist whose course information will be used
        Wishlist wishlist = preApprovalForm.getWishlist();

        // Student Information
        String name = student.getUser().getName();
        String surname = student.getUser().getSurname();
        long id  = student.getUser().getBilkentId();
        String department = student.getDepartment().toString();
        String hostInst = student.getHostUni().getName();
        String academicYear = student.getAcademicYear();
        String semester = student.getSemester().toString();
        String coordinatorName = student.getCoordinator().getUser().getName();
        
        
        String approvedBy = "Exchange Coordinator";
        signature.scaleAbsolute(50, 30);


        // Date Information
        String date = preApprovalForm.getDate();

        // Wishlist Information
        List<WishlistItem> wishlistItems = wishlist.getItems();

        //ArrayList<String> hostCourses = new ArrayList<>();
        ArrayList<String> bilkentCourses = new ArrayList<>();
        ArrayList<String> courseCode = new ArrayList<>();
        ArrayList<Double> ects = new ArrayList<>();
        ArrayList<Double> bilkentCredit = new ArrayList<>();
        ArrayList<String> directlyEquivalent = new ArrayList<>();
        ArrayList<ArrayList<String>> hostCourses = new ArrayList<>();
        // pseudo data starts
        for (int i = 0; i < wishlistItems.size(); i++){
            bilkentCourses.add(wishlistItems.get(i).getBilkentCourse());
            bilkentCredit.add(wishlistItems.get(i).getBilkentCredits().toString());
            double ectsTotal = 0;
            for (int j = 0; j < wishlistItems.get(i).getMappings().size(); j++){
                hostCourses.get(i).add(wishlistItems.get(i).getMappings().get(j).getHostCourse());
                ectsTotal = ectsTotal + wishlistItems.get(i).getMappings().get(j).getEcts();
            }
            // Only gets the course code of the first host university course 
            courseCode.add(wishlistItems.get(i).getMappings().get(0).getCourseCode());
            // It is assumed that ects in the WishlistItem model since there should be one ects value even there are more than one host course
            ects.add(ectsTotal);
            directlyEquivalent.add("");
        }
        // pseudo data ends

        try {
            // Make the document landscape
            document.setPageSize(PageSize.A4.rotate());

            PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.dir") + "/backend/src/main/resources/testPDF.pdf"));
            
            //FileOutputStream fos = new FileOutputStream("d:/testPDF.pdf");
            //PdfWriter.getInstance(document, fos);

            // Create the document
            document.setMargins(0, 0,5, 5);
            document.open();

            addTitleTable(document);
            addStudentInfo(document ,name, surname, id, department);
            addInstAndPeriodInfo( document, hostInst, academicYear, semester );
            createCourseLists(document, hostCourses, bilkentCourses, courseCode, ects, bilkentCredit, directlyEquivalent);
            createApprovalTable(document, approvedBy, coordinatorName, signature, date);

            document.close();
            
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("PDF generated!");

        File createdPdf = new File(System.getProperty("user.dir") + "/backend/src/main/resources/testPDF.pdf");
        return createdPdf; // need to return InputStream (convert outputstream to inputStream)
    }

    private void addTitleTable(Document document) throws MalformedURLException, IOException, DocumentException {
           // Image handling
           String imagePath = System.getProperty("user.dir") + "/backend/src/main/resources/bilkent_logo.png";
           Image bilkentLogo = Image.getInstance(imagePath);
           bilkentLogo.scaleAbsolute(40,40);

           // Create Title Table
           PdfPTable titleTable = new PdfPTable(3);
           titleTable.setWidths(new float [] {1, 2, 7});

           // Image Cell
           PdfPCell imageCell = new PdfPCell(bilkentLogo);
           imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
           imageCell.setBorder(0);
           titleTable.addCell(imageCell);

           // Phrase to put in cells
           Phrase currentPhrase = new Phrase();
           currentPhrase.setFont(new Font(FontFamily.TIMES_ROMAN,15, Font.BOLD));

           // Generic type of cells to put in title table
           PdfPCell titleCell = new PdfPCell();
           titleCell.setBorder(0);
           titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);

           // Uni name cell in the title table
           currentPhrase.add("Bilkent University");
           titleCell.addElement(currentPhrase);
           titleTable.addCell(titleCell);

           // Title in the title table
           currentPhrase.clear();
           currentPhrase.add("Course Exemption Pre-Approval Form for Outgoing Exchange Students");
           titleCell = new PdfPCell(currentPhrase);
           titleCell.setBorder(0);
           titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);

           titleTable.addCell(titleCell);
           document.add(titleTable);
    }

    private void addStudentInfo(Document document, String name, String surname, long id, String department) throws DocumentException {
        // Create Student information table
        PdfPTable siTable = new PdfPTable(4);
        siTable.setWidths(new float [] {2, 7, 2, 7});
        siTable.setSpacingBefore(10);

        // Phrase to put into cells
        Phrase currentPhrase = new Phrase();
        Phrase currentPhraseBold = new Phrase();
        currentPhrase.setFont(new Font(FontFamily.TIMES_ROMAN,12));
        currentPhraseBold.setFont(new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD));

        // Name static
        currentPhraseBold.add("Name");
        PdfPCell siCell = new PdfPCell();
        siCell.addElement(currentPhraseBold);
        siTable.addCell(siCell);

        // Name variable
        currentPhrase.clear();
        currentPhrase.add(name);
        siCell = new PdfPCell();
        siCell.addElement(currentPhrase);
        siTable.addCell(siCell);

        // ID number static 
        currentPhraseBold.clear();
        currentPhraseBold.add("ID Number");
        siCell = new PdfPCell();
        siCell.addElement(currentPhraseBold);
        siTable.addCell(siCell);

        // ID number variable
        currentPhrase.clear();
        currentPhrase.add(Long.toString(id));
        siCell = new PdfPCell();
        siCell.addElement(currentPhrase);
        siTable.addCell(siCell);

        // Surname static
        currentPhraseBold.clear();
        currentPhraseBold.add("Surname");
        siCell = new PdfPCell();
        siCell.addElement(currentPhraseBold);
        siTable.addCell(siCell);

        // Surname variable
        currentPhrase.clear();
        currentPhrase.add(surname);
        siCell = new PdfPCell();
        siCell.addElement(currentPhrase);
        siTable.addCell(siCell);


        // Department static
        currentPhraseBold.clear();
        currentPhraseBold.add("Department");
        siCell = new PdfPCell();
        siCell.addElement(currentPhraseBold);
        siTable.addCell(siCell);

        // Department variable
        currentPhrase.clear();
        currentPhrase.add(department);
        siCell = new PdfPCell();
        siCell.addElement(currentPhrase);
        siTable.addCell(siCell);


        document.add(siTable);
    }

    private void addInstAndPeriodInfo(Document document, String hostInst, String academicYear, String semester) throws DocumentException {

        PdfPTable table;
        PdfPCell cell;

        table = new PdfPTable(4);
        table.setSpacingBefore(10);
        table.setWidths(new float [] {4, 5, 2, 7});
        Phrase p1 = new Phrase("Name of the Host Institution");
        Phrase p2 = new Phrase("Academic Year");
        Phrase p3 = new Phrase("Semester");
        p1.setFont(new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD));
        p2.setFont(new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD));
        p3.setFont(new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD));


        // Host Int name static
        cell = new PdfPCell(); 
        cell.addElement(p1);
        cell.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        table.addCell(cell);
        
        // Host Int name dynamic
        cell = new PdfPCell(new Phrase(hostInst));
        cell.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        table.addCell(cell);

        // Academic year static
        cell = new PdfPCell();
        cell.addElement(p2); 
        table.addCell(cell);

        // Academic year dynamic
        cell = new PdfPCell(new Phrase(academicYear)); 
        table.addCell(cell);

        // pseudo cell
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        table.addCell(cell);

        // Add the pseudo cell again
        table.addCell(cell);


        // Semester static
        cell = new PdfPCell(); 
        cell.addElement(p3);
        table.addCell(cell);

        // Semester dynamic
        cell = new PdfPCell(new Phrase(semester)); 
        table.addCell(cell);


        document.add(table);
    }

    
    private void createCourseLists(Document document, ArrayList<ArrayList<String>> hostCourses, ArrayList<String> bilkentCourses, ArrayList<String> courseCode, ArrayList<Double> ects, ArrayList<Double> bilkentCredit, ArrayList<String> directlyEquivalent) throws DocumentException {

        // Table for the title of the course list
        PdfPTable titleTable = new PdfPTable(2);
        titleTable.setSpacingBefore(10);
        titleTable.setWidths(new float [] {1,1});

        Phrase p1 = new Phrase();
        Phrase p2 = new Phrase();
        p1.setFont(new Font(FontFamily.TIMES_ROMAN,12));
        p2.setFont(new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD));

        PdfPCell cell;
        p2.add("Host institution courses to be transferred upon approval");
        cell = new PdfPCell();
        cell.addElement(p2);
        titleTable.addCell(cell);

        p2.clear();
        p2.add("Course or requirement to be exempted if transferred course is completed with a passing grade †");
        cell = new PdfPCell();
        cell.addElement(p2);
        titleTable.addCell(cell);

        document.add(titleTable);


        // Table for courses 
        PdfPTable coursesTable = new PdfPTable(7);
        coursesTable.setWidths(new float [] {1, 2, 5, 1, 5, 1, 3});
        p2.setFont(new Font(FontFamily.TIMES_ROMAN,10, Font.BOLD));


        p2.clear();
        p2.add("");
        cell = new PdfPCell();
        cell.addElement(p2);
        coursesTable.addCell(cell);

        p2.clear();
        p2.add("Course Code");
        cell = new PdfPCell();
        cell.addElement(p2);
        coursesTable.addCell(cell);

        p2.clear();
        p2.add("Course Name");
        cell = new PdfPCell();
        cell.addElement(p2);
        coursesTable.addCell(cell);

        p2.clear();
        p2.add("Credits*");
        cell = new PdfPCell();
        cell.addElement(p2);
        coursesTable.addCell(cell);

        p2.clear();
        p2.add("Course Code and Name for a Required Course, Elective Group Name for an Elective Requirement");
        cell = new PdfPCell();
        cell.addElement(p2);
        coursesTable.addCell(cell);

        p2.clear();
        p2.add("Credits");
        cell = new PdfPCell();
        cell.addElement(p2);
        coursesTable.addCell(cell);

        p2.setFont(new Font(FontFamily.TIMES_ROMAN,8, Font.BOLD));

        p2.clear();
        p2.add("Elective Requirement Exemptions only: Course code(s) of directly equivalent course(s), if any **");
        cell = new PdfPCell();
        cell.addElement(p2);
        coursesTable.addCell(cell);


        p2.setFont(new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD));
        // Add courses list
        for (int i = 0; i < hostCourses.size(); i++){
            // row no

            p2.clear();
            p2.add(Integer.toString(i + 1));
            cell = new PdfPCell();
            cell.addElement(p2);
            coursesTable.addCell(cell);

            // Course code
            p1.clear();
            p1.add(courseCode.get(i));
            cell = new PdfPCell();
            cell.addElement(p1);
            coursesTable.addCell(cell);

            // Course Name
            p1.clear();
            // Adds more than 1 course names
            for( int j = 0; j < hostCourses.get(i).size(); i++){
                p1.add(hostCourses.get(i).get(j));
            }

            cell = new PdfPCell();
            cell.addElement(p1);
            coursesTable.addCell(cell);

            // ECTS
            p1.clear();
            p1.add(Double.toString(ects.get(i)));
            cell = new PdfPCell();
            cell.addElement(p1);
            coursesTable.addCell(cell);

            // Bilkent Course name
            p1.clear();
            p1.add(bilkentCourses.get(i));
            cell = new PdfPCell();
            cell.addElement(p1);
            coursesTable.addCell(cell);


            // Bilkent credit
            p1.clear();
            p1.add(Double.toString(bilkentCredit.get(i)));
            cell = new PdfPCell();
            cell.addElement(p1);
            coursesTable.addCell(cell);

            // Directly Equivalent Courses
            p1.clear();
            p1.add(directlyEquivalent.get(i));
            cell = new PdfPCell();
            cell.addElement(p1);
            coursesTable.addCell(cell);
        }



        document.add(coursesTable);



    }

    

    private void createApprovalTable(Document document, String approvedBy, String coordinatorName, Image signature,String date) throws DocumentException {

        // Table for the title of the course list
        PdfPTable approvalTable = new PdfPTable(4);
        approvalTable.setSpacingBefore(5);
        approvalTable.setWidths(new float [] {1,1,1,1});

        Phrase p1 = new Phrase();
        Phrase p2 = new Phrase();
        p1.setFont(new Font(FontFamily.TIMES_ROMAN,12));
        p2.setFont(new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD));

        PdfPCell cell = new PdfPCell();

        // Approved by static
        p2.clear();
        p2.add("Approved by");
        cell = new PdfPCell();
        cell.addElement(p2);
        approvalTable.addCell(cell);

        // Coordinator Name Static
        p2.clear();
        p2.add("Name");
        cell = new PdfPCell();
        cell.addElement(p2);
        approvalTable.addCell(cell);

        // Signature Static
        p2.clear();
        p2.add("Signature");
        cell = new PdfPCell();
        cell.addElement(p2);
        approvalTable.addCell(cell);

        // Date Static
        p2.clear();
        p2.add("Date");
        cell = new PdfPCell();
        cell.addElement(p2);
        approvalTable.addCell(cell);

        // Approved by dynamic
        p2.clear();
        p2.add(approvedBy);
        cell = new PdfPCell();
        cell.addElement(p2);
        approvalTable.addCell(cell);

        // Coordinator Name dynamic
        p2.clear();
        p2.add(coordinatorName);
        cell = new PdfPCell();
        cell.addElement(p2);
        approvalTable.addCell(cell);

        // Signature dynamic
        cell = new PdfPCell();
        cell.addElement(signature);
        approvalTable.addCell(cell);

        // Date dynamic
        p2.clear();
        p2.add(date);
        cell = new PdfPCell();
        cell.addElement(p2);
        approvalTable.addCell(cell);

        document.add(approvalTable);

        Paragraph p4 = new Paragraph("* ECTS credits for Erasmus exchange students.");
        Paragraph p5 = new Paragraph("** Applicable only if there is a directly equivalent course in the elective group that the student is exempted from. The student will be considered to have taken this course by the STARS system.");
        Paragraph p6 = new Paragraph("† A transferred course may provide exemption from a requirement in the curriculum if deemed to be equivalent by the Faculty/School Executive Board. It is possible for one transferred course to provide exemption from one or more curriculum courses or vice versa.");
        p4.setFont(new Font(FontFamily.TIMES_ROMAN,6));
        p5.setFont(new Font(FontFamily.TIMES_ROMAN,6));
        p6.setFont(new Font(FontFamily.TIMES_ROMAN,6));
        p4.setSpacingBefore(5);
        p5.setSpacingBefore(5);
        p6.setSpacingBefore(5);
        document.add(p4);
        document.add(p5);
        document.add(p6);
        
    
    }


}
