import { useState, useEffect } from "react";
import CategoryAPI from "../../apis/CategoryAPI";

function TreeNode({ node, onSelectLeaf }) {
  const [open, setOpen] = useState(false);
  const hasChildren = node.children && node.children.length > 0;

  if (hasChildren) {
    return (
      <li>
        <div className="flex items-center gap-2 px-3 py-2 rounded-[8px] cursor-pointer transition-colors duration-150 select-none hover:bg-solen-surface-soft" onClick={() => setOpen((p) => !p)}>
          <span className={`w-5 h-5 flex items-center justify-center transition-transform duration-200 shrink-0${open ? " rotate-90" : ""}`}>
            <svg width="12" height="12" viewBox="0 0 12 12"><path d="M4 2l4 4-4 4" stroke="currentColor" strokeWidth="1.5" fill="none" /></svg>
          </span>
          <span>{node.name}</span>
        </div>
        {hasChildren && (
          <ul className={`pl-7${open ? " block" : " hidden"}`}>
            {node.children.map((child) => (
              <TreeNode key={child.id} node={child} onSelectLeaf={onSelectLeaf} />
            ))}
          </ul>
        )}
      </li>
    );
  }

  return (
    <li>
      <div
        className="flex items-center gap-2 px-3 py-1.5 pl-10 rounded-[8px] cursor-pointer transition-colors duration-150 text-sm hover:bg-solen-surface-soft"
        style={{ paddingLeft: "12px" }}
        onClick={() => onSelectLeaf(node)}
      >
        {node.name}
      </div>
    </li>
  );
}

function CreatePracticeModal({ isOpen, onClose, onCreate, categoryId, setCategoryId, name, setName, description, setDescription }) {
  const [tree, setTree] = useState([]);

  useEffect(() => {
    if (isOpen) {
      CategoryAPI.getTree().then(setTree).catch((err) => console.error("Failed to load categories", err));
    }
  }, [isOpen]);

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    onCreate();
  };

  return (
    <div className="fixed inset-0 bg-black/20 flex items-center justify-center z-200" onClick={onClose}>
      <div className="bg-solen-surface border border-solen-border rounded-[8px] p-[var(--space-xl)] max-w-[500px] w-[90%] shadow-[0_16px_48px_rgb(0_0_0_/_0.08)]" onClick={(e) => e.stopPropagation()}>
        <h3 className="font-display text-[1.15rem] font-[400] mb-[var(--space-sm)]">New practice</h3>
        <p className="text-[0.9rem] text-solen-muted mb-[var(--space-lg)] leading-[1.6]">Choose a category and name your practice.</p>

        <form onSubmit={handleSubmit}>
          <div className="flex flex-col gap-1.5 mb-4">
            <label>Category</label>
            <div className="overflow-y-auto" style={{ maxHeight: "240px" }}>
              <ul className="list-none">
                {tree.map((node) => (
                  <TreeNode
                    key={node.id}
                    node={node}
                    onSelectLeaf={(leaf) => setCategoryId(leaf.id)}
                  />
                ))}
              </ul>
            </div>
            {categoryId && <span className="font-body text-sm leading-relaxed text-solen-accent mt-1 block">Category selected</span>}
          </div>

          <div className="flex flex-col gap-1.5 mb-4">
            <label htmlFor="practiceName">Practice name</label>
            <input id="practiceName" className="w-full px-3 py-2 border border-solen-border rounded-[8px] bg-solen-surface text-solen-fg font-body text-sm placeholder:text-solen-muted/60 focus:outline-none focus:border-solen-accent-dim transition-colors duration-200" placeholder="e.g. Morning stretch" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>

          <div className="flex flex-col gap-1.5 mb-4">
            <label htmlFor="practiceDesc">Description (optional)</label>
            <textarea id="practiceDesc" className="w-full px-3 py-2 border border-solen-border rounded-[8px] bg-solen-surface text-solen-fg font-body text-sm placeholder:text-solen-muted/60 focus:outline-none focus:border-solen-accent-dim transition-colors duration-200 resize-vertical" placeholder="What does this practice involve?" value={description} onChange={(e) => setDescription(e.target.value)} rows={2} />
          </div>

          <div className="flex gap-2 justify-end">
            <button type="button" className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-solen-fg no-underline hover:bg-solen-surface-soft transition-all duration-200 cursor-pointer font-body bg-transparent border-none" onClick={onClose}>Cancel</button>
            <button type="submit" className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-[var(--color-solen-surface)] bg-solen-accent border border-solen-accent no-underline hover:bg-solen-accent-glow hover:border-solen-accent-glow hover:shadow-[0_0_24px_oklch(78%_0.18_80_/_0.25)] transition-all duration-200 cursor-pointer font-body">Create</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CreatePracticeModal;
