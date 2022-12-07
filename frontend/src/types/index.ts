import React from "react";

export enum UserEnum {
    OutgoingStudent='OUTGOING_STUDENT',
    IncomingStudent='INCOMING_STUDENT',
    Coordinator='COORDINATOR',
    FACMember='FAC_MEMBER',
    Admin='ADMIN'
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
    file: File,
    status?: 'rejected' | 'pending' | 'approved',
}