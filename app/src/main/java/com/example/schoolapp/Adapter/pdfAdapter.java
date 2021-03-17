package com.example.schoolapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Activity.Subject;
import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.example.schoolapp.pdfread.readPdfs;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class pdfAdapter extends RecyclerView.Adapter<pdfAdapter.myviewholder> {

    Context context;
    List<String> nameList;
    List<String> urlList;

    public pdfAdapter(Context context,List<String> nameList, List<String> urlList) {
        this.context = context;
        this.nameList = nameList;
        this.urlList = urlList;
    }


    @NonNull
    @Override
    public pdfAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.pdflist,parent,false);
        return new pdfAdapter.myviewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Log.e ("data==========================", urlList.get (position));

        holder.web1.getSettings().setJavaScriptEnabled(true);
        holder.web1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        String url="";
        try {
            url= URLEncoder.encode(urlList.get (position),"UTF-8"); //Url Convert to UTF-8 It important.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        holder.web1.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);
    }


    @Override
    public int getItemCount() {
        return urlList.size ();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        WebView web1;
        public myviewholder(@NonNull View itemView) {
            super (itemView);
            web1 = itemView.findViewById (R.id.webview1);
        }
    }
}