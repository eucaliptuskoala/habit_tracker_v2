import React, { useEffect, useState } from "react";
import NoteAPI from "../apis/NoteAPI";
import NoteFYP from "../components/fyp/NoteFYP";

function ForYouPage() {
  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const [notes, setNotes] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0); // Track which note to show

  useEffect(() => {
    NoteAPI.getForYouContent()
      .then((data) => {
        setNotes(data);
        setCurrentIndex(0);
      })
      .catch((error) => {
        if (error.response) setErrorMessage(error.response.data);
        else setErrorMessage("Failed to fetch notes.");
      });
  }, []);

  useEffect(() => {
    if (message || errorMessage) {
      const timer = setTimeout(() => {
        setMessage("");
        setErrorMessage("");
      }, 3000);
      return () => clearTimeout(timer);
    }
  }, [message, errorMessage]);

  const handlePrev = () => {
    setCurrentIndex((prev) => (prev > 0 ? prev - 1 : prev));
  };

  const handleNext = () => {
    setCurrentIndex((prev) => (prev < notes.length - 1 ? prev + 1 : prev));
  };

  return (
    <div className="relative min-h-screen flex items-center justify-center px-4 md:px-12">
      
      {errorMessage && (
        <div className="absolute top-4 left-1/2 -translate-x-1/2 bg-red-100 text-red-700 p-3 rounded-lg border border-red-200 z-50">
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
        <div className="absolute top-4 left-1/2 -translate-x-1/2 bg-green-100 text-green-700 p-3 rounded-lg border border-green-200 z-50">
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
        <p className="text-center text-gray-500 text-lg mt-20">
          No recommendations yet. Create notes to get started!
        </p>
      ) : (
        <div className="flex w-full max-w-4xl h-[80vh] justify-center relative">
          <div className="flex-1 flex items-center justify-center">
            <NoteFYP note={notes[currentIndex]} />
          </div>

          <div className="absolute right-4 top-1/2 -translate-y-1/2 flex flex-col gap-4">
            <button
              onClick={handlePrev}
              disabled={currentIndex === 0}
              className={`px-4 py-2 rounded-xl font-medium transition
                ${currentIndex === 0
                  ? "bg-gray-200 text-gray-400 cursor-not-allowed"
                  : "bg-[var(--color-9)] hover:bg-[var(--color-8)] text-white"}`}
            >
              ↑
            </button>
            <button
              onClick={handleNext}
              disabled={currentIndex === notes.length - 1}
              className={`px-4 py-2 rounded-xl font-medium transition
                ${currentIndex === notes.length - 1
                  ? "bg-gray-200 text-gray-400 cursor-not-allowed"
                  : "bg-[var(--color-9)] hover:bg-[var(--color-8)] text-white"}`}
            >
              ↓
            </button>
          </div>

          <div className="absolute bottom-4 left-1/2 -translate-x-1/2 text-sm text-gray-500">
            {currentIndex + 1} / {notes.length}
          </div>
        </div>
      )}
    </div>
  );
}

export default ForYouPage;
