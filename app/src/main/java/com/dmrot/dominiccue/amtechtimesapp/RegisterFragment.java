package com.dmrot.dominiccue.amtechtimesapp;

/**
 * Created by dominiccue on 10/7/2017.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.dmrot.dominiccue.amtechtimesapp.model.ServerRequest;
import com.dmrot.dominiccue.amtechtimesapp.model.ServerResponse;
import com.dmrot.dominiccue.amtechtimesapp.model.User;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.Menu;
import android.view.MenuItem;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton btn_register;
    private EditText et_email, et_password, et_name, et_country, et_confirmpass ,et_design, et_role;
    private TextView tv_login;
    private ProgressBar progress;
    private Spinner acTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        //Find TextView control
        acTextView = (Spinner) view.findViewById(R.id.et_branch);
        return view;
    }

    private void initViews(View view) {

        btn_register = (AppCompatButton) view.findViewById(R.id.btn_register);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_country = (EditText) view.findViewById(R.id.et_country);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_design = (EditText) view.findViewById(R.id.et_design);
        et_role = (EditText) view.findViewById(R.id.et_role);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_confirmpass = (EditText) view.findViewById(R.id.et_confirm);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:
                String name = et_name.getText().toString();
                String country = et_country.getText().toString();
                final Pattern pattern = Pattern.compile("07(\\d){8}");
                String email = et_email.getText().toString();
                String design = et_design.getText().toString();
                String role = et_role.getText().toString();
                String password = et_password.getText().toString();
                String coDomain = "gmail.com";
                String confirmpass = et_confirmpass.getText().toString();
                String branch = acTextView.getSelectedItem().toString();


                if (name.isEmpty() && country.isEmpty() && email.isEmpty() && password.isEmpty() && design.isEmpty()&& role.isEmpty()) {
                    Snackbar.make(getView(), "Field(s) are empty !", Snackbar.LENGTH_LONG).show();
                } else if (!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + Pattern.quote(coDomain) + "$")) {
                    Snackbar.make(getView(), "Email should be of the form @gmail.com", Snackbar.LENGTH_LONG).show();
                } else if (!password.equals(confirmpass)) {
                    Snackbar.make(getView(), "Password don't match", Snackbar.LENGTH_LONG).show();

            }else if(password.length()<8){
                    Snackbar.make(getView(), "Password should have a minimum of 8 characters", Snackbar.LENGTH_LONG).show();
                }else {
                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name, country, email, password,branch ,role,design);

                }
                break;

        }

    }
    private void registerProcess(String name, String country, String email, String password, String branch,String design,String role) {

//Log.d("name",name);Log.d("country",country);Log.d("email",email);Log.d("password",password);Log.d("branch",branch);Log.d("design",design);Log.d("role",role);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User(name,country,role,email,branch,branch,"",password,"","","");
        ServerRequest request = new ServerRequest(Constants.REGISTER_OPERATION,user);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(Objects.requireNonNull(getView()), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
                goToLogin();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                //Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, login).addToBackStack(null);
        ft.commit();
    }
}

