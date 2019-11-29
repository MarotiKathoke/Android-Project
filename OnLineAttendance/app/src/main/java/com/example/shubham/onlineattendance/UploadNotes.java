package com.example.shubham.onlineattendance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.shubham.onlineattendance.R.id.spinner;

public class UploadNotes extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
Spinner sem,subject,type;
    EditText filename,date;
    Button Submit;

    private String semester = null;
    private String fileTypes = null;
    private String subjects = null;
    public static final String DATA_URL = "http://192.168.43.149/subjectlist.php?semes=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_notes);
        sem = (Spinner) findViewById(spinner);
       subject = (Spinner) findViewById(R.id.spinner1);
        type = (Spinner) findViewById(R.id.spinner2);
        filename = (EditText) findViewById(R.id.edit1);
        date = (EditText) findViewById(R.id.edit2);
        Submit = (Button) findViewById(R.id.submit1);
        sem.setOnItemSelectedListener(this);
        subject.setOnItemSelectedListener(this);
        type.setOnItemSelectedListener(this);
        selectSemester();
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text122, listItems);
        Submit.setOnClickListener(this);

      
    }

    @Override
    public void onClick(View v) {


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void selectSemester() {

        //get reference to the spinner from the XML layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //Array list of animals to display in the spinner
        List<String> list = new ArrayList<String>();
        list.add("Third Semester");
        list.add("Fourth Semester");
        list.add("Fifth Semseters");
        list.add("Sixth Semseters");
        list.add("Seventh Semseters");
        list.add("Eight Semseters");

        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, R.id.text122, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        //set the ArrayAdapter to the spinner
        spinner.setAdapter(dataAdapter);
        //attach the listener to the spinner
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());



    }

    private void selectfiletype() {

        //get reference to the spinner from the XML layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        //Array list of animals to display in the spinner
        List<String> list = new ArrayList<String>();
        list.add("I Want to Upload A Image");
        list.add("I Want to Upload A Doc File");
        list.add("I Want to Upload A Pdf File");

        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, R.id.text122, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        //set the ArrayAdapter to the spinner
        spinner.setAdapter(dataAdapter);
        //attach the listener to the spinner
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String selectedItem = parent.getItemAtPosition(pos).toString();

            //check which spinner triggered the listener
            switch (parent.getId()) {
                //country spinner
                case R.id.spinner: {
                    //make sure the country was already selected during the onCreate
                    semester = selectedItem;
                    if (semester != null) {
                        if (semester.contains("Third Semester")) {
                            String semes = "3";
                            Toast.makeText(parent.getContext(), selectedItem,
                                    Toast.LENGTH_LONG).show();
                            getData(semes);
                            //selectfiletype();
                        } else if (semester.contains("Fourth Semester")) {
                            String semes = "4";
                            Toast.makeText(parent.getContext(), selectedItem,
                                    Toast.LENGTH_LONG).show();
                            getData(semes);
                            selectfiletype();
                        } else if (semester.contains("Fifth Semseters")) {
                            String semes = "5";
                            getData(semes);
                            selectfiletype();
                        } else if (semester.contains("Sixth Semseters")) {
                            String semes = "6";
                            getData(semes);
                            selectfiletype();
                        } else if (semester.contains("Seventh Semseters")) {
                            String semes = "7";
                            getData(semes);
                            selectfiletype();
                        } else if (semester.contains("Eight Semseters")) {
                            String semes = "8";
                            getData(semes);
                            selectfiletype();
                        } else {
                            Toast.makeText(UploadNotes.this, "Opps!!!!..... Somthing Went Wrong!", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    }
                }
                //animal spinner
                case R.id.spinner1: {
                    //make sure the animal was already selected during the onCreate
                    if (fileTypes!= null) {
                        Toast.makeText(parent.getContext(), selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    fileTypes = selectedItem;
                    break;
                }case R.id.spinner2: {
                    //make sure the animal was already selected during the onCreate
                    if (subjects != null) {
                        Toast.makeText(parent.getContext(),selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    subjects = selectedItem;
                    break;
                }
            }


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }
    private void getData(String semes) {

            final ProgressDialog loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

            String url = DATA_URL + semes;

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
                            Toast.makeText(UploadNotes.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }


    private void showJSON(String response){

        String subjectname="";
      ;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("results");

            JSONObject collegeData = result.getJSONObject(0);
            subjectname = collegeData.getString("subjectname");
                listItems.add(subjectname);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}