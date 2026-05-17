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
        <div className="category-tree-node" onClick={() => setOpen((p) => !p)}>
          <span className={`category-tree-toggle${open ? " open" : ""}`}>
            <svg width="12" height="12" viewBox="0 0 12 12"><path d="M4 2l4 4-4 4" stroke="currentColor" strokeWidth="1.5" fill="none" /></svg>
          </span>
          <span className="tree-node-icon">{node.icon || "📁"}</span>
          <span>{node.name}</span>
        </div>
        {hasChildren && (
          <ul className={`category-tree-children${open ? " open" : ""}`}>
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
        className={`category-tree-leaf${isSelected ? " selected" : ""}`}
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
    }).catch(() => {});
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
      <div className="category-tree-wrapper">
        <ul className="category-tree">
          {tree.map((node) => (
            <TreeNode key={node.id} node={node} selectedIds={selectedIds} onToggle={handleToggle} />
          ))}
        </ul>
      </div>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginTop: "var(--space-md)" }}>
        <span className="selected-count">{selectedIds.size} practices selected</span>
      </div>
      <div className="onboarding-actions">
        <button className="btn btn-ghost" onClick={() => onSelect([])}>Skip</button>
        <button className="btn btn-primary" onClick={handleFinish}>Finish</button>
      </div>
    </div>
  );
}

export default CategoryTreeBrowser;
