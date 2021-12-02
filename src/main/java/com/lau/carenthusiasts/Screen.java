package com.lau.carenthusiasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    ArrayList<String> salesPrices;
    ArrayList<String> salesPhoneNo;
    EditText description;
    EditText imageURL;
    EditText price;
    EditText contactphoneNo;
    public static final String EXTRA_MESSAGE = "Link";
    public static final String DESCRIPTION = "Description";
    public static final String SALEPRICE="SalePrice";
    public static final String SALEPHONENO="SalePhoneNo";
    Button addSales;
    Button addToList;
    int state=0;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Intent intent=getIntent();
        newsTitles=new ArrayList<String>();
        newsURLs=new ArrayList<String>();
        salesDesc=new ArrayList<String>();
        salesURLs=new ArrayList<String>();
        salesPrices=new ArrayList<String>();
        salesPhoneNo=new ArrayList<String>();
        view= (ListView) findViewById(R.id.listView);
        addSales=(Button) findViewById(R.id.addSaleButton);
        addToList=(Button) findViewById(R.id.addToListBtn);
        description=(EditText) findViewById(R.id.Description);
        imageURL=(EditText) findViewById(R.id.ImageURL);
        price=(EditText) findViewById(R.id.Price);
        contactphoneNo=(EditText) findViewById(R.id.contactPhoneNo);
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
                    intent.putExtra(SALEPRICE,salesPrices.get(i));
                    intent.putExtra(SALEPHONENO,salesPhoneNo.get(i));

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
                    salesURLs.add(saleobj.getString("imgURL"));
                    salesPhoneNo.add(saleobj.getString("phoneNo"));
                    salesPrices.add(saleobj.getString("price"));
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,salesDesc);
                    view.setAdapter(adapter);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void salesFunc(View v){
        state=1;
        addSales.setVisibility(View.VISIBLE);
        salesURLs.clear();
        salesDesc.clear();
        SalesJsonGetter salesJsonGetter=new SalesJsonGetter();
        salesJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/sales.json");

    }
    public void homeFunc(View v){
        state=0;
        newsURLs.clear();
        newsTitles.clear();
        NewsJsonGetter newsJsonGetter=new NewsJsonGetter();
        newsJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/news.json");


    }
    public void addToList(View currBtn){
        String etDescription=description.getText().toString();
        String etURL=imageURL.getText().toString();
        String etPrice=price.getText().toString();
        String etPhoneNo=contactphoneNo.getText().toString();
        if(!etDescription.isEmpty()&&!etURL.isEmpty()&&!etPrice.isEmpty()&&!etPhoneNo.isEmpty()){
            rootNode=FirebaseDatabase.getInstance();
            reference= rootNode.getReference("sales");
            SaleHelperClass saleHelperClass=new SaleHelperClass(etDescription,etURL,etPrice,etPhoneNo);
            reference.child("sale"+Integer.toString(numSales+1)).setValue(saleHelperClass);
            description.setVisibility(View.INVISIBLE);
            imageURL.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);
            contactphoneNo.setVisibility(View.INVISIBLE);
            view.setVisibility(View.VISIBLE);
            salesFunc(currBtn);
            currBtn.setVisibility(View.INVISIBLE);

        }

    }
    public void addSaleFunc(View btn){
        view.setVisibility(View.INVISIBLE);
        description.setVisibility(View.VISIBLE);
        imageURL.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        addToList.setVisibility(View.VISIBLE);
        contactphoneNo.setVisibility(View.VISIBLE);
        btn.setVisibility(View.INVISIBLE);



    }
}