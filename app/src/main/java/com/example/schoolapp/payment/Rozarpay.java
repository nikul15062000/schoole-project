package com.example.schoolapp.payment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.Activity.Standard;
import com.example.schoolapp.Global.Session;
import com.example.schoolapp.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;


import org.json.JSONObject;


public class Rozarpay extends Activity implements PaymentResultListener {
    private static final String TAG = Standard.class.getSimpleName ();
    public Session Sessionclass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_rozarpay);

        Sessionclass = new Session (this);
         /*
              To ensure faster loading of the Checkout form,
              call this method as early as possible in your checkout flow.
         */
        Checkout.preload (getApplicationContext ());
        // Payment button created by you in XML layout
        Button button = (Button) findViewById (R.id.btn_pay);

        button.setOnClickListener (v -> startPayment ());

        TextView privacyPolicy = (TextView) findViewById (R.id.txt_privacy_policy);

        privacyPolicy.setOnClickListener (v -> {
            Intent httpIntent = new Intent (Intent.ACTION_VIEW);
            httpIntent.setData (Uri.parse ("https://razorpay.com/sample-application/"));
            startActivity (httpIntent);
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout ();
        try {
            JSONObject options = new JSONObject ();
            options.put ("name", "Razorpay Corp");
            options.put ("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put ("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put ("currency", "INR");
            options.put ("amount", "100");

            JSONObject preFill = new JSONObject ();
            preFill.put ("email", "test@razorpay.com");
            preFill.put ("contact", "9876543210");

            options.put ("prefill", preFill);

            co.open (activity, options);
        } catch (Exception e) {
            Toast.makeText (activity, "Error in payment: " + e.getMessage (), Toast.LENGTH_SHORT)
                    .show ();
            e.printStackTrace ();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText (this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show ();
            Sessionclass.setPay (1);
            Intent i = new Intent (Rozarpay.this, Standard.class);
            startActivity (i);
            finish ();
        } catch (Exception e) {
            Log.e (TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText (this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show ();
        } catch (Exception e) {
            Log.e (TAG, "Exception in onPaymentError", e);
        }
    }
}