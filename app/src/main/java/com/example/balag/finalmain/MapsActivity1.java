package com.example.balag.finalmain;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class MapsActivity1 extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    double end_latitude, end_longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);
        int s = getIntent().getIntExtra("val", 0);
        switch (s) {
            case 1: {
                LatLng adm = new LatLng(10.938637, 76.958999);
                mMap.addMarker(new MarkerOptions().position(adm).title("Marker in noothuku muttai"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(adm));
                LatLng cm = new LatLng(10.936087, 76.955544);
                mMap.addMarker(new MarkerOptions().position(cm).title("Marker in cafe area"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(cm));
                LatLng fc = new LatLng(10.938679, 76.956587);
                mMap.addMarker(new MarkerOptions().position(fc).title("Marker in food court"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(fc));
                LatLng mex = new LatLng(10.938805, 76.956057);
                mMap.addMarker(new MarkerOptions().position(mex).title("Marker in mexitos"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mex));
                LatLng adm1 = new LatLng(10.934778, 76.953847);
                mMap.addMarker(new MarkerOptions().position(adm1).title("Marker in noothuku muttai"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(adm1));
                LatLng axis= new LatLng(10.938637, 76.959078);
                mMap.addMarker(new MarkerOptions().position(axis).title("near boy's noothu muttai"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(axis));
                LatLng axis1= new LatLng(10.936223, 76.955569);
                mMap.addMarker(new MarkerOptions().position(axis1).title("near cafe noothu muttai"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(axis1));
                break;
            }
            case 2: {
                LatLng mc = new LatLng(10.937341, 76.955660);
                mMap.addMarker(new MarkerOptions().position(mc).title("Marker in MCA Block"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mc));
                LatLng atm1 = new LatLng(10.937315, 76.955443);
                mMap.addMarker(new MarkerOptions().position(atm1).title("Marker in C5 block"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(atm1));
                LatLng c3 = new LatLng(10.936944, 76.955814);
                mMap.addMarker(new MarkerOptions().position(c3).title("Marker in C3 BLOCK"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(c3));
                LatLng atm = new LatLng(10.936520, 76.955707);
                mMap.addMarker(new MarkerOptions().position(atm).title("Marker in CIVIL BLOCK"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(atm));
                LatLng man = new LatLng(10.936137, 76.956137);
                mMap.addMarker(new MarkerOptions().position(man).title("Marker in Mechanical block"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(man));
                LatLng m1 = new LatLng(10.937178, 76.956083);
                mMap.addMarker(new MarkerOptions().position(m1).title("Marker in C1 block"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(m1));
                LatLng m2 = new LatLng(10.937208, 76.956797);
                mMap.addMarker(new MarkerOptions().position(m2).title("Marker in C2 block"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(m2));
                break;
            }
            case 3: {
                LatLng m2 = new LatLng(10.936569, 76.955656);
                mMap.addMarker(new MarkerOptions().position(m2).title("Marker in washroom near C3 block"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(m2));
                break;
            }
            case 4:
            {
                LatLng cc = new LatLng(10.938492, 76.956521);
                mMap.addMarker(new MarkerOptions().position(cc).title(" convention-center"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(cc));
                LatLng kh = new LatLng(10.939006, 76.958999);
                mMap.addMarker(new MarkerOptions().position(kh).title(" Krishna Hall"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(kh));
                LatLng kh1 = new LatLng(10.936654, 76.956127);
                mMap.addMarker(new MarkerOptions().position(kh1).title("BS-03"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(kh1));
                break;
            }
        }
    }

    public void onClick(View v) {
        Object dataTransfer[];
        switch (v.getId()) {
            case R.id.button5:
                dataTransfer = new Object[3];
                String url = getDirectionsUrl();
                GetDirectionsData getDirectionsData = new GetDirectionsData();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = new LatLng(end_latitude, end_longitude);
                getDirectionsData.execute(dataTransfer);
                Toast.makeText(MapsActivity1.this, "Fetching routes", Toast.LENGTH_SHORT).show();
                break;


        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private String getDirectionsUrl() {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + latitude + "," + longitude);
        googleDirectionsUrl.append("&destination=" + end_latitude + "," + end_longitude);
        googleDirectionsUrl.append("&key=" + "AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g");

        return googleDirectionsUrl.toString();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();


        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        Toast.makeText(MapsActivity1.this, "Your Current Location", Toast.LENGTH_LONG).show();


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        end_latitude = marker.getPosition().latitude;
        end_longitude = marker.getPosition().longitude;

        Log.d("end_lat", "" + end_latitude);
        Log.d("end_lng", "" + end_longitude);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void search(View view) {

        EditText locationSearch = (EditText) findViewById(R.id.editText3);
        String location = locationSearch.getText().toString();
        String loca = location.toLowerCase();
        if (Objects.equals(location, "axis") || Objects.equals(location, "axis bank")) {
            LatLng axis = new LatLng(10.938878, 76.952286);
            mMap.addMarker(new MarkerOptions().position(axis).title("Marker in Axis bank"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(axis));

        } else if (Objects.equals(location, "krishna hall") || Objects.equals(location, "hall")) {
            LatLng kh = new LatLng(10.939006, 76.958999);
            mMap.addMarker(new MarkerOptions().position(kh).title("Marker in Krishna Hall"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(kh));

        } else if (Objects.equals(loca, "library") || Objects.equals(loca, "library-skcet") || Objects.equals(loca, "vankatram library")) {
            LatLng lb = new LatLng(10.938454, 76.956076);
            mMap.addMarker(new MarkerOptions().position(lb).title("Marker in SKCET-library"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lb));

        } else if (Objects.equals(loca, "parking") || Objects.equals(loca, "student parking") || Objects.equals(loca, "two wheeler parking")) {
            LatLng pk = new LatLng(10.937975, 76.954878);
            mMap.addMarker(new MarkerOptions().position(pk).title("Marker in parking"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(10.938158, 76.954858)).title("Marker in parking"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pk));
        } else if (Objects.equals(loca, "car parking")) {
            LatLng cpk = new LatLng(10.937764, 76.954344);
            mMap.addMarker(new MarkerOptions().position(cpk).title("Marker in car parking"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(10.937529, 76.958173)).title("Marker in car parking"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cpk));
        } else if (Objects.equals(loca, "krishna square")) {
            LatLng lb = new LatLng(10.937953, 76.955343);
            mMap.addMarker(new MarkerOptions().position(lb).title("Marker in Krishna Square"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lb));
        } else if (Objects.equals(loca, "convention centre") || Objects.equals(loca, "convention") || Objects.equals(loca, "convention center")) {
            LatLng cc = new LatLng(10.938492, 76.956521);
            mMap.addMarker(new MarkerOptions().position(cc).title("Marker in convention-center"));
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
        } else if (Objects.equals(loca, "admin block")) {
            LatLng adm = new LatLng(10.937730, 76.956282);
            mMap.addMarker(new MarkerOptions().position(adm).title("Marker in admin block"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(adm));
        } else {
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
        }
    }
}
