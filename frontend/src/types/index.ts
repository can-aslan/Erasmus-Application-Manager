
import React from "react";
import { DEPARTMENTS, FACULTIES } from "../utils/constants";

export type ApprovalStatus = 'WAITING' | 'PENDING' | 'APPROVED' | 'REJECTED'
export type EvaluationStatus = 'EMPTY' | 'SUBMITTED' | 'SAVED'
export type Semester = 'FALL' | 'SPRING' | 'YEAR'
export type Continent = 'ASIA' | 'AFRICA' | 'NORTH_AMERICA' | 'SOUTH_AMERICA' | 'EUROPE' | 'AUSTRALIA' | 'ANTARCTICA'
export type Sex = 'MALE' | 'FEMALE'
export type StudyType = 'MINOR' | 'MAJOR' | 'NORMAL'
export type Faculty = 'ENGINEERING_FAC'
export type Department = 'CS' | 'EEE' | 'IE' | 'ME'
export type CourseRequestDestination = 'COORDINATOR' | 'INSTRUCTOR'

export enum UserEnum {
    OutgoingStudent = 'OUTGOING_STUDENT',
    IncomingStudent = 'INCOMING_STUDENT',
    Coordinator = 'COORDINATOR',
    FACMember = 'FAC_MEMBER',
    Admin = 'ADMIN',
    Instructor = 'INSTRUCTOR',
    ExperiencedStudent = 'EXPERIENCED_STUDENT',
    OISEP="OISEP"
}

export type NewStaff = {
    name: string,
    surname: string,
    bilkentId: string,
    email: string,
    userType: UserEnum,
    department?: typeof DEPARTMENTS[number]['value'],
    faculty?: typeof FACULTIES[number]['value'],
    bilkentCourseCodes?: Array<string>
}

export type User = {
    id: string,
    name: string,
    surname: string,
    email: string,
    userType: UserEnum,
    accessToken: string,
    refreshToken: string,
    bilkentId: string
}

export type MenuItem = {
    label: string,
    action?: React.MouseEventHandler,
    to?: string,
    bgColor?: string,
    color?: string, // TODO: Specify colors
    icon?: React.ReactNode,
}

export type NavbarLink = {
    label: string,
    to: string,
    icon?: React.ReactNode,
}

// Models
export type StudentAssociatedWishlist = {
    wishlistItems: Array<Course>,
    wishlistUuid: string,
    studentName: string,
    studentId: string,
    status: ApprovalStatus
}

export type Course = {
    courseCode: string,
    ects: number,
    courseId?: string | null,
    department?: string,
    syllabus?: string,
    webPage?: string
    instructorId?: string,
    universityId?: string,
}

export type BilkentCourse = Course & {
    bilkentName: string,
    bilkentCredit: number,
    elective?: boolean,
    courseName?: string
}

export type HostCourse = Course & {
    hostName: string,
    courseName?: string,
    courseApproval?: ApprovalStatus | null
}

export type CourseWishlist = {
    wishlistStatus: ApprovalStatus,
    wishlistId: string,
    wishlistItems: Array<CourseWishlistItem>, //
}

export type CourseWishlistItem = {
    otherUniCourses: Array<HostCourse>,
    correspondingBilkentCourse: BilkentCourse,
}

export type NewCourseWish = {
    bilkentCourse: string,
    mappings: Array<CourseWishlistItemMapping>,
    ects: number,
    bilkentCredits: number,
    bilkentName?: string,
}

export type CourseWishlistItemMapping = {
    mappingItemId: string,
    hostCourse: string, // course code
    ects: number,
    hostName: string, // course name
    wishlistItem?: string,
}

export type ExistingCourseWishlist = {
    studentId: string,
    status: ApprovalStatus,
    items: Array<WishlistItemsInterface>,
    feedback?: string | null
}

export interface WishlistItemsInterface {
    wishlistItemId: string,
    studentId: string,
    ects: number,
    bilkentCredits: number,
    bilkentName: string, //
    bilkentCourse: string, // Course code
    ownerWishlist: string,
    mappings: Array<CourseWishlistItemMapping>
}

export type UniAssociatedCourse = {
    uniId: string,
    uniName: string,
    uuid: string,
    courseCode: string,
    courseName: string,
    ECTSCredits: number,
}

export type PreviousCourseRequest = CourseRequest & {
    uuid: string,
    status: 'rejected' | 'pending' | 'approved',
}

export type CourseRequest = {
    requestId?: string | null,
    studentId: string
    hostCode: string,
    name: string,
    bilkentCode: string,
    webpage: string,
    syllabusLink: string,
    hostEcts: string,
    destination?: CourseRequestDestination | null,
    status?: ApprovalStatus | null
    feedback?: string,
    hostUniName?: string
}

export type StudentAssociatedCourse = Course & {
    studentUuid: string,
    studentName: string,
    approvalStatus: 'rejected' | 'pending' | 'approved',
}

export type PreApprovalWishlistItems = {
    feedback: string | undefined | null,
    items: Array<WishlistItemsInterface>,
    status: ApprovalStatus,
    studentId: string // Bilkent Id
}

