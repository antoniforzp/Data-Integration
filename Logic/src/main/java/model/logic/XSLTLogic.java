package model.logic;

import model.resources.XsltTransformations;

public class XSLTLogic {
    public static String toTxt(String xml, String xslt, String output) {
        return XsltTransformations.createNewTxt(xml, xslt, output);
    }

    public static String toXml(String xml, String xslt, String output) {
        return XsltTransformations.createNewXml(xml, xslt, output);
    }

    public static String toHtml(String xml, String xslt, String output) {
        return XsltTransformations.createNewHtml(xml, xslt, output);
    }
}
