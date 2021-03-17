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

import com.example.schoolapp.Adapter.SubjectAdapter;
import com.example.schoolapp.Adapter.optionAdapter;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class option extends AppCompatActivity {

    List<String> nameList;
    optionAdapter optionadp;
    RecyclerView recyclerView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_option);

        Bindview ();
    }


    private void Bindview() {
        pd = new ProgressDialog (this);
        pd.setMessage ("loading");
        nameList = new ArrayList<> ();
        SetRecycleView ();
        getData ();
    }

    private void getData() {
        nameList.clear ();
        pd.show ();
        DatabaseReference myRef = FirebaseDatabase.getInstance ().getReference ("student");
        Query checkUser = myRef.child (Constant.standard).child (Constant.sem).child (Constant.subject).child (Constant.chepter).child (Constant.part);
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
                    Log.e ("Data===========================",e.getMessage ());
                }
                optionadp.notifyDataSetChanged ();
                pd.dismiss ();
                pd.cancel ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (option.this, "error", Toast.LENGTH_SHORT).show ();
                pd.dismiss ();
                pd.cancel ();
            }
        });
    }

    private void SetRecycleView() {
        recyclerView = findViewById (R.id.recycle1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager (this, 1);
        recyclerView.setLayoutManager (linearLayoutManager);
        optionadp = new optionAdapter (this, nameList);
        recyclerView.setAdapter (optionadp);
    }

}