package Import;

import Source.*;
import Source.Character;

import java.awt.FileDialog;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Class load data form txt file
 * @author Ilya
 */
public class TXTImporter extends JFrame{
    static String fileNameOpen = null;
    static String separator = "\\|";
    public TXTImporter(String str, MainForm form, DefaultTableModel model)
    {
        try {
            FileDialog open = new FileDialog(this, str, FileDialog.LOAD);
            open.setFile("*.txt");
            open.setVisible(true);

            fileNameOpen = open.getDirectory() + open.getFile();

            if (fileNameOpen == null || fileNameOpen.equals("nullnull"))
                return;

            BufferedReader reader = new BufferedReader(new FileReader(fileNameOpen));

            List<Character> charcters = new ArrayList<Character>();
            String line;

            while ((line = reader.readLine())!= null)
            {
                String[] splitted = line.split(separator);
                if (splitted.length == Columns.values().length)
                    charcters.add(new Character(splitted));
            }
            form.SetData(new DataTable(charcters));
            form.GetData().InsertDataInTableModel(model);
            reader.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

