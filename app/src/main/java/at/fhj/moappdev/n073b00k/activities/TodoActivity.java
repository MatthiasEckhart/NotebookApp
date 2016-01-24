package at.fhj.moappdev.n073b00k.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import at.fhj.moappdev.n073b00k.R;
import at.fhj.moappdev.n073b00k.adapters.NoteAdapter;
import at.fhj.moappdev.n073b00k.database.DatabaseHelper;
import at.fhj.moappdev.n073b00k.helpers.Log;
import at.fhj.moappdev.n073b00k.models.Note;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

/**
 * This class displays notes which have not been completed in the to do tab.
 * See {@link DoneActivity}.
 */
public class TodoActivity extends Activity {

    /**
     * The Data Access Object, used to access the SQLite database.
     */
    private Dao<Note, String> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the layout of this activity. */
        setContentView(R.layout.tab_todo);
        initializeSQLCipher();
        /* Call method which populates list with notes which are not finished yet. */
        showTasks();
    }

    /**
     * Populates {@link NoteAdapter} with note objects which have not been finished yet.
     */
    public void showTasks() {
         /* Access database to check if Notes table is empty. */
        /* Retrieve all note entries from database which have the attribute done set to false. */
        List<Note> notes = null;
        try {
            notes = dao.queryForEq("done", false);
        } catch (SQLException e) {
            Log.e(TodoActivity.class.getName(), "Cannot query database.");
        }
        if (notes == null) throw new IllegalStateException("Cannot populate notes!");
        /* Create the adapter to convert the note objects to view elements. */
        final NoteAdapter adapter = new NoteAdapter(this, notes);
        /* Attach the adapter to a ListView. */
        ListView listView = (ListView) findViewById(R.id.main_list);
        listView.setAdapter(adapter);

        /* Item click listener to set the status of a note to done. */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, final int position, final long id) {
                /* Get the note to update. */
                Note note = (Note) parent.getAdapter().getItem(position);
                /* Update respective row in Notes table. */
                try {
                    dao.update(note.setDone(true));
                } catch (SQLException e) {
                    Log.e(TodoActivity.class.getName(), "Cannot update note in database.");
                }
                /* Remove note from list view, since its status has been changed to done. */
                adapter.remove(note);
                adapter.notifyDataSetChanged();
                Log.i(TodoActivity.class.getName(), "Note completed. Details: title '" + note.getTitle() + "', description '" + note.getContent() + "', due date '" + note.getDate().toString() + "' and location (lat/long) '" + note.getLatitude() + "/" + note.getLongitude() + "'.");
            }
        });
    }

    /**
     * Initializes the SQL Cipher for OrmLite.
     */
    private void initializeSQLCipher() {
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        helper.getWritableDatabase(DatabaseHelper.DATABASE_PASSWORD);
        try {
            dao = helper.getDao(Note.class);
        } catch (SQLException e) {
            Log.e(TodoActivity.class.getName(), "Cannot initialize SQL Cipher.");
        }
    }

}