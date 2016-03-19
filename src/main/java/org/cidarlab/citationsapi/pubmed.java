/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author innatur, prash
 */
public class pubmed {
    
    public List<String> getPubmedIds(String dbName, List<String> authorNames, ReturnType retMode){
        List<String> ids = new ArrayList<String>();
        String url = "";
        if(retMode.equals(ReturnType.JSON)){
            ids = idsFromJSON(null);
        }
        else if(retMode.equals(ReturnType.XML)){
            
        }
        
        return ids;
    }
    
    /*private static List<String> xmlToList(){
        
    }*/
    
    private static List<String> idsFromJSON(JSONObject json){
        List<String> ids = new ArrayList<String>();
        
        
        return ids;
    } 
    
    public static JSONObject getSummary(String dbName, String id, String returnType){
        JSONObject citationSummary = new JSONObject();
        
        
        return citationSummary;
    }
}
