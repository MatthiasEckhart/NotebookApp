package at.fhj.moappdev.n073b00k.database;

import at.fhj.moappdev.n073b00k.models.Note;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * This class is used to configure the database model.
 * Every time you modify the {@link Note} model, you will need to run the main method.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[]{
            Note.class,
    };

    /**
     * Writes a configuration file in the raw directory.
     * If running main does not work, edit configurations of this file and set working directory to 'app/src/main'.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
