import { useState, useEffect, useCallback } from "react";
import CheckInAPI from "../apis/CheckInAPI";
import CheckInTimeline from "../components/checkin/CheckInTimeline";
import EditCheckInModal from "../components/checkin/EditCheckInModal";

function CheckInsPage() {
  const [entries, setEntries] = useState([]);
  const [editEntry, setEditEntry] = useState(null);
  const [showEdit, setShowEdit] = useState(false);

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

  return (
    <main className="page">
      <CheckInTimeline entries={entries} onEdit={handleEdit} onDelete={handleDelete} />

      <EditCheckInModal
        isOpen={showEdit}
        entry={editEntry}
        onSave={handleSave}
        onClose={() => { setShowEdit(false); setEditEntry(null); }}
      />
    </main>
  );
}

export default CheckInsPage;
