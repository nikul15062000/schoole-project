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

import com.example.schoolapp.Activity.Subject;
import com.example.schoolapp.Activity.Youtube;
import com.example.schoolapp.Activity.option;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.example.schoolapp.converter.convert;
import com.example.schoolapp.pdfread.readPdfs;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.myviewholder> {

    Context context;
    List<String> nameList;

    public PartAdapter(Context context, List<String> nameList) {
        this.context = context;
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public PartAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.partlist, parent, false);
        return new PartAdapter.myviewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        //holder.textView.setText (nameList.get (position));
        String text = nameList.get (position);
        convert ct = new convert ();
        ct.convertText (text,holder.textView);
        holder.textView.setOnClickListener (v -> {
            Intent i = new Intent (context, option.class);
            Constant.part = nameList.get (position);
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