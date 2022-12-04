import React from "react";

export enum UserEnum {
    OutgoingStudent='Outgoing Student',
    IncomingStudent='Incoming Student',
    Coordinator='Coordinator',
    FACMember='FAC Member',
    Admin='Admin'
}

export type User = {
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
    courseCode: string,
    courseName: string,
    bilkentCredits: number,
    ECTSCredits: number
}