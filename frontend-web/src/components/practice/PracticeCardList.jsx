import PracticeCard from "./PracticeCard";

function PracticeCardList({ practices, onDone, onDelete }) {
  if (practices.length === 0) {
    return (
      <div className="text-center py-[var(--space-2xl)] text-solen-muted">
        <p className="font-display text-[1.15rem] mb-[var(--space-md)] text-solen-fg">No practices yet</p>
        <div className="font-body text-[0.875rem] leading-[1.5] mb-[var(--space-lg)]">
          Start by adding a practice that matters to you.
        </div>
      </div>
    );
  }

  return (
    <div className="grid grid-cols-[repeat(auto-fill,minmax(280px,1fr))] gap-[var(--space-md)]">
      {practices.map((p) => (
        <PracticeCard key={p.id} practice={p} onDone={onDone} onDelete={onDelete} />
      ))}
    </div>
  );
}

export default PracticeCardList;
