import { AppShell, Group, Title } from "@mantine/core";
import { Outlet } from "react-router-dom";
import AppHeader from "./navbar/AppHeader";
import AppNavbar from "./navbar/AppNavbar";

const Layout = () => {

    return (      
        <main className="App">
            <AppShell
                padding="md"
                navbar={<AppNavbar />}
                header={<AppHeader/>}
                styles={(theme) => ({
                    main: { backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0] },
                })}
            >
                <Group position="center">
                    <Title>Welcome to Beam</Title>
                </Group>
                <Outlet />
            </AppShell>
        </main>
    );
}
 
export default Layout;