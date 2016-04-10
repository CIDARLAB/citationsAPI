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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author innatur, prash
 */
public class GoogleScholar {
    
    public static String createScriptFile(){
        String _filepath = "";
        _filepath += Utilities.getResourcesFilepath();
        _filepath += "script";
        String filepath = "";
        do{
            filepath = _filepath + Utilities.getCounter().getAndIncrement() + ".sh";
        }while(Utilities.validFilepath(filepath));
        return filepath;
    }
    
    private static String getScriptOriginal(){
        String _filepath = "";
        _filepath += Utilities.getResourcesFilepath();
        _filepath += "script.sh";
        return _filepath;
    }
    public static String getBibtexFromGoogleScholar(String author, String phrase) throws IOException {
    
        String s="";
        try {
            Runtime rt = Runtime.getRuntime();
            
            String scriptPath = createScriptFile();
            
            Path source = Paths.get(getScriptOriginal());
            Path destination = Paths.get(scriptPath);
            try {
                Files.copy(source, destination, COPY_ATTRIBUTES);
            } catch (IOException ex) {
                Logger.getLogger(GoogleScholar.class.getName()).log(Level.SEVERE, null, ex);
            }
            File file = new File(scriptPath);
            String scholarPyCommand = Utilities.getPythonLocation() + " " + Utilities.getResourcesFilepath() + "scholar.py -c 1 --author "
                    + "\"" + author + "\"" + " --phrase " + "\"" + phrase  + "\"" + " --citation bt";
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write("#!/bin/bash");
            br.write("\n");
            br.write(scholarPyCommand);
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
            
            file.deleteOnExit();
        } catch (InterruptedException ex) {
            Logger.getLogger(GoogleScholar.class.getName()).log(Level.SEVERE, null, ex);
        }
         return s;
    }
        
    public static void main(String[] args) throws IOException {
       
        Utilities.setPythonLocation("/Library/Frameworks/Python.framework/Versions/2.7/bin/python");
        System.out.println(getBibtexFromGoogleScholar("Prashant Vaidyanathan","A framework for genetic logic synthesis"));
        
    }
    
    
    
    
}
