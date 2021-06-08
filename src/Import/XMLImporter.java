package Import;


import Source.*;

import Source.Character;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class import data from xml file
 * @author  Ilya
 */
public class XMLImporter extends  JFrame{
    private CharactersTable readableData;
    public static final Logger logger = Logger.getLogger(XMLImporter.class);
    /**
     * Constructor load all needed data
     * @param form
     */
    public XMLImporter(MainForm form)
    {
        FileDialog open = new FileDialog(this, "XML Import", FileDialog.LOAD);
        open.setFile("*.xml");
        open.setVisible(true);

        String fullname = open.getDirectory() + open.getFile();

        if (fullname == null|| fullname.equals("nullnull"))
            return;

        Document doc = null;
        try
        {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = documentBuilder.parse( new File(fullname));
            doc.getDocumentElement().normalize();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        NodeList Records = doc.getElementsByTagName("record");
        List<Character> dataList = new ArrayList<Character>();

        for (int i = 0; i < Records.getLength(); i++)
        {
            dataList.add( new Character(Records.item(i)));
        }
        form.ApplyData(new CharactersTable(dataList));
    }

    /**
     * Constructor for get data from xml file without mainform
     */
    public XMLImporter()
    {
        FileDialog open = new FileDialog(this, "XML Import", FileDialog.LOAD);
        open.setFile("*.xml");
        open.setVisible(true);

        String fullname = open.getDirectory() + open.getFile();

        if (fullname == null|| fullname.equals("nullnull"))
        {
            logger.warn("User aborted file loading");
            return;
        }


        Document doc = null;
        try
        {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = documentBuilder.parse( new File(fullname));
            doc.getDocumentElement().normalize();

        } catch (ParserConfigurationException e) {
            logger.error("Parser failed", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO exception occured", e);
            e.printStackTrace();
        } catch (SAXException e) {
            logger.error("SAX Exception", e);
            e.printStackTrace();
        }

        NodeList Records = doc.getElementsByTagName("record");
        List<Character> dataList = new ArrayList<Character>();

        for (int i = 0; i < Records.getLength(); i++)
        {
            dataList.add( new Character(Records.item(i)));
        }
        logger.debug("File imported successfully");
        readableData = new CharactersTable(dataList);
    }

    /**
     * Function for get DataTable for lab 8
     * @return - Data
     */
    public CharactersTable ReadData()
    {
        return readableData;
    }
}
