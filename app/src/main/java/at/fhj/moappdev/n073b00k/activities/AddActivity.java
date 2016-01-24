package at.fhj.moappdev.n073b00k.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import at.fhj.moappdev.n073b00k.R;
import at.fhj.moappdev.n073b00k.helpers.LocationTracker;
import at.fhj.moappdev.n073b00k.helpers.Log;
import at.fhj.moappdev.n073b00k.models.Note;
import com.j256.ormlite.dao.Dao;

import at.fhj.moappdev.n073b00k.database.DatabaseHelper;

import java.sql.SQLException;
import java.util.Date;

/**
 * This activity is used to add notes.
 */
public class AddActivity extends Activity {

    /**
     * The Data Access Object, used to access the SQLite database.
     */
    private Dao<Note, String> dao;

    /**
     * This instance is used to retrieve GPS data.
     */
    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the layout of this activity. */
        setContentView(R.layout.activity_add);
        /* Create a new instance for GPS tracking. */
        locationTracker = new LocationTracker(AddActivity.this);

        /* Check if GPS is enabled */
        if (!locationTracker.canGetLocation()) {
            /* We can't get the user's current location.
             * Either the user has deactivated GPS or the network.
             * We will now ask the user to enable GPS or the network in the phone's settings.
             */
            locationTracker.showSettingsAlert();
        }
        initializeSQLCipher();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();
        /* Save the task. */
        if (id == R.id.add_save) {
            /* If we have successfully saved the user's task, we return to the MainActivity. */
            if (save()) startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method saves user's notes.
     *
     * @return {@code true} if the task has been successfully saved
     */
    public boolean save() {
        if (locationTracker == null) throw new IllegalStateException("LocationTracker is null.");
        /* Get UI elements from GUI. */
        EditText title = (EditText) findViewById(R.id.add_title);
        EditText content = (EditText) findViewById(R.id.add_content);
        DatePicker dueDate = (DatePicker) findViewById(R.id.add_date);

        /* Check if title is empty. */
        if (title.getText().toString().equals("")) {
            title.setError(getResources().getString(R.string.activity_add_input_empty_title));
            return false;
        }
        /* Check if content is empty. */
        else if (content.getText().toString().equals("")) {
            content.setError(getResources().getString(R.string.activity_add_input_empty_content));
            return false;
        } else {
            /* If title nor content is empty, save task in database. */
            /* Create a new Note object. */
            Note note = new Note(title.getText().toString(), content.getText().toString(), new Date(dueDate.getCalendarView().getDate()), locationTracker.getLatitude(), locationTracker.getLongitude());
            /* Save note in database. */
            try {
                dao.create(note);
            } catch (SQLException e) {
                Log.e(AddActivity.class.getName(), "Cannot save note in database.");
            }
            Log.i(AddActivity.class.getName(), "Note created. Details: title '" + note.getTitle() + "', description '" + note.getContent() + "', due date '" + note.getDate().toString() + "' and location (lat/long) '" + note.getLatitude() + "/" + note.getLongitude() + "'.");
            return true;
        }
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
            Log.e(AddActivity.class.getName(), "Cannot initialize SQL Cipher.");
        }
    }

}