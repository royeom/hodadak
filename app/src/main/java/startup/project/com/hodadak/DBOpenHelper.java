package startup.project.com.hodadak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE0);
            db.execSQL(DataBases.CreateDB._CREATE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME1);
            onCreate(db);
        }
    }

    public DBOpenHelper(Context context){
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    public long insertPathColumn(String title, String departure, String arrive){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.TITLE, title);
        values.put(DataBases.CreateDB.DEPARTURE, departure);
        values.put(DataBases.CreateDB.ARRIVE, arrive);
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    public long insertPathTimeColumn(long pathid, String day, int hour, int minute){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.PATHID, pathid);
        values.put(DataBases.CreateDB.DAY, day);
        values.put(DataBases.CreateDB.HOUR, hour);
        values.put(DataBases.CreateDB.MINUTE, minute);
        return mDB.insert(DataBases.CreateDB._TABLENAME1, null, values);
    }

    // 일단 모든 튜플을 가져온다.
    public Cursor selectPathColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    public Cursor selectPathTimeColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME1, null, null, null, null, null, null);
    }

    // 특정 Column을 기준으로 정렬
    public Cursor sortPathColumn(String sort){
        Cursor c = mDB.rawQuery("SELECT * FROM "+DataBases.CreateDB._TABLENAME0+" ORDER BY " + sort + ";", null);
        return c;
    }

    public Cursor sortPathTimeColumn(String sort){
        Cursor c = mDB.rawQuery("SELECT * FROM "+DataBases.CreateDB._TABLENAME1+" ORDER BY " + sort + ";", null);
        return c;
    }

    public boolean updatePathColumn(long pathid, String title, String departure, String arrive){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.TITLE, title);
        values.put(DataBases.CreateDB.DEPARTURE, departure);
        values.put(DataBases.CreateDB.ARRIVE, arrive);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "pathid=" + pathid, null) > 0;
    }

    public boolean updatePathTimeColumn(long pathid, String day, int hour, int minute){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.PATHID, pathid);
        values.put(DataBases.CreateDB.DAY, day);
        values.put(DataBases.CreateDB.HOUR, hour);
        values.put(DataBases.CreateDB.MINUTE, minute);
        return mDB.update(DataBases.CreateDB._TABLENAME1, values, "pathid=" + pathid, null) > 0;
    }

    public void deletePathAllColumns(){
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    public void deletePathTimeAllColumns(){
        mDB.delete(DataBases.CreateDB._TABLENAME1, null, null);
    }

    public boolean deletePathColumn(long pathid){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "pathid=" + pathid, null) > 0l;
    }

    public boolean deletePathTimeColumn(long pathid){
        return mDB.delete(DataBases.CreateDB._TABLENAME1, "pathid=" + pathid, null) > 0l;
    }
}
