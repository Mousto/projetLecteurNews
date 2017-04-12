package com.dia.mous.lecteurdenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class TitreNewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recycler);

        final RecyclerView rv = (RecyclerView)findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MonAdapter adapter = new MonAdapter();
        rv.setAdapter(adapter);

        XMLAsyncTask task1 = new XMLAsyncTask(adapter);
        task1.execute("http://www.lemonde.fr/rss/une.xml","http://www.lemonde.fr/afrique/rss_full.xml","http://www.lemonde.fr/ameriques/rss_full.xml");
    }

}
