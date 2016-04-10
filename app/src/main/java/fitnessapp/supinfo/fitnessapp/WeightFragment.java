package fitnessapp.supinfo.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.*;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import fitnessapp.supinfo.fitnessapp.adapters.RunnerListviewAdapter;
import fitnessapp.supinfo.fitnessapp.dao.implemented.RunnerDAOImpl;
import fitnessapp.supinfo.fitnessapp.listeners.RunnerListTextviewListener;
import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.ArrayList;


public class WeightFragment extends Fragment {


    public ArrayList<Runner> runnersList;
    private RunnerDAOImpl rDAO;
    private View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_weight, container, false);
        this.rDAO = new RunnerDAOImpl(this.getActivity());
        this.refresh();
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public void refresh(){
        this.runnersList = this.rDAO.getAll();
        ListView ll = (ListView) v.findViewById(R.id.runnersview);
        ListAdapter quotesAdapter = new RunnerListviewAdapter(this.runnersList, getContext());
        ll.setAdapter(quotesAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isRemoving() && v != null) {
            ((ViewGroup) v).removeAllViews();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if (isRemoving() && v != null) {
            ((ViewGroup) v).removeAllViews();
        }
    }

}
