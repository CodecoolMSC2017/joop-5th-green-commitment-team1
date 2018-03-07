package com.codecool.greencommitment.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    final private Socket s;
    private Document finalDom;

    // Constructor
    ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;

        /*try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("/home/robertn/Documents/java_develop/git_repos/Green_Commitment/joop-5th-green-commitment-team1/src/java/com/codecool/greencommitment/server/horn.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            Document finalDom = db.newDocument();
            this.finalDom = finalDom;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        while (true) {

            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                System.out.println("Object received from: " + s);


                Document domRecieved = (Document) ois.readObject();



                //System.out.println(domRecieved.getElementsByTagName("time").item(0).getTextContent());

                if (finalDom.getElementsByTagName("measurements").getLength() < 1) {
                    Element rootE = finalDom.createElement("measurements");
                    rootE.setAttribute("id", domRecieved.getElementsByTagName("measurement").item(0).getAttributes().item(0).getTextContent());
                    finalDom.appendChild(rootE);
                }

                Element rRoot = domRecieved.getDocumentElement();

                Node imported = domRecieved.importNode(rRoot, true);
                finalDom.adoptNode(imported);
                Element tmpElement = (Element) imported;
                tmpElement.getAttributes().removeNamedItem("id");
                finalDom.getDocumentElement().appendChild(imported);


                try {
                    File f = new File(finalDom.getElementsByTagName("measurements").item(0).getAttributes().item(0).getTextContent() + ".xml");

                    // Use a Transformer for output
                    TransformerFactory tFactory =
                            TransformerFactory.newInstance();
                    Transformer transformer =
                            tFactory.newTransformer();

                    DOMSource source = new DOMSource(finalDom);
                    StreamResult result = new StreamResult(f);
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.transform(source, result);
                } catch (TransformerConfigurationException tce) {
                    System.out.println("* Transformer Factory error");
                    System.out.println(" " + tce.getMessage());

                    Throwable x = tce;
                    if (tce.getException() != null)
                        x = tce.getException();
                    x.printStackTrace();
                } catch (TransformerException te) {
                    System.out.println("* Transformation error");
                    System.out.println(" " + te.getMessage());

                    Throwable x = te;
                    if (te.getException() != null)
                        x = te.getException();
                    x.printStackTrace();
                }

            } catch (Exception e) {
                break;
            }
        }
        System.out.println("ClientHandler exits");
        FileCollector files = new FileCollector(".");
        String[] arr = files.getXMLFileNames();
        Charter ex = null;
        try {
            ex = new Charter(arr);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ex.setVisible(true);
    }
}

