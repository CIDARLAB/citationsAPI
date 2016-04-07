/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author innatur, prash
 */
public class googleScholar {
    
    public static String getBibtexByAuthor(String author, String phrase) throws IOException {
    
        String s="";
        try {
            Runtime rt = Runtime.getRuntime();
            
            String scriptPath = "/Users/innaturshudzhyan/Documents/citationsAPI/resources/script.sh";
            File file = new File(scriptPath);
            
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write("#!/bin/bash");
            br.write("\n");
            br.write("/Library/Frameworks/Python.framework/Versions/2.7/bin/python /Users/innaturshudzhyan/Documents/citationsAPI/scholar.py  --author "
                    + author + " --phrase " + phrase + " --citation bt");
            br.flush();
            br.close();
            
            String commandLine = scriptPath;
            Process pr = rt.exec(commandLine);
            pr.waitFor();
            System.out.println(commandLine);
            InputStream inStream = pr.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(inStream));
            String line = "";
            while( (line=br1.readLine()) != null ){
                String yearCheckVal = "year={";
                if(line.contains(yearCheckVal)){
                    
                    String yearVal = line.substring(line.indexOf(yearCheckVal) + yearCheckVal.length());
                    yearVal = yearVal.substring(0, yearVal.indexOf("}"));
                    System.out.println("year = " + yearVal);
                }
                
                s += line;
            }
            
           
        } catch (InterruptedException ex) {
            Logger.getLogger(googleScholar.class.getName()).log(Level.SEVERE, null, ex);
        }
         return s;
    }
    
    
    public static void main(String[] args) throws IOException {
       
        System.out.println(getBibtexByAuthor("densmore prashant","biology"));
        
    }
    
    
}
