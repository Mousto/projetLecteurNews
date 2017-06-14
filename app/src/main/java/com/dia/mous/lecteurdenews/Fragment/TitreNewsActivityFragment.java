package com.dia.mous.lecteurdenews.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dia.mous.lecteurdenews.MonAdapter;
import com.dia.mous.lecteurdenews.R;
import com.dia.mous.lecteurdenews.SimpleDividerItemDecoration;
import com.dia.mous.lecteurdenews.monAsyncTask;

/**
 * Created by Mous on 19/04/2017.
 */

public class TitreNewsActivityFragment extends Fragment {

    public static monAsyncTask laTache = null;
    public static MonAdapter adapter;
    private ProgressBar maProgressBar;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.titre__fragment, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        rv = (RecyclerView) view.findViewById(R.id.listFragment);
        maProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MonAdapter((MonAdapter.URLLoader) getActivity());
        rv.setAdapter(adapter);

        laTache = new monAsyncTask(adapter);
        laTache.execute("http://www.lemonde.fr/rss/une.xml", "http://www.lemonde.fr/afrique/rss_full.xml", "http://www.lemonde.fr/ameriques/rss_full.xml");

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                maProgressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (laTache != null) {
            //laTache = null;
            laTache.cancel(true);//Arrêt du téléchargement quand on quite l'activité(le thread de l'asynktask sera interrompu)
            //on peut donc le catcher dans InterruptedException de l'asynktask
            //Toast.makeText(getActivity(),"onDestroy de fragment",Toast.LENGTH_LONG).show();
        }
    }

}