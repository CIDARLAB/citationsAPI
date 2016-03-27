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

/**
 *
 * @author innatur, prash
 */
public class googleScholar {
    
    public static void main(String[] args) throws IOException {
        Runtime rt = Runtime.getRuntime();
        
        String commandLine = "/Library/Frameworks/Python.framework/Versions/3.5/bin/python3 /Users/innaturshudzhyan/Documents/citationsAPI/scholar.py  --author \"douglas densmore\" --phrase \"a framework for genetic logic synthesis\" --citation bt";
        Process pr = rt.exec(commandLine);
        System.out.println(commandLine);
        InputStream inStream = pr.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        while( (line=br.readLine()) != null ){
            System.out.println(line);
        }
    
    }
    
    
}
