import React, {useEffect, useState} from "react";
import CreateNoteModal from "../components/note/CreateNoteModal";
import NoteAPI from "../apis/NoteAPI";
import HabitAPI from "../apis/HabitAPI";
import Note from "../components/note/Note";
import UpdateNoteForm from "../components/note/UpdateNoteForm";

function NotesPage(){

    const [message, setMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const [userHabits, setUserHabits] = useState([]);

    const [createNoteRequest, setCreateNoteRequest] = useState({
        title: "",
        content: "",
        personalFeeling: 0,
        isPublic: false,
        habitId: null,
    });

    const [notes, setNotes] = useState([]);

    const [updateNoteRequest, setUpdateNoteRequest] = useState({
        title: "",
        content: "",
        personalFeeling: 0,
        isPublic: false,
    });

    const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
    const [selectedNote, setSelectedNote] = useState(null);

    useEffect(() => {
        const fetchUserHabits = () => {
            HabitAPI.getHabitsByUser()
                .then(data => {
                    setUserHabits(data);
                    console.log("Fetched habits:", data);
                }).catch(error => {
                    if(error.response){
                        setErrorMessage(error.response.data);
                    }
                    else{
                        setErrorMessage("Failed to fetch habits.");
                    }
                });
        };

        const fetchNotes = () => {
            NoteAPI.getNotes()
                .then(data => {
                    setNotes(data);
                })
                .catch(error => {
                    if(error.response){
                        setErrorMessage(error.response.data);
                    }
                    else{
                        setErrorMessage("Failed to fetch notes.");
                    }
                });
        };

        fetchUserHabits();
        fetchNotes();

    }, []);

    useEffect(() => {
        if(message || errorMessage){
          const timer = setTimeout(() => {
            setMessage("");
            setErrorMessage("");
          }, 3000);
          return () => clearTimeout(timer);
        }
    }, [message, errorMessage]);

    const handleCreateNote = () => {
        NoteAPI.createNote(createNoteRequest)
            .then((response) => {
                if(response){
                    setMessage("Note created successfully!");
                    setNotes(prev => [...prev, response]);

                    setCreateNoteRequest({
                        title: "",
                        content: "",
                        personalFeeling: 0,
                        isPublic: false,
                        habitId: null,
                    });
                }
            })
            .catch((error) => {
                if(error.response){
                    setErrorMessage(error.response.data);
                }
                else{
                    setErrorMessage("Failed to create note.");
                }
            });
    };

    const openUpdateModal = (note) => {
        setSelectedNote(note);
        setUpdateNoteRequest({
        title: note.title ?? "",
        content: note.content ?? "",
        personalFeeling: note.personalFeeling ?? 0,
        isPublic: note.isPublic ?? false,
        });
        setIsUpdateModalOpen(true);
    };


    const handleUpdateNote = (updateNoteRequest, noteId) => {
        NoteAPI.updateNote(updateNoteRequest, noteId)
            .then((response) => {
                console.log("Update response:", response);

                setNotes(prevNotes =>
                    prevNotes.map(note =>
                    note.id === response.id ? response: note
                    )
                );
                if(response){
                    setMessage("Note updated successfully!");
                }
            })
            .catch((error) => {
                if(error.response){
                    setErrorMessage(error.response.data);
                }
                else{
                    setErrorMessage("Failed to update note.");
                }
            });
    };

    const handleDeleteNote = (noteId) => {
        NoteAPI.deleteNote(noteId)
            .then(() => {
                setNotes(prevNotes => prevNotes.filter(note => note.id !== noteId));
                setMessage("Note deleted successfully!");
            })
            .catch((error) => {
                if(error.response){
                    setErrorMessage(error.response.data);
                }
                else{
                    setErrorMessage("Failed to delete note.");
                }
            });
    }

    return (
    <div className="px-6 py-6 md:px-12 md:py-10">
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Your Notes</h1>
        <CreateNoteModal
          handleCreateNote={handleCreateNote}
          userHabits={userHabits}
          createNoteRequest={createNoteRequest}
          setCreateNoteRequest={setCreateNoteRequest}
        />
      </div>
      <br></br>

      {errorMessage && (
        <div className="relative bg-red-100 text-red-700 p-3 rounded-lg mb-4 border border-red-200">
          <span>{errorMessage}</span>
          <button
            onClick={() => setErrorMessage("")}
            className="absolute top-2 right-2 font-bold text-lg hover:text-red-900"
          >
            ×
          </button>
        </div>
      )}
      {message && (
        <div className="relative bg-green-100 text-green-700 p-3 rounded-lg mb-4 border border-green-200">
          <span>{message}</span>
          <button
            onClick={() => setMessage("")}
            className="absolute top-2 right-2 font-bold text-lg hover:text-green-900"
          >
            ×
          </button>
        </div>
      )}

      {notes.length === 0 ? (
        <p className="text-center mt-20 text-gray-500 text-lg">No notes yet. Create one to get started!</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {notes.map(note => (
            <Note
              key={note.id}
              note={note}
              onEdit={openUpdateModal}
              handleDeleteNote={handleDeleteNote}
            />
          ))}
        </div>
      )}

      {isUpdateModalOpen && selectedNote && (
        <UpdateNoteForm
          note={selectedNote}
          updateNoteRequest={updateNoteRequest}
          setUpdateNoteRequest={setUpdateNoteRequest}
          handleUpdateNote={handleUpdateNote}
          onClose={() => setIsUpdateModalOpen(false)}
        />
      )}
    </div>
  );
}

export default NotesPage