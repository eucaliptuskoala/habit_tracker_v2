import MDEditor from '@uiw/react-md-editor';

function NoteContentEditor({ value, onChange }) {
  return (
    <div className="rounded-xl border border-[var(--color-8)] p-2 bg-white">
      <MDEditor
        value={value}
        onChange={(val = "") => onChange(val)}
        height={400}
        width="100%"
      />
    </div>
  );
}

export default NoteContentEditor;
