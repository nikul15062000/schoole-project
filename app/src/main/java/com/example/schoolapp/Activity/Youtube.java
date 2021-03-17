package com.example.schoolapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.schoolapp.Adapter.StandardAdapter;
import com.example.schoolapp.Adapter.youtubeAdapter;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Youtube extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> nameList,UrlList;
    youtubeAdapter yadapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_youtube);

        Bindview ();
    }

    private void Bindview() {
        pd = new ProgressDialog (this);
        pd.setMessage ("loading");
        nameList = new ArrayList<> ();
        UrlList = new ArrayList<> ();
        SetRecycleView();
        getData ();
    }

    private void SetRecycleView() {
        recyclerView = findViewById (R.id.recycle1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager (this, 1);
        recyclerView.setLayoutManager (linearLayoutManager);
        yadapter = new youtubeAdapter (this, nameList,UrlList);
        recyclerView.setAdapter (yadapter);
    }

    private void getData() {
        pd.show ();
        nameList.clear ();
        UrlList.clear ();
        nameList.clear ();
        DatabaseReference myRef = FirebaseDatabase.getInstance ().getReference ("student");
        Query checkUser = myRef.child (Constant.standard).child (Constant.sem).child (Constant.subject).child (Constant.chepter).child (Constant.part).child (Constant.option);
        checkUser.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JSONObject partsData = null;
                try {
                    partsData = new JSONObject ((Map)snapshot.getValue ());
                    String name = partsData.getString ("name");
                    String url = partsData.getString ("url");
                    nameList.add (name);
                    UrlList.add (url);
                } catch (JSONException e) {
                    Log.e ("Data===========================",e.getMessage ());
                }
                yadapter.notifyDataSetChanged ();
                pd.dismiss ();
                pd.cancel ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (Youtube.this, ""+error.getMessage (), Toast.LENGTH_SHORT).show ();
                pd.dismiss ();
                pd.cancel ();
            }
        });

    }
}