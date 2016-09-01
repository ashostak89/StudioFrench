package com.example.ashostak89.studiofrench;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashostak89.studiofrench.conectors.Specials;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpecialsActivity extends AppCompatActivity {


    ListView listView;
    MySpecialArrayAdapter adapter;
    ArrayList<Specials> specialsAry;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specials);


    }
    @Override
    protected void onResume() {
        super.onResume();
        linearLayout= (LinearLayout) findViewById(R.id.linearspecial);
        Firebase backgroundref =new Firebase("https://frenchstudio-98610.firebaseio.com/backgroun_special");
        backgroundref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Bitmap btGmap=decodeBase64(dataSnapshot.getValue(String.class));
                BitmapDrawable bitmapDrawableGmap=new BitmapDrawable(btGmap);
                linearLayout.setBackground(bitmapDrawableGmap);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

       specialsAry =new ArrayList<Specials>();
        listView= (ListView) findViewById(R.id.special_lv);
        adapter=new MySpecialArrayAdapter(this,R.layout.specials_item_list,specialsAry);
        listView.setAdapter(adapter);
        Firebase ref =new Firebase("https://frenchstudio-98610.firebaseio.com/specials");
        ref.addChildEventListener(reflis);
    }
    private ChildEventListener reflis=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Specials specials=new Specials(dataSnapshot.getKey(),dataSnapshot.getValue(String.class));
            specialsAry.add(specials);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String st=dataSnapshot.getKey();
            for (int i = 0; i< specialsAry.size(); i++){
                if (specialsAry.get(i).getSpecialName().equals(st));
                specialsAry.get(i).setSpecialInfo(dataSnapshot.getValue(String.class));
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String st=dataSnapshot.getKey();
            for (int i = 0; i< specialsAry.size(); i++){
                if (specialsAry.get(i).getSpecialName().equals(st));
                specialsAry.remove(i);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    };
    public class MySpecialArrayAdapter extends ArrayAdapter<Specials> {

        private Context context;

        private int resource;
        private List<Specials> object;
        public MySpecialArrayAdapter(Context context, int resource, List<Specials> objects) {
            super(context, resource, objects);
            this.context=context;
            this.resource=resource;
            this.object=objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            }
            TextView tvname= (TextView) convertView.findViewById(R.id.specialtv);
            tvname.setText(object.get(position).getSpecialInfo());
            return convertView;
        }

    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
