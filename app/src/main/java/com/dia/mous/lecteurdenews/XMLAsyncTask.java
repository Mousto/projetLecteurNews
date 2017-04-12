package com.dia.mous.lecteurdenews;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mous on 06/03/2017.
 */

public class XMLAsyncTask extends AsyncTask<String, Void, ArrayList<Element>> {

    interface DocumentConsumer{
        void setXMLDocument(ArrayList<Element> elementArrayList);
    }

    private DocumentConsumer _consumer;
    private ArrayList<NodeList> nodeListArrayList;//Permet de récupérer les "ensembles" d'éléments provenant des trois flux rss.
    private ArrayList<Element> elementArrayList;//permet de récupérer une liste d'éléments provenant des ensembles précédents.

    public XMLAsyncTask(DocumentConsumer consumer ){
        _consumer = consumer;
    }

    @Override
    protected ArrayList<Element> doInBackground(String...params)
    {
        ArrayList<Document> arrayList = new ArrayList<>();
        elementArrayList = new ArrayList<>();
        nodeListArrayList = new ArrayList<>();
        Document document = null;
        try {
            Thread.sleep(0);
            for(String u : params) {
                URL url = new URL(u);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                try {
                    document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
                    NodeList lesEntrees = document.getElementsByTagName("item");//récupération de l'ensemble d'élément du document
                    nodeListArrayList.add(lesEntrees);//ajout de cet ensemble
                    arrayList.add(document);
                } finally {
                    stream.close();
                }
            }
            //récupération des éléments dans les différents ensembles obtenus
            for(NodeList nodeList : nodeListArrayList){
                for(int i=0; i<nodeList.getLength(); i++){
                    Element element = (Element)nodeList.item(i);
                    elementArrayList.add(element);
                }
            }
        }
        catch (InterruptedException ex)//par intérruption entre autre de XMLAsinctask (appel de _task.cancel(true) dans onDestroy de MainAcrivity)
        {
           // Log.i("XMLAsinctask","Téléchargement intérrompu");
            return null;
        }
        catch (Exception ex) {
           // Log.i("XMLasyncTask", "Exception pendant téléchargement",ex);
            throw new RuntimeException(ex);
        }
        return elementArrayList;//renvoi du resultat
    }

    @Override
    protected void onPostExecute(ArrayList<Element> resultat)
    {
        _consumer.setXMLDocument(resultat);
    }
}
