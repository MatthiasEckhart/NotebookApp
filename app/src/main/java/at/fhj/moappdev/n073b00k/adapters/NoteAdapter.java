package at.fhj.moappdev.n073b00k.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.fhj.moappdev.n073b00k.R;
import at.fhj.moappdev.n073b00k.models.Note;

import java.util.List;

/**
 * This class is used to populate both tabs, {@link at.fhj.moappdev.n073b00k.activities.TodoActivity} and {@link at.fhj.moappdev.n073b00k.activities.DoneActivity}.
 */
public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* Get the note item for this position. */
        Note note = getItem(position);
        /* Check if an existing view is being reused, otherwise we inflate the view. */
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);
        /* Get UI elements for data population. */
        TextView noteTitle = (TextView) convertView.findViewById(R.id.list_item_title);
        TextView noteContent = (TextView) convertView.findViewById(R.id.list_item_content);
        TextView noteDueDate = (TextView) convertView.findViewById(R.id.list_item_duedate);
        /* Populate the note into the template view. */
        noteTitle.setText(note.getTitle());
        noteContent.setText(note.getContent());
        noteDueDate.setText(note.getDate().toString());
        /* Finally, return the completed view to render on the phone's screen. */
        return convertView;
    }
}
