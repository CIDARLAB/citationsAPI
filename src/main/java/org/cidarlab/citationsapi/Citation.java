/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import static org.cidarlab.citationsapi.CrossRef.changeFormat;
import static org.cidarlab.citationsapi.CrossRef.getDoiURL;
import static org.cidarlab.citationsapi.CrossRef.readJsonFromUrl;
import org.json.JSONObject;

/**
 *
 * @author innaturshudzhyan
 */

// Source:: https://verbosus.com/bibtex-style-examples.html
public class Citation {
    
    @Getter
    @Setter
    private List<Author> authors;
    
    @Getter
    @Setter
    private String title;
    
    @Getter
    @Setter
    private String journal;
    
    @Getter
    @Setter
    private String publisher;
    
    @Getter
    @Setter
    private String howpublished;
      
    @Getter
    @Setter
    private int year;
    
    @Getter
    @Setter
    private int month;
    
    @Getter
    @Setter
    private String note;
    
    @Getter
    @Setter
    private int number;
    
    @Getter
    @Setter
    private int volume;

    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String isbn;
    
    @Getter
    @Setter
    private String pages;
    
    @Getter
    @Setter
    private String address;
    
    @Getter
    @Setter
    private int edition;
    
    @Getter
    @Setter
    private String series;
    
    /*public static String getGoogleScholar(String author, String phrase) throws IOException {
        Utilities.setPythonLocation("/Library/Frameworks/Python.framework/Versions/2.7/bin/python");
        String s = GoogleScholar.getBibtexFromGoogleScholar(author, phrase);
        return s;
    }
    //title,authors,description,year,other - 
    public static JSONObject getCrossRef(String Doi) throws IOException {
        JSONObject cross = readJsonFromUrl(getDoiURL(Doi));
        
        JSONObject result = new JSONObject();
        JSONObject temp = new JSONObject();
        temp = changeFormat(cross);
        
        result.put("title", temp.getJSONArray("title").get(0).toString());
        
        //result.put("authors"), null);
        //result.put("description"), null);
        //result.put("year"), null);
        //result.put("other"), null);
        
    }
    
    public static String getPubmed(String id) throws IOException {
        String s = new String();
        return s;
    }*/
    
}
