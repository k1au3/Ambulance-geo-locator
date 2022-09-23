package com.dmrot.dominiccue.amtechtimesapp;

/**
 * Created by dominiccue on 10/16/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dmrot.dominiccue.amtechtimesapp.adapters.DateAdapter;
import com.dmrot.dominiccue.amtechtimesapp.adapters.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.dmrot.dominiccue.amtechtimesapp.Constants.BASE_URL;

public class TaskMainActivity extends AppCompatActivity {

    List<DateAdapter> DataAdapterClassList;

    RecyclerView recyclerView;

    TextView tv_month;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    JsonArrayRequest jsonArrayRequest;

    ArrayList<String> Remarks;

    RequestQueue requestQueue;

    String HTTP_SERVER_URL = BASE_URL + "/gps/gps/task/DisplayTask.php", email;

    View ChildView;

    int RecyclerViewClickedItemPOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_task);

        DataAdapterClassList = new ArrayList<>();

        Remarks = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        email = bundle.getString("email");


        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);tv_month=(TextView)findViewById(R.id.tv_month);


        // JSON data web call function call from here.
        JSON_WEB_CALL();

        //RecyclerView Item click listener code starts from here.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(TaskMainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked item value.
                    RecyclerViewClickedItemPOS = Recyclerview.getChildAdapterPosition(ChildView);

                    //Printing RecyclerView Clicked item clicked value using Toast Message.
                    //Toast.makeText(TaskMainActivity.this, Remarks.get(RecyclerViewClickedItemPOS), Toast.LENGTH_LONG).show();

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });




    }

    public void JSON_WEB_CALL() {
        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
        };

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            DateAdapter GetDataAdapter2 = new DateAdapter();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                if (json.getString("email").equalsIgnoreCase(email)) {
                    GetDataAdapter2.setTdate(json.getString("name"));
                    GetDataAdapter2.setPlace(json.getString("idno"));
                    GetDataAdapter2.setAmbulance(json.getString("ambulance"));
                    GetDataAdapter2.setPatient(json.getString("patient"));
                    Remarks.add(json.getString("phone"));

                    DataAdapterClassList.add(GetDataAdapter2);
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        progressBar.setVisibility(View.GONE);

        Calendar mCalendar = Calendar.getInstance();
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int year=Calendar.getInstance().get(Calendar.YEAR);

        tv_month.setVisibility(View.VISIBLE);

        tv_month.setText("Requests Records \n For: "+email.toString()+" "+"Month: "+month.toString()+","+String.valueOf(year));

        recyclerViewadapter = new RecyclerViewAdapter(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}
