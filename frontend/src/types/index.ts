import React from "react";

export enum UserEnum {
    OutgoingStudent='OUTGOING_STUDENT',
    IncomingStudent='INCOMING_STUDENT',
    Coordinator='COORDINATOR',
    FACMember='FAC_MEMBER',
    Admin='ADMIN',
    Instructor='INSTRUCTOR',
    ExperiencedStudent='EXPERIENCED_STUDENT'
}

export type User = {
    id: string,
    name: string,
    surname: string,
    email: string,
    userType: UserEnum,
    accessToken: string,
    refreshToken: string,
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
export type WishlistItemType = {
    uuid: string,
    courseCode: string,
    courseName: string,
    bilkentCredits: number,
    ECTSCredits: number
}

export type StudentAssociatedWishlist = {
    wishlistItems: Array<WishlistItemType>,
    wishlistUuid: string,
    studentName: string,
    studentId: string,
    status: 'rejected' | 'pending' | 'approved'
}

export type Course = {
    uuid: string,
    courseCode: string,
    courseName: string,
    bilkentCredits: number,
    ECTSCredits: number
}

export type SchoolAssociatedCourse = {
    schoolId: string,
    schoolName: string,
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
    courseRequestUuid: string,
    courseCode: string,
    courseName: string,
    courseWebPage: string,
    syllabusLink: string,
    correspondingCourseInBilkent: string,
}

export type StudentAssociatedCourse = Course & {
    studentUuid: string,
    studentName: string,
    approvalStatus: 'rejected' | 'pending' | 'approved',
}

export type PreApprovalForm = {
    formUuid: string,
    studentUuid: string,
    file: string,
    status?: 'rejected' | 'pending' | 'approved',
}

export type CoordinatorAssociatedStudents = PreApprovalForm & {
    studentName: string,
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
    bgImage?: string, // URL to image
    logoImage?: string, //URL to image
}

export type SpecialCases = {
    semesterLimit: string[],
    languageRequirements: string[]
}