package com.beam.beamBackend.service.form;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.hibernate.grammars.hql.HqlParser.CurrentDateFunctionContext;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.Sex;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

public class LearningAgreementGenerator {

    public void generateLearningAgreementForm() {
        // Student information
        String name = "Kubilay";
        String surname = "Yilmaz";
        String dateOfBirth = "26.11.2022";
        String nationality = "Turkish";
        String sex = "MALE";
        String department = "Computer Engineering";
        String studyCycle = "First Cycle";
        String academicYear = "2022 - 2023";
        String email = "kubilay.yilmaz@ug.bilkent.edu.tr";
        String currentDate = "18.12.2022";

        // Coordinator information
        String coordinatorName = "Can Alkan";
        String coordinatorEmail = "calkan@cs.bilkent.edu.tr";

        // Bilkent Information
        String uniName = "Ihsan Dogramaci Bilkent University";
        String faculty = "Engneering Faculty";
        String erasmusCode = "TR ANKARA 07";
        String address = "Bilkent University 06800 Ankara";
        String country = "Turkey";
        String contactInfo = "Office of International Students and Exchange Programs exchange@bilkent.edu.tr Tel: +90 312 290 2435";

        // Receiving institution information
        String hostName = "Virje University";
        String hostFaculty = "Engineering Faculty";
        String hostErasmusCode = "NT AMSTERDAM 03";
        String hostAddress = "Amsterdam mahallesi, 167. st. Merkez Campus";
        String hostCountry = "Netherlands";
        String hostContact = "Office of International Students and Exchange Programs exchange@virje.edu.tr Tel: +90 312 290 2435";

        // Table A courses in host uni
        ArrayList<String> courseCodeTableA = new ArrayList<>();
        ArrayList<String> courseNameTableA = new ArrayList<>();
        ArrayList<String> semesterTableA = new ArrayList<>();
        ArrayList<String> ectsCreditsTableA = new ArrayList<>();

        // Mock Data A
        for (int i = 0; i < 6; i++) {
            courseCodeTableA.add("X_400614");
            courseNameTableA.add("Data Structures and Algorithms");
            semesterTableA.add("Fall");
            ectsCreditsTableA.add("6.0");
        }
        // Table B in Bilkent
        ArrayList<String> courseCodeTableB = new ArrayList<>();
        ArrayList<String> courseNameTableB = new ArrayList<>();
        ArrayList<String> semesterTableB = new ArrayList<>();
        ArrayList<String> ectsCreditsTableB = new ArrayList<>();

        // Mock Data B
        for (int i = 0; i < 6; i++) {
            courseCodeTableB.add(" CS473");
            courseNameTableB.add("Technical Elective");
            semesterTableB.add("Fall");
            ectsCreditsTableB.add("5.0");
        }
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("d:/LAtestPdf.pdf"));

            document.setMargins(-40, -40, 5, 5);
            document.open();

