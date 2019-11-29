package com.example.shubham.onlineattendance;

import android.bluetooth.BluetoothAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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

public class Preseentes extends AppCompatActivity {
    Button sub;
    Spinner s1;
    ListView list1;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preseentes);
        list1 = (ListView) findViewById(R.id.list1);
        s1 = (Spinner) findViewById(R.id.subjects);
        sub = (Button) findViewById(R.id.button3);

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text122, listItems);
        s1.setAdapter(adapter);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {


                    }
                });
                String Text = s1.getSelectedItem().toString();
            }
        });
    }
    protected void onStart() {
        super.onStart();
        Preseentes.BackTask bt = new Preseentes.BackTask();
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
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothAdapter.enable();
            String macAddress = mBluetoothAdapter.getAddress();

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.43.149:80/Attendance/Attendance.php?macAddress=");
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
