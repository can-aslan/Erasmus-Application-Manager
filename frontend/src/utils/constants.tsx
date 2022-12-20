import {
    IconArrowsTransferDown,
    IconBook,
    IconClick,
    IconFileCheck,
    IconFileReport,
    IconFileSearch,
    IconGift,
    IconLicense,
    IconList,
    IconRegistered,
    IconReplace,
    IconSchool,
    IconSignature,
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
    [UserEnum.ISOStaff]: []
}

export const NAVBAR_LINK_OBJECTS: Record<UserEnum, Array<NavbarLink>> = {
    [UserEnum.OutgoingStudent]: [
        {
            label: 'Upload Signature',
            to: '/student/upload-signature',
            icon: <IconSignature />
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
            label: 'Upload Signature',
            to: '/coordinator/upload-signature',
            icon: <IconSignature />
        },
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
            label: 'Register User',
            to: '/admin/register-page',
            icon: <IconRegistered />
        },
        {
            label: 'Place Students',
            to: '/admin/placement-page',
            icon: <IconReplace />
        },
        {
            label: 'Add University/Course',
            to: '/admin/add-uni-course-page',
            icon: <IconSchool />
        }
    ],
    [UserEnum.Instructor]: [
        {
            label: 'Approve Course Request',
            to: '/instructor/approve-course-request',
            icon: <IconFileCheck />
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
    [UserEnum.ISOStaff]: [
        {
            label: 'Upload Transcript',
            to: '/oisep-staff/transcript-upload',
            icon: <IconUpload />
        }
    ]

}

export const DEPARTMENTS = [
    {value: "CS" , label: 'Computer Science'},
    {value: "EEE", label: "Electrical & Electronics Engineering"},
    {value: "IE", label: "Industrial Engineering"},
    {value: "ME", label: "Mechanical Engineering"},
    {value: "ARCH", label: "Architecture"},
    {value: "COMD", label: "Communication and Design"},
    {value: "GRA", label: "Graphic Design"},
    {value: "IAED", label: "Interior Architecture and Environmental Design"},
    {value: "CTIS", label: "Information Systems and Technologies"},
    {value: "THM", label: "Tourism and Hotel Management",},
    {value: "FA", label: "Fine Arts",},
    {value: "LAUD", label: "Urban Design and Landscape Architecture",},
    {value: "ECON", label: "Economics"},
    {value: "HIST", label: "History"},
    {value: "MAN", label: "Management"},
    {value: "IR", label: "International Relations"},
    {value: "POLS", label: "Political Science and Public Administration"},
    {value: "PSYC", label: "Psychology"},
    {value: "ES", label: "Education Sciences",},
    {value: "AMER", label: "American Culture and Literature"},
    {value: "ELIT", label: "English Language and Literature"},
    {value: "PHIL", label: "Philosophy"},
    {value: "FRP", label: "Translation and Interpretation"},
    {value: "EDEB", label: "Turkish Literature"},
    {value: "LAW", label: "Law"},
    {value: "CHEM", label: "Chemistry"},
    {value: "MATH", label: "Mathematics"},
    {value: "MBG", label: "Molecular Biology and Genetics"},
    {value: "PHYS", label: "Physics"},
    {value: "MSC", label: "Music and Performing Arts"},
]

export const FACULTIES = [
    {value: "APPLIED_SCIENCES_FAC" , label: 'Faculty of Applied Sciences'},
    {value: "ART_DESIGN_AND_ARCH_FAC" , label: 'Faculty of Art, Design, and Architecture'},
    {value: "BUSSINESS_ADMINISTRATION_FAC" , label: 'Faculty of Business Administration'},
    {value: "ECON_ADMIN_AND_SOCIAL_SCIENCES_FAC" , label: 'Faculty of Economics, Administrative, and Social Sciences'},
    {value: "EDUCATION_FAC" , label: 'Faculty of Education'},
    {value: "ENGINEERING_FAC" , label: 'Faculty of Engineering'},
    {value: "HUM_AND_LETTERS_FAC" , label: 'Faculty of Humanities and Letters'},
    {value: "LAW_FAC" , label: 'Faculty of Law'},
    {value: "SCIENCE_FAC" , label: 'Faculty of Science'},
    {value: "MUSIC_FAC" , label: 'Faculty of Music and Performing Arts'},
]