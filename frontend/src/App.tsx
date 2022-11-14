import { Center, ColorScheme, ColorSchemeProvider, MantineProvider, Text } from '@mantine/core';
import { useHotkeys, useLocalStorage } from '@mantine/hooks';
import ToggleThemeIcon from './components/ToggleThemeIcon';

function App() {
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
    <ColorSchemeProvider colorScheme={colorScheme} toggleColorScheme={toggleColorScheme}>
      <MantineProvider theme={{colorScheme}} withGlobalStyles withNormalizeCSS>
        <Center>
          <ToggleThemeIcon />
        </Center>
      </MantineProvider>
    </ColorSchemeProvider>
  );
}

export default App
