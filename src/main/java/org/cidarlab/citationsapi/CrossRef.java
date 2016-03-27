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
import org.codehaus.jackson.map.ObjectMapper;

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
    
    private static String getDoiURL(String Doi) throws IOException {

        String url = DoiURL + Doi;
        return url;
    }
    
    public static JSONObject changeFormat(JSONObject json) throws IOException {
        
        return (JSONObject)json.getJSONObject("message").getJSONArray("items").get(0);
        
    }
    
}
