## About the Frontend Setup

### Quick Start
1. To get started you need to clone this repository to your local workspace. After cloning, make sure you have already downloaded [npm](https://www.npmjs.com/package/npm).

2. If you already have npm, make sure you are in the `/frontend` folder.

3. Use `npm install` command on the terminal and npm will install all the dependencies.

### About Vite
We use [Vite](https://vitejs.dev/) during the development process. **Vite** will act as a local server during development, and will be the build tool when we decide to deploy the application.

index.html is the file that is served by Vite during development. It is the entry point of the application. No need to worry about this file and do not change this file in any way.

### During development
Use `npm run dev` command to start the development server. Vite will serve the application on a local endpoint. The endpoint can be accessed from the console.

Vite supports **Hot Module Replacement**. This means whenever you make some change on the code and save the file, Vite will automatically re-render (some magic is involved in here I assume) your page. 

### Dependencies
#### [React-Query](https://tanstack.com/query/v4/docs/overview)
We will use react-query to handle the interactions with the backend. react-query comes with ways to manage the server state that are not available in React.

#### [React-Router](https://v5.reactrouter.com/web/guides/quick-start)
react-router handles the client side routing. In an ideal world we would have used Next.js or Remix but we are not in an ideal world and I have no clue how to work with those frameworks. 

### Folder Structure
Most of our work will go under `/src`. `main.tsx` and `App.tsx` are the default files that must be in the folder in order for `React` to work. 

+ `/src/components` folder will include all the components we will use from forms to sidebars.

+ `/src/pages` folder will ideally include the pages we will render on specific routes.

+ `/src/hooks` and `/src/providers` are too long for me to describe here. Either watch a youtube video or come and ask me face-to-face.


### Config
Config is config. Just don't change any of the config files.