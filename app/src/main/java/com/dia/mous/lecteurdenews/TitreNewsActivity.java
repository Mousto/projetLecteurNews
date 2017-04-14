package com.dia.mous.lecteurdenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

public class TitreNewsActivity extends AppCompatActivity {

    private XMLAsyncTask laTache = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recycler);

        final RecyclerView rv = (RecyclerView)findViewById(R.id.list);
        rv.addItemDecoration(new SimpleDividerItemDecoration(this));

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);



        rv.setLayoutManager(new LinearLayoutManager(this));
        MonAdapter adapter = new MonAdapter();
        rv.setAdapter(adapter);

        laTache = new XMLAsyncTask(adapter);
        laTache.execute("http://www.lemonde.fr/rss/une.xml","http://www.lemonde.fr/afrique/rss_full.xml","http://www.lemonde.fr/ameriques/rss_full.xml");

        final ProgressBar maProgressBar = (ProgressBar) findViewById(R.id.progress);
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

}
