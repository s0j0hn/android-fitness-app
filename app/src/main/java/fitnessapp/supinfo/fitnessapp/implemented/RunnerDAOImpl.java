package fitnessapp.supinfo.fitnessapp.implemented;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;
import fitnessapp.supinfo.fitnessapp.dao.RunnerDAO;
import fitnessapp.supinfo.fitnessapp.model.Runner;
import fitnessapp.supinfo.fitnessapp.tools.MySQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RunnerDAOImpl implements RunnerDAO {

    public static final String TABLE_NAME = "runner";
    public static final String RUNNER_DATE = "date";
    public static final String RUNNER_WEIGHT = "weight";

    public static final String[] COLUMNS = {RUNNER_DATE,RUNNER_WEIGHT};

    private MySQLite openHelper;
    private SQLiteDatabase db;
    private Context context;

    public RunnerDAOImpl(Context context){
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
                "'"+RUNNER_WEIGHT+"' text NOT NULL,"+
                "'"+RUNNER_DATE+"' date NOT NULL);";
    }

    public static String getDrop() {
        return "DROP TABLE "+TABLE_NAME+";";
    }

    public static String getUpgrade(int odlVersion, int currentVersion){
        return RunnerDAOImpl.getDrop()+RunnerDAOImpl.getCreate();
    }

    @Override
    public void save(Runner runner) {
        this.open();
        //DEBUG
        ContentValues v = new ContentValues();
        v.put(RUNNER_WEIGHT,runner.getWeight());
        //v.put(RUNNER_DATE,runner.getDate());
        Log.i("DEBUG","save runner weight");

        String[] args = new String[]{DateFormat.format("yyyy-MM-dd kk:mm:ss", runner.getDate()).toString()};
        Cursor cursor = this.db.query(TABLE_NAME,COLUMNS,RUNNER_DATE+"=?",args,null,null,null);

        if (cursor.getCount() > 0){
            this.db.update(TABLE_NAME,v,RUNNER_DATE+"=?",args);
        }
        else {
            v.put(RUNNER_DATE,DateFormat.format("yyyy-MM-dd kk:mm:ss", runner.getDate()).toString());
            this.db.insert(TABLE_NAME,null,v);
        }
        this.close();
    }

    @Override
    public void delete(Runner runner) {
        this.open();

        String[] args = new String[]{DateFormat.format("yyyy-MM-dd kk:mm:ss", runner.getDate()).toString()};
        this.db.delete(TABLE_NAME,RUNNER_DATE+"=?",args);
        this.close();
    }

    @Override
    public ArrayList<Runner> getAll() {
        ArrayList<Runner> result = new ArrayList<Runner>();
        this.open();
        //DEBUG pour la console
        Log.i("DEBUG","get all runner weights");
        Cursor cursor = this.db.query(TABLE_NAME,COLUMNS,null,null,null,null,null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()){
            Runner r = new Runner();
            r.setWeight(cursor.getColumnIndex(RUNNER_WEIGHT));
            //On formate notre date avant de l'envoyer
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
            try {
                r.setDate(formater.parse(cursor.getString(cursor.getColumnIndex(RUNNER_DATE))));
            }catch (ParseException e){
                e.printStackTrace();
            }
            result.add(r);
            cursor.moveToNext();
        }
        this.close();
        return result;
    }
}
