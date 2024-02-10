package tcf.kcompute.com.tcf_schoolprofile.Helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbDua.db";
    private static final int DATABASE_VERSION = 5;
//    public static SQLiteDatabase myDataBase;
    private static final String DB_NAME ="dbDua.db";
//    private static String DB_PATH ="/data/data/tcf.kcompute.com.tcf_schoolprofile/databases/";
    private static String DB_PATH ="";
    private final Context myContext;
    private static final String SP_KEY_DB_VER = "db_ver";
//    private static DBhelper dInstance = null;
    private SQLiteDatabase syncronizedDb;
    private static DBhelper newInstance = null;

    public DBhelper(Context context)
    {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
        this.myContext=context;
//        this.DB_PATH = this.myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        initialize();
    }

//    public static synchronized DBhelper getInstance(Context context) {
//
//        // Use the application context, which will ensure that you
//        // don't accidentally leak an Activity's context.
//        // See this article for more information: http://bit.ly/6LRzfx
//        if (dInstance == null) {
//            dInstance = new DBhelper(context.getApplicationContext());
////            myDataBase = dInstance.getWritableDatabase();
//        }
//        return dInstance;
//    }

    public static synchronized DBhelper getNewInstance(Context context) {
        if (newInstance == null) {
            newInstance = new DBhelper(context.getApplicationContext());
        }
        return newInstance;
    }

    public SQLiteDatabase getDB() {
        synchronized (this) {
            if (syncronizedDb == null || !syncronizedDb.isOpen() || syncronizedDb.isReadOnly())
                syncronizedDb = this.getWritableDatabase();
            return syncronizedDb;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        newChangesInDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            newChangesInDb(db);
        }
    }
//  Upgrade db version when new changes in table
    private void newChangesInDb(SQLiteDatabase db){
        try {
            db.execSQL(PrincipalSurveyHelperClass.getInstance(myContext).CREATE_TABLE_Principal_Survey_Question);
            db.execSQL(PrincipalSurveyHelperClass.getInstance(myContext).CREATE_TABLE_Principal_Survey_Answer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_PQIScoreCurrentSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_PQIScoreCurrentSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_PQIPreviousSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_PQIPreviousSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_PQICurrentSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_PQICurrentSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_EECurrentSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_EECurrentSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_EEPreviousSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_EEPreviousSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_TCTCurrentSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_TCTCurrentSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_TCTPreviousSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_TCTPreviousSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_WSICurrentSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_WSICurrentSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_WSIPreviousSession)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_WSIPreviousSession + " VARCHAR");
            }

            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_DOWNLOADED_ON)) {
                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_DOWNLOADED_ON + " VARCHAR");
            }

//            if (!isColumnExists(db, DAO.TABLE_School, DAO.School_SSR_Year)) {
//                db.execSQL("ALTER TABLE " + DAO.TABLE_School+ " ADD " + DAO.School_SSR_Year + " VARCHAR");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isColumnExists(SQLiteDatabase Db, String table, String column) {
        boolean isExists = false;
        Cursor cursor = null;
        try {
            cursor = Db.rawQuery("PRAGMA table_info(" + table + ")", null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    if (column.equalsIgnoreCase(name)) {
                        isExists = true;
                        break;
                    }
                }
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }
        return isExists;
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
//    public void createDataBase(Context context) throws IOException{
//
//        boolean dbExist = databaseExists();
//
//        if(!dbExist){
//            //By calling this method and empty database will be created into the default system path
//            //of your application so we are gonna be able to overwrite that database with our database.
//
//            this.getReadableDatabase();
//            this.close();
//            Log.e("Path", this.getReadableDatabase().getPath());
//
//            try {
//                copyDataBase(myContext);
//                Log.v("copyDataBase---", "Successfully");
//
//            } catch (IOException e) {
//                throw new Error("Error copying database");
//            }
//        }else {
////            this.DB_PATH = this.myContext.getDatabasePath(DB_NAME).getAbsolutePath();
//        }
//
//    }

    /**
     * Creates database by copying it from assets directory.
     */
    private void createDatabase() {
        String parentPath = myContext.getDatabasePath(DATABASE_NAME).getParent();
        String path = myContext.getDatabasePath(DATABASE_NAME).getPath();

        File file = new File(parentPath);
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.w(TAG, "Unable to create database directory");
                return;
            }
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = myContext.getAssets().open(DATABASE_NAME);
            os = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(myContext);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(SP_KEY_DB_VER, DATABASE_VERSION);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase(Context context) throws IOException{
        InputStream myInput=null;
        OutputStream myOutput =null;
        try {
            //Open your local db as the input stream
             myInput = context.getAssets().open(DB_NAME);

            // Path to the just created empty db
//        String outFileName = DB_PATH + DB_NAME;
            String outFileName = DB_PATH;

            //Open the empty db as the output stream
             myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            //Close the streams
            try {
                if (myOutput != null) {
                    myOutput.flush();
                    myOutput.close();
                }
                if (myInput != null)
                    myInput.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * Initializes database. Creates database if doesn't exist.
     */
    private void initialize() {
        if (databaseExists()) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(myContext);
            int dbVersion = prefs.getInt(SP_KEY_DB_VER, 1);
            if (DATABASE_VERSION != dbVersion) {
                File dbFile = myContext.getDatabasePath(DATABASE_NAME);
                if (!dbFile.delete()) {
                    Log.w(TAG, "Unable to update database");
                }
            }
        }
        if (!databaseExists()) {
            createDatabase();
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean databaseExists(){

        File dbFile = myContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

//    public void openDataBase() throws SQLException{
//
//        //Open the database
//        String myPath = DB_PATH + DB_NAME;
//        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//    }

    @Override
    public synchronized void close() {

        if(syncronizedDb != null)
            syncronizedDb.close();

        super.close();

    }


}
