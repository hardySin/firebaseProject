package com.example.d3ll.firebaseproject.fragment_class;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.d3ll.firebaseproject.R;
import com.example.d3ll.firebaseproject.chatPage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Requests extends Fragment implements LocationListener {

    GoogleApiClient googleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 214;
    private static final int REQUEST_ENABLE_GPS = 516;
    private Context context;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    @BindView(R.id.enableLocation)
    Button enableLocation;

    @BindView(R.id.locations)
            TextView locations;
    String wayLatitude,wayLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, view);
        init();
         return view;
    }

    @OnClick(R.id.enableLocation)
    public void enableLocation() {
         locationCheck();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public  void init()
    {
        locationRequest = new LocationRequest();
         locationRequest.setFastestInterval(4000);
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

          mSettingsClient = LocationServices.getSettingsClient(getContext());
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getContext());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
               // location is received
                mCurrentLocation = locationResult.getLastLocation();
                Toast.makeText(getContext(),"location coordinates :"+mCurrentLocation.getLatitude()+","+mCurrentLocation.getLongitude(),Toast.LENGTH_LONG).show();
         //       mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

            }
        };
    }

    public boolean enableLoc() {
        final boolean[] val = {false};
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Success Perform Task Here
                        val[0] =true;
                    //    Toast.makeText(context, "Success method fired :permission" + locationCheck(), Toast.LENGTH_LONG).show();
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                        locationCallback, Looper.myLooper());
//                        Toast.makeText(getContext(),"location coordinates :"+mCurrentLocation.getLatitude()+","+mCurrentLocation.getLongitude(),Toast.LENGTH_LONG).show();
                          }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        Toast.makeText(context, "resolution faliure fired", Toast.LENGTH_LONG).show();

                                        ResolvableApiException rae = (ResolvableApiException) e;
                                        rae.startResolutionForResult((AppCompatActivity)context, REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException sie) {
                                        Toast.makeText(context, "Unable to execute request", Toast.LENGTH_LONG).show();
                                     }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    Toast.makeText(context, "Location settings are inadequate, and cannot be fixed here. Fix in Settings", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(context, "cancel listener fired ", Toast.LENGTH_LONG).show();

                         }
                    });

        return  val[0];
        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "result ok", Toast.LENGTH_LONG).show();

                        //Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "result cancelled", Toast.LENGTH_LONG).show();

                        //  Log.e(TAG, "User chose not to make required location settings changes.");
                         break;
                }
                break;
        }
       if (requestCode == REQUEST_ENABLE_GPS)
    {
        Toast.makeText(context, "GPS enable ", Toast.LENGTH_LONG).show();
    }
    }


    public boolean locationCheck()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED )
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},101);
            }
            else
            {

                enableLoc();
                return  true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length >0) {
                    boolean coarse = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean fine = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (coarse  && fine) {
                    }

                    else if(shouldShowRequestPermissionRationale(permissions[0]))
                    {
                    }
                    else
                    {
                        Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.requesthead), "Allow Permission", Snackbar.LENGTH_LONG)
                                .setAction("Setting", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        final Intent i = new Intent();
                                        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        i.addCategory(Intent.CATEGORY_DEFAULT);
                                        i.setData(Uri.parse("package:" + context.getPackageName()));
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //new activty open flag activity new task
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        //no histort avaiable where recent pages avaliable
                                        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                        // direct back to pervious activity or fragment
                                        context.startActivity(i);

                                    }
                                });
                        View view = snackbar.getView();
                        snackbar.setActionTextColor(Color.RED);
                        final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                        snackbar.show();
                    }

                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
            if(enableLoc())
            {
                Toast.makeText(getContext(),"Longitude latitude :"+location.getLongitude()+" "+location.getLatitude(),Toast.LENGTH_LONG).show();
            }
    }
}

