/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 * XMLManager- It works only if XML is correctly formated.
 *
 * @author Luming Wu . Learned from mkyong.com
 */
// Should Look For JDOM2 API to get a better performance.
public class IndexedXMLManager {

    private File file;
    private Document document;
    private int i;
    private int j;
    private int size1;
    private int size2;

    private ArrayList<ArrayList<String>> _data;

    public IndexedXMLManager() {
        document = null;
        _data = new ArrayList<ArrayList<String>>();
    }

    public void setFile(String filepath) {
        try {
            file = new File(filepath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.out.println("Exception from setFile() in IndexedXMLManager");
        }
    }

    // Reason: Simple, if I read it right and rewrite it right with program, there is no reason for me to check if the XML file is right unless I am hacked.
    public ArrayList<ArrayList<String>> quickParseFile() {
        if (document == null) {
            return null;
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

    public void quickUpdateFileValues(ArrayList<ArrayList<String>> updatelist) {
        if (document == null) {

        } else {
            NodeList list = document.getChildNodes().item(0).getChildNodes();
            size1 = updatelist.size();
            for (i = 0; i < size1; i++) {
                list.item(1 + 2 * i).getAttributes().item(0).setTextContent(updatelist.get(i).get(0));
                size2 = updatelist.get(i).size();
                for (j = 1; j < size2; j++) {
                    list.item(1 + 2 * i).getChildNodes().item(1 + 2 * (j - 1)).setTextContent(updatelist.get(i).get(j));
                }
            }
        }
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println("Exception from updateFileValues() in IndexedXMLManager");
        }
    }

    public void quickRewrite(ArrayList<ArrayList<String>> rewritelist) {
        if (document == null) {

        } else {
            try {
                DocumentBuilderFactory documentfactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentbuilder = documentfactory.newDocumentBuilder();

                document = documentbuilder.newDocument();
                Element rootelement = document.createElement("STORAGE");
                document.appendChild(rootelement);

                size1 = rewritelist.size();
                for (i = 0; i < size1; i++) {
                    Element element = document.createElement("OPTION_LIST");
                    rootelement.appendChild(element);

                    Attr attribute = document.createAttribute("NAME");
                    attribute.setValue(rewritelist.get(i).get(0));
                    element.setAttributeNode(attribute);

                    size2 = rewritelist.get(i).size();
                    for (j = 1; j < size2; j++) {
                        Element subelement = document.createElement("OPTION");
                        subelement.appendChild(document.createTextNode(rewritelist.get(i).get(j)));

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
                System.out.println("Exception from rewrite() in IndexedXMLManager");
            } catch (TransformerConfigurationException ex) {
                System.out.println("Exception from rewrite() in IndexedXMLManager");
            } catch (TransformerException ex) {
                System.out.println("Exception from rewrite() in IndexedXMLManager");
            }
        }
    }
    
}
