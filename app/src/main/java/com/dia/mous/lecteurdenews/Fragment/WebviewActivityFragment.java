package com.dia.mous.lecteurdenews.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dia.mous.lecteurdenews.MainActivity;
import com.dia.mous.lecteurdenews.MonAdapter;
import com.dia.mous.lecteurdenews.R;

/**
 * Created by Mous on 02/05/2017.
 */

public class WebviewActivityFragment extends Fragment {

    WebView webView;

    public static WebviewActivityFragment create(String link){
        Bundle args = new Bundle();
        args.putString("link", link);
        WebviewActivityFragment webviewActivityFragment = new WebviewActivityFragment();
        webviewActivityFragment.setArguments(args);
        return webviewActivityFragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Ajout du titre à l'actionBar
       // getActivity().setTitle(MainActivity.titreArticle);

        WebView webView = new WebView(getActivity());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return false;
            }
        });
        webView.loadUrl(getArguments().getString("link"));
        return webView;
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_fragment, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        webView = (WebView) view.findViewById(R.id.ma_webview);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // On récupère l'intent qui a lancé cette activité
        Intent i = getActivity().getIntent();

        // Puis on récupère le htmlContent donné dans l'autre classe(MonAdapter.java).
        String url = i.getStringExtra(MonAdapter.monUrl);
        // Puis on récupère le titre de l'article donné dans l'autre classe(MonAdapter.java).
        String titre = i.getStringExtra(MonAdapter.monTitre);
        //Ajout du titre à l'actionBar
        getActivity().setTitle(titre);
        //insertion de contenu dans la webview
       // WebView wv = (WebView)findViewById(R.id.ma_webview);
        //permettre à la page web charger de s'ouvrir au sein de la webview et non dans le navigateur du téléphone
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return false;
            }
        });
        webView.loadUrl(url);
    }
*/

}
