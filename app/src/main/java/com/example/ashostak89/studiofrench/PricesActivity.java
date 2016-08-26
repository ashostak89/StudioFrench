package com.example.ashostak89.studiofrench;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class PricesActivity extends AppCompatActivity {



ListView listView;
MyPriceArrayAdapter adapter;
    ArrayList<Prices>priceAry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);


    }



    @Override
    protected void onResume() {
        super.onResume();
        priceAry=new ArrayList<Prices>();
        listView= (ListView) findViewById(R.id.price_listView);
        adapter=new MyPriceArrayAdapter(this,R.layout.price_list_item,priceAry);
        listView.setAdapter(adapter);
        Firebase ref =new Firebase(this.getString(R.string.prices_url));
        ref.addChildEventListener(reflis);
    }

    private ChildEventListener reflis=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
Prices price=new Prices(dataSnapshot.getKey(),dataSnapshot.getValue(String.class));
            priceAry.add(price);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String st=dataSnapshot.getKey();
            for (int i=0;i<priceAry.size();i++){
                if (priceAry.get(i).getProcedureName().equals(st));
                priceAry.get(i).setProcedurePrice(dataSnapshot.getValue(String.class));
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String st=dataSnapshot.getKey();
            for (int i=0;i<priceAry.size();i++){
                if (priceAry.get(i).getProcedureName().equals(st));
                priceAry.remove(i);
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



    public class MyPriceArrayAdapter extends ArrayAdapter<Prices>{

        private Context context;

        private int resource;
        private List<Prices> object;
        public MyPriceArrayAdapter(Context context, int resource, List<Prices> objects) {
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
            TextView tvname= (TextView) convertView.findViewById(R.id.tvprocedurename);
            TextView tvprice= (TextView) convertView.findViewById(R.id.tvprocedureprice);
            tvname.setText(object.get(position).getProcedureName());
            tvprice.setText(object.get(position).getProcedurePrice());
            return convertView;
        }

    }
}
