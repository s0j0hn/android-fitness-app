package fitnessapp.supinfo.fitnessapp.dao.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fitnessapp.supinfo.fitnessapp.dao.implemented.RunnerDAOImpl;

/**
 * Created by jan on 26/03/16.
 */
public class MySQLite extends SQLiteOpenHelper {

    public static final int VERSIONDB = 1;
    private static final String DATABASE_NAME = "appfitness.db";

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, VERSIONDB);
    }

    public SQLiteDatabase open(SQLiteDatabase db){
        if (db ==null || !db.isOpen()){
            db = this.getWritableDatabase();
        }
        return db;
    }

    public SQLiteDatabase close(SQLiteDatabase db){
        if (db.isOpen() && db != null){
            db.close();
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(RunnerDAOImpl.getCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RunnerDAOImpl.getUpgrade(oldVersion, newVersion));
    }
}
