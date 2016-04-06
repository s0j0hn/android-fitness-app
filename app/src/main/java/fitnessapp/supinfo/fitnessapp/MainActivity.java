package fitnessapp.supinfo.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import fitnessapp.supinfo.fitnessapp.listeners.RunnerListTextviewListener;
import fitnessapp.supinfo.fitnessapp.dao.implemented.RunnerDAOImpl;
import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ArrayList<Runner> rlist;
    private RunnerDAOImpl rDAO;
    private ViewPager mViewPager;
    public static final int RUNNER_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.rDAO = new RunnerDAOImpl(this);
        if (savedInstanceState != null) {
            ((EditText) findViewById(R.id.runnerweight)).setText(savedInstanceState.getString("Type here"));
            Log.i("RUNNER", "RESTORED FROM onCREATE");
        }
        RunnerListTextviewListener.setActivity(this);

        this.refresh();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MainActivity.RUNNER_ACTIVITY_CODE:
                if(resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    Runner q = (Runner)b.getSerializable("weight");
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
        outState.putString("newWeight", ((EditText) findViewById(R.id.runnerweight)).getText().toString().trim());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ((EditText) findViewById(R.id.runnerweight)).setText(savedInstanceState.getString("newWeight"));
        Log.i("RUNNER", "RESTORED");
    }

    public void refresh() {

        this.rlist = this.rDAO.getAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new WeightFragment();
                    break;
                case 1:
                    fragment = new StatsFragment();
                    break;
                case 2:
                    fragment = new StatsFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Weight";
                case 1:
                    return "Foot Track";
                case 2:
                    return "Stats";
            }
            return null;
        }

    }
}