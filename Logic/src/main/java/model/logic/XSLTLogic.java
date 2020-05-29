package model.logic;

import model.resources.XsltTransformations;
import org.jdom2.transform.XSLTransformException;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public class XSLTLogic {
    public static String toTxt(String xml, String xslt, String output) throws TransformerException, IOException {
        return XsltTransformations.createNewTxt(xml, xslt, output);
    }

    public static String toXml(String xml, String xslt, String output) throws XSLTransformException {
        return XsltTransformations.createNewXml(xml, xslt, output);
    }

    public static String toHtml(String xml, String xslt, String output) throws XSLTransformException {
        return XsltTransformations.createNewHtml(xml, xslt, output);
    }
}
