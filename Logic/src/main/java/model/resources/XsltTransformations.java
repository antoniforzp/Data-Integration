package model.resources;

import org.jdom2.Document;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;

public class XsltTransformations {
    public static String createNewXml(String xml, String xslt, String output) {
        Document doc = XMLJDomFunctions.readDocumentXML(xml);
        if (doc != null) {
            Document x = JDOMFunctions_XSLT.transformDocument(doc, xml, xslt);
            XMLJDomFunctions.writeDocumentToFile(x, output);


            doc = XMLJDomFunctions.readDocumentXML(output);
            String t = XMLJDomFunctions.readDocumentToString(doc);
            return t;
        }
        return "No such file";
    }

    public static String createNewHtml(String xml, String xslt, String output){
        Document doc = XMLJDomFunctions.readDocumentXML(xml);
        if (doc != null) {
            Document x = JDOMFunctions_XSLT.transformDocument(doc, xml, xslt);
            XMLJDomFunctions.writeDocumentToFile(x, output);

            String url = output;
            File htmlFile = new File(url);
            try {
                String content = new String(Files.readAllBytes(htmlFile.toPath()));
                Desktop.getDesktop().browse(htmlFile.toURI());
                System.out.println(htmlFile.toURI());
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String createNewTxt(String xml, String xslt, String output){
        Document doc = XMLJDomFunctions.readDocumentXML(xml);
        if (doc != null) {
            JDOMFunctions_XSLT.transformDocument2(xml, xslt, output);

            try {
                FileReader reader = new FileReader(output);
                BufferedReader bufferedReader = new BufferedReader(reader);

                StringBuilder builder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "No such file";
    }
}
