package com.lau.carenthusiasts;

import static com.lau.carenthusiasts.Screen.MERCHDESCRIPTION;
import static com.lau.carenthusiasts.Screen.MERCHIMAGEURL;
import static com.lau.carenthusiasts.Screen.MERCHRATING;
import static com.lau.carenthusiasts.Screen.MERCHURL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Merch extends AppCompatActivity {
    ImageView imageView;
    TextView desc,ratingtxt,linktxt;
    String imgURL,description,rating,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch);
        Intent intent=getIntent();
        desc=(TextView) findViewById(R.id.merchDesc);
        ratingtxt=(TextView) findViewById(R.id.merchRating);
        linktxt=(TextView) findViewById(R.id.merchLink);
        imageView=(ImageView) findViewById(R.id.merchImage);
        imgURL=intent.getStringExtra(MERCHIMAGEURL);
        description=intent.getStringExtra(MERCHDESCRIPTION);
        rating=intent.getStringExtra(MERCHRATING);
        link=intent.getStringExtra(MERCHURL);
        desc.setText(description);
        ratingtxt.setText("Rating"+rating);
        linktxt.setText("Link to buy: "+link);
        ImageDownloader task=new ImageDownloader();
        Bitmap downloadedImage;

        try{
            downloadedImage=task.execute(imgURL).get();
            imageView.setImageBitmap(downloadedImage);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public class ImageDownloader extends AsyncTask<String,Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try{
                URL url=new URL(urls[0]);

                HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
                connection.connect();

                InputStream in=connection.getInputStream();
                Bitmap downloadedImage= BitmapFactory.decodeStream(in);

                return downloadedImage;
            }catch(Exception e){
                e.printStackTrace();
                return null;

            }

        }
    }


    }
