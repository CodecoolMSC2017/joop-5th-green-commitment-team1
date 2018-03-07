package com.codecool.greencommitment.server;

import java.io.File;
import java.util.ArrayList;

public class FileCollector {
    private File[] listOfFiles;

    public FileCollector(String filepath) {
        File folder = new File(filepath);
        listOfFiles = folder.listFiles();
    }

    public String[] getXMLFileNames() {
        ArrayList<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            String temp = listOfFiles[i].getName();
            if (temp.contains(".xml") && !(temp.contains("pom.xml"))){
                fileNames.add(temp.replace(".xml", ""));
            }
        }

        return fileNames.toArray(new String[0]);
    }

    public void printFolderContent(){
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println(" |----- File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());

            }
        }
    }
}
