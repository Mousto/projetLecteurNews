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

public class TitreNewsActivity extends AppCompatActivity {

    private monAsyncTask laTache = null;
    private MonAdapter adapter;
    private ProgressBar maProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recycler);

        final RecyclerView rv = (RecyclerView)findViewById(R.id.list);
        rv.addItemDecoration(new SimpleDividerItemDecoration(this));
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MonAdapter();
        rv.setAdapter(adapter);

        laTache = new monAsyncTask(adapter);
        laTache.execute("http://www.lemonde.fr/rss/une.xml","http://www.lemonde.fr/afrique/rss_full.xml","http://www.lemonde.fr/ameriques/rss_full.xml");

        maProgressBar = (ProgressBar) findViewById(R.id.progress);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                maProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(laTache != null){
            laTache.cancel(true);//Arrêt du téléchargement quand on quite l'activité(le thread de l'asynktask sera interrompu)
                                    //on peut donc le catcher dans InterruptedException de l'asynktask
        }
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
