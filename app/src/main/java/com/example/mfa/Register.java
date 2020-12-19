package com.example.mfa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    LinearLayoutCompat LayoutCustomer,LayoutRetailer;
    RadioButton RadioCustomer,RadioRetailer;
    Spinner ProductCategory;
    TextInputEditText RegFirstName,RegSecondName,RegEmail,RegPassword,RegConfirmPassword;
    TextInputEditText RegRetailerFirstName,RegRetailerSecondName,RegRetailerEmail,RegRetailerPhone,RegRetailerOrgName,
            RegRetailerGST,RegRetailerGoods,RegRetailerPassword,RegRetailerConfirmPassword;
    Button RegisterBtn;

    DatabaseReference reff;

    String GoodsType = "";
    String[] Category = {"Bath","Bed","Grocery","Crockery","Electrical","Fabric","Footware","Fresh Fruits",
            "Fresh Vegetables","Furniture","Gift Articles","Handkerchief/Napikins/Towels","Home Appliances","Kids Wear","Luggage",
            "Mens wear","Electronics","Stationery","Winter wear","Womens wear","Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LayoutCustomer = (LinearLayoutCompat)findViewById(R.id.LayoutCustomer);
        LayoutRetailer = (LinearLayoutCompat)findViewById(R.id.LayoutRetailer);
        RadioCustomer = (RadioButton)findViewById(R.id.RadioCustomer);
        RadioRetailer = (RadioButton)findViewById(R.id.RadioRetailer);

        RegFirstName = (TextInputEditText)findViewById(R.id.RegFirstName);
        RegSecondName = (TextInputEditText)findViewById(R.id.RegSecondName);
        RegEmail = (TextInputEditText)findViewById(R.id.RegEmail);
        RegPassword = (TextInputEditText)findViewById(R.id.RegPassword);
        RegConfirmPassword = (TextInputEditText)findViewById(R.id.RegConfirmPassword);
        RegisterBtn = (Button)findViewById(R.id.RegisterBtn);


        RegRetailerFirstName = (TextInputEditText)findViewById(R.id.RegRetailerFirstName);
        RegRetailerSecondName = (TextInputEditText)findViewById(R.id.RegRetailerSecondName);
        RegRetailerEmail = (TextInputEditText)findViewById(R.id.RegRetailerEmail);
        RegRetailerPhone = (TextInputEditText)findViewById(R.id.RegRetailerPhone);
        RegRetailerOrgName = (TextInputEditText)findViewById(R.id.RegRetailerOrgName);
        RegRetailerGST = (TextInputEditText)findViewById(R.id.RegRetailerGST);
        RegRetailerGoods = (TextInputEditText)findViewById(R.id.RegRetailerGoods);
        ProductCategory = (Spinner)findViewById(R.id.ProductCategory);
        RegRetailerPassword = (TextInputEditText)findViewById(R.id.RegRetailerPassword);
        RegRetailerConfirmPassword = (TextInputEditText)findViewById(R.id.RegRetailerConfirmPassword);

        reff = FirebaseDatabase.getInstance().getReference().child("Member");

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(Register.this,android.R.layout.simple_spinner_item,Category);
        ProductCategory.setAdapter(adapter);

        ProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsType = Category[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RadioCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RadioCustomer.isChecked())
                {
                    LayoutRetailer.setVisibility(View.GONE);
                    LayoutCustomer.setVisibility(View.VISIBLE);
                }
            }
        });
        RadioRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RadioRetailer.isChecked())
                {
                    LayoutRetailer.setVisibility(View.VISIBLE);
                    LayoutCustomer.setVisibility(View.GONE);
                }
            }
        });

    }

    /*@Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addMember() {
        String type="";

        if(RadioRetailer.isChecked())
        {
            type = RadioRetailer.getText().toString();
        }
        else if(RadioCustomer.isChecked())
        {
            type = RadioCustomer.getText().toString();
        }

        if(type.equals("Customer")) {
            final String fname = RegFirstName.getText().toString().trim();
            final String sname = RegSecondName.getText().toString().trim();
            final String email = RegEmail.getText().toString().trim();
            final String password = RegPassword.getText().toString().trim();


        }

        if(type.equals("Retailer"))
        {
            String Rfname = RegRetailerFirstName.getText().toString().trim();
            String Rsname = RegRetailerSecondName.getText().toString().trim();
            String Rphone = RegRetailerPhone.getText().toString().trim();
            String Rorgname = RegRetailerOrgName.getText().toString().trim();
            String Rgst = RegRetailerGST.getText().toString().trim();
            String Remail = RegRetailerEmail.getText().toString().trim();
            String Rgoods = RegRetailerGoods.getText().toString().trim();
            String Rgoodstype = GoodsType;
            
            if (!TextUtils.isEmpty(Rfname)) {

                Member member2 = new Member(type,Rfname,Remail,Rsname,Rphone,Rgst,Rgoods,Rorgname, Rgoodstype);
                reff.child(Remail).setValue(member2);
                Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Enter first name", Toast.LENGTH_SHORT).show();
            }
        }

    }
     */

    private void sendData() {
        String type="";

        if(RadioRetailer.isChecked())
        {
            type = RadioRetailer.getText().toString();
        }
        else if(RadioCustomer.isChecked())
        {
            type = RadioCustomer.getText().toString();
        }

        if(type.equals("Customer"))
        {
        final String firstname = RegFirstName.getText().toString().trim();
        final String secondname = RegSecondName.getText().toString().trim();
        final String email = RegEmail.getText().toString().trim();
        final String password = RegPassword.getText().toString().trim();

        if(firstname.isEmpty())
        {
            Toast.makeText(this, "Enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(secondname.isEmpty())
        {
            Toast.makeText(this, "Enter Second Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(email.isEmpty())
        {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.isEmpty())
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            StringRequest request = new StringRequest(Request.Method.POST, "https://incriminating-admir.000webhostapp.com/insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Data inserted")) {
                                Toast.makeText(Register.this, "Data inserted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("firstname", firstname);
                    params.put("secondname", secondname);
                    params.put("email", email);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
            requestQueue.add(request);
        }
        }

        if(type.equals("Retailer"))
        {
            final String rfirstname = RegRetailerFirstName.getText().toString().trim();
            final String rsecondname = RegRetailerSecondName.getText().toString().trim();
            final String remail = RegRetailerEmail.getText().toString().trim();
            final String rcontact = RegRetailerPhone.getText().toString().trim();
            final String rorgname = RegRetailerOrgName.getText().toString().trim();
            final String rgstnumber = RegRetailerGST.getText().toString().trim();
            final String rcategory = GoodsType;
            final String rservice = RegRetailerGoods.getText().toString().trim();
            final String rpassword = RegRetailerPassword.getText().toString().trim();
            String rconfirmpassword = RegRetailerConfirmPassword.getText().toString().trim();

            if(rfirstname.isEmpty())
            {
                Toast.makeText(this, "Enter First Name", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rsecondname.isEmpty())
            {
                Toast.makeText(this, "Enter Second Name", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(remail.isEmpty())
            {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rcontact.isEmpty())
            {
                Toast.makeText(this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rorgname.isEmpty())
            {
                Toast.makeText(this, "Enter Organisation Name", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rgstnumber.isEmpty())
            {
                Toast.makeText(this, "Enter GST Number", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rcategory.isEmpty())
            {
                Toast.makeText(this, "Enter Product Category", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rservice.isEmpty())
            {
                Toast.makeText(this, "Enter Services offered", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rpassword.isEmpty())
            {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(rconfirmpassword.isEmpty())
            {
                Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                StringRequest request = new StringRequest(Request.Method.POST, "https://incriminating-admir.000webhostapp.com/insertRetailer.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("Data inserted")) {
                                    Toast.makeText(Register.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("firstname", rfirstname);
                        params.put("secondname", rsecondname);
                        params.put("email", remail);
                        params.put("contact", rcontact);
                        params.put("orgname", rorgname);
                        params.put("gstnumber", rgstnumber);
                        params.put("category", rcategory);
                        params.put("service", rservice);
                        params.put("password", rpassword);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                requestQueue.add(request);
            }
        }

    }
}

