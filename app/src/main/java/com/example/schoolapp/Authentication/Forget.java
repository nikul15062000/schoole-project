package com.example.schoolapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.schoolapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Forget extends AppCompatActivity {

    LinearLayout layout1,layout2;
    EditText email,phone,pass,conpass;
    Button verify,change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_forget);

        Bindview();
    }

    private void Bindview() {
        //btns
        change = findViewById (R.id.changepass);
        verify = findViewById (R.id.btnVerify);
        //layouts
        layout1 = findViewById (R.id.layout1);
        layout2 = findViewById (R.id.layout2);
        //change pass
        pass = findViewById (R.id.password);
        conpass = findViewById (R.id.conpassword);
        //verify user
        email = findViewById (R.id.emailname);
        phone = findViewById (R.id.phonenumber);

        verify.setOnClickListener (v -> {
            Verify(email.getText ().toString (),phone.getText ().toString ());
        });
        change.setOnClickListener (v -> {
            ChangePass(pass.getText ().toString (),conpass.getText ().toString (),phone.getText ().toString ());
        });
    }

    private void ChangePass(String pass, String conpass, String phone) {
        if(pass.equals (conpass)){
            FirebaseDatabase database = FirebaseDatabase.getInstance ();
            DatabaseReference myRef = database.getReference ("register").child (phone).child ("password");
            myRef.setValue (pass);
        }
    }


    private void Verify(String email, String phone) {
        DatabaseReference myRef = FirebaseDatabase.getInstance ().getReference ("register");
        Query checkUser = myRef.child (phone);
        checkUser.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists ()) {
                    String phoneDB = snapshot.child ("phone").getValue (String.class);
                    String emailDB = snapshot.child ("email").getValue (String.class);
                    if (phoneDB.equals (phone)) {
                        if (emailDB.equals (email)) {
                            layout1.setVisibility (View.GONE);
                            layout2.setVisibility (View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (Forget.this, "" + error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });
    }
}