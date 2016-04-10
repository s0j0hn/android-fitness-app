package fitnessapp.supinfo.fitnessapp.model;

import java.io.Serializable;


public class Track implements Serializable {
    private Double latitude;
    private Double longitude;

    public Double getLatitude(){return latitude;}
    public void setLatitude(Double lat){this.latitude = lat;}

    public Double getLongitude(){return longitude;}

    public void setLongitude(Double lng) {
        this.longitude = lng;
    }

}
