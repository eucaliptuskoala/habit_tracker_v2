import React, {useEffect, useState } from 'react'
import HabitAPI from '../apis/HabitAPI';
import HabitTemplateAPI from '../apis/HabitTemplateAPI';
import Habit from '../components/habit/Habit';
import NoteModalWrapper from '../components/note/NoteModalWrapper';
import AuthHandler from '../apis/AuthHandler';
import NoteAPI from '../apis/NoteAPI';
import EmptyHabitsState from '../components/habit/EmptyHabitsState';
import Toasts from '../components/visual/Toasts';
import CreateHabitModal from '../components/habit/CreateHabitModal';

function DashboardPage() {

  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const [habits, setHabits] = useState([]);
  const [habitTemplates, setHabitTemplates] = useState([]);
  const [popularHabitTemplates, setPopularHabitTemplates] = useState([]);
  const [createHabitRequest, setCreateHabitRequest] = useState({
    name: "",
    description: "",
    userId: null,
    templateId: null
  });

  const [createNoteRequest, setCreateNoteRequest] = useState({
    title: "",
    content: "",
    personalFeeling: 0,
    isPublic: false,
    habitId: null,
  });

  const [noteModalOpen, setNoteModalOpen] = useState(false);
  const [noteHabitId, setNoteHabitId] = useState(null);

  const userId = AuthHandler.getUserId();

  useEffect(() =>{
    HabitAPI.getHabitsByUser()
      .then(data => {
        setHabits(data);
        console.log(data);
      }).catch(error => {
        if(error.response){
          setErrorMessage(error.response.data);
        }
        else{
          setErrorMessage("Failed to fetch habits.");
        }
    });

    HabitTemplateAPI.getAllHabitTemplates()
      .then(data => {
        setHabitTemplates(data);
        setPopularHabitTemplates(data);
      })

    HabitTemplateAPI.getPopularHabitTemplates()
      .then(data => {
        setPopularHabitTemplates(data);
      })
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

  const handleCreateHabit = (createHabitRequest) => {
    HabitAPI.createHabit({
      name: createHabitRequest.name,
      description: createHabitRequest.description,
      userId: userId,
      templateId: createHabitRequest.id
    })
      .then(data => {
        setHabits(prev => [...prev, data]);
        setMessage("Habit created successfully!");
      }).catch(error => {
        if(error.response){
          setErrorMessage(error.response.data);
        }
        else{
          setErrorMessage("Failed to create habit.");
        }});
    }

  const handleCreateNote = () => {
    NoteAPI.createNote(createNoteRequest)
      .then((response) => {
        if(response){
          setMessage("Note created successfully!");
          setCreateNoteRequest({
              title: "",
              content: "",
              personalFeeling: 0,
              isPublic: false,
              habitId: null,
            });
          setNoteModalOpen(false);
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
  }

  const handleUpdateStreak = (id) => {
    HabitAPI.updateStreak(id)
      .then(response => {
        const updatedHabit = response.data;
        setHabits(prev => prev.map(h => h.id === id ? updatedHabit : h));
        setMessage("Habit streak updated successfully!");

        setCreateNoteRequest(prev => ({
        ...prev,
        habitId: id,
        title: "",
        content: "",
        personalFeeling: 0,
        isPublic: false,
      }));
      setNoteHabitId(id);
      setNoteModalOpen(true);
      
      }).catch(error => {
        if(error.response){
          setErrorMessage(error.response.data);
        }
        else{
          setErrorMessage("Failed to update habit streak.");
        }});
  }

  const handleDeleteHabit = (id) => {
    HabitAPI.deleteHabit(id)
    .then(() =>
      {setHabits(prev => prev.filter(h => h.id !== id))
        setMessage("Habit deleted successfully!");
      }).catch(error => {
        if(error.response){
          setErrorMessage(error.response.data);
        }
        else{
          setErrorMessage("Failed to delete habit.");
        }});
  }

  return (
    <>
      <div className="px-6 py-6 md:px-12 md:py-10">
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-2xl font-bold text-gray-800">Your Habits</h1>
            <CreateHabitModal
              habitTemplates={habitTemplates}
              popularHabitTemplates={popularHabitTemplates}
              handleCreateHabit={handleCreateHabit}
            />
        </div>
        <main className="max-w-7xl mx-auto px-6 py-10">
          {habits.length === 0 ? (
            <EmptyHabitsState />
          ) : (
            <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
              {habits.map(habit => (
                <Habit
                  key={habit.id}
                  habit={habit}
                  handleUpdate={handleUpdateStreak}
                  handleDelete={handleDeleteHabit}
                />
              ))}
            </div>
          )}
        </main>
      </div>

      <NoteModalWrapper
        isOpen={noteModalOpen}
        onClose={() => setNoteModalOpen(false)}
        habitId={noteHabitId}
        createNoteRequest={createNoteRequest}
        setCreateNoteRequest={setCreateNoteRequest}
        handleCreateNote={handleCreateNote}
        userHabits={habits}
      />

      <Toasts message={message} errorMessage={errorMessage} />
      {errorMessage && (
        <div className="bg-red-100 text-red-700 p-2 rounded mb-4 relative">
          <span>{errorMessage}</span>
          <button
            onClick={() => setErrorMessage("")}
            className="font-bold px-2 absolute top-3 right-3"
          >
            ×
          </button>
        </div>
      )}
      {message && (
        <div className="bg-green-100 text-green-700 p-2 rounded mb-4">
          <span>{message}</span>
          <button
            onClick={() => setMessage("")}
            className="font-bold px-2 absolute top-3 right-3"
          >
            ×
          </button>
        </div>
      )}
  </>
  );
}

export default DashboardPage;