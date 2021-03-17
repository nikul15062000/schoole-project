package com.example.schoolapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Activity.Youtube;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.example.schoolapp.converter.convert;
import com.example.schoolapp.pdfread.readPdfs;

import java.util.List;

public class optionAdapter extends RecyclerView.Adapter<optionAdapter.myviewholder> {

    Context context;
    List<String> nameList;

    public optionAdapter(Context context, List<String> nameList) {
        this.context = context;
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public optionAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.partlist, parent, false);
        return new optionAdapter.myviewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        //holder.textView.setText (nameList.get (position));
        String text = nameList.get (position);
        convert ct = new convert ();
        ct.convertText (text,holder.textView);
        holder.textView.setOnClickListener (v -> {
            if(nameList.get (position).equals ("video")){
                Intent i = new Intent (context, Youtube.class);
                Constant.option = "video";
                context.startActivity (i);
            }
            if(nameList.get (position).equals ("pdf")){
                Intent i = new Intent (context, readPdfs.class);
                Constant.option ="pdf";
                context.startActivity (i);
            }
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