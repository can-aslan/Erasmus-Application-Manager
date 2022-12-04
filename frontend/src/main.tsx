import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import RequireAuth from './components/auth/RequireAuth'
import Layout from './components/Layout'
import ProviderWrapper from './components/ProviderWrapper'
import './index.css'
import ApproveWishlistsPage from './pages/Coordinator/ApproveWishlistsPage'
import ApprovePreApprovalsPage from './pages/FACMember/ApprovePreApprovalsPage'
import LoginPage from './pages/LoginPage'
import MissingPage from './pages/MissingPage'
import CourseRequestPage from './pages/OutgoingStudent/CourseRequestPage'
import PreApprovalFormPage from './pages/OutgoingStudent/PreApprovalFormPage'
import { UserEnum } from './types'

const router = createBrowserRouter([
  {
    path: '/login',
    element: <LoginPage />
  },
  {
    path: '/',
    element: <Layout />,
    errorElement: <MissingPage />,
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
])

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <ProviderWrapper>
      <RouterProvider router={router} />
    </ProviderWrapper>
  </React.StrictMode>
)
