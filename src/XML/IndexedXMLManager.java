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
 * The first list is the index list. Each letter has its starting index.All name
 * must start with capital alphabet.
 *
 * @author Luming Wu . Learned from mkyong.com
 */
public class IndexedXMLManager {

    private File file;
    private static IndexedXMLManager me = null;
    private ArrayList<OptionList> _data = new ArrayList<OptionList>();

    private IndexedXMLManager() {
    }

    public static IndexedXMLManager getManager() {
        if (me == null) {
            me = new IndexedXMLManager();
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
                OptionList columndata = new OptionList(list.item(i).getAttributes().item(0).getTextContent());
                int rows = list.item(i).getChildNodes().getLength();
                for (int j = 1; j < rows; j = j + 2) {
                    columndata.insert(list.item(i).getChildNodes().item(j).getTextContent());
                }
                _data.add(columndata);
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(RegularXMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(OptionList newdata) {
        _data.add(newdata);
        int position = _data.size() - 1;
        while (_data.get(position).compareTo(_data.get(position - 1)) < 0 && position > 1) {
            OptionList temp = _data.get(position - 1);
            _data.set(position - 1, _data.get(position));
            _data.set(position, temp);
            position = position - 1;
        }
        for (int i = newdata.getID().charAt(0) - 'A' + 1; i < 26; i++) {
            _data.get(0).getList().set(i, "" + (Integer.parseInt(_data.get(0).getList().get(i)) + 1));
        }
    }

    public void remove(String name) {
        XMLFinder finder = new XMLFinder();
        _data.remove(finder.find(name));
        for (int i = name.charAt(0) - 'A' + 1; i < 26; i++) {
            _data.get(0).getList().set(i, "" + (Integer.parseInt(_data.get(0).getList().get(i)) - 1));
        }
    }

    public void rewrite() {
        try {
            //Get builder from singleton structure.
            DocumentBuilderFactory documentfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = documentfactory.newDocumentBuilder();

            //Create first node for being a nodelist;
            Document document = documentbuilder.newDocument();
            Element rootelement = document.createElement("STORAGE");
            document.appendChild(rootelement);
            //Create the index nodelist and add it to storage.
            Element firstelement = document.createElement("OPTION_LIST");
            rootelement.appendChild(firstelement);
            Attr firstattribute = document.createAttribute("NAME");
            firstattribute.setValue("INDEX");
            firstelement.setAttributeNode(firstattribute);
            for (int k = 0; k < 26; k++) {
                Element initialsubelement = document.createElement("OPTION");
                initialsubelement.appendChild(document.createTextNode(_data.get(0).getList().get(k)));

                firstelement.appendChild(initialsubelement);
            }
            //Create remaining nodelists and add them to storage.
            int size1 = _data.size();
            for (int i = 1; i < size1; i++) {
                Element element = document.createElement("OPTION_LIST");
                rootelement.appendChild(element);

                Attr attribute = document.createAttribute("NAME");
                attribute.setValue(_data.get(i).getID());
                element.setAttributeNode(attribute);

                int size2 = _data.get(i).getList().size();
                for (int j = 1; j < size2; j++) {
                    Element subelement = document.createElement("OPTION");
                    subelement.appendChild(document.createTextNode(_data.get(i).getList().get(j)));

                    element.appendChild(subelement);
                }
            }

            //Format the document.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            //Save the document.
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

    public void sort() {
        for (int i = 2; i < _data.size(); i++) {
            int position = i;
            while (_data.get(position).compareTo(_data.get(position - 1)) > 0) {
                exchange(position, position - 1);
                position = position - 1;
                if (position == 1) {
                    break;
                }
            }
        }
    }

    private void exchange(int index1, int index2) {
        OptionList temp = _data.get(index1);
        _data.set(index1, _data.get(index2));
        _data.set(index2, temp);
    }

    public void indexScan() {
        _data.get(0).getList().clear();
        int target = 'A';
        for (int i = 1; i < _data.size(); i++) {
            if (_data.get(i).getID().charAt(0) == target) {
                _data.get(0).getList().add("" + i);
                target = target + 1;
            }
        }
    }

    public String getOption(String name) {
        XMLFinder finder = new XMLFinder();
        int index = finder.find(name);
        if (_data.get(index).isOption()) {
            return _data.get(index).getList().get(0);
        }
        return null;
    }

    public ArrayList<String> getOptionList(String name) {
        XMLFinder finder = new XMLFinder();
        int index = finder.find(name);
        if (_data.get(index).isOptionList()) {
            return _data.get(index).getList();
        }
        return null;
    }

    private class XMLFinder {

        public int find(String name) {
            int datainitial = name.charAt(0) - 'A';
            int left = Integer.parseInt(_data.get(0).getList().get(datainitial));
            int right;
            if (datainitial < 25) {
                right = Integer.parseInt(_data.get(0).getList().get(datainitial + 1));
            } else {
                right = _data.size() - 1;
            }
            int middle;
            while (left <= right) {
                middle = (left + right) / 2;
                if (_data.get(middle).getID().compareTo(name) == 0) {
                    return middle;
                }
                if (_data.get(middle).getID().compareTo(name) > 0) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
            return -1;
        }
    }
}
