import { useState, useEffect, useCallback } from "react";
import CheckInAPI from "../apis/CheckInAPI";
import InspireCard from "../components/inspire/InspireCard";
import InspireCardNavigator from "../components/inspire/InspireCardNavigator";

function InspirePage() {
  const [entries, setEntries] = useState([]);
  const [view, setView] = useState("feed");

  const fetchEntries = useCallback(() => {
    CheckInAPI.getFyp()
      .then(setEntries)
      .catch((err) => console.error("Failed to fetch entries", err));
  }, []);

  useEffect(() => { fetchEntries(); }, [fetchEntries]);

  return (
    <main className="page">
      <div className="inspire-header animate-in">
        <div>
          <span className="label" style={{ display: "block", marginBottom: "var(--space-sm)" }}>
            Community
          </span>
          <h1 className="display-lg">Inspire</h1>
        </div>
        <div className="view-toggle">
          <button
            className={view === "feed" ? "active" : ""}
            onClick={() => setView("feed")}
          >
            Feed
          </button>
          <button
            className={view === "card" ? "active" : ""}
            onClick={() => setView("card")}
          >
            Card
          </button>
        </div>
      </div>

      {entries.length === 0 ? (
        <div className="inspire-empty">
          <p>No stories yet</p>
          <div className="body-sm" style={{ color: "var(--muted)" }}>
            Be the first to share a public check-in.
          </div>
        </div>
      ) : view === "feed" ? (
        <div className="inspire-grid animate-in animate-in-d1">
          {entries.map((entry) => (
            <InspireCard key={entry.id} entry={entry} />
          ))}
        </div>
      ) : (
        <div className="animate-in animate-in-d1">
          <InspireCardNavigator entries={entries} />
        </div>
      )}
    </main>
  );
}

export default InspirePage;
