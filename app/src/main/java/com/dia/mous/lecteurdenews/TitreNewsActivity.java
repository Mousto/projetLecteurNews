package com.dia.mous.lecteurdenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.dia.mous.lecteurdenews.Fragment.TitreNewsActivityFragment;

import static com.dia.mous.lecteurdenews.Fragment.TitreNewsActivityFragment.adapter;
import static com.dia.mous.lecteurdenews.Fragment.TitreNewsActivityFragment.laTache;

public class TitreNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recycler);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

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
