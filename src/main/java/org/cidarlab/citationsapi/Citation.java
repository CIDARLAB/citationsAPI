/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.citationsapi;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

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
    
}
