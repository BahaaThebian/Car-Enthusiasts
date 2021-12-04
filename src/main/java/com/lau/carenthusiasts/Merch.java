package com.lau.carenthusiasts;

import static com.lau.carenthusiasts.Screen.MERCHDESCRIPTION;
import static com.lau.carenthusiasts.Screen.MERCHIMAGEURL;
import static com.lau.carenthusiasts.Screen.MERCHRATING;
import static com.lau.carenthusiasts.Screen.MERCHURL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Merch extends AppCompatActivity {
    ImageView imageView,ratingImg;
    TextView desc,ratingtxt;
    Button btn;
    String imgURL,description,rating,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch);
        Intent intent=getIntent();
        desc=(TextView) findViewById(R.id.merchDesc);
        ratingtxt=(TextView) findViewById(R.id.merchRating);
        btn=(Button) findViewById(R.id.buyButton);
        imageView=(ImageView) findViewById(R.id.merchImage);
        ratingImg=(ImageView) findViewById(R.id.ratingImage);
        imgURL=intent.getStringExtra(MERCHIMAGEURL);
        description=intent.getStringExtra(MERCHDESCRIPTION);
        rating=intent.getStringExtra(MERCHRATING);
        String twoDigits=rating.charAt(0)+""+rating.charAt(1);
        int rrating=Integer.parseInt(twoDigits);
        if(rrating>=90){
            ratingImg.setImageResource(R.drawable.fourptfive);
        }else if(rrating>=80&&rrating<90){
            ratingImg.setImageResource(R.drawable.fourstars);
        }else if(rrating<80&&rrating>=70){
            ratingImg.setImageResource(R.drawable.threeptfive);
        }

        link=intent.getStringExtra(MERCHURL);
        desc.setText(description);
        ratingtxt.setText("Rating: "+rating);
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
    public void goToURL(View v){
        Uri uriUrl=Uri.parse(link);
        Intent launchBrowser= new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(launchBrowser);
    }

    }