export type PreApprovalForm = {
    id: string,
    student: Student
    wishlist: PreApprovalWishlistItems
    date: string
    preApprovalStatus?: 'COORDINATOR_APPROVED' | 'COORDINATOR_REJECTED' | 'PENDING' | 'FAC_APPROVED' | 'WAITING',
}

export type CoordinatorAssociatedStudents = {
    studentUuid: string,
    studentName: string,
    studentSurname: string,
    studentId: string,
    studentDepartment: Array<string>,
}

export type UniversityProxy = {
    id: string,
    universityName: string,
    city: string,
    country: string,
    dormitory?: boolean,
    studentGrant?: number,
    specialCase?: SpecialCases,
    // TODO: Add accepted departments
}

export type UniversityDetailed = {
    universityWebsite: string,
    generalInfo: string,
    acceptedDepartmentsInBilkent: Array<string>
    evals: Array<PastEvaluationItem>,
    bgImage?: string, // URL to image
    logoImage?: string, //URL to image
}

export type SpecialCases = {
    semesterLimit: string[],
    languageRequirements: string[]
}

export type ProgressBarStep = {
    label: string,
    description: string
}

export type PastEvaluation = {
    average_rate: number,
    eval_list: Array<PastEvaluationItem>
}
export type PastEvaluationItem = {
    author_id: string,
    rate: number,
    comment: string,
}

export type StudentAssociatedCoursePastEvaluationItem = PastEvaluationItem & {
    course_id: string,
    eval_status: string
}

export type StudentAssociatedUniPastEvaluationItem = PastEvaluationItem & {
    uni_id?: string,
    eval_status: string
}

export type LearningAgreement = {
    formUuid: string,
    studentName: string,
    studentId: string,
    status: string,
    rejectionFeedback: string,
}

export type Student = {
    user: User;
    department1: Department;
    faculty1: Faculty;
    department2: Department;
    faculty2: Faculty;
    telephoneNo: string;
    studyType: StudyType;
    nationality: string;
    dateOfBirth: string;
    sex: Sex;
    homeUni: University;
    hostUni: University;
    academicYear: string;
    semester: Semester;
    coordinator: Staff;
}
export type Staff = {
    id: string;
    user: User;
    department: Department;
    faculty: Faculty;
}

export type University = {
    id: string;
    name: string;
    city: string;
    country: Country;
}

export type Country = {
    id: string;
    name: string;
    isIncludedInErasmus: boolean;
    continent: Continent;
}

export enum Form {
    PRE_APPROVAL = "PRE_APPROVAL",
    TRANSCRIPT = "TRANSCRIPT",
    LEARNING_AGREEMENT = "LEARNING_AGREEMENT",
    SIGNATURE = "SIGNATURE",
}

export type InstructorCourseRequestChange = {
    courseStatus: string;
    instructorId: string;
    courseRequestId: string;
    feedback: string
}

export type CoordinatorWishlistChange = {
    status: string;
    coordinatorUserId: string;
    studentBilkentId: string;
    feedback?: string | null;

}
// export type LearningAgreement = {
//     formUuid: string,
//     studentId: string,
//     studentInfo: StudentInfo,
//     sendingInst: InstInfo,
//     receivingInst: InstInfo,
//     tableA: Array<LAStudyProgItem>,
//     tableB: Array<LAStudyProgItem>,
//     weblinkCatalogue: string,
//     language: Language,
//     studentCommitBefore: Commit,
//     responsibleSendingCommitBefore: Commit,
//     responsibleReceivingCommitBefore: Commit,
//     tableAMobility: Array<TableMobilityItem>,
//     tableBMobility: Array<TableMobilityItem>,
//     studentCommitDuring: Commit,
//     responsibleSendingCommitDuring: Commit,
//     responsibleReceivingCommitDuring: Commit,
//     tableC: Array<TableCandDItem>,
//     tableD: Array<TableCandDItem>
// }

// export type StudentInfo = {
//     lastName: string,
//     name: string,
//     dob: string,
//     nationality: string,
//     sex: string,
//     academicYear: string,
//     studyCycle: string,
//     subjectArea: string,
// }

// export type InstInfo = {
//     name: string,
//     faculty: string,
//     erasmusCode: string,
//     dept: string,
//     adress: string,
//     country: string,
//     contactName: string,
//     contactEmail: string,
//     contactPhone: string,
// }

// export type LAStudyProgItem ={
//     componentCode: string,
//     componentTitle: string,
//     semester: string,
//     credits: string,
// }

// export type Language = {
//     language: string,
//     level: string,
// }

// export type Commit = {
//     name: string,
//     function: string,
//     phone: string,
//     email: string,
//     date: string,
//     signature: string,
// }

// export type TableMobilityItem = {
//     componentCode: string,
//     componentTitle: string,
//     deletedComponent: boolean,
//     addedComponent: boolean,
//     reason: string,
//     credits: string,
// }

// export type TableCandDItem = {
//     componentCode: string,
//     componentTitle: string,
//     successful: string,
//     credits: string,
//     grade: string,
// }
