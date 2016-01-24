package at.fhj.moappdev.n073b00k.activities;

import android.app.Application;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * This class is a custom application class. It is used to load libraries only once per application start-up.
 */
public class NotebookApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /* Load libraries. */
        SQLiteDatabase.loadLibs(this);
    }
}
