package com.example.schoolapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Activity.semester;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.example.schoolapp.converter.convert;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.List;

public class StandardAdapter extends RecyclerView.Adapter<StandardAdapter.myviewholder> {

    Context context;
    List<String> nameList;

    public StandardAdapter(Context context, List<String> nameList) {
        this.context = context;
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public StandardAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.standardlist, parent, false);
        return new myviewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        String text = nameList.get (position);
        convert ct = new convert ();
        ct.convertText (text,holder.textView);
        holder.textView.setOnClickListener (v -> {
            Intent i = new Intent (context, semester.class);
            Constant.standard = nameList.get (position);
            context.startActivity (i);
        });
    }




    @Override
    public int getItemCount() {
        return nameList.size ();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView textView;

        public myviewholder(@NonNull View itemView) {
            super (itemView);
            textView = itemView.findViewById (R.id.text1);
        }
    }
}