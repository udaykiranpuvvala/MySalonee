package com.unik.salonee.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unik.salonee.R;
import com.unik.salonee.locations.GPSTracker;
import com.unik.salonee.utilities.PopUtilities;
import com.unik.salonee.utilities.Utility;
import com.unik.salonee.webservices.viewmodels.RegistrationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    RegistrationViewModel registrationViewModel;
    GPSTracker gpsTracker;

    EditText edtFullName;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtGender;
    EditText edtArea;
    Button btnSignIN;

    String countryCode;
    String phno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        gpsTracker = new GPSTracker(this);
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        getIntentData();
        initUI();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        countryCode = intent.getStringExtra("countryCode") + "";
        phno = intent.getStringExtra("phno") + "";
    }

    private void initUI() {

        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtGender = findViewById(R.id.edtGender);
        edtArea = findViewById(R.id.edtArea);
        btnSignIN = findViewById(R.id.btnSignIN);

        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderDialog();
            }
        });

        btnSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validated()) {
                    callAPIForRegistration();
                }
            }
        });
    }

    private void genderDialog() {
        ArrayList<String> genderList = new ArrayList<String>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Others");
        PopUtilities.dialogListShow(this, genderList, "Select Gender", edtGender);
    }

    private boolean validated() {
        boolean validated = false;

        if (Utility.isValueNullOrEmpty(edtFullName.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edtFullName, "Please Enter FullName");
            edtFullName.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edtEmail.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edtEmail, "Please Enter EMail Id");
            edtEmail.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edtPassword.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edtPassword, "Please Enter Password");
            edtPassword.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edtGender.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edtGender, "Please Select Gender");
            edtGender.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edtArea.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edtArea, "Please Enter Area");
            edtArea.requestFocus();
        } else {
            validated = true;
        }
        return validated;
    }


    public void callAPIForRegistration() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "" + edtFullName.getText().toString().trim());
        jsonObject.addProperty("mobile_code", "" + countryCode);
        jsonObject.addProperty("mobile", "" + phno);
        jsonObject.addProperty("area", "" + edtArea.getText().toString().trim());
        if (edtGender.getText().toString().trim().equals("Male")) {
            jsonObject.addProperty("gender", "1");
        } else {
            jsonObject.addProperty("gender", "2");
        }
        jsonObject.addProperty("email", "" + edtEmail.getText().toString().trim());
        jsonObject.addProperty("password", "" + edtPassword.getText().toString().trim());
        jsonObject.addProperty("latitude", "" + gpsTracker.getLatitude());
        jsonObject.addProperty("longitude", "" + gpsTracker.getLongitude());
        jsonObject.addProperty("device_token", "skjdbvkjsdskdbvhksd");

        registrationViewModel.getRegistrationResponseViewModel(jsonObject).observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(JsonElement jsonElement) {
                Utility.hideLoadingDialog();
                if (jsonElement != null) {
                    try {
                        JSONObject response = new JSONObject(jsonElement.toString());
                        Utility.showLog("Json", "" + response);

                        Toast.makeText(RegistrationActivity.this, "" + response.optString("message"), Toast.LENGTH_SHORT).show();

                        if (response.optString("status").equals("200")) {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}