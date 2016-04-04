package fitnessapp.supinfo.fitnessapp.dao;

import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.ArrayList;

public interface RunnerDAO {

    public void save(Runner runner);
    public void delete(Runner runner);
    public ArrayList<Runner> getAll();
}
