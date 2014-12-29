/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.io.File;
import java.io.FileNotFoundException;
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
public class XMLManager {

    private Document document;
    private int i;
    private int j;
    private int size1;
    private int size2;

    public XMLManager() {
        document = null;
    }

    public void setFile(String filepath) {
        try {
            File file = new File(filepath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ArrayList<String>> parseFile() throws FileNotFoundException {
        if (document == null) {
            throw new FileNotFoundException();
        }
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        NodeList list = document.getChildNodes().item(0).getChildNodes();
        size1 = list.getLength();
        for (i = 1; i < size1; i = i + 2) {
            ArrayList<String> data = new ArrayList<String>();
            data.add(list.item(i).getAttributes().item(0).getTextContent());
            size2 = list.item(i).getChildNodes().getLength();
            for (j = 1; j < size2; j = j + 2) {
                data.add(list.item(i).getChildNodes().item(j).getTextContent());
            }
            result.add(data);
        }
        return result;
    }

    public void updateFileValues(ArrayList<ArrayList<String>> updatelist) throws FileNotFoundException {
        if (document == null) {
            throw new FileNotFoundException();
        }
        NodeList list = document.getChildNodes().item(0).getChildNodes();
        size1 = updatelist.size();
        for (i = 0; i < size1; i++) {
            list.item(1 + 2 * i).getAttributes().item(0).setTextContent(updatelist.get(i).get(0));
            size2 = updatelist.get(i).size();
            for (j = 1; j < size2; j++) {
                list.item(1 + 2 * i).getChildNodes().item(1 + 2 * j).setTextContent(updatelist.get(i).get(j));
            }
        }
    }
}
