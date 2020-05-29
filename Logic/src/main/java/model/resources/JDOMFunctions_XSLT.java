package model.resources;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.transform.XSLTransformException;
import org.jdom2.transform.XSLTransformer;

/**
 * @author abs
 */
public class JDOMFunctions_XSLT {

    public static Document transformDocument(Document XMLdoc, String xmlFile, String xslFile) throws XSLTransformException {

        DocType d = XMLdoc.getDocType();
        if (d != null) {
            XMLdoc.setDocType(null);
            XMLJDomFunctions.writeDocumentToFile(XMLdoc, xmlFile);
            XMLdoc = XMLJDomFunctions.readDocumentXML(xmlFile);
        }

        XSLTransformer transformer = new XSLTransformer(xslFile);
        Document doc2 = transformer.transform(XMLdoc);
        if (doc2 == null) System.out.println("Null");
        return doc2;
    }

    public static void transformDocument2(String xmlFile, String xslFile, String sOutFile) throws TransformerException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(xmlFile));
        String sLine;
        StringBuilder sBuffer = new StringBuilder();
        while ((sLine = br.readLine()) != null)
            sBuffer.append(sLine).append("\n");
        String sXML = sBuffer.toString();
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
        transformer.transform(new StreamSource(new StringReader(sXML)),
                new StreamResult(new FileOutputStream(sOutFile)));

    }

}
