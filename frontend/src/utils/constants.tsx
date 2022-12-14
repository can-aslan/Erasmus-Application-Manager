import {
    IconArrowsTransferDown,
    IconBook,
    IconClick,
    IconFileReport,
    IconFileSearch,
    IconGift,
    IconLicense,
    IconList,
    IconRegistered,
    IconSchool,
    IconUpload
} from "@tabler/icons";
import { NavbarLink, ProgressBarStep, UserEnum } from "../types";

export const PROGRESSBAR_STEP_OBJECTS: Record<UserEnum, Array<ProgressBarStep>> = {
    [UserEnum.OutgoingStudent]: [
        {
            label: "Apply To Host",
            description: "Apply to host university"
        },
        {
            label: "Submit Wishlist",
            description: "Submit Wishlist to Coordinator"
        },
        {
            label: "Submit Pre Approval",
            description: "Submit Pre Approval to Coordinator"
        },
        {
            label: "Submit Learning Agreement Coordinator",
            description: "Submit Learning Agreement to Coordinator"
        },
        {
            label: "SubmitLearningAgreementOISEP",
            description: "Submit Learning Agreement to OISEP"
        }
    ],
    [UserEnum.IncomingStudent]: [],
    [UserEnum.Coordinator]: [],
    [UserEnum.FACMember]: [],
    [UserEnum.Admin]: [],
    [UserEnum.Instructor]: [],
    [UserEnum.ExperiencedStudent]: [],
    [UserEnum.OISEPStaff]: []
}

export const NAVBAR_LINK_OBJECTS: Record<UserEnum, Array<NavbarLink>> = {
    [UserEnum.OutgoingStudent]: [
        {
            label: 'Universities',
            to: '/student/universities',
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
        {
            label: 'Course Evaluations',
            to: '/student/course-evaluations',
            icon: <IconBook/>
        }
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
            label: 'Course Transfer',
            to: '/coordinator/course-transfer-form',
            icon: <IconArrowsTransferDown />
        },
        {
            label: 'Approve Pre-Approvals',
            to: '/coordinator/approve-pre-approvals',
            icon: <IconFileSearch />
        },
        {
            label: 'Approve Learning Agreements',
            to: '/coordinator/approve-learning-agreements',
            
        }
    ],
    [UserEnum.Admin]: [
        {
            // TODO: Admin pages are not determined
            label: 'Register User',
            to: '/admin/register-page',
            icon: <IconRegistered />
        }
    ],
    [UserEnum.Instructor]: [
        {
            label: 'Approve Course Request',
            to: '/instructor/approve-course-request'

        }
    ],
    [UserEnum.ExperiencedStudent]: [
        {
            label: 'Evaluate University',
            to: '/experienced-student/evaluate-university',
            icon: <IconSchool />,
        },
        {
            label: 'Evaluate Courses',
            to: '/experienced-student/evaluate-courses',
            icon: <IconBook />
        }
    ],
    [UserEnum.OISEPStaff]: [
        {
            label: 'Upload Transcript',
            to: '/oisep-staff/transcript-upload',
            icon: <IconUpload />
        }
    ]

}

export const DEPARTMENTS = [
    "Information Systems and Technologies",
    "Tourism and Hotel Management",
    "Department of Architecture",
    "Department of Communication and Design",
    "Department of Fine Arts",
    "Department of Graphic Design",
    "Department of Interior Architecture and Environmental Design",
    "Department of Urban Design and Landscape Architecture",
    "Department of Management",
    "Department of Economics",
    "Department of History",
    "Department of International Relations",
    "Department of Political Science and Public Administration",
    "Department of Psychology",
    "Education Sciences",
    "Teacher Education",
    "M.A. in Teaching English as a Foreign Language",
    "Department of Computer Engineering",
    "Department of Electrical and Electronics Engineering",
    "Department of Industrial Engineering",
    "Department of Mechanical Engineering",
    "Department of American Culture and Literature",
    "Department of Archaeology",
    "Department of English Language and Literature",
    "Department of Philosophy",
    "Department of Translation and Interpretation",
    "Department of Turkish Literature",
    "Department of Law",
    "Department of Chemistry",
    "Department of Mathematics",
    "Department of Molecular Biology and Genetics",
    "Department of Physics",
    "Department of Music",
    "Department of Performing Arts",
]

export const FACULTIES = [
    "Faculty of Applied Sciences",
    "Faculty of Art, Design, and Architecture",
    "Faculty of Business Administration",
    "Faculty of Economics, Administrative, and Social Sciences",
    "Faculty of Education",
    "Faculty of Engineering",
    "Faculty of Humanities and Letters",
    "Faculty of Law",
    "Faculty of Science",
    "Faculty of Music and Performing Arts"
]