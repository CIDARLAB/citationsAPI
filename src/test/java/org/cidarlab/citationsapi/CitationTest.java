/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author innaturshudzhyan
 */
public class CitationTest {
    
    @Test
    public void testGetPhagebookCitation() throws Exception {
        //PubMed examples: 25012162, 24911500
        //CrossRef examples: 10.1038/nbt.2891, 10.1038/nmeth.2939
//        System.out.println("CrossRef: ");
//        System.out.println(CrossRef.getPhagebookCitation("10.1038/nmeth.2939").getAuthors());
//        System.out.println("Pubmed: ");
//        System.out.println(Pubmed.getPhagebookCitation("24911500").getAuthors());
        
        
        String title = "Hello";
        
        String bibtex = "";
        
        
        String temp = "";
        
        if (title.indexOf(' ') == 1) {
            temp = title.substring(2, title.length());
            bibtex += temp.substring(0, temp.indexOf(' ')).toLowerCase() + "}, title={";
        } else if(title.indexOf(' ') == 2) {
            temp = title.substring(3, title.length());
            bibtex += temp.substring(0, temp.indexOf(' ')).toLowerCase() + "}, title={";
        }
        else if (title.indexOf(' ') == 3) {
            temp = title.substring(4, title.length());
            bibtex += temp.substring(0, temp.indexOf(' ')).toLowerCase() + "}, title={";
        } else {
            if (title.replace(" ", "") == title) //if there are no spaces in title
                bibtex += title.substring(0, title.length()).toLowerCase() + "}, title={";
            else
                bibtex += title.substring(0, title.indexOf(' ')).toLowerCase() + "}, title={";
        }
        
        System.out.println(bibtex);
        
    }
    
}
