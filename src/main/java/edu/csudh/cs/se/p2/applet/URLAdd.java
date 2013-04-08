package edu.csudh.cs.se.p2.applet;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JTextArea;

import edu.csudh.cs.se.p2.repository.UrlRepository;
import edu.csudh.cs.se.p2.service.IndexSearcher;

public class URLAdd extends JApplet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JButton add, clr;
    JTextArea descUrlContent;
    
    private UrlRepository repository;
    private IndexSearcher searcher;

    public void init() {
        initContainer();
    }

    public void actionPerformed(ActionEvent ee) {
        if (ee.getSource() == add) {
            repository.setContent(descUrlContent.getText());
            searcher.reload();
        }
        if (ee.getSource() == clr) {

        }
    }
    
    private void initContainer(){
        Container content = getContentPane();
        content.setBackground(new Color(201,189,224));
        content.setLayout(null);
        
        descUrlContent = new JTextArea();
        descUrlContent.setSize(new Dimension(100, 100));
        descUrlContent.setLocation(new Point(200, 50));
        
        content.add(descUrlContent);
        
        add = new JButton("Add");
        add.setSize(new Dimension(80, 20));
        add.setLocation(new Point(300, 100));
        
        content.add(add);
        
        
        clr = new JButton("Clear");
        clr.setSize(new Dimension(80, 20));
        clr.setLocation(new Point(350, 100));
                
        content.add(clr);
    }
}