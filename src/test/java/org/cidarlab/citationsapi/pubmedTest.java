/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author innaturshudzhyan
 */
public class pubmedTest {
    
    public pubmedTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetCitationSummary(){
        List<String> author = new ArrayList<String>();
        author.add("Densmore,Douglas");
        author.add("Bhatia,Swapnil");
        try {
            System.out.println(author);
            System.out.println("List of IDS :: ");
            List<String> ids = pubmed.getPubmedIds("pubmed", author, ReturnType.JSON);
            
            JSONObject json = new JSONObject();
            json = pubmed.getCitationSummary("pubmed", ids.get(0), ReturnType.JSON);
            
            System.out.println("BibTex  ::\n" + pubmed.convertJSONtoBibtex(json));
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(pubmedTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //@Test
    public void testGetPubMedIds(){
        List<String> author = new ArrayList<String>();
        author.add("Densmore,Douglas");
        author.add("Bhatia,Swapnil");
        author.add("Vaidyanathan,Prashant");
        try {
            System.out.println(author);
            System.out.println("List of IDS :: ");
            List<String> ids = pubmed.getPubmedIds("pubmed", author, ReturnType.JSON);
            System.out.println(ids);
            System.out.println("\n\nNumber of Ids :: " + ids.size());
        } catch (IOException ex) {
            Logger.getLogger(pubmedTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
