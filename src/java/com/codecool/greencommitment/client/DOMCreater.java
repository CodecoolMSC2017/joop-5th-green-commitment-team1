package com.codecool.greencommitment.client;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class DOMCreater {
    public Document createDOM(String time,String value,String type) {
        Document dom=null;
        Element e = null;
        GetMac gm = new GetMac();

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("measurement");
            rootEle.setAttribute("id",gm.getMacAddress());
            dom.appendChild(rootEle);

            // create data elements and place them under root
            e = dom.createElement("time");
            e.setTextContent(time);
            rootEle.appendChild(e);

            e = dom.createElement("value");
            e.setTextContent(value);
            rootEle.appendChild(e);

            e = dom.createElement("type");
            e.setTextContent(type);
            rootEle.appendChild(e);



        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
        return dom;

    }
}
