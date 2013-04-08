package edu.csudh.cs.se.p2.applet;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import edu.csudh.cs.se.p2.repository.KwicRepository;
import edu.csudh.cs.se.p2.service.IndexSearcher;

public class URLAdd extends JApplet implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JLabel url,descl;
    JTextArea urt,desct;
    JTextArea shiftedText;
    JButton b1,b2, shift;
    
    private Pattern validUrlPattern = Pattern.compile("^((?:https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|$])$", Pattern.CASE_INSENSITIVE);
    
    private static final String NEW_LINE = System.getProperty("line.separator");
    
    private KwicRepository kwicRepository;
    private IndexSearcher searcher;

    public void init() {
        initDependencies();
        initContainer();
    }

    public void actionPerformed(ActionEvent ee) {
        String urlString = urt.getText();
        String descriptionString = desct.getText();

        if (ee.getSource() == b1) {

            if(Strings.isNullOrEmpty(urlString)|| 
                    !validUrlPattern.matcher(urlString).matches() || 
                    Strings.isNullOrEmpty(descriptionString)){
                desct.setText("Please provide valid text");                
            }else{
                kwicRepository.createUrlEntry(urlString, descriptionString);
                shiftText(urlString, descriptionString);
            }
        }else if (ee.getSource() == b2) {
            urt.setText("");
            desct.setText("");            
        }else if (ee.getSource() == shift){
            shiftText(urlString, descriptionString);
        }
    }
  
    private void shiftText(String url, String description){
        Map<String, String> input = Maps.newHashMap();
        input.put(description, url);
        Map<String, String> output = searcher.rotate(input);
        shiftedText.setText(Joiner.on(NEW_LINE).withKeyValueSeparator(",").join(output));
    }
    
    private void initDependencies(){
        kwicRepository = MicrominerContainer.getKwicRepository();
        searcher = MicrominerContainer.getSearcher();
    }
    
    private void initContainer(){
        Container content = getContentPane();
        content.setBackground(new Color(201,189,224));
        content.setLayout(null);
        url = new JLabel("Url");
        url.setFont(new Font("Serif", Font.BOLD, 14));
        url.setForeground(Color.BLUE);
        url.setLocation(200,100);
        url.setSize(200,50);
        content.add(url);
        urt= new JTextArea();
        urt.setFont(new Font("Cambria", Font.PLAIN, 14));
        urt.setLocation(300,100);
        urt.setSize(400,20);
        content.add(urt);
        descl = new JLabel("Description");
        descl.setFont(new Font("Serif", Font.BOLD, 14));
        descl.setForeground(Color.BLUE);
        descl.setLocation(200,200);
        descl.setSize(200,50);
        content.add(descl);
        desct= new JTextArea();
        desct.setFont(new Font("Cambria", Font.PLAIN, 14));
        desct.setLocation(300,200);
        desct.setSize(400,20);
        content.add(desct);
        b1 = new JButton("Add");
        b1.setLocation(210,250);
        b1.setSize(70,20);
        content.add(b1);
        b1.addActionListener(this);
        b2= new JButton("Clear");
        b2.setLocation(325,250);
        b2.setSize(70,20);
        content.add(b2);
        b2.addActionListener(this);
        shift = new JButton("Shift");
        shift.setLocation(440,250);
        shift.setSize(70,20);
        content.add(shift);
        shift.addActionListener(this);
        
        JLabel shiftedTextDescription = new JLabel("Shifted Text");
        descl.setFont(new Font("Serif", Font.BOLD, 14));
        descl.setForeground(Color.BLUE);
        descl.setLocation(200,400);
        descl.setSize(200,50);
        content.add(shiftedTextDescription);
        
        shiftedText= new JTextArea();
        shiftedText.setFont(new Font("Cambria", Font.PLAIN, 14));
        shiftedText.setLocation(300,250);
        shiftedText.setSize(400,20);
        content.add(shiftedText);

        
    }
    
}