import { Link } from "react-router-dom";
import SunIllustration from "../components/landing/SunIllustration";

function LandingPage() {
  return (
    <>
      <main className="page">
        <div className="landing-hero">
          <div className="landing-hero-content animate-in">
            <div className="landing-hero-kicker">Solen</div>
            <h1 className="display-xl landing-hero-title">
              Gentle care,<br />daily.
            </h1>
            <p className="landing-hero-sub">
              A warm space for your personal check-ins and quiet reflections.
              No streaks to keep, no graphs to chase — just a moment with yourself, every day.
            </p>
            <div className="landing-hero-cta">
              <Link to="/sign-up" className="btn btn-primary">
                Begin your practice
              </Link>
              <Link to="/sign-in" className="btn btn-ghost">
                Sign in
              </Link>
            </div>
            <p className="landing-hero-note">Free. No account needed to browse.</p>
          </div>

          <SunIllustration />
        </div>

        <div className="landing-values">
          <div className="landing-value">
            <strong>Reflect, not track</strong>
            A check-in is a moment of presence, not a data point. Your reflections belong to you.
          </div>
          <div className="landing-value">
            <strong>Your rhythm</strong>
            No streaks, no pressure. Practices adapt to your life, not the other way around.
          </div>
          <div className="landing-value">
            <strong>Warm by design</strong>
            Sun-washed colours, generous space, quiet typography. Built to feel like morning light, not a dashboard.
          </div>
        </div>
      </main>

      <footer className="landing-footer">
        Solen &mdash; gentle care, daily
      </footer>
    </>
  );
}

export default LandingPage;
