package com.example.mfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {

    Button AddProductBtn;
    TextInputEditText productName,productDescription,productPrice,shopName;
    Spinner ProductCategory,ProductQuantity;

    String GoodsType = "";
    String GoodsQty = "";
    String[] Category = {"Bath","Bed","Grocery","Crockery","Electrical","Fabric","Footware","Fresh Fruits",
            "Fresh Vegetables","Furniture","Gift Articles","Handkerchief/Napikins/Towels","Home Appliances","Kids Wear","Luggage",
            "Mens wear","Electronics","Stationery","Winter wear","Womens wear","Other"};
    String[] quantity = {"piece","grams","kilo","liter","militer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        AddProductBtn = (Button)findViewById(R.id.AddProductBtn);
        productName = (TextInputEditText)findViewById(R.id.productName);
        productDescription = (TextInputEditText)findViewById(R.id.productDescription);
        productPrice = (TextInputEditText)findViewById(R.id.productPrice);
        ProductCategory = (Spinner)findViewById(R.id.ProductCategory);
        ProductQuantity = (Spinner)findViewById(R.id.ProductQuantity);
        shopName = (TextInputEditText)findViewById(R.id.shopName);

        ArrayAdapter adapter = new ArrayAdapter(AddData.this,android.R.layout.simple_spinner_item,Category);
        ProductCategory.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter(AddData.this,android.R.layout.simple_spinner_item,quantity);
        ProductQuantity.setAdapter(adapter1);

        ProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsType = Category[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ProductQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsQty = quantity[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }

    public void addData()
    {
        final String addshopname = shopName.getText().toString().trim();
        final String addproductname = productName.getText().toString().trim();
        final String addproductdescription = productDescription.getText().toString().trim();
        final String addprice = productPrice.getText().toString().trim();

        if(addshopname.isEmpty())
        {
            Toast.makeText(this, "Enter Shop Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(addproductname.isEmpty())
        {
            Toast.makeText(this, "Enter Product Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(addproductdescription.isEmpty())
        {
            Toast.makeText(this, "Enter Product Description", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(addprice.isEmpty())
        {
            Toast.makeText(this, "Enter Price", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            StringRequest request = new StringRequest(Request.Method.POST, "https://incriminating-admir.000webhostapp.com/insertProduct.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Data inserted")) {
                                Toast.makeText(AddData.this, "Data inserted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddData.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddData.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("shopname", addshopname);
                    params.put("productname", addproductname);
                    params.put("productdescription", addproductdescription);
                    params.put("productprice", addprice);
                    params.put("goodstype", GoodsType);
                    params.put("goodsqty", GoodsQty);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AddData.this);
            requestQueue.add(request);
        }
    }
    }

