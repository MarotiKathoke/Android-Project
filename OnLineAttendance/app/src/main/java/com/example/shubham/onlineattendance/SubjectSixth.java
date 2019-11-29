package com.example.shubham.onlineattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class SubjectSixth extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Spinner spinnerSubj;
    Button Adds,ivUpload;
    String et_sub;
    EditText sub;
    private static final String REGISTER_URL = "http://192.168.43.149/ChooseSubject/spinner/sixth/CatAss.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_sixth);
        Adds = (Button) findViewById(R.id.sbok);

        sub = (EditText) findViewById(R.id.newsub);
        ivUpload = (Button)findViewById(R.id.ivUpload);
        spinnerSubj = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text122, listItems);
        spinnerSubj.setAdapter(adapter);


        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerSubj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String Text = spinnerSubj.getSelectedItem().toString();
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {


                    }
                });


                String Text = spinnerSubj.getSelectedItem().toString();
                Intent intent = new Intent(SubjectSixth.this, SixthSemester.class);
                intent.putExtra("message", Text);
                startActivity(intent);

            }
        });

        Adds.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        addData();
    }

    public void addData() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Please Fill The subject Name", Toast.LENGTH_SHORT).show();

        } else {
            final String imgname = sub.getText().toString().trim().toLowerCase();
            if (imgname.isEmpty() || imgname.length() > 32 || imgname.length() < 4) {
                sub.setError("please enter a image name");

            }

            register(et_sub);
        }
    }
    public boolean validate() {
        boolean valid = true;
        if (et_sub.isEmpty() || et_sub.length() < 10 || et_sub.length() > 32) {
            sub.setError("please provide full name of subject");
            valid = false;
        }

        return valid;
    }

    public void initialize() {
        et_sub = sub.getText().toString().trim().toLowerCase();


    }

    private void register(String sname) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SubjectSixth.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("sname", params[0]);
                String result = ruc.sendPostRequest(REGISTER_URL, data);


                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(sname);
    }
    protected void onStart() {
        super.onStart();
        SubjectSixth.BackTask bt = new SubjectSixth.BackTask();
        bt.execute();
    }

    private class BackTask extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();

        }

        @Override
        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.43.149:80/ChooseSubject/spinner/sixth/get_categories.php");
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result = result + line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject object = new JSONObject(result);
                JSONArray jsonArray = object.getJSONArray("catsassign");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(jsonObject.getString("name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            listItems.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

}
