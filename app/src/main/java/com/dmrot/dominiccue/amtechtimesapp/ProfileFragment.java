package com.dmrot.dominiccue.amtechtimesapp;

/**
 * Created by dominiccue on 10/7/2017.
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dmrot.dominiccue.amtechtimesapp.apiservices.ApiService;
import com.dmrot.dominiccue.amtechtimesapp.clients.ApiClient;
import com.dmrot.dominiccue.amtechtimesapp.gps_codinates.TrackProf;
import com.dmrot.dominiccue.amtechtimesapp.mapstutorial.MapsActivity;
import com.dmrot.dominiccue.amtechtimesapp.model.InsertTaskResponseModel;
import com.dmrot.dominiccue.amtechtimesapp.model.ServerRequest;
import com.dmrot.dominiccue.amtechtimesapp.model.ServerResponse;
import com.dmrot.dominiccue.amtechtimesapp.model.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private TextView tv_user, tv_message;
    private SharedPreferences pref;
    private AppCompatButton btn_chg_password, btn_logout, btn_addnewtask, btn_viewtask;
    private EditText et_old_password, et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    private ProgressDialog progressDialog;
    //AutoCompleteTextView acTextView;
    String[] Places = {"Secondary application", "College application", "Universirty application"};

String location;
    double longitude;
    double latitude;
    private TrackProf gps;
    //private String loc;
    protected static final String TAG = "LocationOnOff";


    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // loc=this.getArguments().getString("loc");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getPreferences(0);
        tv_user.setText(pref.getString(Constants.EMAIL, ""));
    }

    private void initViews(View view) {

        tv_user = (TextView) view.findViewById(R.id.tv_user);
        btn_chg_password = (AppCompatButton) view.findViewById(R.id.btn_chg_password);
        btn_logout = (AppCompatButton) view.findViewById(R.id.btn_logout);
        btn_addnewtask = (AppCompatButton) view.findViewById(R.id.btn_addnewtask);
        btn_viewtask = (AppCompatButton) view.findViewById(R.id.btn_viewtaskhistory);
        btn_addnewtask.setOnClickListener(this);
        btn_viewtask.setOnClickListener(this);
        btn_chg_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Submitting your application request");
        progressDialog.setMessage("Please wait ....");
    }

    private void enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(getActivity(), REQUEST_LOCATION);

                                getActivity().finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    private boolean hasGPSDevice(Activity activity) {
        final LocationManager mgr = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void showChangePassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText) view.findViewById(R.id.et_old_password);
        et_new_password = (EditText) view.findViewById(R.id.et_new_password);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if (!old_password.isEmpty() && !new_password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    changePasswordProcess(pref.getString(Constants.EMAIL, ""), old_password, new_password);

                } else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("MissingPermission")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chg_password:
                Intent intent= new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.btn_addnewtask:
                gps = new TrackProf(getActivity());
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Log.d("location", String.valueOf(longitude));
                    showAddTaskDialog();
                    //Get USer location, save Case Number
                } else {
                    gps.showSettingsAlert();
                }
                break;
            case R.id.btn_viewtaskhistory:
                gotToviewTask();

                break;


        }
    }

    private void gotToviewTask() {
        Intent i = new Intent(getActivity(), TaskMainActivity.class);
        String email = pref.getString(Constants.EMAIL, "");

//Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("email", email);

//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_addtask, null);
        builder.setView(view);
        builder.setTitle("Emergency Request");

        //final EditText et_tdate = (EditText) view.findViewById(R.id.et_date);
        //SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        //et_tdate.setText( sdf.format( new Date() ));

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_idno = (EditText) view.findViewById(R.id.et_idno);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);
        final EditText et_ambulance = (EditText) view.findViewById(R.id.et_ambulance);
        final EditText et_appoint = (EditText) view.findViewById(R.id.et_appoint);
        final EditText et_loca = (EditText) view.findViewById(R.id.et_loca);
        final EditText et_patient = (EditText) view.findViewById(R.id.et_patient);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice, Places);
               //acTextView.setThreshold(1);
        //Set the adapter
       // acTextView.setAdapter(adapter);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                String email = tv_user.getText().toString();
                String name = et_name.getText().toString();
                String idno = et_idno.getText().toString();
                String phone = et_phone.getText().toString();
                String ambulance = et_ambulance.getText().toString();
                String appoint = et_appoint.getText().toString();
                String loca = et_loca.getText().toString();
                String patient = et_patient.getText().toString();
                String location=latitude+","+longitude;
                progressDialog.show();
                if (TextUtils.isEmpty(name)) {
                    progressDialog.dismiss();
                    Snackbar.make(getView(), "Name is required", Snackbar.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(idno)) {
                    progressDialog.dismiss();
                    Snackbar.make(getView(), "id is required", Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, "Food Quantity is required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone)) {
                    progressDialog.dismiss();
                    Snackbar.make(getView(), "phone number is required", Snackbar.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(ambulance)) {
                    progressDialog.dismiss();
                    Snackbar.make(getView(), "Ambulance  is required", Snackbar.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(appoint)) {
                    progressDialog.dismiss();
                    Snackbar.make(getView(), "Appointment is required", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(loca)) {
                    progressDialog.dismiss();
                    Snackbar.make(getView(), "physical location  is required", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(patient)) {
                    progressDialog.dismiss();
                    Snackbar.make(getView(), "Number of patients  is required", Snackbar.LENGTH_LONG).show();

                } else if (location.equalsIgnoreCase("0.0,0.0")) {

                    progressDialog.dismiss();
                    //Snackbar.make(getView(), "We can't trace your location, please try again later", Snackbar.LENGTH_LONG).show();
                    getActivity().setFinishOnTouchOutside(true);

                    // Todo Location Already on  ... start
                    final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(getActivity())) {
                        Toast.makeText(getActivity(),"Gps already enabled",Toast.LENGTH_SHORT).show();
                        //getActivity().finish();
                    }
                    // Todo Location Already on  ... end

                    if(!hasGPSDevice(getActivity())){
                        Toast.makeText(getActivity(),"Gps not Supported",Toast.LENGTH_SHORT).show();
                    }

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(getActivity())) {
                        Log.e("keshav","Gps already enabled");
                        Toast.makeText(getActivity(),"Gps not enabled",Toast.LENGTH_SHORT).show();
                        enableLoc();
                    }else{
                        Log.e("keshav","Gps already enabled");
                        Toast.makeText(getActivity(),"Gps already enabled",Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(MainActivity.this, "Food Quantity is required", Toast.LENGTH_SHORT).show();
                }else {
                    insertData(email, name, idno , phone , ambulance , loca , patient , location);
                }
            }



            private void insertData(String email, String name, String idno, String phone,String ambulance,String loca,String patient, String location) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<InsertTaskResponseModel> call = apiService.insertTask(email, name, idno , phone , ambulance , loca , patient,location);
                call.enqueue(new Callback<InsertTaskResponseModel>() {
                    @Override
                    public void onResponse(Call<InsertTaskResponseModel> call, Response<InsertTaskResponseModel> response) {

                        InsertTaskResponseModel insertTaskResponseModel = response.body();

                        //check the status code
                        if (insertTaskResponseModel.getStatus() == 1) {
                            Snackbar.make(getView(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Snackbar.make(getView(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertTaskResponseModel> call, Throwable t) {
                        Snackbar.make(getView(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void logout() {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean(Constants.IS_LOGGED_IN, false);
                        editor.putString(Constants.EMAIL, "");
                        editor.putString(Constants.NAME, "");
                        editor.putString(Constants.UNIQUE_ID, "");
                        editor.apply();
                        goToLogin();
                    }
                }).create().show();
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, login);
        ft.commit();
    }

    private void changePasswordProcess(String email, String old_password, String new_password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User("","","","","","","","",old_password,new_password,"");
        ServerRequest request = new ServerRequest(Constants.CHANGE_PASSWORD_OPERATION,user);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if (resp.getResult().equals(Constants.SUCCESS)) {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                } else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, "failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());


            }
        });
    }

}

