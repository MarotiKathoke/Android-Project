package com.example.shubham.onlineattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadCatsAssignments extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private final String TAG= this.getClass().getName();
    ImageView ivGallery,ivImage;
    Button ivUpload;
    EditText et_image;

    GalleryPhoto galleryPhoto;
    final int GALLERY_REQUEST = 2200;
    String SelectPhoto;
    RadioButton Cat, ass;
    Button Adds;
    EditText sub;
    RadioGroup option;
    Spinner spinnerSubj;

    private static final String REGISTER_URL = "http://192.168.43.149/CatAss.php";
    private String et_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.upload_cats_assignments);
        option = (RadioGroup) findViewById(R.id.option1);
        Cat = (RadioButton) findViewById(R.id.cat);
        ass = (RadioButton) findViewById(R.id.ass);
        sub = (EditText) findViewById(R.id.newsub);
        Adds = (Button) findViewById(R.id.sbok);

        galleryPhoto = new GalleryPhoto(getApplicationContext());
        ivImage = (ImageView)findViewById(R.id.ivImage);
        ivGallery = (ImageView)findViewById(R.id.ivGallery);
        ivUpload = (Button)findViewById(R.id.ivUpload);
        et_image = (EditText) findViewById(R.id.editText2);

        spinnerSubj = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text122, listItems);
        spinnerSubj.setAdapter(adapter);


        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });
        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Bitmap bitmap = ImageLoader.init().from(SelectPhoto).requestSize(1024, 1024).getBitmap();
                    final String encodeImage = ImageBase64.encode(bitmap);
                    Log.d(TAG, encodeImage);
                    // POST Image for uploading......
                    final String imgname = et_image.getText().toString().trim().toLowerCase();
                    if (imgname.isEmpty() || imgname.length() > 32 || imgname.length() < 4) {
                        et_image.setError("please enter a image name");

                    } else {
                        spinnerSubj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                String Text = spinnerSubj.getSelectedItem().toString();

                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {


                            }
                        });
                        String Text = spinnerSubj.getSelectedItem().toString();

                        final int selectedId = option.getCheckedRadioButtonId();
                        if (selectedId == Cat.getId()) {
                            String Cat = "CAT";

                        HashMap<String, String> postData = new HashMap<String, String>();
                        postData.put("image", encodeImage);
                        postData.put("name", imgname);
                        postData.put("cName", Cat);
                        postData.put("Subject", Text);
                        PostResponseAsyncTask task = new PostResponseAsyncTask(UploadCatsAssignments.this, postData, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {

                                if (s.contains("Successfully Uploaded")) {
                                    Toast.makeText(UploadCatsAssignments.this, "Image Successfully Upload", Toast.LENGTH_SHORT).show();
                                    Intent myIn = new Intent(getApplicationContext(), OpenImage.class);
                                    startActivity(myIn);
                                } else {
                                    Toast.makeText(UploadCatsAssignments.this, s, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        task.execute("http://192.168.43.149/ChooseSubject/uploadImages/UploadImages.php");
                        task.setEachExceptionsHandler(new EachExceptionsHandler() {
                            @Override
                            public void handleIOException(IOException e) {
                                Toast.makeText(UploadCatsAssignments.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void handleMalformedURLException(MalformedURLException e) {
                                Toast.makeText(UploadCatsAssignments.this, "Error in URL", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void handleProtocolException(ProtocolException e) {
                                Toast.makeText(UploadCatsAssignments.this, "Protocal Error", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                                Toast.makeText(UploadCatsAssignments.this, "Encode Error", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                       else if (selectedId == ass.getId()) {


                            String assignment = "Assignment";

                            HashMap<String, String> postData = new HashMap<String, String>();
                            postData.put("image", encodeImage);
                            postData.put("name", imgname);
                            postData.put("Assignment", assignment);
                            postData.put("Subject", Text);

                            PostResponseAsyncTask task = new PostResponseAsyncTask(UploadCatsAssignments.this, postData, new AsyncResponse() {
                                @Override
                                public void processFinish(String s) {

                                    if (s.contains("Successfully Uploaded")) {
                                        Toast.makeText(UploadCatsAssignments.this, "Image Successfully Upload", Toast.LENGTH_SHORT).show();
                                        Intent myIn = new Intent(getApplicationContext(), OpenImage.class);
                                        startActivity(myIn);
                                    } else {
                                        Toast.makeText(UploadCatsAssignments.this, s, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            task.execute("http://192.168.43.149/ChooseSubject/uploadImages/UploadImages.php");
                            task.setEachExceptionsHandler(new EachExceptionsHandler() {
                                @Override
                                public void handleIOException(IOException e) {
                                    Toast.makeText(UploadCatsAssignments.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void handleMalformedURLException(MalformedURLException e) {
                                    Toast.makeText(UploadCatsAssignments.this, "Error in URL", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void handleProtocolException(ProtocolException e) {
                                    Toast.makeText(UploadCatsAssignments.this, "Protocal Error", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                                    Toast.makeText(UploadCatsAssignments.this, "Encode Error", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                }


                } catch (FileNotFoundException e) {
                    Toast.makeText(UploadCatsAssignments.this, "Somthing went wrong while encoding a photo", Toast.LENGTH_SHORT).show();
                }

            }
        });





        Adds.setOnClickListener(this);


        option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if (checkedId == R.id.cat) {

                    Toast.makeText(getApplicationContext(), "You want to submit CAT que",

                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.ass) {

                    Toast.makeText(getApplicationContext(), "You want to submit Assignment que",

                            Toast.LENGTH_SHORT).show();

                }

            }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQUEST){
                Uri uri =data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                SelectPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    ivImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(UploadCatsAssignments.this, "Somthing went wrong for Loading a photo", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG,photoPath);
            }
        }
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
                loading = ProgressDialog.show(UploadCatsAssignments.this, "Please Wait", null, true, true);
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
        UploadCatsAssignments.BackTask bt = new UploadCatsAssignments.BackTask();
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
                HttpPost httpPost = new HttpPost("http://192.168.43.149:80/ChooseSubject/get_categories.php");
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

