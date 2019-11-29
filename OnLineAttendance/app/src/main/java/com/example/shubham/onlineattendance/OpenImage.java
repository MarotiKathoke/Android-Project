package com.example.shubham.onlineattendance;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class OpenImage extends ListActivity implements View.OnClickListener {
    final String SERVER_ADDRESS = "http://192.168.43.149/ChooseSubject/uploadImages/";

    private String URL_Students = "http://192.168.43.149/ChooseSubject/uploadImages/getFixture.php";
    JSONArray matchRecords = null;

    TextView on;
    ImageView Downloadimage;

    ArrayList<HashMap<String, String>> matchStudentsList = new ArrayList<HashMap<String, String>>();

    TextView text;
    private static final String TAG_Table = "images";
    private static final String TAG_NAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_image);
        final View addView = getLayoutInflater().inflate(R.layout.image_download, null);
        final View addVieww = getLayoutInflater().inflate(R.layout.imageviewtext, null);
        text = (TextView) addVieww.findViewById(R.id.viewimage);
       on = (TextView) addVieww.findViewById(R.id.download);
        Downloadimage = (ImageView) addView.findViewById(R.id.DownloadImage);



    }


    private class GetFixture extends AsyncTask<Object, Object, ArrayList<HashMap<String, String>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Object... arg0) {
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + URL_Students);
            String json = serviceClient.makeServiceCall(URL_Students, ServiceHandler.GET);
            // print the json response in the log
            Log.d("Get match fixture response: ", "> " + json);
            if (json != null) {
                try {
                    Log.d("try", "in the try");
                    JSONObject jsonObj = new JSONObject(json);
                    Log.d("jsonObject", "new json Object");
                    // Getting JSON Array node
                    matchRecords = jsonObj.getJSONArray(TAG_Table);
                    Log.d("json aray", "user point array");
                    int len = matchRecords.length();
                    Log.d("len", "get array length");
                    for (int i = 0; i < len; i++) {
                        JSONObject c = matchRecords.getJSONObject(i);
                        String name = c.getString(TAG_NAME);
                        Log.d("name", name);
                        HashMap<String, String> matchFixture = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        matchFixture.put(TAG_NAME, name);
                        matchStudentsList.add(matchFixture);


                    }

                } catch (JSONException e) {
                    Log.d("catch", "in the catch");
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter(
                    OpenImage.this, matchStudentsList,
                    R.layout.imageviewtext, new String[]{TAG_NAME}
                    , new int[]{R.id.viewimage}

            );
            setListAdapter(adapter);

        }

    }



    @Override
    public void onClick(View v) {
        new DownloadImage().execute();

    }
    private class DownloadImage extends AsyncTask<Void, Void, Bitmap>
    {
        ProgressDialog loading;


        String name;

       public void DownloadImage(String name)
       {
           this.name=name;
       }

        @Override
        protected Bitmap doInBackground(Void... params) {

            String url = SERVER_ADDRESS + "upload/" + name + ".jpeg";
            try
            {
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000*30);
                connection.setReadTimeout(1000*30);

                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);

            }catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Downloadimage.setImageBitmap(bitmap);
        }
    }

}