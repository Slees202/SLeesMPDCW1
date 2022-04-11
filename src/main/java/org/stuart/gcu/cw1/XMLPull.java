//Stuart Lees S1821982
package org.stuart.gcu.cw1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class SitesXmlPullParser {

    static final String ITEM = "item";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String GEORSS = "georss";
    static final String AUTHOR = "author";
    static final String COMMENTS = "comments";
    static final String PUBDATE = "pubdate";



    public static List<TrafficScotland> getTrafficScotlandsFromFile(Context ctx) {


        List<TrafficScotland> trafficScotlands;
        trafficScotlands = new ArrayList<TrafficScotland>();


        TrafficScotland curTrafficScotland = null;

        String curText = "";

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();


            FileInputStream fis = ctx.openFileInput("TrafficScotlands.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            xpp.setInput(reader);

            int eventType = xpp.getEventType();


            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = xpp.getName();


                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(ITEM)) {
                            curTrafficScotland = new TrafficScotland();
                        }
                        break;

                    case XmlPullParser.TEXT:

                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase(ITEM)) {
                            trafficScotlands.add(curTrafficScotland);
                        } else if (tagname.equalsIgnoreCase(TITLE)) {
                            curTrafficScotland.setTitle(curText);
                        } else if (tagname.equalsIgnoreCase(DESCRIPTION)) {
                            curTrafficScotland.setDescription(curText);
                        } else if (tagname.equalsIgnoreCase(LINK)) {
                            curTrafficScotland.setLink(curText);
                        } else if (tagname.equalsIgnoreCase(GEORSS)) {
                            curTrafficScotland.setGeorss(curText);
                        } else if (tagname.equalsIgnoreCase(AUTHOR)) {
                            curTrafficScotland.setAuthor(curText);
                        } else if (tagname.equalsIgnoreCase(COMMENTS)) {
                            curTrafficScotland.setComments(curText);
                        } else if (tagname.equalsIgnoreCase(PUBDATE)) {
                            curTrafficScotland.setPubDate(curText);
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trafficScotlands;
    }
}