            addTitle(document, name, surname, academicYear);
            addGeneralInfoTable(document, name, surname, dateOfBirth, nationality, sex, studyCycle, department, uniName,
                    faculty, erasmusCode, address, country, contactInfo, hostName, hostFaculty, hostErasmusCode,
                    hostAddress,
                    hostCountry, hostContact);
            addTable(document, courseCodeTableA, courseNameTableA, semesterTableA, ectsCreditsTableA, "A");
            addTable(document, courseCodeTableB, courseNameTableB, semesterTableB, ectsCreditsTableB, "B");
            addCommitmentTable(document, name, email, currentDate, coordinatorName, coordinatorEmail);
            document.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("LA PDF generated!");
    }

    private void addCommitmentTable(Document document, String name, String email, String currentDate,
            String coordinatorName, String coordinatorEmail) throws DocumentException {
        String commitment = "By signing this document, the student, the Sending Institution and the Receiving Institution confirm that they approve the Learning Agreement and that they will comply with all the arrangements agreed by all parties. Sending and Receiving Institutions undertake to apply all the principles of the Erasmus Charter for Higher Education relating to mobility for studies (or the principles agreed in the Inter-Institutional Agreement for institutions located in Partner Countries). The Beneficiary Institution and the student should also commit to what is set out in the Erasmus+ grant agreement. The Receiving Institution confirms that the educational components listed in Table A are in line with its course catalogue and should be available to the student. The Sending Institution commits to recognise all the credits or equivalent units gained at the Receiving Institution for the successfully completed educational components and to count them towards the student's degree as described in Table B. Any exceptions to this rule are documented in an annex of this Learning Agreement and agreed by all parties. The student and the Receiving Institution will communicate to the Sending Institution any problems or changes regarding the study programme, responsible persons and/or study period.";
        PdfPTable commitmentTable = new PdfPTable(1);
        commitmentTable.setSpacingBefore(10);

        addTableBold("Commitment", commitmentTable, 0);
        addTableNormal(commitment, commitmentTable, 1);
        document.add(commitmentTable);

        // Signatures table
        PdfPTable signaturestable = new PdfPTable(6);
        signaturestable.setWidths(new float[] { 3, 3, 3, 2, 2, 3 });

        // Headers
        addTableBold("Commitment", signaturestable, 2);
        addTableBold("Name", signaturestable, 2);
        addTableBold("Email", signaturestable, 2);
        addTableBold("Position", signaturestable, 2);
        addTableBold("Date", signaturestable, 2);
        addTableBold("Signature", signaturestable, 2);

        // Information part
        // Student
        addTableNormal("Student", signaturestable, 2);
        addTableNormal(name, signaturestable, 2);
        addTableNormal(email, signaturestable, 2);
        addTableNormal("Student", signaturestable, 2);
        addTableNormal(currentDate, signaturestable, 2);
        addTableNormal("", signaturestable, 2);

        // Bilkent Coordinator Part
        addTableNormal("Responsible person at the Sending Institution", signaturestable, 2);
        addTableNormal(coordinatorName, signaturestable, 2);
        addTableNormal(coordinatorEmail, signaturestable, 2);
        addTableNormal("Dept Coordinator", signaturestable, 2);
        addTableNormal(currentDate, signaturestable, 2);
        addTableNormal("", signaturestable, 2);

        // Host reponsible person
        addTableNormal("", signaturestable, 2);
        addTableNormal("", signaturestable, 2);
        addTableNormal("", signaturestable, 2);
        addTableNormal("", signaturestable, 2);
        addTableNormal("", signaturestable, 2);
        addTableNormal("", signaturestable, 2);

        document.add(signaturestable);
    }

    private void addTitle(Document document, String name, String surname, String academicYear)
            throws MalformedURLException, IOException, DocumentException {
        // Image handling
        String imagePath = System.getProperty("user.dir") + "/backend/src/main/resources/erasmus_logo.jpeg";
        Image erasmusLogo = Image.getInstance(imagePath);
        erasmusLogo.scaleAbsolute(50, 50);

        // Create Title Table
        PdfPTable titleTable = new PdfPTable(3);
        titleTable.setWidths(new float[] { 3, 5, 4 });

        // Image Cell
        PdfPCell imageCell = new PdfPCell(erasmusLogo);
        imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        imageCell.setBorder(0);
        titleTable.addCell(imageCell);

        // Phrase to put in cells
        Phrase currentPhrase = new Phrase();
        Font font = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        font.setColor(BaseColor.BLUE);
        currentPhrase.setFont(font);

        // Generic type of cells to put in title table
        PdfPCell titleCell = new PdfPCell();
        titleCell.setBorder(0);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Learning Agreement title cell in the title table
        currentPhrase.add("       Learning Agreement");
        titleCell.addElement(currentPhrase);
        currentPhrase.clear();
        currentPhrase.add("Student Mobility for Studies");
        titleCell.addElement(currentPhrase);
        titleTable.addCell(titleCell);

        // Right corner text in the title table
        currentPhrase.clear();
        currentPhrase.add(
                "Higher Education: Learning Agreement form " + name + " " + surname + " Academic year " + academicYear);
        titleCell = new PdfPCell(currentPhrase);
        titleCell.setBorder(0);
        titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        titleTable.addCell(titleCell);
        document.add(titleTable);
    }

    private void addGeneralInfoTable(Document document, String name, String surname, String dateOfBirth,
            String nationality, String sex,
            String studyCycle, String department, String uniName, String faculty, String erasmusCode, String address,
            String country, String contactInfo, String hostName, String hostFaculty, String hostErasmusCode,
            String hostAddress, String hostCountry, String hostContact) throws DocumentException {

        // Create General information table
        // Student Table
        PdfPTable studentTable = new PdfPTable(8);
        studentTable.setWidths(new float[] { 2, 2, 2, 2, 2, 2, 2, 2 });
        studentTable.setSpacingBefore(10);

        // Student static
        addTableBold("Student", studentTable, 0); // Bottomless
        addTableBold("Lastname(s)", studentTable, 2);
        addTableBold("First Name(s)", studentTable, 2);
        addTableBold("Date of Birth", studentTable, 2);
        addTableBold("Nationality", studentTable, 2);
        addTableBold("Gender: [Male/Female/undefined]", studentTable, 2);
        addTableBold("Study Cycle", studentTable, 2);
        addTableBold("Field of education", studentTable, 2);

        // Student dynamic
        addTableNormal("", studentTable, 1); // Topless
        addTableNormal(surname, studentTable, 2);
        addTableNormal(name, studentTable, 2);
        addTableNormal(dateOfBirth, studentTable, 2);
        addTableNormal(nationality, studentTable, 2);
        addTableNormal(sex, studentTable, 2);
        addTableNormal(studyCycle, studentTable, 2);
        addTableNormal(department, studentTable, 2);

        document.add(studentTable);

        // Institution Table
        PdfPTable instTable = new PdfPTable(7);
        instTable.setWidths(new float[] { 2, 2, 2, 2, 2, 2, 4 });

        // Sending Institution static
        addTableBold("Sending Institution", instTable, 0);
        addTableBold("Name", instTable, 2);
        addTableBold("Faculty/Department", instTable, 2);
        addTableBold("Erasmus code (if applicable)", instTable, 2);
        addTableBold("Address", instTable, 2);
        addTableBold("Country", instTable, 2);
        addTableBold("ContactPerson name; email; phone", instTable, 2);

        // Sending Institution dynamic
        addTableNormal("", instTable, 1);
        addTableNormal(uniName, instTable, 2);
        addTableNormal(faculty, instTable, 2);
        addTableNormal(erasmusCode, instTable, 2);
        addTableNormal(address, instTable, 2);
        addTableNormal(country, instTable, 2);
        addTableNormal(contactInfo, instTable, 2);

        // Receiving Institution static
        addTableBold("Receiving Institution", instTable, 0);
        addTableBold("Name", instTable, 2);
        addTableBold("Faculty/Department", instTable, 2);
        addTableBold("Erasmus code (if applicable)", instTable, 2);
        addTableBold("Address", instTable, 2);
        addTableBold("Country", instTable, 2);
        addTableBold("ContactPerson name; email; phone", instTable, 2);

        // Receiving Institution dynamic
        addTableNormal("", instTable, 1);
        addTableNormal(hostName, instTable, 2);
        addTableNormal(hostFaculty, instTable, 2);
        addTableNormal(hostErasmusCode, instTable, 2);
        addTableNormal(hostAddress, instTable, 2);
        addTableNormal(hostCountry, instTable, 2);
        addTableNormal(hostContact, instTable, 2);
        document.add(instTable);
    }

    private void addTableBold(String phrase, PdfPTable table, int sideChoice) {
        Phrase currentPhraseBold = new Phrase(phrase);
        currentPhraseBold.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
        currentPhraseBold.clear();
        currentPhraseBold.add(phrase);
        PdfPCell cell = new PdfPCell();
        cell.addElement(currentPhraseBold);

        // No bottom
        if (sideChoice == 0) {
            cell.setBorder(PdfPCell.TOP | PdfPCell.LEFT | PdfPCell.RIGHT);
        } else if (sideChoice == 1) {
            cell.setBorder(PdfPCell.BOTTOM | PdfPCell.LEFT | PdfPCell.RIGHT); // No top
        } else if (sideChoice < 0) {
            cell.setBorder(0);
        }
        table.addCell(cell);
    }

    private void addTableNormal(String phrase, PdfPTable table, int sideChoice) {
        Phrase currentPhrase = new Phrase();
        currentPhrase.setFont(new Font(FontFamily.TIMES_ROMAN, 9));

        currentPhrase.clear();
        currentPhrase.add(phrase);
        PdfPCell cell = new PdfPCell();
        cell.addElement(currentPhrase);

        // No bottom
        if (sideChoice == 0) {
            cell.setBorder(PdfPCell.TOP | PdfPCell.LEFT | PdfPCell.RIGHT); // No bottom
        } else if (sideChoice == 1) {
            cell.setBorder(PdfPCell.BOTTOM | PdfPCell.LEFT | PdfPCell.RIGHT); // No top
        } else if (sideChoice == 5) {
            cell.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        } else if (sideChoice < 0) {
            cell.setBorder(0);
        }
        table.addCell(cell);
    }

    private void addTable(Document document, ArrayList<String> courseCodeTableA, ArrayList<String> courseNameTableA,
            ArrayList<String> semesterTableA, ArrayList<String> ectsCreditsTableA, String tableName)
            throws DocumentException {

        // Title
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setWidths(new float[] { 1 });
        titleTable.setSpacingBefore(10);

        Phrase titlePhrase;
        if (tableName == "A") {
            titlePhrase = new Phrase("Before the Mobility");
        } else {
            titlePhrase = new Phrase("");
        }
        titlePhrase.setFont(new Font(FontFamily.TIMES_ROMAN, 16, Font.BOLD));
        PdfPCell cell = new PdfPCell();
        cell.addElement(titlePhrase);
        cell.setBorder(0);
        titleTable.addCell(cell);
        document.add(titleTable);

        // Table

        // Table Top
        PdfPTable tableATop = new PdfPTable(1);
        tableATop.setSpacingBefore(10);
        if (tableName == "A") {
            addTableBold("Study Programme at the Receiving Institution", tableATop, 0);
            addTableBold("Planned period of the mobility: from [month/year] 08/2022 to [month/year] 02/2023", tableATop,
                    1);
        } else {
            addTableBold("Recognition at the Sending Institution", tableATop, 0);
        }

        document.add(tableATop);
        PdfPTable tableA = new PdfPTable(5);
        tableA.setWidths(new float[] { 1, 1, 3, 1, 2 });

        // Header
        addTableBold("Table " + tableName + " Before the mobility", tableA, 0);
        addTableBold("Component Code (if any)", tableA, 2);
        addTableBold("Component title at the Receiving Institution (as indicated in the course catalogue)", tableA, 2);
        addTableBold("Semester", tableA, 2);
        addTableBold(
                "Number of ECTS credits (or equivalent) to be awarded by the Receiving Institution upon successful completion",
                tableA, 2);

        // Data rows
        for (int i = 0; i < courseCodeTableA.size(); i++) {
            if (i == courseCodeTableA.size() - 1) {
                addTableNormal("", tableA, 1);
            } else {
                addTableNormal("", tableA, 5);
            }
            addTableNormal(courseCodeTableA.get(i), tableA, 2);
            addTableNormal(courseNameTableA.get(i), tableA, 2);
            addTableNormal(semesterTableA.get(i), tableA, 2);
            addTableNormal(ectsCreditsTableA.get(i), tableA, 2);
        }
        document.add(tableA);
    }
}
