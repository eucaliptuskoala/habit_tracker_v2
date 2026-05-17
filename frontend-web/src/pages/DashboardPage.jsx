import { useState, useEffect, useCallback } from "react";
import HabitAPI from "../apis/HabitAPI";
import CheckInAPI from "../apis/CheckInAPI";
import AuthHandler from "../apis/AuthHandler";
import MoodPicker from "../components/practice/MoodPicker";
import PracticeCardList from "../components/practice/PracticeCardList";
import CreatePracticeModal from "../components/practice/CreatePracticeModal";
import DeleteConfirmationDialog from "../components/practice/DeleteConfirmationDialog";

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
  const [checkedIn, setCheckedIn] = useState(false);
  const [checkedInHidden, setCheckedInHidden] = useState(false);

  const [showCreateModal, setShowCreateModal] = useState(false);
  const [newName, setNewName] = useState("");
  const [newDesc, setNewDesc] = useState("");
  const [newCategoryId, setNewCategoryId] = useState(null);

  const [deleteTarget, setDeleteTarget] = useState(null);

  const todayStr = formatDate(new Date());

  const fetchPractices = useCallback(() => {
    HabitAPI.getHabitsByUser()
      .then(setPractices)
      .catch(() => {});
  }, []);

  useEffect(() => { fetchPractices(); }, [fetchPractices]);

  const handleDone = (habitId) => {
    HabitAPI.updateStreak(habitId)
      .then(() => fetchPractices())
      .catch(() => {});
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
    if (!mood) return;
    const checkin = { mood, content: reflection };
    CheckInAPI.create(checkin)
      .then(() => {
        setCheckedIn(true);
        setCheckedInHidden(false);
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

      {!checkedInHidden && !checkedIn && (
        <div className="dash-checkin-prompt animate-in animate-in-d1">
          <h2>How are you feeling today?</h2>

          <div className="checkin-flow-mood">
            <MoodPicker value={mood} onChange={setMood} />
          </div>

          <textarea
            className="dash-checkin-textarea"
            placeholder="What's on your mind today? (optional)"
            rows={2}
            value={reflection}
            onChange={(e) => setReflection(e.target.value)}
          />

          <div className="dash-checkin-actions">
            <button className="btn btn-primary" onClick={handleSaveCheckIn}>
              Save check-in
            </button>
            <button className="btn btn-ghost" onClick={() => setCheckedInHidden(true)}>
              Skip for now
            </button>
          </div>
        </div>
      )}

      {checkedIn && (
        <div className="checked-in-state visible">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
            <path d="M20 6L9 17l-5-5" />
          </svg>
          <span>Today's check-in saved.</span>
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

      <DeleteConfirmationDialog
        isOpen={!!deleteTarget}
        practiceName={deleteTarget?.name || ""}
        onCancel={() => setDeleteTarget(null)}
        onConfirm={handleDeleteConfirm}
      />
    </main>
  );
}

export default DashboardPage;
