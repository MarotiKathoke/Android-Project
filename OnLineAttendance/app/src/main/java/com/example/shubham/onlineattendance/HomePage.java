package com.example.shubham.onlineattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePage extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        final Button LoginPage = (Button) findViewById(R.id.enjoy);
        LoginPage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
        {
            Toast.makeText(v.getContext(), "You acceped licence",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this ,LoginPage.class );
            startActivity(intent);

        }

    }

