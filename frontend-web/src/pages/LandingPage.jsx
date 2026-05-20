import { Link } from "react-router-dom";
import SunIllustration from "../components/landing/SunIllustration";

function LandingPage() {
  return (
    <>
      <main className="max-w-[1280px] mx-auto px-[var(--gutter)] py-[var(--space-xl)]">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-[var(--space-2xl)] md:gap-[var(--space-2xl)] items-center min-h-[80vh] py-16 relative">
          <div className="max-w-[540px] animate-[fade-in_0.5s_ease_both]">
            <div className="font-mono text-[0.75rem] tracking-[0.1em] uppercase text-solen-accent mb-[var(--space-lg)]">Solen</div>
            <h1 className="font-display text-[length:var(--fs-hero)] leading-[1.1] tracking-[-0.02em] font-[400] text-solen-fg mb-[var(--space-lg)]">
              Gentle care,<br />daily.
            </h1>
            <p className="text-[length:var(--fs-sub)] leading-[1.7] text-solen-muted mb-[var(--space-xl)] max-w-[440px]">
              A warm space for your personal check-ins and quiet reflections.
              No streaks to keep, no graphs to chase — just a moment with yourself, every day.
            </p>
            <div className="flex flex-col items-start md:flex-row md:items-center gap-4">
              <Link to="/sign-up" className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-[var(--color-solen-surface)] bg-solen-accent border border-solen-accent no-underline hover:bg-solen-accent-glow hover:border-solen-accent-glow hover:shadow-[0_0_24px_oklch(78%_0.18_80_/_0.25)] transition-all duration-200">
                Begin your practice
              </Link>
              <Link to="/sign-in" className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-solen-fg no-underline hover:bg-solen-surface-soft transition-all duration-200">
                Sign in
              </Link>
            </div>
            <p className="font-mono text-[0.7rem] text-solen-muted mt-[var(--space-md)] opacity-60">Free. No account needed to browse.</p>
          </div>

          <SunIllustration />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-[var(--space-xl)] py-[var(--space-2xl)] border-t border-solen-border">
          <div className="font-mono text-[0.7rem] tracking-[0.08em] uppercase text-solen-muted leading-[1.6]">
            <strong className="block font-display text-[1.1rem] tracking-[0] not-uppercase text-solen-fg mb-[var(--space-sm)]">Reflect, not track</strong>
            A check-in is a moment of presence, not a data point. Your reflections belong to you.
          </div>
          <div className="font-mono text-[0.7rem] tracking-[0.08em] uppercase text-solen-muted leading-[1.6]">
            <strong className="block font-display text-[1.1rem] tracking-[0] not-uppercase text-solen-fg mb-[var(--space-sm)]">Your rhythm</strong>
            No streaks, no pressure. Practices adapt to your life, not the other way around.
          </div>
          <div className="font-mono text-[0.7rem] tracking-[0.08em] uppercase text-solen-muted leading-[1.6]">
            <strong className="block font-display text-[1.1rem] tracking-[0] not-uppercase text-solen-fg mb-[var(--space-sm)]">Warm by design</strong>
            Sun-washed colours, generous space, quiet typography. Built to feel like morning light, not a dashboard.
          </div>
        </div>
      </main>

      <footer className="text-center py-[var(--space-xl)] border-t border-solen-border font-mono text-[0.7rem] text-solen-muted">
        Solen &mdash; gentle care, daily
      </footer>
    </>
  );
}

export default LandingPage;
