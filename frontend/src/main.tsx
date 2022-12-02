import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Layout from './components/Layout'
import './index.css'
import MissingPage from './pages/MissingPage'

const router = createBrowserRouter([
  // {
  //   path: '/login',
  //   element: <LoginPage />
  // },
  {
    path: '/',
    element: <Layout />,
    errorElement: <MissingPage />,
    children: [
      {
        path: 'hey',
        element: <div>test</div>
      }
    ]
  }
])


ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
)
