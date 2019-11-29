package com.example.shubham.onlineattendance;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterStudent extends AppCompatActivity implements View.OnClickListener {
    private EditText et_fname, et_mname, et_lname, et_phone, et_address, et_email,et_password, et_cpassword, et_semester;
    Button Register;
    BluetoothAdapter bt=null;

    private static final String REGISTER_URL = "http://192.168.43.149/StudentsRegisterSuccess.php";
    private String fname, mname, lname, phonno, address, emails,passwors, cpassword,semester;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_student);
         bt= BluetoothAdapter.getDefaultAdapter();
         et_fname = (EditText) findViewById(R.id.fname);
         et_mname = (EditText) findViewById(R.id.mname);
         et_lname = (EditText) findViewById(R.id.lname);
         et_phone = (EditText) findViewById(R.id.phoneno);
         et_address = (EditText) findViewById(R.id.sphone);
         et_password = (EditText) findViewById(R.id.Username1);
         et_cpassword = (EditText) findViewById(R.id.Cpassword);
         et_email = (EditText) findViewById(R.id.emails);
         et_semester = (EditText) findViewById(R.id.number);
         Register = (Button) findViewById(R.id.reg);
         Register.setOnClickListener(this);
    }
    public void registeru()
    {
        initialize();
        if(!validate())
        {
            Toast.makeText(this, "Register Has Failed", Toast.LENGTH_SHORT).show();

        }
        else
        {

            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(mBluetoothAdapter==null)
            {
                Toast.makeText(this, "Your Device is not Support Bluetooth currently", Toast.LENGTH_SHORT).show();
            }
            else{
                mBluetoothAdapter.enable();
                String macAddress = mBluetoothAdapter.getAddress();
                register(fname,mname,lname,emails,phonno,address,passwors,semester,macAddress);
            }


        }
    }



    public boolean validate()
    {
        boolean valid = true;
        if(fname.isEmpty()||fname.length()<4||fname.length()>15)
        {
            et_fname.setError("Please Enter Valid Name");
            valid = false;
        }
        if(mname.isEmpty()||mname.length()<4||mname.length()>15)
        {
            et_mname.setError("Please Enter Valid Name");
            valid = false;
        }
        if(lname.isEmpty()||lname.length()<4||lname.length()>15)
        {
            et_lname.setError("Please Enter Valid Name");
            valid = false;
        }
        if(emails.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(emails).matches()||emails.length()<10||emails.length()>32)
        {
            et_email.setError("Please Enter valid Email id");
            valid = false;
        }
        if(address.isEmpty()||address.length()>100||address.length()<32)
        {
            et_address.setError("Plese proviide proper address");
            valid = false;
        }
        if(phonno.isEmpty()||phonno.length()<10||phonno.length()>10)
        {
            et_phone.setError("Please Enter minimum 10 digits");
            valid = false;
        }

        if (passwors.isEmpty() || passwors.length() < 8 || passwors.length() > 15) {
                et_password.setError("password must be enter in 8 to 15 degits");
                valid = false;
            }
        if(!cpassword.equals(passwors))
        {
                et_cpassword.setError("password not valid");
                valid = false;
        }
        if(semester.isEmpty()||semester.length()>1)
        {
            et_semester.setError("Please Enter minimum Valid Semester");
            valid = false;
        }
        return valid;
    }
    public void  initialize()
    {
        fname = et_fname.getText().toString().trim().toLowerCase();
        mname = et_mname.getText().toString().trim().toLowerCase();
        lname = et_lname.getText().toString().trim().toLowerCase();
        phonno = et_phone.getText().toString();
        address = et_address.getText().toString().trim().toLowerCase();
        passwors = et_password.getText().toString();
        cpassword = et_cpassword.getText().toString();
        semester = et_semester.getText().toString();

      emails = et_email.getText().toString();
    }

    @Override
    public void onClick(View v)
    {

        registeru();

    }
    private void register(String fname, String mname, String lname, final String emails, final String phonno, String address, String passwors, String semester, String macAddress) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterStudent.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("successfully registered"))
                {
                    Intent in = new Intent(getApplicationContext(), StudentView.class);
                    startActivity(in);
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                }
                else
                {

                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("fname",params[0]);
                data.put("mname",params[1]);
                data.put("lname",params[2]);
                data.put("emails",params[3]);
                data.put("phonno",params[4]);
                data.put("address",params[5]);
                data.put("passwors",params[6]);
                data.put("semester",params[7]);
                data.put("macAddress",params[8]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(fname,mname,lname,emails,phonno,address,passwors,semester,macAddress);
    }

}
