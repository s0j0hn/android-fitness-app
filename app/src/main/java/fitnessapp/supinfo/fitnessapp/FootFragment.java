package fitnessapp.supinfo.fitnessapp;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import fitnessapp.supinfo.fitnessapp.dao.implemented.TrackDAOImpl;
import fitnessapp.supinfo.fitnessapp.model.Track;

import java.text.DateFormat;
import java.util.Date;

public class FootFragment extends Fragment implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private SupportMapFragment fragment;
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    MapView mMapView;
    private Location myCurrentLocation;
    String mytimeupdate;
    private TrackDAOImpl tDAO;

    private static final long interval = 1000 * 60 * (1 / 2); //toute demi minute
    LocationRequest mLocation;

    private int mapIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foot, container, false);
        createLocation();
        tDAO = new TrackDAOImpl(this.getContext());
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); //on affcihe la map une seul fois
        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);
        googleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        return v;
    }

    @Override
    public void onLocationChanged(Location location) {

        myCurrentLocation = location;
        addTrack(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude());
        mytimeupdate = DateFormat.getTimeInstance().format(new Date());
        addMarker();
    }

    private void addMarker() {
        MarkerOptions options = new MarkerOptions();

        // following four lines requires 'Google Maps Android API Utility Library'
        // https://developers.google.com/maps/documentation/android/utility/
        // I have used this to display the time as title for location markers
        // you can safely comment the following four lines but for this info
        IconGenerator iconFactory = new IconGenerator(this.getContext());
        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);

        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mytimeupdate)));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        LatLng currentcoord = new LatLng(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude());
        options.position(currentcoord);

        Marker mapMarker = googleMap.addMarker(options);
        long atTime = myCurrentLocation.getTime();
        mytimeupdate = DateFormat.getTimeInstance().format(new Date(atTime));
        mapMarker.setTitle(mytimeupdate);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentcoord,
                13));


    }

    public void addTrack(Double lat, Double lng) {
        Track track = new Track();
        track.setLatitude(lat);
        track.setLongitude(lng);
        this.tDAO.save(track);
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }


    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, mLocation, this);
    }

    protected void createLocation() {
        mLocation = new LocationRequest();
        mLocation.setInterval(interval);
        mLocation.setFastestInterval(interval);
        mLocation.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }
    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

/*public class FootFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_foot);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override

}*/
