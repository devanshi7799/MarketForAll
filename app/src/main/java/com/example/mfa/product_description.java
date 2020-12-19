package com.example.mfa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class product_description extends AppCompatActivity {

    ListView productList;
    productDescriptionAdapter adapter;
    ImageView backBtn;

    String url = "https://incriminating-admir.000webhostapp.com/retrieve.php";
    public static ArrayList<Product> productArrayList = new ArrayList<>();
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        backBtn = (ImageView)findViewById(R.id.backBtn);
        productList = (ListView)findViewById(R.id.productList);
        adapter = new productDescriptionAdapter(this,productArrayList);
        productList.setAdapter(adapter);
        retrieveData();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(product_description.this,Profile.class);
                startActivity(intent);
            }
        });
    }

   public void retrieveData() {

       final ProgressDialog progressDialog = new ProgressDialog(product_description.this);
       progressDialog.setMessage("Loading");
       progressDialog.show();
       
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        productArrayList.clear();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("product");

                            if(success.equals("1"))
                            {
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    progressDialog.dismiss();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String shopname = object.getString("shopname");
                                    String productname = object.getString("productname");
                                    String productdescription = object.getString("productdescription");
                                    String productprice = object.getString("productprice");
                                    String producttype = object.getString("producttype");

                                    product = new Product(shopname,productname,productdescription,productprice,producttype);
                                    productArrayList.add(product);
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(product_description.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
   }
}
