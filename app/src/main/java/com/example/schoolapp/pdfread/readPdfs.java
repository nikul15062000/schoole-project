package com.example.schoolapp.pdfread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.schoolapp.Activity.Youtube;
import com.example.schoolapp.Adapter.SubjectAdapter;
import com.example.schoolapp.Adapter.pdfAdapter;
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
import java.util.List;
import java.util.Map;

public class readPdfs extends AppCompatActivity {

    RecyclerView recyclerView;
    pdfAdapter pdfAdapterread;
    List<String> nameList,UrlList;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView (R.layout.activity_pdfread);

        bindview();
    }

    private void bindview() {
        pd = new ProgressDialog (this);
        pd.setMessage ("loading");
        nameList = new ArrayList<> ();
        UrlList = new ArrayList<> ();
        SetRecycleView ();
        getData ();
    }

    private void SetRecycleView() {
        recyclerView = findViewById (R.id.recycle1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager (this, 1);
        recyclerView.setLayoutManager (linearLayoutManager);
        pdfAdapterread = new pdfAdapter (this, nameList,UrlList);
        recyclerView.setAdapter (pdfAdapterread);
    }

    private void getData() {
        nameList.clear ();
        pd.show ();
        DatabaseReference myRef = FirebaseDatabase.getInstance ().getReference ("student");
        Query checkUser = myRef.child (Constant.standard).child (Constant.sem).child (Constant.subject).child (Constant.chepter).child (Constant.part).child (Constant.option);
        checkUser.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JSONObject partsData = null;
                try {
                    partsData = new JSONObject ((Map)snapshot.getValue ());
                    String name = partsData.getString ("name");
                    String pdfurl = partsData.getString ("pdf");
                    nameList.add (name);
                    UrlList.add (pdfurl);
                } catch (JSONException e) {
                    Log.e ("error=================================",e.getMessage ());
                }
                pdfAdapterread.notifyDataSetChanged ();
                pd.cancel ();
                pd.dismiss ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}