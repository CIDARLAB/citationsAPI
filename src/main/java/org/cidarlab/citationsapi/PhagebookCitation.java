/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author innaturshudzhyan
 */
public class PhagebookCitation {
    @Getter @Setter private String id;
    
    @Getter @Setter private String user;
    
    @Getter @Setter
    private String title;
    
    @Getter @Setter
    private String authors;
    
    @Getter @Setter
    private int year;
    
    @Getter @Setter
    private String otherInformation;
    
    @Getter @Setter
    private String bibtex;
    
    public PhagebookCitation(String _title, String _authors, int _year, String _otherInfo, String _bibtex){
        this.title = _title;
        this.authors = _authors;
        this.year = _year;
        this.otherInformation = _otherInfo;
        this.bibtex = _bibtex;
        this.id = "Not Set";
    }
    
    public PhagebookCitation(){
        this.title ="Not Set";
        this.authors = "Not Set";
        this.year = 1970;
        this.otherInformation = "Not Set";
        this.bibtex = "Not Set";
        this.id = "Not Set";
        this.user="Not Set";
    }
    
}
