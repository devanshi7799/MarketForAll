package com.example.mfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Profile extends AppCompatActivity {

    ListView lv;
    ImageView btnMenu,btnCart;
    FloatingActionButton addBtn;

    int img1[] = {R.drawable.bath, R.drawable.cups, R.drawable.electronics,  R.drawable.slippers,
             R.drawable.vegetables,  R.drawable.groceries, R.drawable.application,R.drawable.suitcases,  R.drawable.stationary,  R.drawable.woman};
    String name1[] = {"Bath",  "Crockery",  "Electronics", "Footware",  "Fresh Vegetables","Grocery","Home Appliance","Luggage","Stationery","Women's Wear"};

    int img2[] = {R.drawable.beds,R.drawable.plug,R.drawable.fabric,R.drawable.fruits,R.drawable.lamp, R.drawable.gift,R.drawable.girl,R.drawable.man,
            R.drawable.cap,R.drawable.others};
    String name2[] = {"Bed","Electrical", "Fabric","Fresh Fruits","Furniture","Gift Articles","Kid's Wear","Men's Wear","Winter Wear","Others"};

    ClipboardManager clipboardManager;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        lv = (ListView) findViewById(R.id.lv);
        btnMenu = (ImageView)findViewById(R.id.btnMenu);
        btnCart = (ImageView)findViewById(R.id.btnCart);
        addBtn = (FloatingActionButton)findViewById(R.id.addBtn);

        Intent i = getIntent();
        type = i.getStringExtra("type");

        if(type.equals("Customer"))
        {
            addBtn.hide();
        }

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Profile.this,btnMenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.mprofile:
                                Toast.makeText(Profile.this, "Profile", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.mrate:
                                Toast.makeText(Profile.this, "Rate us", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.mshare:
                                clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

                                String url = "www.google.com";

                                if(!url.equals(""))
                                {
                                    ClipData clipData = ClipData.newPlainText("url",url);
                                    clipboardManager.setPrimaryClip(clipData);

                                    Toast.makeText(Profile.this, "Link Copied to Clipboard", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            case R.id.mcontact:
                                String to = "devanshis177@gmail.com";
                                String subject = "Customer Inquiry";
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                                email.putExtra(Intent.EXTRA_SUBJECT, subject);

                                //need this to prompts email client only
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                                return true;
                            case R.id.mlogout:
                                Intent intent = new Intent(Profile.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                return true;
                        }
                        return true;
                    }
                });
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Cart.class);
                startActivity(intent);
            }
        });

        CustomAdapter_list list = new CustomAdapter_list();

        lv.setAdapter(list);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,AddData.class);
                startActivity(intent);
            }
        });
    }

    class CustomAdapter_list extends BaseAdapter
    {
        @Override
        public int getCount() {
            return img1.length;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_list,null);
            TextView dname1 = (TextView)convertView.findViewById(R.id.dname1);
            ImageView dimg1 = (ImageView)convertView.findViewById(R.id.dimg1);
            TextView dname2 = (TextView)convertView.findViewById(R.id.dname2);
            ImageView dimg2 = (ImageView)convertView.findViewById(R.id.dimg2);

            dname1.setText(name1[position]);
            dimg1.setImageResource(img1[position]);

            dname2.setText(name2[position]);
            dimg2.setImageResource(img2[position]);

            dimg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this,product_description.class);
                    startActivity(intent);
                }
            });

            dimg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this,product_description.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}