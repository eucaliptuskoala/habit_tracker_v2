import PracticeCard from "./PracticeCard";

function PracticeCardList({ practices, onDone, onDelete }) {
  if (practices.length === 0) {
    return (
      <div className="empty-practices">
        <p>No practices yet</p>
        <div className="body-sm" style={{ marginBottom: "var(--space-lg)" }}>
          Start by adding a practice that matters to you.
        </div>
      </div>
    );
  }

  return (
    <div className="practice-grid">
      {practices.map((p) => (
        <PracticeCard key={p.id} practice={p} onDone={onDone} onDelete={onDelete} />
      ))}
    </div>
  );
}

export default PracticeCardList;
