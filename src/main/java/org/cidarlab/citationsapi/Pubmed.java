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
import static org.cidarlab.citationsapi.CrossRef.getPhagebookCitation;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author innatur, prash
 */
public class Pubmed {

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
        for (Object obj : (JSONArray) json.getJSONObject("esearchresult").get("idlist")) {
            ids.add((String) obj);
        }
        return ids;
    }

    public static JSONObject getCitationSummary(String dbName, String id, ReturnType returnType) throws IOException {
        JSONObject citationSummary = new JSONObject();
        String url = getPubmedSummaryURL(dbName, id, returnType);
        citationSummary = readJsonFromUrl(url);

        String id1 = citationSummary.getJSONObject("result").getJSONArray("uids").get(0).toString();

        citationSummary = citationSummary.getJSONObject("result").getJSONObject(id1);

        return citationSummary;
    }
    
    
    public static Citation convertJSONtoCitation(JSONObject json){
        Citation citation = new Citation();
        
        
        return citation;
    }
    
    public static String convertJSONtoBibtex(JSONObject json) throws IOException {

        String s = "";
        String name = "";
        String date = "";
        String title = "";
        String title1 = "";
        String pages = "";
        String page1 = "";
        String page2 = "";
        int numberOfAuthors;
        
        if (json.has("authors") && json.has("pubdate"))
        {
            name = json.getJSONArray("authors").getJSONObject(0).get("name").toString();
            date = json.get("pubdate").toString();
            s = "@article{" + name.substring(0, name.indexOf(" ")).toLowerCase() + date.substring(0, date.indexOf(" "));
        }
            
        if (json.has("title")) {
            
            title = json.get("title").toString();
            
            if (title.indexOf(' ') == 1) {
                title1 = title.substring(2, title.length()).toLowerCase();
                s += title1.substring(0, title1.indexOf(' ')) + "}, title={";
            }
            if (title.indexOf(' ') == 2) {
                title1 = title.substring(3, title.length()).toLowerCase();
                s += title1.substring(0, title1.indexOf(' ')) + "}, title={";
            } else if (title.indexOf(' ') == 3) {
                title1 = title.substring(4, title.length()).toLowerCase();
                s += title1.substring(0, title1.indexOf(' ')) + "}, title={";
            } else {
                if (title.replace(" ", "") == title)
                    s += title.substring(0, title.length()) + "}, title={";
                else
                    s += title.substring(0, title.indexOf(' ')).toLowerCase() + "}, title={";
            }
            s += title.replace(".", "");
        }

        s += "}, author={";
        
        if (json.has("authors")) {
            numberOfAuthors = json.getJSONArray("authors").length();

            for (int i = 0; i < numberOfAuthors; i++) {
                if (i != numberOfAuthors - 1) {
                    s = s + json.getJSONArray("authors").getJSONObject(i).get("name").toString().replace(" ", ",") + " and ";
                } else {
                    s = s + json.getJSONArray("authors").getJSONObject(i).get("name").toString().replace(" ", ",") + "}, ";
                }

            }
        }
        
        if (json.has("fulljournalname") && json.has("volume") && json.has("issue"))
        {
            s += "journal={" + json.getString("fulljournalname") + "}, volume={"
            + json.getString("volume") + "}, number={"
            + json.getString("issue") + "}, pages={";
        }
        

        if (json.has("pages") && json.get("pages")!="") {
            pages = json.getString("pages");
            page1 = pages.substring(0, pages.indexOf('-'));
            page2 = pages.substring(pages.indexOf('-') + 1, pages.length());

            if (page1.length() == page2.length()) {
                s += page1 + "--" + page2;
            } else if (page1.length() - page2.length() == 1) {
                s += page1 + "--" + page1.charAt(0) + page2;
            } else if (page1.length() - page2.length() == 2) {
                s += page1 + "--" + page1.substring(0, 1) + page2;
            } else if (page1.length() - page2.length() == 3) {
                s += page1 + "--" + page1.substring(0, 2) + page2;
            }
        }
        
        if (json.has("pubdate"))
        {
            date = json.get("pubdate").toString();
            s += "}, year{" + date.substring(0, date.indexOf(" ")) + "} }";
        }
        
        return s;

    }
    
    public static PhagebookCitation getPhagebookCitation(String id) throws IOException{
        String url = SummaryURL + "pubmed&id=" + id + "&retmode=" + ReturnType.JSON;
        
        JSONObject pub = readJsonFromUrl(url);
        String title = "";
        String authors = "";
        String year1 = "";
        int year2 = 0;
        String other = "";
        String bibtex = "";
        String pages = "";
        String page1 = "";
        String page2 = "";
        int numberOfAuthors;
        
        if(pub.has("result"))
        pub = pub.getJSONObject("result").getJSONObject(id);
        
        
        if(pub.has("title"))
        title = pub.get("title").toString();
        
        if (pub.has("authors"))
        {
            numberOfAuthors = pub.getJSONArray("authors").length();
            authors = "";
            for (int i = 0; i < numberOfAuthors; i++) {
                if (i != numberOfAuthors - 1) {
                    authors += pub.getJSONArray("authors").getJSONObject(i).get("name").toString() + ", ";
                } else {
                    authors += pub.getJSONArray("authors").getJSONObject(i).get("name").toString();
                }
            }
        }
        if (pub.has("pubdate"))
        {
            year1 = pub.get("pubdate").toString();
            year1 = year1.substring(0,year1.indexOf(" "));
            year2 = Integer.parseInt(year1);
        }
        if (pub.has("fulljournalname") && pub.has("volume") && pub.has("issue"))
        {
            other += pub.getString("fulljournalname") + ", "
            + pub.getString("volume") + ", "
            + pub.getString("issue") + ", ";
        }
        

        if (pub.has("pages") && pub.get("pages")!="") {
            pages = pub.getString("pages");
            page1 = pages.substring(0, pages.indexOf('-'));
            page2 = pages.substring(pages.indexOf('-') + 1, pages.length());

            if (page1.length() == page2.length()) {
                other += page1 + "--" + page2;
            } else if (page1.length() - page2.length() == 1) {
                other += page1 + "--" + page1.charAt(0) + page2;
            } else if (page1.length() - page2.length() == 2) {
                other += page1 + "--" + page1.substring(0, 1) + page2;
            } else if (page1.length() - page2.length() == 3) {
                other += page1 + "--" + page1.substring(0, 2) + page2;
            }
        }
        bibtex = convertJSONtoBibtex(pub);
        
        return new PhagebookCitation(title,authors,year2,other,bibtex);
        
        
    }
    
    public static void main(String[] args) throws IOException {

        String id = "15496466";
        
        String url = SummaryURL + "pubmed&id=" + id + "&retmode=" + ReturnType.JSON;
        //System.out.println(getPhagebookCitation(id).getTitle());
        
    }

    

}
