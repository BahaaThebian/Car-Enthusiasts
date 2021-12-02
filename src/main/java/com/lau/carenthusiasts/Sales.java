package com.lau.carenthusiasts;

import static com.lau.carenthusiasts.Screen.DESCRIPTION;
import static com.lau.carenthusiasts.Screen.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Sales extends AppCompatActivity {
    ImageView imageView;
    TextView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        Intent intent=getIntent();
        imageView=(ImageView) findViewById(R.id.carImage);
        desc=(TextView) findViewById(R.id.saleDescription);
        String imgURL=intent.getStringExtra(EXTRA_MESSAGE);
        String description=intent.getStringExtra(DESCRIPTION);
        desc.setText(description);
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