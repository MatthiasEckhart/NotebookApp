package at.fhj.moappdev.n073b00k.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import at.fhj.moappdev.n073b00k.R;
import at.fhj.moappdev.n073b00k.database.DatabaseHelper;
import at.fhj.moappdev.n073b00k.helpers.Log;
import at.fhj.moappdev.n073b00k.models.Note;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays a Google Map which shows the location where tasks have been created.
 */
public class TasksMapActivity extends Activity {

    /**
     * The Data Access Object, used to access the SQLite database.
     */
    private Dao<Note, String> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the layout of this activity. */
        setContentView(R.layout.activity_tasks_map);
        /* Create a Google Map instance by retrieving the respective fragment. */
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        initializeSQLCipher();
        List<Note> notes = null;
        /* Retrieve all notes from database. */
        try {
            notes = dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TasksMapActivity.class.getName(), "Cannot query database.");
        }
        if (notes == null) throw new IllegalStateException("Cannot retrieve notes!");
        /* Iterate over all notes in order to add a new marker on map. */
        for (Note note : notes) {
            map.addMarker(new MarkerOptions().position(new LatLng(note.getLatitude(), note.getLongitude()))
                    .title(note.getTitle()).snippet(note.getContent()));
        }
        /* Check if at least on note is stored in database. This is usually true. */
        if (notes.size() > 0) {
            /* Get the note which has been created recently. */
            Note lastNoteCreated = notes.get(notes.size() - 1);
            /* Move the camera instantly to the location of the last created note with a zoom of 15. */
            if (lastNoteCreated != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastNoteCreated.getLatitude(), lastNoteCreated.getLongitude()), 15));
            }
        }
        /* Zoom in and animate the camera. */
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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