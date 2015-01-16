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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author LuLu
 */
public class RegularXMLManager {

    private File file;
    private static RegularXMLManager me = null;
    private ArrayList<ArrayList<String>> _data = new ArrayList<ArrayList<String>>();

    private RegularXMLManager() {
    }

    public static RegularXMLManager getManager() {
        if (me == null) {
            me = new RegularXMLManager();
        }
        return me;
    }

    public void setFile(String filePath) {
        _data.clear();
        try {
            file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList list = document.getChildNodes().item(0).getChildNodes();
            int columns = list.getLength();
            for (int i = 1; i < columns; i = i + 2) {
                ArrayList<String> columndata = new ArrayList<String>();
                columndata.add(list.item(i).getAttributes().item(0).getTextContent());
                int rows = list.item(i).getChildNodes().getLength();
                for (int j = 1; j < rows; j = j + 2) {
                    columndata.add(list.item(i).getChildNodes().item(j).getTextContent());
                }
                _data.add(columndata);
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(RegularXMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public synchronized void insert(ArrayList<String> newdata) {
        _data.add(newdata);
        int position = _data.size() - 1;
        while (_data.get(position).get(0).compareTo(_data.get(position - 1).get(0)) < 0) {
            ArrayList<String> temp = _data.get(position - 1);
            _data.set(position - 1, _data.get(position));
            _data.set(position, temp);
            position = position - 1;
        }
    }
    
    public synchronized void sort() {
        for (int i = 1; i < _data.size(); i++) {
            int position = i;
            while (_data.get(position).get(0).compareTo(_data.get(position - 1).get(0)) > 0) {
                exchange(position, position - 1);
                position = position - 1;
                if (position == 0) {
                    break;
                }
            }
        }
    }

    private void exchange(int index1, int index2) {
        ArrayList<String> temp = _data.get(index1);
        _data.set(index1, _data.get(index2));
        _data.set(index2, temp);
    }

    
    public void rewrite() {
        try {
            DocumentBuilderFactory documentfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = documentfactory.newDocumentBuilder();

            Document document = documentbuilder.newDocument();
            Element rootelement = document.createElement("STORAGE");
            document.appendChild(rootelement);

            int size1 = _data.size();
            for (int i = 0; i < size1; i++) {
                Element element = document.createElement("OPTION_LIST");
                rootelement.appendChild(element);

                Attr attribute = document.createAttribute("NAME");
                attribute.setValue(_data.get(i).get(0));
                element.setAttributeNode(attribute);

                int size2 = _data.get(i).size();
                for (int j = 1; j < size2; j++) {
                    Element subelement = document.createElement("OPTION");
                    subelement.appendChild(document.createTextNode(_data.get(i).get(j)));

                    element.appendChild(subelement);
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            System.out.println("Exception from Parse of rewrite() in IndexedXMLManager");
        } catch (TransformerConfigurationException ex) {
            System.out.println("Exception from TransformConfiguration of rewrite() in IndexedXMLManager");
        } catch (TransformerException ex) {
            System.out.println("Exception from Transform of rewrite() in IndexedXMLManager");
        }
    }

    public String getOption(String name) {
        XMLFinder finder = new XMLFinder();
        int index = finder.find(name);
        if (_data.get(index).size() == 2) {
            return _data.get(index).get(1);
        }
        return null;
    }

    public ArrayList<String> getOptionList(String name) {
        XMLFinder finder = new XMLFinder();
        int index = finder.find(name);
        int size = _data.get(index).size();
        if (size > 2) {
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 1; i < size; i++) {
                list.add(_data.get(index).get(i));
            }
            return list;
        }
        return null;
    }

    private class XMLFinder {

        public int find(String name) {
            int left = 0;
            int right = _data.size() - 1;
            int middle;
            while (left <= right) {
                middle = (left + right) / 2;
                if (_data.get(middle).get(0).compareTo(name) == 0) {
                    return middle;
                }
                if (_data.get(middle).get(0).compareTo(name) > 0) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
            return -1;
        }
    }
}
