package model.resources;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 *
 * @author abs
 */

public class XMLJDomFunctions{
    /*Read a XML file from disk*/
    public static Document readDocumentXML(String filename) {
        try {        
            File file = new File(filename);
            InputStreamReader stream = new InputStreamReader(new FileInputStream(file), "utf-8");
            Reader reader = new BufferedReader(stream);
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(reader);
            return anotherDocument; 
        } catch (JDOMException | IOException ex) {
            System.out.println("File XML not found");
            return null;
        }
    }
    
    public static void writeDocumentToFile(Document doc, String filename) {
        OutputStreamWriter writer = null;
        try {


            Format outputFormat = Format.getPrettyFormat();
            outputFormat.setIndent("     ");
            outputFormat.setEncoding("utf-8"); 

            XMLOutputter outputter = new XMLOutputter(outputFormat);            
            writer = new OutputStreamWriter(new FileOutputStream(filename), "utf-8");
            outputter.output(doc, writer);
            writer.close();     
        } catch (IOException ex) {
            Logger.getLogger(XMLJDomFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(XMLJDomFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /*Read a Document XML to a String*/
    public static String readDocumentToString(Document doc) {
            Format outputFormat = Format.getPrettyFormat();
            outputFormat.setIndent("     ");

            XMLOutputter outputter = new XMLOutputter(outputFormat);
            String txt = outputter.outputString(doc);
            return txt;
    }

    public static Document readStringToDocument(String node) {
        try {
            SAXBuilder builder = new SAXBuilder();
            InputStream stream = new ByteArrayInputStream(node.getBytes(StandardCharsets.UTF_8));
            return builder.build(stream);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
        return null;
    }
}
