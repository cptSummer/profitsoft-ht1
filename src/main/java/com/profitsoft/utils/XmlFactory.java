package com.profitsoft.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;

public class XmlFactory {
    public void toXml(String directoryPath, HashMap<String, HashMap<String, Integer>> toXml) {
        for (String key : toXml.keySet()) {
            String fileName = directoryPath + File.separator + "statistics_by_" + key + ".xml";
            writeXml(fileName, toXml.get(key));
        }
    }

    private static void writeXml(String fileName, HashMap<String, Integer> attributeCount) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("statistics");
            doc.appendChild(rootElement);

            for (String key : attributeCount.keySet()) {
                Node attribute = createAttributeElement(doc, key, attributeCount.get(key));
                rootElement.appendChild(attribute);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File(fileName));
            transformer.transform(source, file);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
    }

    private static Node createAttributeElement(Document doc, String value, Integer count) {
        Element item = doc.createElement("item");


        item.appendChild(createAttributeElements(doc, item, "value", value));

        item.appendChild(createAttributeElements(doc, item, "count", count.toString()));

        return item;
    }

    private static Node createAttributeElements(Document doc, Element item, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}
