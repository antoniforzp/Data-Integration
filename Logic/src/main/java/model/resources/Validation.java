package model.resources;

import org.jdom2.*;

import java.io.File;
import java.io.IOException;

public class Validation {
    public static String validateDocumentDTD(String xmlFile, String DTDFile) {
        try {
            Document doc = XMLJDomFunctions.readDocumentXML(xmlFile);
            File f = new File(DTDFile);
            if (doc != null && f.exists()) { //DTD e XML exist?
                Element root = doc.getRootElement();
                //Link the DTD to XML
                DocType dtd = new DocType(root.getName(), DTDFile);
                doc.setDocType(dtd);
                //Save XML file
                XMLJDomFunctions.writeDocumentToFile(doc, xmlFile);
                //Use the validation function
                Document docDTD = JDOMFunctions_Validation.validateDTD(xmlFile);
                if (docDTD == null) {
                    System.out.println("logic.resources.Validation using " + DTDFile + " failed");
                    return "Validation using " + DTDFile + " failed";
                } else {
                    System.out.println("The XML file is correct using " + DTDFile);
                    return "The XML file is correct using " + DTDFile;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "No such XML or DTD file";
    }

    public static String validateDocumentXSD(String xmlFile, String XSDFile) {
        Document doc = XMLJDomFunctions.readDocumentXML(xmlFile);
        File f = new File(XSDFile);
        if (doc != null && f.exists()) {//XSD and XML exist?
            Element root = doc.getRootElement();
            //Link XSD to XML
            Namespace XSI = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
            root.addNamespaceDeclaration(XSI);
            root.setAttribute(new Attribute("noNamespaceSchemaLocation", XSDFile,
                    Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
            //Save XML file
            XMLJDomFunctions.writeDocumentToFile(doc, xmlFile);
            //logic.resources.Validation using XSD
            Document docXSD = JDOMFunctions_Validation.validateXSD(xmlFile);
            if (docXSD == null) {
                System.out.println("logic.resources.Validation using " + XSDFile + " failed");
                return "logic.resources.Validation using " + XSDFile + " failed";
            }
            else {
                System.out.println("The XML file is correct using " + XSDFile);
                return "The XML file is correct using " + XSDFile;
            }
        }
        return "No such XML or XSD file";
    }
}
