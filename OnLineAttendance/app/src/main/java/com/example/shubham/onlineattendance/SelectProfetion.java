package com.example.shubham.onlineattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectProfetion extends AppCompatActivity  {
    RadioGroup option;
    RadioButton student, teacher, parent;
    Button continue1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_profetion);
        option = (RadioGroup) findViewById(R.id.Rgbutoon1);
        student = (RadioButton) findViewById(R.id.Students);
        teacher = (RadioButton) findViewById(R.id.teacher);
        parent = (RadioButton) findViewById(R.id.parents);
        continue1 = (Button) findViewById(R.id.continues);
        option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if(checkedId == R.id.Students) {

                    Toast.makeText(getApplicationContext(), "You are a student",

                            Toast.LENGTH_SHORT).show();

                } else if(checkedId == R.id.teacher) {

                    Toast.makeText(getApplicationContext(), "You are a teacher",

                            Toast.LENGTH_SHORT).show();

                } else if(checkedId == R.id.parents) {

                    Toast.makeText(getApplicationContext(), "You are a parent",

                            Toast.LENGTH_SHORT).show();

                }

            }

        });
        continue1.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int selectedId = option.getCheckedRadioButtonId();

                        // find which radioButton is checked by id

                        if(selectedId == student.getId())
                        {

                            Intent myIntent =  new Intent(v.getContext(), RegisterStudent.class);
                            startActivity(myIntent);
                        }
                        else if(selectedId == teacher.getId())
                        {

                            Intent myIntent =  new Intent(v.getContext(), RegisterTeacher.class);
                            startActivity(myIntent);

                        }
                        else if(selectedId == parent.getId())
                        {


                            Intent myIntent =  new Intent(v.getContext(), RegisterParents.class);
                            startActivity(myIntent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Please select any one profession",

                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                }
        );

    }



}
