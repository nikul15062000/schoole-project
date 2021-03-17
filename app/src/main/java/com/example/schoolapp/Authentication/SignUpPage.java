package com.example.schoolapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.schoolapp.Global.Session;
import com.example.schoolapp.Model.studentRegister;
import com.example.schoolapp.R;
import com.example.schoolapp.payment.Rozarpay;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpPage extends AppCompatActivity {

    TextView Login;
     EditText username, useremail, userphone, userpassword;
    Button Register;
    String NamePatter = "^[A-Za-z]+$";
    String MobilePattern = "^[0-9]{10}$";
    String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String error = null;
    String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";
    public Session SessionClass;
    public static String name;
    public static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sign_up_page);
        Bindview ();
    }

    private void Bindview() {
        SessionClass = new Session (this);
        username = findViewById (R.id.editText_name_register);
        useremail = findViewById (R.id.editText_email_register);
        userphone = findViewById (R.id.editText_phoneNo_register);
        userpassword = findViewById (R.id.editText_password_register);
        Login = findViewById (R.id.textView_login_register);
        Register = findViewById (R.id.button_submit);
        Register.setOnClickListener (v -> RegisterDetail ());
        Login.setOnClickListener (v -> LoginPage ());
    }

    private void LoginPage() {
        startActivity (new Intent (SignUpPage.this, SignInPage.class));
        finish ();
    }


    private void RegisterDetail() {
         name = username.getText ().toString ();
         email = useremail.getText ().toString ();
        String phone = userphone.getText ().toString ();
        String password = userpassword.getText ().toString ();

        if (Uservalidation (name) && phonevalidation (phone) && passwordCharValidation (password) && emailvalidation (email)) {
            studentRegister registerDetail = new studentRegister (name, email, phone, password);
            FirebaseDatabase database = FirebaseDatabase.getInstance ();
            DatabaseReference myRef = database.getReference ("register").child (phone);
            myRef.setValue (registerDetail);
            SessionClass.setRegister (1);
            GoToPay(phone);
        }else{
            Toast.makeText (this, ""+error, Toast.LENGTH_SHORT).show ();
        }
    }

    private void GoToPay(String phone) {
        Intent i = new Intent (SignUpPage.this, Rozarpay.class);
        i.putExtra ("number",phone);
        startActivity (i);
        finish ();
    }


    private boolean emailvalidation(String email) {
        if (email.matches (EMAIL_STRING)) {
            return true;
        }
        error = "Worng Email Patter";
        return false;
    }

    private boolean Uservalidation(String name) {
        if (name.matches (NamePatter)) {
            return true;
        }
        error = "Worng Name Patter";
        return false;
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
}