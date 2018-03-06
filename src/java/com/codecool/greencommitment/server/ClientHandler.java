package com.codecool.greencommitment.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClientHandler extends Thread {

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    Document finalDom;




        // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;

        /*try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("/home/robertn/Documents/java_develop/git_repos/Green_Commitment/joop-5th-green-commitment-team1/src/java/com/codecool/greencommitment/server/horn.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try{
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            Document finalDom = db.newDocument();
            this.finalDom=finalDom;
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void run() {

        BufferedInputStream bis;
        BufferedOutputStream bos;
        int num;

        try {
            int i = 1;
            Socket incoming = s;
            System.out.println("Spawning " + i);

            try {

                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                    OutputStream outStream = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);


                    out.println("Object received ");



                    Document domRecieved = (Document) ois.readObject();

                    if(finalDom.getElementsByTagName("measurements").getLength()<1){
                        Element rootE = finalDom.createElement("measurements");
                        rootE.setAttribute("id",domRecieved.getElementsByTagName("measurement").item(0).getAttributes().item(0).getTextContent());
                        finalDom.appendChild(rootE);
                    }

                    domRecieved.getElementsByTagName("measurement").item(0).getAttributes().item(0).setNodeValue(null);

                    Node imported = domRecieved.importNode(domRecieved.getFirstChild(),true);
                    Element e = (Element) imported;
                    finalDom.getFirstChild().appendChild((e));

                try {
                    File f = new File(finalDom.getElementsByTagName("measurements").item(0).getAttributes().item(0).getTextContent()+".xml");
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.parse(f);

                    // Use a Transformer for output
                    TransformerFactory tFactory =
                            TransformerFactory.newInstance();
                    Transformer transformer =
                            tFactory.newTransformer();

                    DOMSource source = new DOMSource(finalDom);
                    StreamResult result = new StreamResult(System.out);
                    transformer.transform(source, result);
                }catch (TransformerConfigurationException tce) {
                    System.out.println("* Transformer Factory error");
                    System.out.println(" " + tce.getMessage());

                    Throwable x = tce;
                    if (tce.getException() != null)
                        x = tce.getException();
                    x.printStackTrace();
                }
                catch (TransformerException te) {
                    System.out.println("* Transformation error");
                    System.out.println(" " + te.getMessage());

                    Throwable x = te;
                    if (te.getException() != null)
                        x = te.getException();
                    x.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}





