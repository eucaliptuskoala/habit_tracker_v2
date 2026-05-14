function Toasts({ message, errorMessage, clearMessage, clearError }) {
  return (
    <div className="fixed bottom-6 right-6 space-y-2 z-50">
      {message && (
        <div className="bg-green-100 text-green-800 px-4 py-2 rounded-xl shadow">
          {message}
          <button onClick={clearMessage} className="ml-3 font-bold">×</button>
        </div>
      )}
      {errorMessage && (
        <div className="bg-red-100 text-red-800 px-4 py-2 rounded-xl shadow">
          {errorMessage}
          <button onClick={clearError} className="ml-3 font-bold">×</button>
        </div>
      )}
    </div>
  );
}

export default Toasts;
