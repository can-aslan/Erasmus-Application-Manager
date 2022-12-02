import { AppShell, ColorScheme, ColorSchemeProvider, MantineProvider } from "@mantine/core";
import { useHotkeys, useLocalStorage } from "@mantine/hooks";
import { Outlet } from "react-router-dom";
import { UserProvider } from "../provider/UserProvider";
import { User, UserEnum } from "../types";
import AppHeader from "./navbar/AppHeader";
import AppNavbar from "./navbar/AppNavbar";

const Layout = () => {
    const [colorScheme, setColorScheme] = useLocalStorage<ColorScheme>({
        key: 'mantine-color-scheme',
        defaultValue: 'light',
        getInitialValueInEffect: true,
      })
    
    const toggleColorScheme = (value?: ColorScheme) => {
    setColorScheme(value || colorScheme === 'light' ? 'dark' : 'light')
    }

    // Shortcut for toggling theme is: CTRL + J
    useHotkeys([['mod+J', () => toggleColorScheme()]])

    return (
        <UserProvider>
            <ColorSchemeProvider colorScheme={colorScheme} toggleColorScheme={toggleColorScheme}>
                <MantineProvider theme={{colorScheme}} withGlobalStyles withNormalizeCSS>
                    <main className="App">
                        <AppShell
                            padding="md"
                            navbar={<AppNavbar />}
                            header={<AppHeader/>}
                            styles={(theme) => ({
                                main: { backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0] },
                            })}
                        >
                            <Outlet />
                        </AppShell>
                    </main>
                </MantineProvider>
            </ColorSchemeProvider>
        </UserProvider>
    );
}
 
export default Layout;