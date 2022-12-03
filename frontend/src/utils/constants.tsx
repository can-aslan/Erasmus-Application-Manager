import {
    IconArrowsTransferDown,
    IconClick,
    IconFileReport,
    IconFileSearch,
    IconGift,
    IconLicense,
    IconList,
    IconSchool
} from "@tabler/icons";
import { NavbarLink, UserEnum } from "../types";

export const NAVBAR_LINK_OBJECTS: Record<UserEnum, Array<NavbarLink>> = {
    [UserEnum.OutgoingStudent]: [
        {
            label: 'Universities', 
            to:'/student/universities', 
            icon: <IconSchool />
        },
        {
            label: 'Course Wishlist',
            to: '/student/course-wishlist',
            icon: <IconList />
        },
        {
            label: 'Course Request',
            to: '/student/course-request',
            icon: <IconGift />
        },
        {
            label: 'Pre-approval Form',
            to: '/student/pre-approval-form',
            icon: <IconFileReport />
        },
        {
            label: 'Learning Agreement',
            to: '/student/learning-agreement',
            icon: <IconLicense />
        },
    ],
    [UserEnum.IncomingStudent]: [
        {
            label: 'Course List',
            to: '/incoming-student/course-list',
            icon: <IconList />,
        },
        {
            label: 'Choose Courses',
            to: '/incoming-student/choose-courses',
            icon: <IconClick />,
        }
    ],
    [UserEnum.FACMember]: [
        {
            label: 'Approve Pre-Approvals',
            to: '/fac-member/approve-pre-approvals',
            icon: <IconFileSearch />
        },
        {
            label: 'Course Transfer Decisions',
            to: '/fac-member/course-transfer',
            icon: <IconArrowsTransferDown />
        },
    ],
    [UserEnum.Coordinator]: [
        {
            label: 'Student Wishlists',
            to: '/coordinator/student-wishlists',
            icon: <IconList />
        },
        {
            label: 'Course Transfer Forms',
            to: '/coordinator/course-transfer-form',
            icon: <IconArrowsTransferDown />
        },
    ],
    [UserEnum.Admin]: [
        {
            // TODO: Admin pages are not determined
            label: 'Course Transfer Forms',
            to: '/coordinator/course-transfer-form',
            icon: <IconArrowsTransferDown />
        }
    ]

}