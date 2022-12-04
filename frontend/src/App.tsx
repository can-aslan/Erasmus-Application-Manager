import { Route, Routes } from "react-router-dom";
import LoginForm from "./components/auth/LoginForm";
import RequireAuth from "./components/auth/RequireAuth";
import Layout from "./components/Layout";
import LoginPage from "./pages/LoginPage";
import MissingPage from "./pages/MissingPage";
import Unauthorized from "./pages/UnauthorizedPage";
import { UserEnum } from "./types";

const App = () => {
    return (
        <Routes>
            {/* Public pages */}
            <Route path="login" element={<LoginPage />} />
            <Route path='unauthorized' element={<Unauthorized />} />

            {/* Pages wrapped with app shell */}
            <Route path="/" element={<Layout />}>
                {/* Protected routes */}
                <Route element={<RequireAuth allowedUsers={[UserEnum.OutgoingStudent]}/>}>
                    <Route path="test" element={<LoginForm />} />
                </Route>
            </Route>


            {/* Page does not exist */}
            <Route path="*" element={<MissingPage />} />
        </Routes>
    );
}
 
export default App;