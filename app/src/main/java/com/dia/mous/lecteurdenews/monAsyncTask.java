package com.dia.mous.lecteurdenews;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mous on 06/03/2017.
 */

public class monAsyncTask extends AsyncTask<String, Void, ArrayList<Element>> {

    interface DocumentConsumer{
        void setXMLDocument(ArrayList<Element> elementArrayList);
    }

    private DocumentConsumer _consumer;
    private ArrayList<NodeList> nodeListArrayList;//Permet de récupérer les "ensembles" d'éléments provenant des trois flux rss.
    private ArrayList<Element> elementArrayList;//permet de récupérer une liste d'éléments provenant des ensembles précédents.
    String date;

    public monAsyncTask(DocumentConsumer consumer ){
        _consumer = consumer;
    }

    @Override
    protected ArrayList<Element> doInBackground(String...params)
    {
        ArrayList<Document> arrayList = new ArrayList<>();
        elementArrayList = new ArrayList<>();
        nodeListArrayList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//Format de la date
        Document document = null;
        try {
            Thread.sleep(2000);
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
                    date = element.getFirstChild().getTextContent();
                    elementArrayList.add(element);
                }
            }
        }
        catch (InterruptedException ex)//par intérruption entre autre de XMLAsynktask (appel de _task.cancel(true) dans onDestroy de Activité concernée)
        {
            Log.i("XMLAsinctask","Téléchargement intérrompu");
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
        Log.i("XMLAsinctask date : ", date);
        _consumer.setXMLDocument(resultat);
    }
}
