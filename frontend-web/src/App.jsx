import { Routes, Route } from 'react-router-dom'
import NavBar from './components/nav/NavBar'
import LandingPage from './pages/LandingPage'
import DashboardPage from './pages/DashboardPage'
import CheckInsPage from './pages/CheckInsPage'
import SignUpPage from './pages/SignUpPage'
import SignInPage from './pages/SignInPage'
import InspirePage from './pages/InspirePage'
import ProgressPage from './pages/ProgressPage'

function App() {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/checkins" element={<CheckInsPage />} />
        <Route path="/sign-up" element={<SignUpPage />} />
        <Route path="/sign-in" element={<SignInPage />} />
        <Route path="/inspire" element={<InspirePage />} />
        <Route path="/progress" element={<ProgressPage />} />
      </Routes>
    </>
  )
}

export default App
