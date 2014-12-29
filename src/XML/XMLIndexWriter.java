/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author LuLu
 */
public class XMLIndexWriter {

    private int i;
    private int j;
    private int size1;
    private int size2;

    public XMLIndexWriter() {

    }

    public void modifiedFile(ArrayList<ArrayList<String>> list, String filepath) {
        try {
            File file = new File(filepath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(XMLIndexReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
