/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

 package ucf.assignment5;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HTMLManager {
    private File file;
    private ObservableList<listItem> list;

    public HTMLManager(File newFile, ObservableList<listItem> newList) {
        this.file = newFile;
        this.list = newList;
    }
    
    public void write() {
        try {
            PrintWriter pw = new PrintWriter(file);

            String html = "<!DOCTYPE html>\n<html>\n";

            for(listItem i : list) {
                html += addDiv(i);
            }
            html += "</html>";

            pw.write(html);
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<listItem> read() {
        ObservableList<listItem> returnList = FXCollections.observableArrayList();
        String html = "";

        try {
            Scanner in = new Scanner(file);

            while(in.hasNextLine()) {
                html += in.nextLine();
            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if(html.isBlank()) {return null;}

        try {
            Elements elements = Jsoup.parse(html).getAllElements().get(0).getElementsByTag("div");

            for(Element e : elements) {
                String[] Split = e.text().split(" ");
                int splitSize = Split.length;

                listItem newItem = new listItem(null, null, null);

                String name = "";
                for(int i = 0; i < splitSize - 2; i++) {
                    name += Split[i] + " ";
                }

                newItem.setName(name.stripTrailing());
                newItem.setSerialNumber(Split[splitSize - 2]);
                newItem.setValue(Split[splitSize - 1]);

                returnList.add(newItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return returnList;

    }

    private String addDiv(listItem i) {
        String s1 = i.getName();
        String s2 = i.getSerialNumber();
        String s3 = i.getValue();

        String ret = addH1(s1) + addH1(s2) + addH1(s3);

        return "\t<div>\n" + ret + "\t</div>\n";
    }

    private String addH1(String in) {
        return "\t\t<h1>" + in + "</h1>\n";
    }
}
