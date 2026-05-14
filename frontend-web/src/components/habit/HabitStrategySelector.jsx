import React, { useState } from "react";
import HabitTemplateCard from "../template/HabitTemplateCard";

function HabitStrategySelector({ habitTemplates, popularHabitTemplates, onSubmit }) {
  const [activeTab, setActiveTab] = useState("popular");
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const resetForm = () => {
    setSelectedTemplate(null);
    setName("");
    setDescription("");
  };

  const createFromTemplate = (template) => {
    setSelectedTemplate(template);
    setName(template.name);
    setDescription("");
  };

  const createCustom = () => {
    setSelectedTemplate({ id: null });
    setName("");
    setDescription("");
  };

  const submit = () => {
    onSubmit({
      name,
      description,
      templateId: selectedTemplate?.id ?? null,
    });
    resetForm();
  };

  let content;
  if (activeTab === "popular") content = popularHabitTemplates;
  if (activeTab === "all") content = habitTemplates;
  if (activeTab === "custom") content = "custom";

  return (
    <div className="bg-[var(--color-6)] rounded-2xl p-5 border border-[var(--color-5)]">
      {!selectedTemplate ? (
        <>
            <div className="flex gap-2 mb-6 bg-[var(--color-5)] p-1 rounded-xl">
            {[
              { id: "popular", label: "Popular" },
              { id: "all", label: "All habits" },
              { id: "custom", label: "Custom" },
            ].map(tab => (
              <button
                key={tab.id}
                onClick={() => {
                  setActiveTab(tab.id);
                  if (tab.id === "custom") createCustom();
                }}
                className={`flex-1 py-2 rounded-lg text-sm transition-all
                  ${activeTab === tab.id
                    ? "bg-white shadow font-medium"
                    : "text-gray-600 hover:text-black"
                  }`}

                >
                {tab.label}
              </button>
            ))}
          </div>

          <div className="grid gap-3 max-h-72 overflow-y-auto pr-1">
            {Array.isArray(content) &&
              content.map((t) => (
                <HabitTemplateCard
                  key={t.id}
                  t={t}
                  createFromTemplate={createFromTemplate}
                />
              ))}
          </div>
        </>
      ) : (
        <div className="space-y-6">
          <button
            onClick={resetForm}
            className="text-sm text-gray-600 hover:text-black flex items-center gap-1"
          >
            ← Back to templates
          </button>

          <div>
            <label className="block text-sm text-gray-700 mb-1">
              Habit name
            </label>
            <input
              className={`w-full px-4 py-3 rounded-xl border border-[var(--color-8)]
                          focus:ring-2 focus:ring-[var(--color-9)]
                          ${selectedTemplate?.id ? "bg-gray-100 cursor-not-allowed" : "bg-white"}`}
              value={name}
              onChange={(e) => setName(e.target.value)}
              disabled={!!selectedTemplate?.id} // disable if a template is selected
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-1">
              Description <span className="text-gray-400">What is the purpose of this habit?</span>
            </label>
            <input
              className="w-full px-4 py-3 rounded-xl border bg-white border-[var(--color-8)]
                        focus:ring-2 focus:ring-[var(--color-9)]"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>

          <button
            onClick={submit}
            className="w-full py-3 rounded-xl bg-[var(--color-9)]
                      hover:bg-[var(--color-8)]
                      transition-all font-medium"
          >
            Create habit
          </button>
        </div>
      )}
    </div>
  );
}

export default HabitStrategySelector;