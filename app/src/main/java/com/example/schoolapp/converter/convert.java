package com.example.schoolapp.converter;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.schoolapp.Global.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class convert {

    public convert() {
    }

    public void convertText(String text, TextView textView) {
        FirebaseTranslatorOptions options;
        if (Constant.language == 0) {
            options =
                    new FirebaseTranslatorOptions.Builder ()
                            .setSourceLanguage (FirebaseTranslateLanguage.EN)
                            .setTargetLanguage (FirebaseTranslateLanguage.EN)
                            .build ();
        } else if (Constant.language == 1) {
            options =
                    new FirebaseTranslatorOptions.Builder ()
                            .setSourceLanguage (FirebaseTranslateLanguage.EN)
                            .setTargetLanguage (FirebaseTranslateLanguage.HI)
                            .build ();
        } else {
            options =
                    new FirebaseTranslatorOptions.Builder ()
                            .setSourceLanguage (FirebaseTranslateLanguage.EN)
                            .setTargetLanguage (FirebaseTranslateLanguage.GU)
                            .build ();
        }
       /* FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder ()
                        .setSourceLanguage (FirebaseTranslateLanguage.EN)
                        .setTargetLanguage (FirebaseTranslateLanguage.GU)
                        .build ();*/
        final FirebaseTranslator translator =
                FirebaseNaturalLanguage.getInstance ().getTranslator (options);
        translator.downloadModelIfNeeded ()
                .addOnSuccessListener (
                        new OnSuccessListener<Void> () {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
                                startTranslate (translator, text, textView);
                            }
                        })
                .addOnFailureListener (
                        new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e ("Error==============================", e.getMessage ());
                            }
                        });
    }

    private void startTranslate(FirebaseTranslator translater, String text, TextView holder) {
        translater.translate (text)
                .addOnSuccessListener (
                        new OnSuccessListener<String> () {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                holder.setText (translatedText);
                            }
                        })
                .addOnFailureListener (
                        new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e ("error===========================", e.getMessage ());
                            }
                        });
    }


}
