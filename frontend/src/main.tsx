import {
  QueryClient,
  QueryClientProvider
} from '@tanstack/react-query'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import 'react-toastify/dist/ReactToastify.css'
import PersistentLogin from './components/auth/PersistentLogin'
import RequireAuth from './components/auth/RequireAuth'
import Layout from './components/Layout'
import ProviderWrapper from './components/ProviderWrapper'
import './index.css'
import RegisterPage from './pages/Admin/RegisterPage'
import ApproveLearningAgreementPage from './pages/Coordinator/ApproveLearningAgreementPage'
import CoordinatorApprovePreApprovalsPage from './pages/Coordinator/ApprovePreApprovalsPage'
import ApproveWishlistsPage from './pages/Coordinator/ApproveWishlistsPage'
import CourseTransferPage from './pages/Coordinator/CourseTransferPage'
import EvaluateCoursesPage from './pages/ExperiencedStudent/EvaluateCoursesPage'
import EvaluateUniversityPage from './pages/ExperiencedStudent/EvaluateUniversityPage'
import ApprovePreApprovalsPage from './pages/FACMember/ApprovePreApprovalsPage'
import MissingPage from './pages/Feedback/MissingPage'
import Unauthorized from './pages/Feedback/UnauthorizedPage'
import ForgotPasswordPage from './pages/ForgotPasswordPage'
import ApproveCourseRequestPage from './pages/Instructor/ApproveCourseRequestPage'
import LoginPage from './pages/LoginPage'
import TranscriptUploadPage from './pages/OISEPStaff/TranscriptUploadPage'
import CourseRequestPage from './pages/OutgoingStudent/CourseRequestPage'
import CourseWishlistPage from './pages/OutgoingStudent/CourseWishlistPage'
import LearningAgreementPage from './pages/OutgoingStudent/LearningAgreementPage'
import PreApprovalFormPage from './pages/OutgoingStudent/PreApprovalFormPage'
import UniversitiesPage from './pages/OutgoingStudent/UniversitiesPage'
import UniversityDetails from './pages/OutgoingStudent/UniversityDetailsPage'
import { UserEnum } from './types'
const router = createBrowserRouter([
  {
    path: '/login',
    element: <LoginPage />
  },
  {
    path: '/unauthorized',
    element: <Unauthorized />
  },
  {
    path: '/forgot-password',
    element: <ForgotPasswordPage />
  },
  {
    element: <PersistentLogin />,
    errorElement: <MissingPage />,
    children: [
      {
        element: <RequireAuth allowedUsers={[
          UserEnum.Admin, 
          UserEnum.Coordinator, 
          UserEnum.FACMember, 
          UserEnum.IncomingStudent, 
          UserEnum.IncomingStudent, 
          UserEnum.OutgoingStudent, 
          UserEnum.Instructor, 
          UserEnum.ExperiencedStudent,
          UserEnum.OISEPStaff,
        ]} />,
        children: [
          {
            path: '/',
            element: <Layout />,
            children: [
              {
                element: <RequireAuth allowedUsers={[UserEnum.OutgoingStudent]} />,
                children: [
                  {
                    path: '/student/pre-approval-form',
                    element: <PreApprovalFormPage />
                  },
                  {
                    path: '/student/course-request',
                    element: <CourseRequestPage />
                  },
                  {
                    path: '/student/course-wishlist',
                    element: <CourseWishlistPage />
                  },
                  {
                    path: '/student/universities',
                    element: <UniversitiesPage />
                  },
                  {
                    path: '/student/universities/:universityId',
                    element: <UniversityDetails />
                  },
                  {
                    path: '/student/learning-agreement',
                    element: <LearningAgreementPage/>
                  },
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.IncomingStudent]} />,
                children: [
                  {
                    // Incoming student pages
                  }
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.FACMember]} />,
                children: [
                  {
                    path: '/fac-member/approve-pre-approvals',
                    element: <ApprovePreApprovalsPage />
                  }
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.Coordinator]} />,
                children: [
                  {
                    path: '/coordinator/student-wishlists',
                    element: <ApproveWishlistsPage />
                  },
                  {
                    path: '/coordinator/course-transfer-form',
                    element: <CourseTransferPage />
                  },
                  {
                    path: '/coordinator/approve-pre-approvals',
                    element: <CoordinatorApprovePreApprovalsPage/>
                  },
                  {
                    path: '/coordinator/approve-learning-agreements',
                    element: <ApproveLearningAgreementPage/>
                  }
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.Admin]} />,
                children: [
                  {
                    path: '/admin/register-page',
                    element: <RegisterPage />
                  }
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.Instructor]} />,
                children: [
                  {
                    // Instructor pages
                    path: '/instructor/approve-course-request',
                    element: <ApproveCourseRequestPage />
                  }
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.ExperiencedStudent]} />,
                children: [
                  {
                    path: '/experienced-student/evaluate-university',
                    element: <EvaluateUniversityPage />
                  },
                  {
                    path: '/experienced-student/evaluate-courses',
                    element: <EvaluateCoursesPage />
                  }
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.OISEPStaff]} />,
                children: [
                  {
                    path: '/oisep-staff/transcript-upload',
                    element: <TranscriptUploadPage />
                  },
                ]
              },
            ]
          }
        ]
      }
    ]
  }
])

const queryClient = new QueryClient()

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <ProviderWrapper>
        <RouterProvider router={router} />
      </ProviderWrapper>
    </QueryClientProvider>
  </React.StrictMode>
)
