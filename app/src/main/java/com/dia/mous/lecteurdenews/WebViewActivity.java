package com.dia.mous.lecteurdenews;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        // On récupère l'intent qui a lancé cette activité
        Intent i = getIntent();

        // Puis on récupère le htmlContent donné dans l'autre classe.
        String url = i.getStringExtra(MonAdapter.monUrl);

        //insertion de contenu dans la webview
        WebView wv = (WebView)findViewById(R.id.ma_webview);
        //permettre à la page web charger de s'ouvrir au sein de la webview et non dans le navigateur du téléphone
        wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.toString());
                    return false;
                }
            });
            wv.loadUrl(url);
    }

}
