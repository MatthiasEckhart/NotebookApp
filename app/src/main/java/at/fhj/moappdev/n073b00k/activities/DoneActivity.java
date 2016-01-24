package at.fhj.moappdev.n073b00k.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * This class displays notes which have been completed in the done tab.
 * See {@link TodoActivity}.
 */
public class DoneActivity extends Activity {

    /**
     * The Data Access Object, used to access the SQLite database.
     */
    private Dao<Note, String> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the layout of this activity. */
        setContentView(R.layout.tab_done);
        initializeSQLCipher();
        /* Call method which populates list with notes which have been completed. */
        showTasks();
    }

    @Override
    protected void onResume() {
        showTasks();
        super.onResume();
    }

    /**
     * Populates {@link NoteAdapter} with note objects which have been completed.
     */
    public void showTasks() {
         /* Access database to check if Notes table is empty. */
        /* Retrieve all note entries from database which have the attribute done set to true. */
        List<Note> notes = null;
        try {
            notes = dao.queryForEq("done", true);
        } catch (SQLException e) {
            Log.e(DoneActivity.class.getName(), "Cannot query database.");
        }
        if (notes == null) throw new IllegalStateException("Cannot populate notes!");
        /* Create the adapter to convert the note objects to view elements. */
        final NoteAdapter adapter = new NoteAdapter(this, notes);
        /* Attach the adapter to a ListView. */
        ListView listView = (ListView) findViewById(R.id.main_list);
        listView.setAdapter(adapter);

        /* Item click listener to delete notes. */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, final int position, final long id) {
                /* Display an alert dialog to make sure the user really wants to delete a task. */
                new AlertDialog.Builder(DoneActivity.this)
                        .setTitle(getResources().getString(R.string.done_activity_alert_dialog_delete_task_title))
                        .setMessage(getResources().getString(R.string.done_activity_alert_dialog_delete_task_content))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Get the note to delete. */
                                Note note = (Note) parent.getAdapter().getItem(position);
                                /* Remove the respective note object from database. */
                                try {
                                    dao.delete(note);
                                } catch (SQLException e) {
                                    Log.e(DoneActivity.class.getName(), "Cannot delete note from database.");
                                }
                                /* Remove also the list entry in our view. */
                                adapter.remove(note);
                                adapter.notifyDataSetChanged();
                                Log.i(DoneActivity.class.getName(), "Note deleted. Details: title '" + note.getTitle() + "', description '" + note.getContent() + "', due date '" + note.getDate().toString() + "' and location (lat/long) '" + note.getLatitude() + "/" + note.getLongitude() + "'.");
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Empty on purpose. Use has canceled the delete operation. */
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
            Log.e(DoneActivity.class.getName(), "Cannot initialize SQL Cipher.");
        }
    }

}