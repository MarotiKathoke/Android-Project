package com.example.shubham.onlineattendance;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
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

public class EightSemester extends AppCompatActivity {
    private String URL_Students = "http://192.168.43.149/StudentsList/EigthSem/getFixture.php";

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Whenever a remote Bluetooth device is found
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView

                scanBluetoothDevice(bluetoothDevice.getAddress());

            }

        }
    };
    private BluetoothAdapter bluetoothAdapter;
    private static final int ENABLE_BT_REQUEST_CODE = 1112;
    Button present;
    ListView lv;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eight_semester);
        present = (Button) findViewById(R.id.button2);
        lv = (ListView) findViewById(R.id.lv2);
        Intent intent = getIntent();                                                     //get subject name from previous page...
        String message = intent.getStringExtra("message");
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getApplicationContext(), "Oop! Your device does not support Bluetooth",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetoothIntent, ENABLE_BT_REQUEST_CODE);
            } else {
                Toast.makeText(getApplicationContext(), "Your device has already been enabled." +
                                "\n" + "Scanning for remote Bluetooth devices...",
                        Toast.LENGTH_SHORT).show();
                // To discover remote Bluetooth devices
                discoverDevices();
                // Make local device discoverable by other devices

            }

        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ENABLE_BT_REQUEST_CODE) {

            // Bluetooth successfully enabled!
            if (resultCode == ListActivity.RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Ha! Bluetooth is now enabled." +
                                "\n" + "Scanning for remote Bluetooth devices...",
                        Toast.LENGTH_SHORT).show();


                // To discover remote Bluetooth devices
                discoverDevices();

            } else { // RESULT_CANCELED as user refused or failed to enable Bluetooth
                Toast.makeText(getApplicationContext(), "Bluetooth is not enabled.",
                        Toast.LENGTH_SHORT).show();

            }

        }
    }

    protected void discoverDevices(){
        // To scan for remote Bluetooth devices
        if (bluetoothAdapter.startDiscovery()) {
            Toast.makeText(getApplicationContext(), "Discovering other bluetooth devices...",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Discovery failed to start.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the BroadcastReceiver for ACTION_FOUND
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(broadcastReceiver);
    }

    private void scanBluetoothDevice(final String Address) {
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = URL_Students;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response, Address);
                CustomAdapter adapter=new CustomAdapter(getApplicationContext(),spacecrafts);
                lv.setAdapter(adapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EightSemester.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void showJSON(String response,String Address) {
        String rolll;
        String fname;
        String lname;
        String baddress;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            spacecrafts.clear();
            Spacecraft s=null;
            for (int i= 0;i<=result.length();i++) {

                JSONObject collegeData = result.getJSONObject(i);
                int roll=collegeData.getInt("RollNo");
                rolll = collegeData.getString("RollNo");
                fname = collegeData.getString("Fname");
                lname = collegeData.getString("lname");
                baddress = collegeData.getString("baddress");

                s=new Spacecraft();
                if (Address.equals(baddress)) {
                    s.getRoll(roll);
                    s.setRolll(rolll);
                    s.setName(fname);
                    s.setLname(lname);
                    s.setBaddress(baddress);
                    spacecrafts.add(s);
                }

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }

}
