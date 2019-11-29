package com.example.shubham.onlineattendance;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class PasswordVerification extends AppCompatActivity implements View.OnClickListener {

    private Button Verify;
    EditText et_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_verification);

         Verify = (Button) findViewById(R.id.PassVerify);
        et_text = (EditText) findViewById(R.id.mobileno);


        Verify.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
       String phone = et_text.getText().toString();
        if(phone.isEmpty()||phone.length()!=10){
            et_text.setError("mobile no has Wrong");
        }
        else
        {

                getData();



        }
    }

    private void getData() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Your Device is not Support Bluetooth currently", Toast.LENGTH_SHORT).show();
        } else {
            mBluetoothAdapter.enable();

            String macAddress = mBluetoothAdapter.getAddress();


            final ProgressDialog loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

            String url = Config.DATA_URL + macAddress;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PasswordVerification.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void showJSON(String response){
        String email = null;
        String Password="";
        String Emails="";
        String PhoneNo="";
        Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (gmailPattern.matcher(account.name).matches()) {
                email = account.name;
            }
        }

        TextView viewEmail = (TextView) findViewById(R.id.Username1);
        viewEmail.setText(email);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
             Password = collegeData.getString(Config.KEY_NAME);
             Emails = collegeData.getString(Config.KEY_Email);
             PhoneNo = collegeData.getString(Config.KEY_Phone);
            if(Password.equals("")) {
                Toast.makeText(this, "Server Error Try Again", Toast.LENGTH_SHORT).show();

            }else{
                String phone = et_text.getText().toString();
                if(PhoneNo.equals(phone))
                {
                    if(Emails.equals(email))
                    {
                        EditText str = (EditText) findViewById(R.id.Cpassword);
                        str.setText(Password);
                    }
                    else
                    {
                        Toast.makeText(this, "Email Id Has Wrong Please login with diffrents Email_id in Gemail", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(this, "Phone No Has Wrong Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
