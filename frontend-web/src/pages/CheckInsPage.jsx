import { useState, useEffect, useCallback } from "react";
import CheckInAPI from "../apis/CheckInAPI";
import CheckInTimeline from "../components/checkin/CheckInTimeline";
import EditCheckInModal from "../components/checkin/EditCheckInModal";
import CheckInPopup from "../components/checkin/CheckInPopup";

function CheckInsPage() {
  const [entries, setEntries] = useState([]);
  const [editEntry, setEditEntry] = useState(null);
  const [showEdit, setShowEdit] = useState(false);
  const [showNew, setShowNew] = useState(false);
  const [toast, setToast] = useState(null);

  const showToast = (msg) => {
    setToast(msg);
    setTimeout(() => setToast(null), 2500);
  };

  const fetchEntries = useCallback(() => {
    CheckInAPI.getAll()
      .then(setEntries)
      .catch(() => {});
  }, []);

  useEffect(() => { fetchEntries(); }, [fetchEntries]);

  const handleEdit = (entry) => {
    setEditEntry(entry);
    setShowEdit(true);
  };

  const handleSave = (id, request) => {
    CheckInAPI.update(id, request)
      .then((updated) => {
        setEntries((prev) => prev.map((e) => (e.id === id ? updated : e)));
        setShowEdit(false);
        setEditEntry(null);
      })
      .catch(() => {});
  };

  const handleDelete = (entry) => {
    if (!window.confirm(`Delete this check-in?`)) return;
    CheckInAPI.delete(entry.id)
      .then(() => {
        setEntries((prev) => prev.filter((e) => e.id !== entry.id));
      })
      .catch(() => {});
  };

  const handleNewCheckIn = async ({ habitId, mood, content, public: isPublic }) => {
    await CheckInAPI.create({ habitId, mood, content, public: isPublic });
    setShowNew(false);
    showToast("Check-in saved!");
    fetchEntries();
  };

  return (
    <main className="page">
      <CheckInTimeline entries={entries} onEdit={handleEdit} onDelete={handleDelete} onCreate={() => setShowNew(true)} />

      <EditCheckInModal
        isOpen={showEdit}
        entry={editEntry}
        onSave={handleSave}
        onClose={() => { setShowEdit(false); setEditEntry(null); }}
      />

      <CheckInPopup
        isOpen={showNew}
        habitId={null}
        habitName={null}
        onSave={handleNewCheckIn}
        onClose={() => setShowNew(false)}
      />

      {toast && <div className="toast">{toast}</div>}
    </main>
  );
}

export default CheckInsPage;
