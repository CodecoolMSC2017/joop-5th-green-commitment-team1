package com.codecool.greencommitment.server;

import java.io.File;
import java.util.ArrayList;

public class FileCollector {
    private final String filepath;
    private File folder;
    private File[] listOfFiles;

    public FileCollector(String filepath) {
        this.filepath = filepath;
        folder = new File(filepath);
        listOfFiles = folder.listFiles();
    }

    public ArrayList<String> getFileNames() {
        ArrayList<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            String temp = listOfFiles[i].getName();
            if (temp.contains(".xml") && !(temp.contains("pom.xml"))){
                fileNames.add(temp);
            }
        }
        return fileNames;
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
