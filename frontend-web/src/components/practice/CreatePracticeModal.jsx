import { useState, useEffect } from "react";
import CategoryAPI from "../../apis/CategoryAPI";

function TreeNode({ node, onSelectLeaf }) {
  const [open, setOpen] = useState(false);
  const hasChildren = node.children && node.children.length > 0;

  if (hasChildren) {
    return (
      <li>
        <div className="category-tree-node" onClick={() => setOpen((p) => !p)}>
          <span className={`category-tree-toggle${open ? " open" : ""}`}>
            <svg width="12" height="12" viewBox="0 0 12 12"><path d="M4 2l4 4-4 4" stroke="currentColor" strokeWidth="1.5" fill="none" /></svg>
          </span>
          <span>{node.name}</span>
        </div>
        {hasChildren && (
          <ul className={`category-tree-children${open ? " open" : ""}`}>
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
        className="category-tree-leaf"
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
      CategoryAPI.getTree().then(setTree).catch(() => {});
    }
  }, [isOpen]);

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    onCreate();
  };

  return (
    <div className="dialog-overlay open" onClick={onClose}>
      <div className="dialog" style={{ maxWidth: "500px" }} onClick={(e) => e.stopPropagation()}>
        <h3>New practice</h3>
        <p style={{ marginBottom: "var(--space-lg)" }}>Choose a category and name your practice.</p>

        <form onSubmit={handleSubmit}>
          <div className="field">
            <label>Category</label>
            <div className="category-tree-wrapper" style={{ maxHeight: "240px" }}>
              <ul className="category-tree">
                {tree.map((node) => (
                  <TreeNode
                    key={node.id}
                    node={node}
                    onSelectLeaf={(leaf) => setCategoryId(leaf.id)}
                  />
                ))}
              </ul>
            </div>
            {categoryId && <span className="body-sm" style={{ color: "var(--accent)", marginTop: "4px", display: "block" }}>Category selected</span>}
          </div>

          <div className="field">
            <label htmlFor="practiceName">Practice name</label>
            <input id="practiceName" className="input" placeholder="e.g. Morning stretch" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>

          <div className="field">
            <label htmlFor="practiceDesc">Description (optional)</label>
            <textarea id="practiceDesc" className="textarea" placeholder="What does this practice involve?" value={description} onChange={(e) => setDescription(e.target.value)} rows={2} />
          </div>

          <div className="dialog-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>Cancel</button>
            <button type="submit" className="btn btn-primary">Create</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CreatePracticeModal;
