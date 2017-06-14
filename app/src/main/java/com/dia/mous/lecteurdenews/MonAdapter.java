package com.dia.mous.lecteurdenews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Element;

import java.util.ArrayList;

public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MonViewHolder> implements monAsyncTask.DocumentConsumer {

    public interface URLLoader{
        void load(String titre, String link);
    }

    private final URLLoader urlLoader;
    public ArrayList<Element> _element = null;
    public static String monUrl = "monHtmlContent";
    public static String monTitre = "monTitre";

    public MonAdapter(URLLoader PurlLoader){
        urlLoader = PurlLoader;
    }

    @Override
    public int getItemCount() {
        if(_element != null){
            return _element.size();
        }
        else {
            return 0;
        }
    }

    @Override
    public MonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonViewHolder holder, int position) {

            Element item = (Element) _element.get(position);
            holder.setElement(item);

    }

    @Override
    public void setXMLDocument(ArrayList<Element> document)
    {
        _element = document;
        notifyDataSetChanged();
    }

    public void clear() {
        int size = _element.size();
        _element.clear();
        notifyItemRangeRemoved(0, size);
    }



    public class MonViewHolder extends RecyclerView.ViewHolder {

        private final TextView titre;
        private Element _currentElement;

        public MonViewHolder(final View itemView) {
            super(itemView);

            titre = ((TextView)itemView.findViewById(R.id.titre));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                String link = _currentElement.getElementsByTagName("link").item(0).getTextContent();
                String titreArticle = _currentElement.getElementsByTagName("title").item(0).getTextContent();
                urlLoader.load(titreArticle, link);
                }
            });
        }

        public void setElement(Element element)
        {
            _currentElement = element;
            titre.setText(element.getElementsByTagName("title").item(0).getTextContent());
        }
    }


}
