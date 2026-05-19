import { useState, useEffect, useCallback } from "react";
import HabitAPI from "../apis/HabitAPI";
import CheckInAPI from "../apis/CheckInAPI";
import AuthHandler from "../apis/AuthHandler";
import MoodPicker from "../components/practice/MoodPicker";
import PracticeCardList from "../components/practice/PracticeCardList";
import CreatePracticeModal from "../components/practice/CreatePracticeModal";
import DeleteConfirmationDialog from "../components/practice/DeleteConfirmationDialog";
import CheckInPopup from "../components/checkin/CheckInPopup";

const dayNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

function formatDate(d) {
  const date = new Date(d);
  return `${dayNames[date.getDay()]} ${date.getDate()} ${monthNames[date.getMonth()]} ${date.getFullYear()}`;
}

function getGreeting() {
  const h = new Date().getHours();
  if (h < 12) return "Good morning";
  if (h < 18) return "Good afternoon";
  return "Good evening";
}

function DashboardPage() {
  const [practices, setPractices] = useState([]);
  const [mood, setMood] = useState(null);
  const [reflection, setReflection] = useState("");
  const [checkedInHidden, setCheckedInHidden] = useState(false);
  const [selectedHabitId, setSelectedHabitId] = useState(null);
  const [toast, setToast] = useState(null);

  const [showCreateModal, setShowCreateModal] = useState(false);
  const [newName, setNewName] = useState("");
  const [newDesc, setNewDesc] = useState("");
  const [newCategoryId, setNewCategoryId] = useState(null);

  const [deleteTarget, setDeleteTarget] = useState(null);

  const [popupHabit, setPopupHabit] = useState(null);

  const todayStr = formatDate(new Date());

  const showToast = (msg) => {
    setToast(msg);
    setTimeout(() => setToast(null), 2500);
  };

  const fetchPractices = useCallback(() => {
    HabitAPI.getHabitsByUser()
      .then(setPractices)
      .catch(() => {});
  }, []);

  useEffect(() => { fetchPractices(); }, [fetchPractices]);

  const handleDone = (habitId) => {
    const habit = practices.find((p) => p.id === habitId);
    setPopupHabit(habit || { id: habitId, name: "Practice" });
  };

  const handlePopupSave = async ({ habitId, mood, content, public: isPublic }) => {
    await CheckInAPI.create({ habitId, mood, content, public: isPublic });
    setPopupHabit(null);
    setMood(null);
    setReflection("");
    setSelectedHabitId(null);
    showToast("Check-in saved!");
    fetchPractices();
  };

  const handleCreate = () => {
    const userId = AuthHandler.getUserId();
    if (!userId) return;
    HabitAPI.createHabit({
      name: newName,
      description: newDesc,
      userId,
      categoryId: newCategoryId,
    })
      .then(() => {
        setShowCreateModal(false);
        setNewName("");
        setNewDesc("");
        setNewCategoryId(null);
        fetchPractices();
      })
      .catch(() => {});
  };

  const handleDeleteTarget = (practice) => setDeleteTarget(practice);

  const handleDeleteConfirm = () => {
    if (!deleteTarget) return;
    HabitAPI.deleteHabit(deleteTarget.id)
      .then(() => {
        setDeleteTarget(null);
        fetchPractices();
      })
      .catch(() => {});
  };

  const handleSaveCheckIn = () => {
    if (!mood || !selectedHabitId) return;
    const checkin = { mood, content: reflection, habitId: selectedHabitId, public: false };
    CheckInAPI.create(checkin)
      .then(() => {
        setMood(null);
        setReflection("");
        setSelectedHabitId(null);
        showToast("Check-in saved!");
        fetchPractices();
      })
      .catch(() => {});
  };

  return (
    <main className="page">
      <div className="dash-header animate-in">
        <span className="label" style={{ display: "block", marginBottom: "var(--space-sm)" }}>
          {todayStr}
        </span>
        <h1 className="display-lg">{getGreeting()}.</h1>
      </div>

      {!checkedInHidden && (
        <div className="dash-checkin-prompt animate-in animate-in-d1">
          <h2>How are you feeling today?</h2>

          <div className="checkin-flow-mood">
            <MoodPicker value={mood} onChange={setMood} />
          </div>

          <div className="checkin-habit-picker">
            <span className="checkin-habit-label">Which practice are you checking in for?</span>
            {practices.length === 0 ? (
              <p className="body-sm" style={{ color: "var(--muted)" }}>
                Create a practice first to check in.
              </p>
            ) : (
              <div className="checkin-habit-cards">
                {practices.map((p) => (
                  <button
                    key={p.id}
                    type="button"
                    className={`checkin-habit-card${selectedHabitId === p.id ? " selected" : ""}${p.checkedInToday ? " checked-in-today" : ""}`}
                    onClick={() => !p.checkedInToday && setSelectedHabitId(p.id)}
                    disabled={p.checkedInToday}
                  >
                    <span className="checkin-habit-card-name">{p.name}</span>
                    <span className="streak-indicator">
                      <svg viewBox="0 0 16 16" width="12" height="12">
                        <circle cx="8" cy="8" r="6" fill="oklch(68% 0.16 75 / 0.2)" />
                        <circle cx="8" cy="8" r="3" fill="var(--accent)" />
                      </svg>
                      {p.streak}d
                    </span>
                  </button>
                ))}
              </div>
            )}
          </div>

          <textarea
            className="dash-checkin-textarea"
            placeholder="What's on your mind today? (optional)"
            rows={2}
            value={reflection}
            onChange={(e) => setReflection(e.target.value)}
          />

          <div className="dash-checkin-actions">
            <button className="btn btn-primary" onClick={handleSaveCheckIn} disabled={!mood || !selectedHabitId}>
              Save check-in
            </button>
            <button className="btn btn-ghost" onClick={() => setCheckedInHidden(true)}>
              Skip for now
            </button>
          </div>
        </div>
      )}

      <div className="dash-section-label animate-in animate-in-d2">
        <h2>Your practices</h2>
        <button className="btn btn-ghost btn-sm" onClick={() => setShowCreateModal(true)}>
          + New practice
        </button>
      </div>

      <div className="animate-in animate-in-d3">
        <PracticeCardList
          practices={practices}
          onDone={handleDone}
          onDelete={handleDeleteTarget}
        />
      </div>

      <CreatePracticeModal
        isOpen={showCreateModal}
        onClose={() => setShowCreateModal(false)}
        onCreate={handleCreate}
        categoryId={newCategoryId}
        setCategoryId={setNewCategoryId}
        name={newName}
        setName={setNewName}
        description={newDesc}
        setDescription={setNewDesc}
      />

      <CheckInPopup
        isOpen={!!popupHabit}
        habitId={popupHabit?.id}
        habitName={popupHabit?.name}
        onSave={handlePopupSave}
        onClose={() => setPopupHabit(null)}
      />

      <DeleteConfirmationDialog
        isOpen={!!deleteTarget}
        practiceName={deleteTarget?.name || ""}
        onCancel={() => setDeleteTarget(null)}
        onConfirm={handleDeleteConfirm}
      />

      {toast && <div className="toast">{toast}</div>}
    </main>
  );
}

export default DashboardPage;
