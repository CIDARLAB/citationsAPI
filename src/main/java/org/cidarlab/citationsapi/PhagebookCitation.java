/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import lombok.Getter;

/**
 *
 * @author innaturshudzhyan
 */
public class PhagebookCitation {
    
    @Getter
    public String title;
    
    @Getter
    public String authors;
    
    @Getter
    public int year;
    
    @Getter
    public String otherInformation;
    
    @Getter
    public String bibtex;
    
    public PhagebookCitation(String _title, String _authors, int _year, String _otherInfo, String _bibtex){
        this.title = _title;
        this.authors = _authors;
        this.year = _year;
        this.otherInformation = _otherInfo;
        this.bibtex = _bibtex;
    }
    
}
