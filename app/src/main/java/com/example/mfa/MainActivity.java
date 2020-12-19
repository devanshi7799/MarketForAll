package com.example.mfa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button loginRegBtn,loginBtn;
    TextInputEditText loginEmail,loginPassword;
    RadioButton LoginRadioCustomer,LoginRadioRetailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginRegBtn = (Button)findViewById(R.id.loginRegBtn);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        LoginRadioCustomer = (RadioButton)findViewById(R.id.LoginRadioCustomer);
        LoginRadioRetailer = (RadioButton)findViewById(R.id.LoginRadioRetailer);

        loginEmail = (TextInputEditText)findViewById(R.id.loginEmail);
        loginPassword = (TextInputEditText)findViewById(R.id.loginPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        loginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
    }

    private void Login() {

        final String lemail = loginEmail.getText().toString().trim();
        final String lpassword = loginPassword.getText().toString().trim();

        String type="";

        if(LoginRadioRetailer.isChecked())
        {
            type = LoginRadioRetailer.getText().toString();
        }
        else if(LoginRadioCustomer.isChecked())
        {
            type = LoginRadioCustomer.getText().toString();
        }

        if(type.equals("Customer")) {

            String url ="https://incriminating-admir.000webhostapp.com/loginCustomer.php";

            if (lemail.isEmpty()) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else if (lpassword.isEmpty()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                final String finalType = type;
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                if(response.equalsIgnoreCase("logged in successfully")) {
                                    Intent intent = new Intent(MainActivity.this, Profile.class);
                                    intent.putExtra("type", finalType);
                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", lemail);
                        params.put("password", lpassword);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(request);

            }
        }

        if(type.equals("Retailer"))
        {
            String url ="https://incriminating-admir.000webhostapp.com/loginRetailer.php";

            if (lemail.isEmpty()) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else if (lpassword.isEmpty()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                final String finalType1 = type;
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                if(response.equalsIgnoreCase("logged in successfully")) {
                                    Intent intent = new Intent(MainActivity.this, Profile.class);
                                    intent.putExtra("type", finalType1);
                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", lemail);
                        params.put("password", lpassword);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(request);

            }
        }
    }
}
