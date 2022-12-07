import {
  QueryClient,
  QueryClientProvider
} from '@tanstack/react-query'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import PersistentLogin from './components/auth/PersistentLogin'
import RequireAuth from './components/auth/RequireAuth'
import Layout from './components/Layout'
import ProviderWrapper from './components/ProviderWrapper'
import './index.css'
import ApproveWishlistsPage from './pages/Coordinator/ApproveWishlistsPage'
import ApprovePreApprovalsPage from './pages/FACMember/ApprovePreApprovalsPage'
import MissingPage from './pages/Feedback/MissingPage'
import ForgotPasswordPage from './pages/ForgotPasswordPage'
import LoginPage from './pages/LoginPage'
import CourseRequestPage from './pages/OutgoingStudent/CourseRequestPage'
import CourseWishlistPage from './pages/OutgoingStudent/CourseWishlistPage'
import PreApprovalFormPage from './pages/OutgoingStudent/PreApprovalFormPage'
import { UserEnum } from './types'

const router = createBrowserRouter([
  {
    path: '/login',
    element: <LoginPage />
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
        element: <RequireAuth allowedUsers={[UserEnum.Admin, UserEnum.Coordinator, UserEnum.FACMember, UserEnum.IncomingStudent, UserEnum.IncomingStudent, UserEnum.OutgoingStudent]}/>,
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
                  }
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
                  }
                ]
              },
              {
                element: <RequireAuth allowedUsers={[UserEnum.Admin]} />,
                children: [
                  {
                    // Admin pages
                  }
                ]
              }
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
