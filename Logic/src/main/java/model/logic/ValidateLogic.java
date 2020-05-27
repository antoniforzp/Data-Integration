package model.logic;

import model.resources.Validation;

public class ValidateLogic {

    public static int validateDTD(String xmlFile, String dtdFile) {
        return Validation.validateDocumentDTD(xmlFile, dtdFile);
    }

    public static int validateXSD(String xmlFile, String xsdFile) {
        return Validation.validateDocumentXSD(xmlFile, xsdFile);
    }
}
