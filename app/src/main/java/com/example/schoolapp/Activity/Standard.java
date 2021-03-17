package com.example.schoolapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.Adapter.StandardAdapter;
import com.example.schoolapp.Adapter.SubjectAdapter;
import com.example.schoolapp.Authentication.SignInPage;
import com.example.schoolapp.Authentication.SignUpPage;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.Global.Session;
import com.example.schoolapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Standard extends AppCompatActivity implements OnNavigationItemSelectedListener{

    List<String> nameList;
    RecyclerView recyclerView;
    StandardAdapter standardAdapter;
  //  ProgressDialog pd;
//    Button lang, skip;
    private Session session;
    DrawerLayout drowarlayout;
    ImageView menu,lenguage;
    NavigationView navigationview;
    TextView nav_header_name_id,nav_header_emailaddress_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        Bindview();
        validate();
        clickEvent();
        navigationview.setNavigationItemSelectedListener(this);
    }

    private void clickEvent() {
        lenguage.setOnClickListener (v -> {
            Intent i = new Intent (Standard.this, languagePage.class);
            startActivity (i);
            finish ();
        });
       /* skip.setOnClickListener (v -> {
            Intent i = new Intent (MainActivity.this, Standard.class);
            startActivity (i);
            finish ();
        });*/
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drowarlayout.openDrawer(GravityCompat.START);
            }
        });

    }


    private void validate() {
        if (!session.getusename ()) {
            if (Constant.register == 0) {
                Intent i = new Intent (Standard.this, SignUpPage.class);
                startActivity (i);
                finish ();
            }else{
                Intent i = new Intent (Standard.this, SignInPage.class);
                startActivity (i);
                finish ();
            }
        }else{
            if (Constant.payment == 0) {
                Intent i = new Intent (Standard.this, SignInPage.class);
                startActivity (i);
                finish ();
            }
        }
    }
    private void Bindview() {
        session = new Session (this);
        Constant.language = session.getLan ();
        Constant.payment = session.getPay ();
        Constant.register = session.getRegister ();
        lenguage = findViewById (R.id.lenguage);
        nav_header_name_id = findViewById (R.id.nav_header_name_id);
        nav_header_emailaddress_id = findViewById (R.id.nav_header_emailaddress_id);
/*        nav_header_name_id.setText(SignUpPage.name);
        nav_header_emailaddress_id.setText(SignUpPage.email);*/
        drowarlayout=findViewById(R.id.drowarlayout);
        menu=findViewById(R.id.menu);
        navigationview=findViewById(R.id.navigationview);
      //  pd = new ProgressDialog (this);
      //  pd.setMessage ("loading");
        nameList = new ArrayList<> ();
        SetRecycleView ();
        getData ();
    }

    private void getData() {
       // pd.show ();
        nameList.clear ();
        DatabaseReference myRef = FirebaseDatabase.getInstance ().getReference ("student");
        Query checkUser = myRef;
        checkUser.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JSONObject partsData = null;
                try {
                    partsData = new JSONObject ((Map) snapshot.getValue ());
                    Iterator<String> iterator = partsData.keys ();
                    while (iterator.hasNext ()) {
                        String result = iterator.next ();
                        nameList.add (result);
                    }
                } catch (Exception e) {
                    Log.e ("Error=====================================", e.getMessage ());
                }
                standardAdapter.notifyDataSetChanged ();
               // pd.dismiss ();
              //  pd.cancel ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (Standard.this, "Error"+error.getMessage (), Toast.LENGTH_SHORT).show ();
              //  pd.dismiss ();
              //  pd.cancel ();
            }
        });
    }

    private void SetRecycleView() {
        recyclerView = findViewById (R.id.recycle1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager (this, 1);
        recyclerView.setLayoutManager (linearLayoutManager);
        standardAdapter = new StandardAdapter (this, nameList);
        recyclerView.setAdapter (standardAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.nav_rate_id){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()));
            startActivity(i);
        }else if (id == R.id.nav_share_id){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "School");

            String app_url = "https://play.google.com/store/apps/developer?id="+getPackageName();

            shareIntent.putExtra(Intent.EXTRA_TEXT, app_url);

            startActivity(Intent.createChooser(shareIntent, "Share via"));

        }else if (id == R.id.nav_privacy_id){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://greenpearlstudio.blogspot.com/2020/09/privacy-policy_96.html?m=1"));
            startActivity(i);
        }
       drowarlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}