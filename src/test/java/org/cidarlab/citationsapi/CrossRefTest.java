/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import static org.cidarlab.citationsapi.CrossRef.readJsonFromUrl;
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
public class CrossRefTest {
    
    public CrossRefTest() {
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

    /**
     * Test of readJsonFromUrl method, of class CrossRef.
     */
    @Test
    public void testReadJsonFromUrl() throws Exception {
        String url = "http://api.crossref.org/works?filter=doi:10.1109/JPROC.2015.2443832";
        JSONObject json = new JSONObject();
        json = CrossRef.readJsonFromUrl(url);
        System.out.println(json);
    }

    /**
     * Test of changeFormat method, of class CrossRef.
     */
    @Test
    public void testChangeFormat() throws Exception {
        
        String url = CrossRef.getDoiURL("10.1109/JPROC.2015.2443832");
        JSONObject json = new JSONObject();
        json = CrossRef.readJsonFromUrl(url);
        
        System.out.println("Pretty print JSON :: ");
        System.out.println(CrossRef.changeFormat(json).toString(4));
        //System.out.println(json.toString(4)); --- for ISSN
        
        
    }
    
}
