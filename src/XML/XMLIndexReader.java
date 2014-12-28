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
 * Parse a XML file.
 *
 * @author Luming Wu ,Example: mkyong.
 */
public class XMLIndexReader {

    private int i;
    private int j;
    private int size1;
    private int size2;

    public XMLIndexReader() {

    }

    public ArrayList<ArrayList<String>> parseFile(String filepath) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        if (result.isEmpty()) {
            try {
                File file = new File(filepath);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(file);

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
            } catch (SAXException | IOException | ParserConfigurationException ex) {
                Logger.getLogger(XMLIndexReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}
