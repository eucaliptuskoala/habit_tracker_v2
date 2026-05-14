import { Routes, Route, } from 'react-router-dom'
import './index.css'
import DashboardPage from './pages/DashboardPage'
import NotesPage from './pages/NotesPage'
import Header from './components/visual/Header'
import SignUpPage from './pages/SignUpPage'
import TitlePage from './pages/TitlePage'
import SignInPage from './pages/SignInPage'
import ForYouPage from './pages/ForYouPage'
import ProgressPage from './pages/ProgressPage'

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<TitlePage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/notes" element={<NotesPage />} />
        <Route path="/sign-up" element={<SignUpPage />} />
        <Route path="/sign-in" element={<SignInPage />} />
        <Route path="/fyp" element={<ForYouPage />} />
        <Route path="/progress" element={<ProgressPage />} />
      </Routes>
    </>
  )
}

export default App