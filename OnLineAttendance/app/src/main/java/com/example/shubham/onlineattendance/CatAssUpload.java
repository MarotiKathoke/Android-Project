package com.example.shubham.onlineattendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public class CatAssUpload extends AppCompatActivity {

    private final String TAG= this.getClass().getName();
ImageView ivCamera, ivGallery,ivUpload,ivImage;
    EditText et_image;

    CameraPhoto Cameraphoto;
    final int CAMERA_REQUEST=1122;
    GalleryPhoto galleryPhoto;
    final int GALLERY_REQUEST = 2200;
    String SelectPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_ass_upload);
        Cameraphoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        ivImage = (ImageView)findViewById(R.id.ivImage);
        ivCamera = (ImageView)findViewById(R.id.ivCamera);
        ivGallery = (ImageView)findViewById(R.id.ivGallery);
        ivUpload = (ImageView)findViewById(R.id.ivUpload);
        et_image = (EditText) findViewById(R.id.editText2);

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivityForResult(Cameraphoto.takePhotoIntent(),CAMERA_REQUEST);
                    Cameraphoto.addToGallery();

                } catch (IOException e) {
                    Toast.makeText(CatAssUpload.this, "Somthing went wrong for taking a photo", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    String encodeImage = ImageBase64.encode(bitmap);
                    Log.d(TAG,encodeImage);
                    // POST Image for uploading......
                    String imgname = et_image.getText().toString().trim().toLowerCase();
                    if(imgname.isEmpty()||imgname.length()>32||imgname.length()<4)
                    {
                        et_image.setError("please enter a image name");

                    }
                    else{
                        HashMap<String, String> postData= new HashMap<String, String>();
                        postData.put("image",encodeImage);
                        postData.put("name",imgname);
                        PostResponseAsyncTask task = new PostResponseAsyncTask(CatAssUpload.this, postData, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {

                                if(s.contains("Successfully Uploaded"))
                                {
                                    Toast.makeText(CatAssUpload.this, "Image Successfully Upload", Toast.LENGTH_SHORT).show();
                                    Intent myIn = new Intent(getApplicationContext(),OpenImage.class);
                                    startActivity(myIn);
                                }
                                else
                                {
                                    Toast.makeText(CatAssUpload.this, s, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        task.execute("http://192.168.43.149/ChooseSubject/uploadImages/UploadImages.php");
                        task.setEachExceptionsHandler(new EachExceptionsHandler() {
                            @Override
                            public void handleIOException(IOException e) {
                                Toast.makeText(CatAssUpload.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void handleMalformedURLException(MalformedURLException e) {
                                Toast.makeText(CatAssUpload.this, "Error in URL", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void handleProtocolException(ProtocolException e) {
                                Toast.makeText(CatAssUpload.this, "Protocal Error", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                                Toast.makeText(CatAssUpload.this, "Encode Error", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }


                } catch (FileNotFoundException e) {
                    Toast.makeText(CatAssUpload.this, "Somthing went wrong while encoding a photo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                String photoPath = Cameraphoto.getPhotoPath();
                SelectPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    ivImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(CatAssUpload.this, "Somthing went wrong for Loading a photo", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG,photoPath);

            }
            else if(requestCode == GALLERY_REQUEST){
                Uri uri =data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                SelectPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    ivImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(CatAssUpload.this, "Somthing went wrong for Loading a photo", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG,photoPath);
            }
        }
    }



}
