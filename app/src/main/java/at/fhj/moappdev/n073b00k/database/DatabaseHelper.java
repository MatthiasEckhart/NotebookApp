package at.fhj.moappdev.n073b00k.database;

import android.content.Context;

import java.sql.SQLException;

import at.fhj.moappdev.n073b00k.helpers.Log;
import at.fhj.moappdev.n073b00k.models.Note;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * This class is used to provide database access by OrmLite.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /**
     * The name of the database file for our application.
     */
    private static final String DATABASE_NAME = "notebook.db";
    /**
     * The database password for SQLite.
     */
    public static final String DATABASE_PASSWORD = "notebook";
    /**
     * The database version.
     */
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, DATABASE_PASSWORD);
    }

    @Override
    public void onCreate(net.sqlcipher.database.SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        createTables(connectionSource);
    }

    @Override
    public void onUpgrade(net.sqlcipher.database.SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        Log.i(DatabaseHelper.class.getName(), "Drop old table.");
        dropTables(connectionSource);
        createTables(connectionSource);
    }

    @Override
    public void close() {
        super.close();
    }

    /**
     * Creates tables for our model.
     *
     * @param source the connection source
     */
    private void createTables(ConnectionSource source) {
        try {
            TableUtils.createTable(source, Note.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create tables.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Drops tables for our model.
     *
     * @param source the connection source
     */
    private void dropTables(ConnectionSource source) {
        try {
            TableUtils.dropTable(source, Note.class, true);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop tables.", e);
            throw new RuntimeException(e);
        }
    }

}
