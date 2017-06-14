package com.dia.mous.lecteurdenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.dia.mous.lecteurdenews.Fragment.TitreNewsActivityFragment;
import com.dia.mous.lecteurdenews.Fragment.WebviewActivityFragment;

import static android.content.ContentValues.TAG;
import static com.dia.mous.lecteurdenews.Fragment.TitreNewsActivityFragment.adapter;
import static com.dia.mous.lecteurdenews.Fragment.TitreNewsActivityFragment.laTache;

public class MainActivity extends AppCompatActivity implements MonAdapter.URLLoader{

    public static String titreArticle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pour charger un fragment dans l'interface
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.listFragment, new TitreNewsActivityFragment())
                .commit();

    }

    @Override
    public void load(String titre, String link){
        if(findViewById(R.id.articleFragment) !=null){
            WebviewActivityFragment fragment = WebviewActivityFragment.create(link);
            //titreArticle = titre;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.articleFragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("titre", titre);
            intent.putExtra("link", link);
            startActivity(intent);
        }
    }

    //Utile quant on presse le bouton retour
    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "Dans Activity.onDestroy()");
        /*if(TitreNewsActivityFragment.laTache == null){
            laTache.cancel(true);//Arrêt du téléchargement quand on quite l'activité(le thread de l'asynktask sera interrompu)
                                    //on peut donc le catcher dans InterruptedException de l'asynktask
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Retéléchargement...", Toast.LENGTH_SHORT)
                        .show();
                adapter.clear();//effacement de la liste
                laTache = new monAsyncTask(adapter);
                laTache.execute("http://www.lemonde.fr/rss/une.xml","http://www.lemonde.fr/afrique/rss_full.xml","http://www.lemonde.fr/ameriques/rss_full.xml");

                break;
            default:
                break;
        }

        return true;
    }
}
