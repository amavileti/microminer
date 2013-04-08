package edu.csudh.cs.se.p2.applet;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.google.common.base.Joiner;

import edu.csudh.cs.se.p2.repository.KwicRepository;
import edu.csudh.cs.se.p2.repository.UrlRepository;
import edu.csudh.cs.se.p2.service.IndexSearcher;

public class Mapplet extends JApplet implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    JLabel inp, out;
    JTextArea in, op;
    JButton b1;
    CheckboxGroup radioGroup;
    Checkbox r1;
    Checkbox r2;
    
    private String content  = "description1 test google, http://www.google.com" + System.getProperty("line.separator") + "description2 california state university, http://www.csudh.edu";
    
    private UrlRepository repository;
    private IndexSearcher searcher;
    private KwicRepository kwicRepository;
    
    private String newLine = System.getProperty("line.separator");

    public void init() {
        initDependencies();
        
        Container content = getContentPane();
        content.setBackground(new Color(201, 189, 224));
        content.setLayout(null);        
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        inp = new JLabel("Please Enter Keywords");
        inp.setFont(new Font("Serif", Font.BOLD, 14));
        inp.setForeground(Color.BLUE);
        inp.setLocation(200, 100);
        inp.setSize(200, 50);
        content.add(inp);
        in = new JTextArea();
        JScrollPane scrollfield = new JScrollPane(in);
        in.setBorder(border);
        in.setFont(new Font("Cambria", Font.PLAIN, 14));
        in.setLocation(200, 150);
        in.setSize(600, 20);
        content.add(in);
        content.add(scrollfield);
        out = new JLabel("List of URL's");
        out.setFont(new Font("Serif", Font.BOLD, 14));
        out.setForeground(Color.BLUE);
        out.setLocation(200, 200);
        out.setSize(100, 50);
        content.add(out);
        op = new JTextArea();
        JScrollPane scrollArea = new JScrollPane(op);
        op.setFont(new Font("Cambria", Font.PLAIN, 14));
        op.setBorder(border);
        op.setLocation(200, 250);
        op.setSize(900, 300);
        content.add(op);
        content.add(scrollArea);
        op.setEditable(false);
        b1 = new JButton("Search");
        b1.setLocation(850, 150);
        b1.setSize(80, 20);
        content.add(b1);
        b1.addActionListener(this);
    }

    private void initDependencies(){
        repository = MicrominerContainer.getRepository();
        repository.setContent(content);
        searcher = MicrominerContainer.getSearcher();
        kwicRepository = MicrominerContainer.getKwicRepository();
        Map<String, String> values = kwicRepository.getAll();
        searcher.reload(values);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            Map<String, String> output = searcher.search(in.getText());
            String result = Joiner.on(newLine).withKeyValueSeparator(" ").join(output);
            op.setText(result);
        }
    }

}