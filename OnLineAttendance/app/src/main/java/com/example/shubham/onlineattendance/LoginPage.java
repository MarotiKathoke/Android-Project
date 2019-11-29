package com.example.shubham.onlineattendance;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LoginPage extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
    EditText etPassword, etUsername;
    Button Login1;
    RadioButton forgpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        etUsername = (EditText) findViewById(R.id.Username1);
        etPassword = (EditText) findViewById(R.id.Password1);
        forgpassword = (RadioButton) findViewById(R.id.forgetpassword);
        Login1 = (Button) findViewById(R.id.LoginButton);
        final Button Register = (Button) findViewById(R.id.button);
        Login1.setOnClickListener(this);
        Register.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(), SelectProfetion.class);
                        startActivity(myIntent);
                    }
                });
        forgpassword.setOnClickListener(this);
        String email = null;

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

        TextView viewText = (TextView) findViewById(R.id.Username1);
        viewText.setText(email);
    }


    @Override
    public void processFinish(String result)
    {


        switch (result) {

            case "S": {
                Intent in = new Intent(this, StudentView.class);
                startActivity(in);
                Toast.makeText(this, "Loging Success", Toast.LENGTH_SHORT).show();
                break;
            }
            case "T":
             {
                Intent in = new Intent(this, TeacherView.class);
                startActivity(in);
                Toast.makeText(this, "Loging Success", Toast.LENGTH_SHORT).show();
                 break;

            }
            case "P":
            {
                Intent in = new Intent(this, ParentView.class);
                startActivity(in);
                Toast.makeText(this, "Loging Success", Toast.LENGTH_SHORT).show();
                break;

            }
            default:
            {
                Toast.makeText(this, "Please check your Username or password" + result, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onClick(View v) {

        int selectedid = v.getId();
        switch (selectedid){
            case R.id.forgetpassword:
            {

                Intent myIntent = new Intent(v.getContext(),PasswordVerification.class);
                startActivity(myIntent);
                break;
            }
            case R.id.LoginButton:
            {
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtUsername", etUsername.getText().toString());
                postData.put("txtPassword", etPassword.getText().toString());
                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute("http://192.168.43.149/studentslogin.php");
                break;
            }
        }

    }
}