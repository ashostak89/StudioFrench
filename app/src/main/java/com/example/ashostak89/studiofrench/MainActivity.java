package com.example.ashostak89.studiofrench;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    static  final String PREFS_NAME = "MyPrefsFile";
    static  final String USER_FIRST_NAME = "user_first_name";
    static  final String USER_LAST_NAME = "user_last_name";
    static  final String USER_PHONE = "user_phone";
    static  final String USER_EMAIL = "user_email";
    Firebase mRef;
    Firebase mRefBackground;
    LinearLayout linearLayout;
    LinearLayout linearLogo;
    ImageView ivGMap;
    ImageView ivWaze;
    ImageView ivFace;
    ImageView ivPhone;
    ImageView ivHome;
    String urlGmap="";
    String urlWaze="";
    String urlFace="";
    String urlPhoneNum="";
    String urlHomePage="";
    Button btnOurVideo;
    Button btnPhoto;
    Button btnPrices;
    Button btnSpecials;
    Button btnbuy;
    Handler h;
    ProgressDialog progressDialog;
    int j=0;
    int i=2000;
    SharedPreferences shPre;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myFindViewByIdAndLiseners();
        shPre= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            myFirstdialog();
        }




//        progressDialog = new ProgressDialog(this,R.style.MyTheme);
//        progressDialog.setCancelable(false);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//        progressDialog.show();
//        h = new Handler() {
//            public void handleMessage(Message msg) {
//                if (j < i) {
//                    j+=2000;
//                    h.sendEmptyMessageDelayed(0, 1000);
//                } else {
//                    progressDialog.dismiss();
//                }
//            }
//        };
//        h.sendEmptyMessageDelayed(0, 1000);

    }


    @Override
    protected void onStart() {
        super.onStart();

        mRef =new Firebase("https://frenchstudio-98610.firebaseio.com");
        mRef.child("logo").addValueEventListener( mRefValueEventListener);
        mRef.child("background").addValueEventListener(mRefValueEventListener);
        mRef.child("addressforgmap").addValueEventListener(mRefValueEventListener);
        mRef.child("addressforwaze").addValueEventListener(mRefValueEventListener);
        mRef.child("addressForFacebook").addValueEventListener(mRefValueEventListener);
        mRef.child("homepage").addValueEventListener(mRefValueEventListener);
        mRef.child("phone").addValueEventListener(mRefValueEventListener);

    }



    public void myFindViewByIdAndLiseners(){
        btnSpecials= (Button) findViewById(R.id.btnspec);
        btnSpecials.setOnClickListener(this);
        btnbuy = (Button) findViewById(R.id.btnbuy);
        btnbuy.setOnClickListener(this);
        ivGMap= (ImageView) findViewById(R.id.ivgmap);
        ivWaze= (ImageView) findViewById(R.id.ivwaze);
        ivFace= (ImageView) findViewById(R.id.ivface);
        ivPhone= (ImageView) findViewById(R.id.ivphone);
        ivHome= (ImageView) findViewById(R.id.ivhome);
        ivGMap.setOnClickListener(MainActivity.this);
        ivWaze.setOnClickListener(MainActivity.this);
        ivFace.setOnClickListener(MainActivity.this);
        ivPhone.setOnClickListener(MainActivity.this);
        ivHome.setOnClickListener(MainActivity.this);
        linearLayout= (LinearLayout) findViewById(R.id.linear);
        linearLogo= (LinearLayout) findViewById(R.id.linearlogo);
        btnOurVideo= (Button) findViewById(R.id.btnvideo);
        btnOurVideo.setOnClickListener(MainActivity.this);
        btnPhoto = (Button) findViewById(R.id.btnphoto);
        btnPhoto.setOnClickListener(MainActivity.this);
        btnPrices= (Button) findViewById(R.id.btnprice);
        btnPrices.setOnClickListener(MainActivity.this);
}


    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivgmap:
                Uri gmmIntentUri = Uri.parse(urlGmap);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;
            case R.id.ivwaze:
                try
                {
                    String url = urlWaze;
                    Intent wazeIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                    startActivity( wazeIntent );
                }
                catch ( ActivityNotFoundException ex  )
                {
                    Intent wazeIntent =
                            new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                    startActivity(wazeIntent);
                }
                break;
            case R.id.ivface:
                    String faceurl = urlFace;
                    Intent faceIntent = new Intent(Intent.ACTION_VIEW);
                   faceIntent.setData(Uri.parse(faceurl));
                    startActivity(faceIntent);

                break;
            case R.id.ivphone:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:"+urlPhoneNum));
                startActivity(phoneIntent);
                break;
            case R.id.ivhome:
                String url = urlHomePage;
                Intent homeIntent = new Intent(Intent.ACTION_VIEW);
                homeIntent.setData(Uri.parse(url));
                startActivity(homeIntent);
                break;
            case R.id.btnvideo:
                Intent ourVideoIntent=new Intent(MainActivity.this,OurVideoActivity.class);
                startActivity(ourVideoIntent);
                break;
            case R.id.btnphoto:
                Intent photoIntent=new Intent(MainActivity.this,OurVideoActivity.class);
                startActivity(photoIntent);
                break;
            case R.id.btnprice:
                Intent pricesIntent=new Intent(MainActivity.this,PricesActivity.class);
                startActivity(pricesIntent);
                break;
            case R.id.btnspec:
                Intent specialIntent=new Intent(MainActivity.this,SpecialsActivity.class);
                startActivity(specialIntent);
                break;
            case R.id.btnbuy:
                Intent emailIntent=new Intent(MainActivity.this,EmailActivity.class);
               startActivity(emailIntent);
                break;

        }
    }

    private ValueEventListener mRefValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String text=dataSnapshot.getValue(String.class);
            switch (dataSnapshot.getKey()){
                case "background":
                Bitmap btGmap=decodeBase64(text);
                BitmapDrawable bitmapDrawableGmap=new BitmapDrawable(btGmap);
                linearLayout.setBackground(bitmapDrawableGmap);
                break;
                case "logo":
                    Bitmap btWaze=decodeBase64(text);
                    BitmapDrawable bitmapDrawableWaze=new BitmapDrawable(btWaze);
                    linearLogo.setBackground(bitmapDrawableWaze);
                    break;
                case "addressforgmap":
urlGmap=text;
                    break;
                case "addressforwazec":
urlWaze=text;
                    break;
                case "addressForFacebook":
                    urlFace=text;
                    break;
                case "phone":
urlPhoneNum=text;
                    break;
                case "homepage":
urlHomePage=text;
                    break;
            }
        }
        @Override
        public void onCancelled(FirebaseError firebaseError) {}};



    private void myFirstdialog(){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1 = li.inflate(R.layout.activity_my_alert_dialog, null);
        final EditText edFirst = (EditText) v1.findViewById(R.id.etfirstname);
        final EditText edLast = (EditText) v1.findViewById(R.id.etlastname);
        final EditText edPhone = (EditText) v1.findViewById(R.id.etphonenumber);
        final EditText edEmail = (EditText) v1.findViewById(R.id.etemail);


        adb.setView(v1);
        adb.setTitle(MainActivity.this.getString(R.string.alart_title));

        adb.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String stFirst = edFirst.getText().toString();
                String stLast = edLast.getText().toString();
                String stPhone = edPhone.getText().toString();
                String stEmail = edEmail.getText().toString();
                SharedPreferences.Editor editor = shPre.edit();
                editor.putString(USER_FIRST_NAME, stFirst);
                editor.putString(USER_LAST_NAME,stLast);
                editor.putString(USER_PHONE,stPhone);
                editor.putString(USER_EMAIL,stEmail);
                editor.commit();
                settings = getSharedPreferences(PREFS_NAME, 0);
                settings.edit().putBoolean("my_first_time", false).commit();
            }
        });

        adb.setCancelable(false);
        final AlertDialog alert1=adb.create();
        alert1.show();
        if (edFirst.getText().toString().equals("")||edLast.getText().toString().equals("")){
            alert1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
        edFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edFirst.getText().toString().equals("") || edLast.getText().toString().equals("")) {
                    alert1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else alert1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edLast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edFirst.getText().toString().equals("") || edLast.getText().toString().equals("")) {
                    alert1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else alert1.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}


