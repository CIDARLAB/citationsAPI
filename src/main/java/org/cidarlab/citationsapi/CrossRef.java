package org.cidarlab.citationsapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

//USE THIS LINK TO SEE THE JSON :: http://jsonviewer.stack.hu/


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author innaturshudzhyan
 */
public class CrossRef {
    
    
    public static final String DoiURL = "http://api.crossref.org/works?filter=doi:";
    public static final String JournalsIssnURL = "http://api.crossref.org/journals/";
    
    
    
    
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
    
    public static String getDoiURL(String Doi) throws IOException {

        String url = DoiURL + Doi;
        return url;
    }
    
    public static String getJournalsIssnURL(String Issn) throws IOException {

        String url = JournalsIssnURL + Issn;
        return url;
    }
    
    public static JSONObject changeFormat(JSONObject json) throws IOException {
        
        return (JSONObject)json.getJSONObject("message").getJSONArray("items").get(0);
        
    }
    
    public static String convertJSONtoBibtex(JSONObject json) throws IOException {
        
        JSONObject temp = new JSONObject();
        temp = changeFormat(json);
        
        String s = new String();
        
        s = "@article{" + temp.getJSONArray("author").getJSONObject(0).get("family").toString().toLowerCase()
                + temp.getJSONObject("created").getJSONArray("date-parts").getJSONArray(0).get(0).toString();
        
        String word = new String();
        word = temp.getJSONArray("title").get(0).toString().toLowerCase();
        
        if (word.indexOf(' ') == 1)
        {
            word = word.substring(2, word.length());
            s += word.substring(0, word.indexOf(' ')) + "}, title={";
        }
        else if (temp.getJSONArray("title").get(0).toString().indexOf(' ') == 3)
        {
            word = word.substring(4, word.length());
            s += word.substring(0, word.indexOf(' ')) + "}, title={";
        }
        else
        {
            s += word.substring(0, word.indexOf(' ')) + "}, title={";
        }
        
                
        s += temp.getJSONArray("title").get(0).toString() + "}, author={";
        
        int numberOfAuthors = temp.getJSONArray("author").length();
        for (int i = 0; i<numberOfAuthors; i++)
        {
            if (i != numberOfAuthors-1)
                s = s + temp.getJSONArray("author").getJSONObject(i).get("family").toString() + ", " 
                        + temp.getJSONArray("author").getJSONObject(i).get("given").toString() + " and ";
            else
                s += temp.getJSONArray("author").getJSONObject(i).get("family").toString() + ", " 
                        + temp.getJSONArray("author").getJSONObject(i).get("given").toString() + "}, ";
        }
        
        s += "journal={" + temp.getJSONArray("container-title").get(0).toString() + "}, volume={" 
                + temp.get("volume").toString() + "}, number={"
                + temp.get("issue").toString() + "}, pages={"
                + temp.get("page").toString().replace("-", "--") + "}, year={"
                + temp.getJSONObject("published-print").getJSONArray("date-parts").getJSONArray(0).get(0).toString() + "}, publisher={"
                + temp.get("publisher").toString() + "} }"; 
        
        
        return s;
        
    }
    
    public static void main(String[] args) throws IOException {
        
        JSONObject json = new JSONObject();
        json = readJsonFromUrl(getDoiURL("10.1109/aswec.2009.22"));
        
        System.out.println(convertJSONtoBibtex(json));
        
        
    }
    
}

//function to convert to bibtex
