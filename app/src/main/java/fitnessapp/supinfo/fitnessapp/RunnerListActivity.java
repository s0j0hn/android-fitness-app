package fitnessapp.supinfo.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import fitnessapp.supinfo.fitnessapp.adapters.RunnerListviewAdapter;
import fitnessapp.supinfo.fitnessapp.listeners.RunnerListTextviewListener;
import fitnessapp.supinfo.fitnessapp.dao.implemented.RunnerDAOImpl;
import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.ArrayList;
import java.util.Calendar;

public class RunnerListActivity extends AppCompatActivity {
    public ArrayList<Runner> array;
    private RunnerDAOImpl rDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weight);

        this.rDAO = new RunnerDAOImpl(this);

        if (savedInstanceState != null) {
            ((EditText) findViewById(R.id.runnerweight)).setText(savedInstanceState.getString("newRunner"));
            Log.i("RUNNER", "RESTORED FROM onCREATE");
        }

        RunnerListTextviewListener.setActivity(this);
        this.refresh();


    }

    public void add(View view) {
        EditText eText = (EditText) findViewById(R.id.runnerweight);

        if (eText.getText().toString().trim().length() > 0) {
            this.addWeight(Integer.valueOf(String.valueOf(eText.getText())));

            eText.setText("");
            Toast.makeText(this, "!! Weight added !!", Toast.LENGTH_SHORT).show();
            ;
        } else {
            Toast.makeText(this, "Empty field !", Toast.LENGTH_LONG).show();
        }

    }

    public void addWeight(int weight) {
        Runner runner = new Runner();
        runner.setWeight(weight);
        runner.setDate(Calendar.getInstance().getTime());

        this.rDAO.save(runner);
        this.refresh();
    }


    public void refresh() {

        this.array = this.rDAO.getAll();

        //AVEC ADAPTER personalisé
        ListAdapter quotesAdapter = new RunnerListviewAdapter(this.array, this);
        ListView ll = (ListView) findViewById(R.id.runnersview);
        ll.setAdapter(quotesAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MainActivity.RUNNER_ACTIVITY_CODE:
                if(resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    Runner q = (Runner)b.getSerializable("runner");
                    //this.array.set(b.getInt("id"), q);
                    this.rDAO.save(q);
                    this.refresh();

                }
                break;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("RUNNER", "SAVED");
        assert ((EditText) findViewById(R.id.runnerweight)) != null;
        outState.putString("newWeight", ((EditText) findViewById(R.id.runnerweight)).getText().toString().trim());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ((EditText) findViewById(R.id.runnerweight)).setText(savedInstanceState.getString("newWeight"));
        Log.i("RUNNER", "RESTORED");
    }
}
