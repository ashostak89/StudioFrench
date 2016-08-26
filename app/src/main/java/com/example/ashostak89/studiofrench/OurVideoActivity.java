package com.example.ashostak89.studiofrench;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.List;

public class OurVideoActivity extends AppCompatActivity {
    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

ListView listView;
    ArrayList<Video>videoAry;
    MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_video);

    }



    @Override
    protected void onResume() {
        super.onResume();
        videoAry=new ArrayList<Video>();
        listView= (ListView) findViewById(R.id.listView);
        adapter=new MyArrayAdapter(this,R.layout.video_list_item,videoAry);
        listView.setAdapter(adapter);
        final Firebase ref=new Firebase("https://frenchstudio-98610.firebaseio.com/video");
        ref.addChildEventListener(reflis);

    }

    public static void playinwindow(Context context, String url, Activity activity) {

        Intent myintent = null;
        myintent = YouTubeStandalonePlayer.createVideoIntent(
                activity, Config.DEVELOPER_KEY, url, 0, true, true);
        if (myintent != null) {
            if (canResolveIntent(myintent, activity)) {
                activity.startActivityForResult(myintent, REQ_START_STANDALONE_PLAYER);
            } else {
                // Could not resolve the intent - must need to install or update the YouTube API service.
                YouTubeInitializationResult.SERVICE_MISSING
                        .getErrorDialog(activity, REQ_RESOLVE_SERVICE_MISSING).show();
            }
        }
    }

    private static boolean canResolveIntent(Intent intent, Activity activity) {
        List<ResolveInfo> resolveInfo = activity.getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != OurVideoActivity.RESULT_OK) {
            YouTubeInitializationResult errorReason =
                    YouTubeStandalonePlayer.getReturnedInitializationResult(data);
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(OurVideoActivity.this, 0).show();
            } else {
                String errorMessage =
                        String.format(getString(R.string.error_player), errorReason.toString());
                Toast.makeText(OurVideoActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }

    }



    private ChildEventListener reflis=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
Video video=new Video(dataSnapshot.getKey(),dataSnapshot.getValue(String.class));
            videoAry.add(video);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
String st=dataSnapshot.getKey();
            for (int i=0;i<videoAry.size();i++){
                if (videoAry.get(i).getVideoName().equals(st));
                videoAry.get(i).setVideoUrl(dataSnapshot.getValue(String.class));
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String st=dataSnapshot.getKey();
            for (int i=0;i<videoAry.size();i++){
                if (videoAry.get(i).getVideoName().equals(st));
                videoAry.remove(i);
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
public class MyArrayAdapter extends ArrayAdapter<Video>{
   private Context context;
    private int resource;
    private List<Video> object;
    public MyArrayAdapter(Context context, int resource, List<Video> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.object=objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        TextView tv= (TextView) convertView.findViewById(R.id.video_name);
        ImageView iv= (ImageView) convertView.findViewById(R.id.video_link);
        tv.setText(object.get(position).getVideoName());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playinwindow(getApplicationContext(),object.get(position).getVideoUrl(),OurVideoActivity.this);
            }
        });

        return convertView;
    }
}

}
