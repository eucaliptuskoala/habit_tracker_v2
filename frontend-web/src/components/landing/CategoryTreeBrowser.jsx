import { useState, useEffect } from "react";
import CategoryAPI from "../../apis/CategoryAPI";

function collectLeaves(nodes, map) {
  for (const n of nodes) {
    if (n.children && n.children.length > 0) {
      collectLeaves(n.children, map);
    } else {
      map.set(n.id, n.name);
    }
  }
}

function TreeNode({ node, selectedIds, onToggle, depth = 0 }) {
  const [open, setOpen] = useState(depth < 1);
  const hasChildren = node.children && node.children.length > 0;

  if (hasChildren) {
    return (
      <li>
        <div className="flex items-center gap-[var(--space-sm)] px-3 py-2 rounded-[8px] cursor-pointer select-none hover:bg-solen-surface-soft transition-[background] duration-150" onClick={() => setOpen((p) => !p)}>
          <span className={`w-5 h-5 flex items-center justify-center transition-transform duration-200 shrink-0${open ? " rotate-90" : ""}`}>
            <svg width="12" height="12" viewBox="0 0 12 12"><path d="M4 2l4 4-4 4" stroke="currentColor" strokeWidth="1.5" fill="none" /></svg>
          </span>
          <span className="w-5 h-5 rounded-sm flex items-center justify-center font-mono text-[0.6rem] text-solen-muted shrink-0">{node.icon || "📁"}</span>
          <span>{node.name}</span>
        </div>
        {hasChildren && (
          <ul className={`pl-7 ${open ? "block" : "hidden"}`}>
            {node.children.map((child) => (
              <TreeNode key={child.id} node={child} selectedIds={selectedIds} onToggle={onToggle} depth={depth + 1} />
            ))}
          </ul>
        )}
      </li>
    );
  }

  const isSelected = selectedIds.has(node.id);
  return (
    <li>
      <div
        className={`flex items-center gap-[var(--space-sm)] pl-10 pr-3 py-[6px] rounded-[8px] cursor-pointer text-[0.9rem] hover:bg-solen-surface-soft transition-[background] duration-150${isSelected ? " bg-solen-accent-subtle text-solen-accent" : ""}`}
        onClick={() => onToggle(node.id)}
      >
        {node.name}
      </div>
    </li>
  );
}

function CategoryTreeBrowser({ onSelect }) {
  const [tree, setTree] = useState([]);
  const [leafNames, setLeafNames] = useState(new Map());
  const [selectedIds, setSelectedIds] = useState(new Set());

  useEffect(() => {
    CategoryAPI.getTree().then((data) => {
      setTree(data);
      const map = new Map();
      collectLeaves(data, map);
      setLeafNames(map);
    }).catch((err) => console.error("Failed to fetch category tree", err));
  }, []);

  const handleToggle = (id) => {
    setSelectedIds((prev) => {
      const next = new Set(prev);
      if (next.has(id)) next.delete(id);
      else next.add(id);
      return next;
    });
  };

  const handleFinish = () => {
    const selected = [...selectedIds].map((id) => ({
      id,
      name: leafNames.get(id) || "Practice",
    }));
    onSelect(selected);
  };

  return (
    <div>
      <div className="bg-solen-surface border border-solen-border rounded-[8px] p-[var(--space-md)] max-h-[420px] overflow-y-auto">
        <ul className="list-none">
          {tree.map((node) => (
            <TreeNode key={node.id} node={node} selectedIds={selectedIds} onToggle={handleToggle} />
          ))}
        </ul>
      </div>
      <div className="flex items-center justify-between mt-[var(--space-md)]">
        <span className="font-mono text-[0.75rem] text-solen-muted">{selectedIds.size} practices selected</span>
      </div>
      <div className="flex items-center justify-between mt-[var(--space-xl)] pt-[var(--space-lg)] border-t border-solen-border">
        <button className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-solen-muted no-underline hover:bg-solen-surface-soft transition-all duration-200 cursor-pointer font-body bg-transparent border-none" onClick={() => onSelect([])}>Skip</button>
        <button className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-[var(--color-solen-surface)] bg-solen-accent border border-solen-accent no-underline hover:bg-solen-accent-glow hover:border-solen-accent-glow hover:shadow-[0_0_24px_oklch(78%_0.18_80_/_0.25)] transition-all duration-200 cursor-pointer font-body" onClick={handleFinish}>Finish</button>
      </div>
    </div>
  );
}

export default CategoryTreeBrowser;
