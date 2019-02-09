package com.example.myandroidproject.xml_parse;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {


    /**
     * XML PULL解析 基于时间驱动
     */
    public static void pullParse(){

        try {
            File file = new File("F:\\MyAndroidProject\\app\\src\\main\\java\\com\\example\\myandroidproject\\xml_parse\\person.xml");
            FileInputStream fileInputStream = new FileInputStream(file);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(fileInputStream,"UTF-8");

            int eventType = parser.getEventType();
            List<Person> list = new ArrayList<>();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String data = parser.getName();
                Person person = null;
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if ("person".equals(data)){
                            person = new Person();
                        }
                        if ("name".equals(data)){
                            person.setName(parser.nextText());
                        }
                        if ("age".equals(data)){
                            person.setAge(Integer.parseInt(parser.nextText()));
                        }
                        if ("sex".equals(data)){
                            person.setSex(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("person".equals(data) && person != null){
                            list.add(person);
                        }
                        break;
                }
                eventType = parser.next();
            }
            Log.d("XMLParse","list: " + list.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
