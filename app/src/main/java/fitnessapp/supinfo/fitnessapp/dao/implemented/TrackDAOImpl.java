package fitnessapp.supinfo.fitnessapp.dao.implemented;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fitnessapp.supinfo.fitnessapp.dao.TrackDAO;
import fitnessapp.supinfo.fitnessapp.dao.tools.MySQLite;
import fitnessapp.supinfo.fitnessapp.model.Track;

import java.util.ArrayList;

public class TrackDAOImpl implements TrackDAO {
    public static final String TABLE_NAME = "tracks";
    public static final String TRACK_LONGITUDE = "longitude";
    public static final String TRACK_LATITUDE = "latitude";

    public static final String[] COLUMNS = {TRACK_LONGITUDE, TRACK_LATITUDE};

    private MySQLite openHelper;
    private SQLiteDatabase db;
    private Context context;

    public TrackDAOImpl(Context context){
        this.context = context;
        this.openHelper = new MySQLite(this.context);
    }

    private void open(){
        this.db = this.openHelper.open(this.db);
    }

    private void close(){
        this.db = this.openHelper.close(this.db);
    }


    //init de la base
    public static String getCreate(){
        return "CREATE TABLE "+TABLE_NAME+" ("+
                "'"+ TRACK_LATITUDE +"' text NOT NULL,"+
                "'"+ TRACK_LONGITUDE +"' text NOT NULL);";
    }

    public static String getDrop() {
        return "DROP TABLE "+TABLE_NAME+";";
    }

    public static String getUpgrade(int odlVersion, int currentVersion){
        return TrackDAOImpl.getDrop()+TrackDAOImpl.getCreate();
    }

    @Override
    public void save(Track track) {
        this.open();
        //DEBUG
        ContentValues v = new ContentValues();
        v.put(TRACK_LATITUDE,track.getLatitude());
        v.put(TRACK_LONGITUDE,track.getLongitude());
        Log.i("DEBUG","save runner POSITION");

        /*Cursor cursor = this.db.query(TABLE_NAME,COLUMNS, TRACK_LONGITUDE +"=?",null,null,null,null);
        Cursor cursor = this.db.query(TABLE_NAME,COLUMNS, TRACK_LATITUDE +"=?",null,null,null,null);

        if (cursor.getCount() > 0){
            this.db.update(TABLE_NAME,v, TRACK_LONGITUDE +"=?",null);
        }
        else {
            this.db.insert(TABLE_NAME,null,v);
        }
        /*if (cursor2.getCount() > 0){
            this.db.update(TABLE_NAME,v, TRACK_LATITUDE +"=?",null);
        }
        else {

        }*/
        this.db.insert(TABLE_NAME,null,v);
        this.close();
    }

    @Override
    public void delete(Track track) {
        this.open();

        this.db.delete(TABLE_NAME, TRACK_LONGITUDE +"=?",null);
        this.close();
    }

    @Override
    public ArrayList<Track> getAll() {
        ArrayList<Track> result = new ArrayList<Track>();
        this.open();
        // SQL
        Log.i("DEBUG", "get all tracks");
        Cursor cursor = this.db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()){
            Track t = new Track();
            t.setLatitude(cursor.getDouble(cursor.getColumnIndex(TRACK_LATITUDE)));
            t.setLongitude(cursor.getDouble(cursor.getColumnIndex(TRACK_LONGITUDE)));

            result.add(t);
            cursor.moveToNext();
        }
        this.close();
        return result;
    }
}
