package com.dia.mous.lecteurdenews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dia.mous.lecteurdenews.Fragment.WebviewActivityFragment;

public class WebViewActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.webview_activity);
        setTitle(getIntent().getStringExtra("titre"));
        WebviewActivityFragment fragment = WebviewActivityFragment.create(getIntent().getStringExtra("link"));
        getFragmentManager().beginTransaction()
                .add(android.R.id.content, fragment)//construction permettant de construitre un fragment sans layout
                // (android.R.id.content) est la vue de base d'une activité lorsqu'on ne lui a pas chargé de layout.
                //cette ligne permet d'afficher un fragment dans une activité sans charger de layout
                .commit();

    }

}
