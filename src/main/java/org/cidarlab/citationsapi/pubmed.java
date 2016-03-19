/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author innatur, prash
 */
public class pubmed {

    public static final String QueryURL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=";
    public static final String SummaryURL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String getPubmedURL(String dbName, String term, ReturnType retMode) throws IOException {

        String url = QueryURL + dbName + "&term=" + term + "&retmode=" + retMode;
        return url;
    }

    private static String getPubmedSummaryURL(String dbName, String id, ReturnType retMode) throws IOException {

        String url = SummaryURL + dbName + "&id=" + id + "&retmode=" + retMode;
        return url;
    }

    public static List<String> getPubmedIds(String dbName, List<String> authorNames, ReturnType retMode) throws IOException {
        List<String> ids = new ArrayList<String>();

        if (retMode.equals(ReturnType.JSON)) {

            String term = "";
            for (int i = 0; i < authorNames.size(); i++) {
                if (i == authorNames.size() - 1) {
                    term += authorNames.get(i) + "[author]";
                } else {
                    term += authorNames.get(i) + "[author],";
                }
            }
            String url = getPubmedURL(dbName, term, retMode);
            JSONObject json = readJsonFromUrl(url);
            ids = idsFromJSON(json);
        } else if (retMode.equals(ReturnType.XML)) {

        }

        return ids;
    }

    /*private static List<String> xmlToList(){
        
     }*/
    private static List<String> idsFromJSON(JSONObject json) {
        List<String> ids = new ArrayList<String>();
        for(Object obj : (JSONArray) json.getJSONObject("esearchresult").get("idlist")){
            ids.add((String)obj);
        }
        return ids;
    }

    public static JSONObject getCitationSummary(String dbName, String id, ReturnType returnType) throws IOException {
        JSONObject citationSummary = new JSONObject();
        String url = getPubmedSummaryURL(dbName, id, returnType);
        citationSummary = readJsonFromUrl(url);
        return citationSummary;
    }

}


