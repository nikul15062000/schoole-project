package com.example.schoolapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.Activity.Standard;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.Global.Session;
import com.example.schoolapp.R;
import com.example.schoolapp.payment.Rozarpay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInPage extends AppCompatActivity {

    TextView Register,ForgetPassword;
    EditText userPhone, userpass;
    Button Login;
    private Session session;
    String MobilePattern = "^[0-9]{10}$";
    String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";
    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sign_in_page);

        Bindview ();
    }

    private void Bindview() {
        ForgetPassword = findViewById (R.id.textView_forget_password_login);
        userPhone = findViewById (R.id.editText_email_login_activity);
        userpass = findViewById (R.id.editText_password_login_activity);
        Login = findViewById (R.id.button_login_activity);
        Register = findViewById (R.id.textView_signup_login);
        Login.setOnClickListener (v -> LoginUserDetail ());
        Register.setOnClickListener (v -> RegisterPager ());
        ForgetPassword.setOnClickListener (v -> Forget ());
    }

    private void Forget() {
        startActivity (new Intent (SignInPage.this, Forget.class));
        finish ();
    }

    private void RegisterPager() {
        startActivity (new Intent (SignInPage.this, SignUpPage.class));
        finish ();
    }

    private void LoginUserDetail() {

        String phone = userPhone.getText ().toString ().trim();
        String pass = userpass.getText ().toString ().trim();

        if(phonevalidation (phone) && passwordCharValidation (pass)){
            LoginUser (phone,pass);
        }else{
            Toast.makeText (this, ""+error, Toast.LENGTH_SHORT).show ();
        }

    }

    private boolean phonevalidation(String phone) {
        if (phone.matches (MobilePattern)) {
            return true;
        }
        error = "Worng Phone Patter";
        return false;
    }


    public boolean passwordCharValidation(String passwordEd) {
        if (passwordEd.matches (PASSWORD_PATTERN)) {
            return true;
        }
        error = "Worng Password Patter";
        return false;
    }


    private void LoginUser(String phone, String password) {
        DatabaseReference myRef = FirebaseDatabase.getInstance ().getReference ("register");
        Query checkUser = myRef.child (phone);
        checkUser.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists ()) {
                    String passwordFromDB = snapshot.child ("password").getValue (String.class);
                    if (passwordFromDB.equals (password)) {
                        session = new Session(SignInPage.this);
                        session.setusename(""+phone);

                        if (Constant.payment == 0) {
                            Intent i = new Intent (SignInPage.this, Rozarpay.class);
                            startActivity (i);
                            finish ();
                        }else{
                            Intent i = new Intent (SignInPage.this, Standard.class);
                            startActivity (i);
                            finish ();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (SignInPage.this, "" + error, Toast.LENGTH_SHORT).show ();
            }
        });
    }
}