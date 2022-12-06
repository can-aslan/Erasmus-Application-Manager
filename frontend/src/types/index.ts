import React from "react";

export enum UserEnum {
    OutgoingStudent='Outgoing Student',
    IncomingStudent='Incoming Student',
    Coordinator='Coordinator',
    FACMember='FAC Member',
    Admin='Admin'
}

export type User = {
    uuid: string,
    name: string,
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

export type WishlistItemType = {
    uuid: string,
    courseCode: string,
    courseName: string,
    bilkentCredits: number,
    ECTSCredits: number
}

export type Course = {
    uuid: string,
    courseCode: string,
    courseName: string,
    bilkentCredits: number,
    ectsCredits: number
}

export type PreviousCourseRequest = CourseRequest & {
    uuid: string,
    status: 'rejected' | 'pending' | 'approved',
}

export type CourseRequest = {
    courseCode: string,
    courseName: string,
    courseWebPage: string,
    syllabusLink: string,
    correspondingCourseInBilkent: string,
}

export type StudentAssociatedCourse = Course & {
    studentUuid: string,
}

export type PreApprovalForm = {
    studentUuid: string,
    file: File,
    status?: 'rejected' | 'pending' | 'approved',
}