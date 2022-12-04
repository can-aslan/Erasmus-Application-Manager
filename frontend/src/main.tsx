import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import RequireAuth from './components/auth/RequireAuth'
import Layout from './components/Layout'
import ProviderWrapper from './components/ProviderWrapper'
import './index.css'
import LoginPage from './pages/LoginPage'
import MissingPage from './pages/MissingPage'
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
            
          }
        ]
      },
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
