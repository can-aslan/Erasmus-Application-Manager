import { MantineProvider, Text } from '@mantine/core';

function App() {
  return (
    <MantineProvider withGlobalStyles withNormalizeCSS>
      <Text>Welcome to Mantine!</Text>
    </MantineProvider>
  );
}

export default App
