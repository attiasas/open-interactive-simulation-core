package org.attias.open.interactive.simulation.core.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class XmlUtils {
    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();

    public static Document readXml(Path path) throws ParserConfigurationException, IOException, SAXException {
        return factory.newDocumentBuilder().parse(path.toFile());
    }

    public static void saveXml(Document doc, Path destination) throws TransformerException {
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(destination.toFile());
        transformer.transform(source,result);
    }
}
