package com.example.schoolapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.Global.Session;
import com.example.schoolapp.R;

public class languagePage extends AppCompatActivity {

    TextView en, hi, gu;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_language_page);
        session = new Session (this);
        bindview ();
        clickEvent ();
    }

    private void clickEvent() {
        en.setOnClickListener (v -> {
            Constant.language = 0;
            session.setLan (0);
            goToActivity ();
        });
        hi.setOnClickListener (v -> {
            session.setLan (1);
            Constant.language = 1;
            goToActivity ();
        });
        gu.setOnClickListener (v -> {
            session.setLan (2);
            Constant.language = 2;
            goToActivity ();
        });
    }

    public void goToActivity() {
        Intent i = new Intent (languagePage.this, Standard.class);
        startActivity (i);
        finish ();
    }

    private void bindview() {
        en = findViewById (R.id.eng);
        hi = findViewById (R.id.hind);
        gu = findViewById (R.id.guj);
    }
}