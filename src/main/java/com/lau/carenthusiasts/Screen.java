package com.lau.carenthusiasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    ArrayList<String> merchImageURLs;
    ArrayList<String> merchURLs;
    ArrayList<String> merchRatings;
    ArrayList<String> merchDesc;
    static ArrayList<String> myCart;
    EditText description;
    EditText imageURL;
    EditText price;
    EditText contactphoneNo;
    TextView totPrice,txt;
    public static final String EXTRA_MESSAGE = "Link";
    public static final String DESCRIPTION = "Description";
    public static final String SALEPRICE="SalePrice";
    public static final String SALEPHONENO="SalePhoneNo";
    public static final String MERCHIMAGEURL="merchImageURL";
    public static final String MERCHURL="merchURL";
    public static final String MERCHRATING="merchRating";
    public static final String MERCHDESCRIPTION="merchDescription";
    Button addSales;
    Button addToList;
    Button checkOutButton;
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
        merchDesc=new ArrayList<String>();
        merchImageURLs=new ArrayList<String>();
        merchRatings=new ArrayList<String>();
        merchURLs=new ArrayList<String>();
        myCart=new ArrayList<String>();
        view= (ListView) findViewById(R.id.listView);
        addSales=(Button) findViewById(R.id.addSaleButton);
        addToList=(Button) findViewById(R.id.addToListBtn);
        checkOutButton=(Button) findViewById(R.id.checkOutButton);
        description=(EditText) findViewById(R.id.Description);
        imageURL=(EditText) findViewById(R.id.ImageURL);
        price=(EditText) findViewById(R.id.Price);
        contactphoneNo=(EditText) findViewById(R.id.contactPhoneNo);
        totPrice=(TextView) findViewById(R.id.totalPrice);
        txt=(TextView)findViewById(R.id.Txt);
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
                else if(state==2){
                    Intent intent=new Intent(getApplicationContext(),Merch.class);
                    intent.putExtra(MERCHDESCRIPTION,merchDesc.get(i));
                    intent.putExtra(MERCHIMAGEURL,merchImageURLs.get(i));
                    intent.putExtra(MERCHRATING,merchRatings.get(i));
                    intent.putExtra(MERCHURL,merchURLs.get(i));
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
                salesURLs.clear();
                salesDesc.clear();
                salesPrices.clear();
                salesPhoneNo.clear();
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
    public class MerchJsonGetter extends AsyncTask<String,Void,String>{

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
                    String merch=json.get(json.names().getString(i)).toString();
                    JSONObject merchobj=new JSONObject(merch);
                    merchDesc.add(merchobj.getString("description"));
                    merchImageURLs.add(merchobj.getString("imgURL"));
                    merchURLs.add(merchobj.getString("link"));
                    merchRatings.add(merchobj.getString("rating"));
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,merchDesc);
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

        if(totPrice.getVisibility()==View.VISIBLE){
            totPrice.setVisibility(View.INVISIBLE);
        }
        if(checkOutButton.getVisibility()==View.VISIBLE){
            checkOutButton.setVisibility(View.INVISIBLE);
        }
        if(txt.getVisibility()==View.VISIBLE){
            txt.setVisibility(View.INVISIBLE);
        }
        if(description.getVisibility()==View.VISIBLE){
            description.setVisibility(View.INVISIBLE);
        }
        if(imageURL.getVisibility()==View.VISIBLE){
            imageURL.setVisibility(View.INVISIBLE);
        }
        if(price.getVisibility()==View.VISIBLE){
            price.setVisibility(View.INVISIBLE);
        }
        if(contactphoneNo.getVisibility()==View.VISIBLE){
            contactphoneNo.setVisibility(View.INVISIBLE);
        }
        if(addToList.getVisibility()==View.VISIBLE){
            addToList.setVisibility(View.INVISIBLE);
        }

        SalesJsonGetter salesJsonGetter=new SalesJsonGetter();
        salesJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/sales.json");
        if(view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }

    }
    public void homeFunc(View v){
        state=0;
        if(view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
        if(totPrice.getVisibility()==View.VISIBLE){
            totPrice.setVisibility(View.INVISIBLE);
        }
        if(checkOutButton.getVisibility()==View.VISIBLE){
            checkOutButton.setVisibility(View.INVISIBLE);
        }
        if(txt.getVisibility()==View.VISIBLE){
            txt.setVisibility(View.INVISIBLE);
        }
        if(description.getVisibility()==View.VISIBLE){
            description.setVisibility(View.INVISIBLE);
        }
        if(imageURL.getVisibility()==View.VISIBLE){
            imageURL.setVisibility(View.INVISIBLE);
        }
        if(price.getVisibility()==View.VISIBLE){
            price.setVisibility(View.INVISIBLE);
        }
        if(contactphoneNo.getVisibility()==View.VISIBLE){
            contactphoneNo.setVisibility(View.INVISIBLE);
        }
        if(addToList.getVisibility()==View.VISIBLE){
            addToList.setVisibility(View.INVISIBLE);
        }
        if(addSales.getVisibility()==View.VISIBLE){
            addSales.setVisibility(View.INVISIBLE);
        }
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
            SalesJsonGetter salesJsonGetter=new SalesJsonGetter();
            salesJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/sales.json");
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
    public void cartFunc(View v){
        state=3;
        if(view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
        if(description.getVisibility()==View.VISIBLE){
            description.setVisibility(View.INVISIBLE);
        }
        if(imageURL.getVisibility()==View.VISIBLE){
            imageURL.setVisibility(View.INVISIBLE);
        }
        if(price.getVisibility()==View.VISIBLE){
            price.setVisibility(View.INVISIBLE);
        }
        if(contactphoneNo.getVisibility()==View.VISIBLE){
            contactphoneNo.setVisibility(View.INVISIBLE);
        }
        if(addToList.getVisibility()==View.VISIBLE){
            addToList.setVisibility(View.INVISIBLE);
        }
        if(addSales.getVisibility()==View.VISIBLE){
            addSales.setVisibility(View.INVISIBLE);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,myCart);
        view.setAdapter(adapter);

    }
    public void merchFunc(View v){
        state=2;
        if(view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
        if(totPrice.getVisibility()==View.VISIBLE){
            totPrice.setVisibility(View.INVISIBLE);
        }
        if(checkOutButton.getVisibility()==View.VISIBLE){
            checkOutButton.setVisibility(View.INVISIBLE);
        }
        if(txt.getVisibility()==View.VISIBLE){
            txt.setVisibility(View.INVISIBLE);
        }
        if(description.getVisibility()==View.VISIBLE){
            description.setVisibility(View.INVISIBLE);
        }
        if(imageURL.getVisibility()==View.VISIBLE){
            imageURL.setVisibility(View.INVISIBLE);
        }
        if(price.getVisibility()==View.VISIBLE){
            price.setVisibility(View.INVISIBLE);
        }
        if(contactphoneNo.getVisibility()==View.VISIBLE){
            contactphoneNo.setVisibility(View.INVISIBLE);
        }
        if(addToList.getVisibility()==View.VISIBLE){
            addToList.setVisibility(View.INVISIBLE);
        }
        if(addSales.getVisibility()==View.VISIBLE){
            addSales.setVisibility(View.INVISIBLE);
        }
        merchURLs.clear();
        merchRatings.clear();
        merchDesc.clear();
        merchImageURLs.clear();
        MerchJsonGetter merchJsonGetter=new MerchJsonGetter();
        merchJsonGetter.execute("https://tutorial-a86d5-default-rtdb.firebaseio.com/merch.json");
    }
}