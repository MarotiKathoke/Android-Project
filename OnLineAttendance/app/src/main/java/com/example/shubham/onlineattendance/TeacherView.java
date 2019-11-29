package com.example.shubham.onlineattendance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.example.shubham.onlineattendance.R.id.CheckResult;
import static com.example.shubham.onlineattendance.R.id.GetAttendance;
import static com.example.shubham.onlineattendance.R.id.UploadAudioandVideos;
import static com.example.shubham.onlineattendance.R.id.UploadCATAss;
import static com.example.shubham.onlineattendance.R.id.UploadQuestion;

public class TeacherView extends AppCompatActivity {
    RadioGroup groupp;
    RadioButton et_GetAttendance,et_UploadNotes,et_UploadCATAss,et_UploadAudioandVideos,et_UploadQuestion,et_CheckResult;
    Button et_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_view);
        groupp = (RadioGroup) findViewById(R.id.radioGroup);
        et_GetAttendance = (RadioButton) findViewById(R.id.GetAttendance);
        et_UploadNotes = (RadioButton) findViewById(R.id.UploadNotes);
        et_UploadAudioandVideos = (RadioButton) findViewById(R.id.UploadAudioandVideos);
        et_UploadCATAss = (RadioButton) findViewById(R.id.UploadCATAss);
        et_UploadQuestion = (RadioButton) findViewById(R.id.UploadQuestion);
        et_CheckResult = (RadioButton) findViewById(R.id.CheckResult);
        et_continue = (Button) findViewById(R.id.TContinue);
        groupp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if(checkedId == GetAttendance) {

                    Toast.makeText(getApplicationContext(), "You Select GetAttendance",

                            Toast.LENGTH_SHORT).show();

                } else if(checkedId == R.id.UploadNotes) {

                    Toast.makeText(getApplicationContext(), "You Select Upload Notes",

                            Toast.LENGTH_SHORT).show();

                } else if(checkedId == UploadAudioandVideos) {

                    Toast.makeText(getApplicationContext(), "You Select Upload Audio/Vidios",

                            Toast.LENGTH_SHORT).show();

                }
                else if(checkedId == UploadCATAss) {

                    Toast.makeText(getApplicationContext(), "You Select Sixth Semester",

                            Toast.LENGTH_SHORT).show();

                }
                else if(checkedId == UploadQuestion) {

                    Toast.makeText(getApplicationContext(), "You Select UploadUniversityQuestion",

                            Toast.LENGTH_SHORT).show();

                }
                else if(checkedId == CheckResult) {

                    Toast.makeText(getApplicationContext(), "You Select CheckResult",

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
                        switch (selectedId) {
                            case R.id.GetAttendance: {

                                Intent myIntent = new Intent(v.getContext(), SelectSemester.class);
                                startActivity(myIntent);
                                break;
                            }
                            case R.id.UploadNotes: {

                                Intent myIntent = new Intent(v.getContext(), UploadNotes.class);
                                startActivity(myIntent);
                                break;
                            }
                            case R.id.UploadAudioandVideos: {


                                Intent myIntent = new Intent(v.getContext(), UploadAudioVideo.class);
                                startActivity(myIntent);
                                break;

                            }
                            case R.id.UploadCATAss: {


                                Intent myIntent = new Intent(v.getContext(), UploadCatsAssignments.class);
                                startActivity(myIntent);
                                break;

                            }
                            case R.id.UploadQuestion: {

                                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                                myWebLink.setData(Uri.parse("https://nagpurstudents.org/downloads/downloads.php?_r_direct=un"));
                                startActivity(myWebLink);
                                break;


                            }
                            case R.id.CheckResult: {


                                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                                myWebLink.setData(Uri.parse("http://results.rtmnuresults.org/Default.aspx"));
                                startActivity(myWebLink);
                                break;

                            }
                            default: {
                                Toast.makeText(getApplicationContext(), "Please select any one Semester",

                                        Toast.LENGTH_SHORT).show();
                                break;

                            }
                        }
                    }


                }
        );


    }
}
