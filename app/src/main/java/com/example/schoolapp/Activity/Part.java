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

import com.example.schoolapp.Adapter.ChapterAdapter;
import com.example.schoolapp.Adapter.PartAdapter;
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

public class Part extends AppCompatActivity {


    List<String> nameList;
    PartAdapter partAdapter;
    RecyclerView recyclerView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_part);

        Bindview ();
    }

    private void Bindview() {
        pd = new ProgressDialog (this);
        pd.setMessage ("loading");
        nameList = new ArrayList<> ();
        getData ();
        SetRecycleView ();
    }

    private void getData() {
        pd.show ();
        nameList.clear ();
        DatabaseReference myRef = FirebaseDatabase.getInstance ().getReference ("student");
        Query checkUser = myRef.child (Constant.standard).child (Constant.sem).child (Constant.subject).child (Constant.chepter);
        checkUser.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JSONObject partsData = null;
                try {
                    partsData = new JSONObject ((Map)snapshot.getValue ());
                    Iterator<String> iterator = partsData.keys();
                    while (iterator.hasNext()) {
                        String result=iterator.next();
                        nameList.add (result);
                    }
                } catch (Exception e) {
                    Toast.makeText (Part.this, ""+e.getMessage (), Toast.LENGTH_SHORT).show ();
                }
                partAdapter.notifyDataSetChanged ();
                pd.show ();
                pd.cancel ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (Part.this, "error", Toast.LENGTH_SHORT).show ();
                pd.dismiss ();
                pd.cancel ();
            }
        });
    }

    private void SetRecycleView() {
        recyclerView = findViewById (R.id.recycle1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager (this, 1);
        recyclerView.setLayoutManager (linearLayoutManager);
        partAdapter = new PartAdapter (this, nameList);
        recyclerView.setAdapter (partAdapter);
    }
}