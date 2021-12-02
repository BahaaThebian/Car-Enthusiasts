package com.lau.carenthusiasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Screen extends AppCompatActivity {
    ListView view;
    int numSales=0;
    ArrayList<String> newsTitles;
    ArrayList<String> newsURLs;
    ArrayList<String> salesDesc;
    ArrayList<String> salesURLs;
    public static final String EXTRA_MESSAGE = "Link";
    public static final String DESCRIPTION = "Description";
    int state=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Intent intent=getIntent();
        newsTitles=new ArrayList<String>();
        newsURLs=new ArrayList<String>();
        salesDesc=new ArrayList<String>();
        salesURLs=new ArrayList<String>();
        view= (ListView) findViewById(R.id.listView);
        NewsJsonGetter newsJsonGetter=new NewsJsonGetter();
        newsJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/news.json");
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(state==0) {
                    Intent intent = new Intent(getApplicationContext(), News.class);
                    intent.putExtra(EXTRA_MESSAGE, newsURLs.get(i));
                    startActivity(intent);
                }
                else if(state==1){
                    Intent intent=new Intent(getApplicationContext(),Sales.class);
                    intent.putExtra(EXTRA_MESSAGE,salesURLs.get(i));
                    intent.putExtra(DESCRIPTION,salesDesc.get(i));
                    startActivity(intent);

                }

            }
        });

    }
    public class NewsJsonGetter extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            URL url;
            HttpURLConnection urlConnection;
            try{
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try{
                JSONObject json=new JSONObject(s);
                for(int i=0;i<json.names().length();i++){
                    String news=json.get(json.names().getString(i)).toString();
                    JSONObject newsobj=new JSONObject(news);
                    newsTitles.add(newsobj.getString("title"));
                    newsURLs.add(newsobj.getString("url"));
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,newsTitles);
                    view.setAdapter(adapter);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public class SalesJsonGetter extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            URL url;
            HttpURLConnection urlConnection;
            try{
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try{
                JSONObject json=new JSONObject(s);
                numSales=json.names().length();
                for(int i=0;i<json.names().length();i++){
                    String sale=json.get(json.names().getString(i)).toString();
                    JSONObject saleobj=new JSONObject(sale);
                    salesDesc.add(saleobj.getString("description"));
                    salesURLs.add(saleobj.getString("imgurl"));
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,salesDesc);
                    view.setAdapter(adapter);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void salesFunc(View view){
        state=1;
        salesURLs.clear();
        salesDesc.clear();
        SalesJsonGetter salesJsonGetter=new SalesJsonGetter();
        salesJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/sales.json");
    }
    public void homeFunc(View view){
        state=0;
        newsURLs.clear();
        newsTitles.clear();
        NewsJsonGetter newsJsonGetter=new NewsJsonGetter();
        newsJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/news.json");

    }
}