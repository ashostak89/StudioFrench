package com.example.ashostak89.studiofrench;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class EmailActivity extends AppCompatActivity {


    TableLayout tableLayout;
    Button btnsand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        tableLayout= (TableLayout) findViewById(R.id.tablelayout);
        btnsand= (Button) findViewById(R.id.btnsand);
        btnsand.setOnClickListener(sandlis);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Firebase backgroundref =new Firebase("https://frenchstudio-98610.firebaseio.com/background_email");
        backgroundref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Bitmap btGmap=decodeBase64(dataSnapshot.getValue(String.class));
                BitmapDrawable bitmapDrawableGmap=new BitmapDrawable(btGmap);
                tableLayout.setBackground(bitmapDrawableGmap);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private View.OnClickListener sandlis=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };


}
