/*
 * @(#)FileSplitter.java 2013-3-2 下午12:15:15 XML2SQL
 */
package com.xml2sql.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FileSplitter
 * @author wang
 * @version 1.0
 *
 */
public class FileSplitter {

    public static final List<String> ENTITY_ELEMENT = Arrays.asList(
        "</proceedings>", "</article>", "</inproceedings>", "</phdthesis>",
        "</mastersthesis>", "</www>", "</book>", "</incollection>");

    private int maxLine;

    public FileSplitter(int maxLine) {
        this.maxLine = maxLine;

    }

    public List<File> splitFile(String xmlFolderPath, String xmlFilePath) {
        List<File> fileList = new ArrayList<File>();
        int line = 0, fileCount = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(xmlFolderPath + xmlFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = null;
        try {
            //跳过三行
            br.readLine();
            br.readLine();
            br.readLine();
            String str = null;
            File file = null;
            while ((str = br.readLine()) != null) {
                if (line == 0) {
                    file = new File(xmlFolderPath + xmlFilePath + (++fileCount)
                            + ".xml");
                    bw = new BufferedWriter(new FileWriter(file));
                    bw.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
                    bw.newLine();
                    bw.write("<!DOCTYPE dblp SYSTEM \"dblp.dtd\">");
                    bw.newLine();
                    bw.write("<dblp>");
                    bw.newLine();
                }
                bw.write(str);
                bw.newLine();
                line++;
                if (line > maxLine && ENTITY_ELEMENT.contains(str)) {
                    bw.write("</dblp>");
                    bw.flush();
                    bw.close();
                    fileList.add(file);
                    line = 0;
                }
            }
            bw.flush();
            bw.close();
            fileList.add(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }
}
