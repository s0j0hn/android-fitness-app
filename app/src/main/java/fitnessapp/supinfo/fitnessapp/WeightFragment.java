package fitnessapp.supinfo.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import fitnessapp.supinfo.fitnessapp.adapters.RunnerListviewAdapter;
import fitnessapp.supinfo.fitnessapp.dao.implemented.RunnerDAOImpl;
import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.ArrayList;


public class WeightFragment extends Fragment {
    public ArrayList<Runner> array;
    private RunnerDAOImpl rDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weight, container, false);

        return v;
    }


}
