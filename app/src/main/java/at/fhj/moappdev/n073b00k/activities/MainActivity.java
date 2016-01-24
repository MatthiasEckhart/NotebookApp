package at.fhj.moappdev.n073b00k.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TabHost;
import at.fhj.moappdev.n073b00k.R;
import at.fhj.moappdev.n073b00k.database.DatabaseHelper;
import at.fhj.moappdev.n073b00k.helpers.Log;
import at.fhj.moappdev.n073b00k.models.Note;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * This activity extends from {@link TabActivity} and displays the tabs {@code To Do} and {@code Done}.
 */
public class MainActivity extends TabActivity {

    /**
     * The Data Access Object, used to access the SQLite database.
     */
    private Dao<Note, String> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSQLCipher();
        /* Set the layout of this activity. */
        setContentView(R.layout.activity_main);
        /* Setup Tabs. */
        TabHost tabHost = getTabHost();
        /* Get To Do label. */
        String todoLabel = getResources().getString(R.string.main_tab_todo);
        TabHost.TabSpec todoSpec = tabHost.newTabSpec(todoLabel);
        todoSpec.setIndicator(todoLabel);
        Intent todoIntent = new Intent(this, TodoActivity.class);
        todoSpec.setContent(todoIntent);
        tabHost.addTab(todoSpec);
        /* Get Done label. */
        String doneLabel = getResources().getString(R.string.main_tab_done);
        TabHost.TabSpec doneSpec = tabHost.newTabSpec(doneLabel);
        doneSpec.setIndicator(doneLabel);
        Intent doneIntent = new Intent(this, DoneActivity.class);
        doneSpec.setContent(doneIntent);
        tabHost.addTab(doneSpec);
        logDeviceData();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        long sizeOfNotes = 0;
        try {
        /* Count all notes in respective table. */
            sizeOfNotes = dao.countOf();
        } catch (SQLException ex) {
            Log.e(MainActivity.class.getName(), "Could not count notes!");
        }
        /* Get maps item from action bar. */
        MenuItem item = menu.findItem(R.id.main_maps);
        /* If no notes are stored in our table, we want to hide the maps icon in the action bar. */
        if (sizeOfNotes == 0 && item != null)
            item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.main_add) startActivity(new Intent(this, AddActivity.class));
        else if (id == R.id.main_maps) startActivity(new Intent(this, TasksMapActivity.class));
        return super.onOptionsItemSelected(item);
    }

    /**
     * Logs device settings for better debugging.
     */
    private void logDeviceData() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Log.i(MainActivity.class.getName(), "IMEI: " + telephonyManager.getDeviceId());
        Log.i(MainActivity.class.getName(), "SIM Operator: " + telephonyManager.getSimOperator());
        Log.i(MainActivity.class.getName(), "Network Operator: " + telephonyManager.getNetworkOperator());
        Log.i(MainActivity.class.getName(), "Telephone number: " + telephonyManager.getLine1Number());
        Log.i(MainActivity.class.getName(), "Device name: " + android.os.Build.MODEL);
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this.getBaseContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                Log.i(MainActivity.class.getName(), "E-Mail: " + account.name);
            }
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
            Log.e(MainActivity.class.getName(), "Cannot initialize SQL Cipher.");
        }
    }

}
