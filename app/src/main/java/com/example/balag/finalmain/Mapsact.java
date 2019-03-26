package com.example.balag.finalmain;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class Mapsact extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mMap;
    View myview;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;


    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        displayLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText1);
        String location = locationSearch.getText().toString();
        String loca = location.toLowerCase();
        if(Objects.equals(loca, "noothuku muttai"))
        {
            LatLng axis= new LatLng(10.938637, 76.959078);
            mMap.addMarker(new MarkerOptions().position(axis).title("near boy's noothu muttai"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(axis));
            LatLng axis1= new LatLng(10.936223, 76.955569);
            mMap.addMarker(new MarkerOptions().position(axis1).title("near cafe noothu muttai"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(axis1));
        }
        if(Objects.equals(loca, "krishna temple") || Objects.equals(loca, "temple"))
        {
            LatLng axis= new LatLng(10.938433, 76.952954);
            mMap.addMarker(new MarkerOptions().position(axis).title("Krishna Temple"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(axis));
        }
        if (Objects.equals(location, "axis") || Objects.equals(location, "axis bank")) {
            LatLng axis = new LatLng(10.938878, 76.952286);
            mMap.addMarker(new MarkerOptions().position(axis).title("Axis bank"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(axis));

        } else if (Objects.equals(location, "krishna hall") || Objects.equals(location, "hall")) {
            LatLng kh = new LatLng(10.939006, 76.958999);
            mMap.addMarker(new MarkerOptions().position(kh).title(" Krishna Hall"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(kh));

        } else if (Objects.equals(loca, "library") || Objects.equals(loca, "library-skcet") || Objects.equals(loca, "vankatram library")) {
            LatLng lb = new LatLng(10.938454, 76.956076);
            mMap.addMarker(new MarkerOptions().position(lb).title("SKCET-library"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lb));

        } else if (Objects.equals(loca, "parking") || Objects.equals(loca, "student parking") || Objects.equals(loca, "two wheeler parking")) {
            LatLng pk = new LatLng(10.937975, 76.954878);
            mMap.addMarker(new MarkerOptions().position(pk).title("Marker in parking"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(10.938158, 76.954858)).title("parking"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pk));
        } else if (Objects.equals(loca, "car parking")) {
            LatLng cpk = new LatLng(10.937764, 76.954344);
            mMap.addMarker(new MarkerOptions().position(cpk).title("car parking"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(10.937529, 76.958173)).title("Marker in car parking"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cpk));
        } else if (Objects.equals(loca, "krishna square")) {
            LatLng lb = new LatLng(10.937953, 76.955343);
            mMap.addMarker(new MarkerOptions().position(lb).title("Krishna Square"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lb));
        } else if (Objects.equals(loca, "convention centre") || Objects.equals(loca, "convention") || Objects.equals(loca, "convention center")) {
            LatLng cc = new LatLng(10.938492, 76.956521);
            mMap.addMarker(new MarkerOptions().position(cc).title(" convention-center"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cc));
        } else if (Objects.equals(loca, "food court") || Objects.equals(loca, "jmr")) {
            LatLng fc = new LatLng(10.938679, 76.956587);
            mMap.addMarker(new MarkerOptions().position(fc).title("Marker in FOOD COURT"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(fc));
        } else if (Objects.equals(loca, "kr bakes")) {
            LatLng bk = new LatLng(10.938531, 76.956212);
            mMap.addMarker(new MarkerOptions().position(bk).title("Marker in KR bakes"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(bk));
        } else if (Objects.equals(loca, "atm") || Objects.equals(loca, "sbi atm")) {
            LatLng atm = new LatLng(10.938779, 76.956263);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in SBI ATM"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "spices of paragon") || Objects.equals(loca, "mexitoes")) {
            LatLng lb = new LatLng(10.938805, 76.956057);
            mMap.addMarker(new MarkerOptions().position(lb).title("Marker in Spices of paragon"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lb));
        } else if (Objects.equals(loca, "c5 block") || Objects.equals(loca, "c5")) {
            LatLng atm = new LatLng(10.937315, 76.955443);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in C5 block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "mba block") || Objects.equals(loca, "mba")) {
            LatLng mba = new LatLng(10.937394, 76.955651);
            mMap.addMarker(new MarkerOptions().position(mba).title("Marker in MBA Block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mba));
        } else if (Objects.equals(loca, "mca block") || Objects.equals(loca, "mca")) {
            LatLng mc = new LatLng(10.937341, 76.955660);
            mMap.addMarker(new MarkerOptions().position(mc).title("Marker in MCA Block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mc));
        } else if (Objects.equals(loca, "c3 block") || Objects.equals(loca, "c3")) {
            LatLng c3 = new LatLng(10.936944, 76.955814);
            mMap.addMarker(new MarkerOptions().position(c3).title("Marker in C3 BLOCK"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(c3));
        } else if (Objects.equals(loca, "civil block") || Objects.equals(loca, "civil")) {
            LatLng atm = new LatLng(10.936520, 76.955707);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in CIVIL BLOCK"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "cafe area") || Objects.equals(loca, "coffee day") || Objects.equals(loca, "cafe")) {
            LatLng atm = new LatLng(10.936089, 76.955544);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in CAFE"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "mechanical block") || Objects.equals(loca, "mechanical")) {
            LatLng atm = new LatLng(10.936137, 76.956137);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in Mechanical block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "c1 block") || Objects.equals(loca, "c1")) {
            LatLng atm = new LatLng(10.937178, 76.956083);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in C1 block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "c2 block") || Objects.equals(loca, "c2")) {
            LatLng atm = new LatLng(10.937208, 76.956797);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in C2 block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "it") || Objects.equals(loca, "cse") || Objects.equals(loca, "it/cse block") || Objects.equals(loca, "it block") || Objects.equals(loca, "cse block")) {
            LatLng atm = new LatLng(10.936584, 76.956470);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in IT/CSE BLOCK"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "mct") || Objects.equals(loca, "mct block") || Objects.equals(loca, "mechatronics block") || Objects.equals(loca, "mechatronics block")) {
            LatLng atm = new LatLng(10.936544, 76.956504);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in MCT BLOCK"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "eee block") || Objects.equals(loca, "eee")) {
            LatLng atm = new LatLng(10.936584, 76.956313);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in EEE BLOCK"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "ece block") || Objects.equals(loca, "ece")) {
            LatLng atm = new LatLng(10.936546, 76.956324);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in ECE BLOCK"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "skcet playground") || Objects.equals(loca, "playground")) {
            LatLng atm = new LatLng(10.937451, 76.957011);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in playground"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "basket ball court") || Objects.equals(loca, "basketball court") || Objects.equals(loca, "basketball")) {
            LatLng atm = new LatLng(10.936303, 76.957293);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in basketball court"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if (Objects.equals(loca, "sri krishna institute of management")) {
            LatLng atm = new LatLng(10.937475, 76.960250);
            mMap.addMarker(new MarkerOptions().position(atm).title("Marker in admin block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
        } else if(Objects.equals(loca, "volleyball court") || Objects.equals(loca, "volleyball"))
        {
            LatLng axis= new LatLng(10.936332, 76.957497);
            mMap.addMarker(new MarkerOptions().position(axis).title("volleyball court"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(axis));
        }
        else if (Objects.equals(loca, "admin block")) {
            LatLng adm = new LatLng(10.937730, 76.956282);
            mMap.addMarker(new MarkerOptions().position(adm).title("Marker in admin block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(adm));
        }
        else if (Objects.equals(loca, "boys hostel") || Objects.equals(loca, "boys hostel"))
        {
            LatLng bh=new LatLng(10.939011,76.961397);
            mMap.addMarker(new MarkerOptions().position(bh).title("marker in boys hostel"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(bh));

        }


        /*else {
            List<Address> addressList = null;
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.getMaxZoomLevel();
        }  */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // mMap.setMyLocationEnabled(true);
        // Add a marker in Sri Krishna and move the camera
        LatLng srikrish = new LatLng(10.936509, 76.956240);
        mMap.addMarker(new MarkerOptions().position(srikrish).title("Marker in Sri krishna college of engineering").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(srikrish));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        Toast.makeText(this, "Zoom in to view SKCET", Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Zoom in to view clearly", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }


    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            LatLng cur_loc = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(cur_loc).title("Marker in Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cur_loc));

            Toast.makeText(getApplicationContext(),"Current Location "+latitude+" "+longitude,Toast.LENGTH_LONG).show();

        } else {


            Toast.makeText(getApplicationContext(),"(Couldn't get the location. Make sure location is enabled on the device)",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

}


