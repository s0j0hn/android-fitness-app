package fitnessapp.supinfo.fitnessapp.dao;


import fitnessapp.supinfo.fitnessapp.model.Track;

import java.util.ArrayList;

public interface TrackDAO {

    public void save(Track track);
    public void delete(Track track);
    public ArrayList<Track> getAll();
}
