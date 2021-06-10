package Import;


import Source.*;

import Source.Character;
import Source.CharactersTable;
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
            DocumentBuilder documentBuilder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            doc = documentBuilder.parse( new File(fullname));
            doc.getDocumentElement().normalize();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        if (form.GetModeProgram() == ProgramMode.Characters) {

            NodeList Records = doc.getElementsByTagName("character");

            List<Character> dataList = new ArrayList<>();

            for (int i = 0; i < Records.getLength(); i++) {
                dataList.add(new Character(Records.item(i)));
            }
            form.ApplyDataCharacter(new CharactersTable(dataList));

        } else if (form.GetModeProgram() == ProgramMode.Tasks)
        {
            NodeList Records = doc.getElementsByTagName("Task");

            List<Task> dataList = new ArrayList<>();

            for (int i = 0; i < Records.getLength(); i++) {
                dataList.add(new Task(Records.item(i)));
            }
            form.ApplyDataTask(new TaskTable(dataList));
        }
    }



}
