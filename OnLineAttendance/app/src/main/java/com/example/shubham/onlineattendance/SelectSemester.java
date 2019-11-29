package com.example.shubham.onlineattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectSemester extends AppCompatActivity {
    RadioGroup groupp;
    RadioButton et_third,et_forth,et_fifth,et_sixth,et_seventh,et_eight;
    Button et_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_semester);
        groupp = (RadioGroup) findViewById(R.id.Rgbutoon);
        et_third = (RadioButton) findViewById(R.id.Third);
        et_forth = (RadioButton) findViewById(R.id.Forth);
        et_fifth = (RadioButton) findViewById(R.id.Fifths);
        et_sixth = (RadioButton) findViewById(R.id.sixths);
        et_seventh = (RadioButton) findViewById(R.id.Seventh);
        et_eight = (RadioButton) findViewById(R.id.Eights);
        et_continue = (Button) findViewById(R.id.Getattendance);
        groupp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if(checkedId == R.id.Third) {

                    Toast.makeText(getApplicationContext(), "You Select Third Semester",

                            Toast.LENGTH_SHORT).show();

                } else if(checkedId == R.id.Forth) {

                    Toast.makeText(getApplicationContext(), "You Select Fourth Semester",

                            Toast.LENGTH_SHORT).show();

                } else if(checkedId == R.id.Fifths) {

                    Toast.makeText(getApplicationContext(), "You Select Fifth Semester",

                            Toast.LENGTH_SHORT).show();

                }
                else if(checkedId == R.id.sixths) {

                    Toast.makeText(getApplicationContext(), "You Select Sixth Semester",

                            Toast.LENGTH_SHORT).show();

                }
                else if(checkedId == R.id.Seventh) {

                    Toast.makeText(getApplicationContext(), "You Select Seventh Semester",

                            Toast.LENGTH_SHORT).show();

                }
                else if(checkedId == R.id.Eights) {

                    Toast.makeText(getApplicationContext(), "You Select Eights Semester",

                            Toast.LENGTH_SHORT).show();

                }

            }

        });
        et_continue.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int selectedId =  groupp.getCheckedRadioButtonId();

                        // find which radioButton is checked by id

                        if(selectedId == et_third.getId())
                        {

                            Intent myIntent =  new Intent(v.getContext(), SelectSubject.class);
                            startActivity(myIntent);
                        }
                        else if(selectedId == et_forth.getId())
                        {

                            Intent myIntent =  new Intent(v.getContext(), SubjectFourth.class);
                            startActivity(myIntent);

                        }
                        else if(selectedId == et_fifth.getId())
                        {


                            Intent myIntent =  new Intent(v.getContext(), SubjectFifth.class);
                            startActivity(myIntent);

                        }
                        else if(selectedId == et_sixth.getId())
                        {


                            Intent myIntent =  new Intent(v.getContext(), SubjectSixth.class);
                            startActivity(myIntent);

                        }
                        else if(selectedId == et_seventh.getId())
                        {


                            Intent myIntent =  new Intent(v.getContext(), SubjectSeventh.class);
                            startActivity(myIntent);

                        }
                        else if(selectedId == et_eight.getId())
                        {


                            Intent myIntent =  new Intent(v.getContext(), SubjectEiegth.class);
                            startActivity(myIntent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Please select any one Semester",

                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                }
        );

    }
}
