package Import;

import Source.*;
import Source.Character;
import Source.CharactersTable;

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
    public TXTImporter(String str, MainForm form)
    {
        try {
            FileDialog open = new FileDialog(this, str, FileDialog.LOAD);
            open.setFile("*.txt");
            open.setVisible(true);

            fileNameOpen = open.getDirectory() + open.getFile();

            if (fileNameOpen == null || fileNameOpen.equals("nullnull"))
                return;

            BufferedReader reader = new BufferedReader(new FileReader(fileNameOpen));

            if (form.GetModeProgram() == ProgramMode.Characters) {
                List<Character> characters = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] splitted = line.split(separator);
                    if (splitted.length == CharacterColumns.values().length)
                        characters.add(new Character(splitted));
                }
                form.GetCharactersData().ClearTable(form.GetCharacterModel());
                form.SetCharacterData(new CharactersTable(characters));
                form.GetCharactersData().SaveDB();
                form.GetCharactersData().InsertDataInTableModel(form.GetCharacterModel());

            }else if (form.GetModeProgram() == ProgramMode.Tasks)
            {
                List<Task> tasks = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] splitted = line.split(separator);
                    if (splitted.length == TasksColumns.values().length)
                        tasks.add(new Task(splitted));
                }
                form.GetTaskData().ClearTable(form.GetTaskModel());
                form.SetTaskData(new TaskTable(tasks));
                form.GetTaskData().SaveDB();
                form.GetTaskData().InsertDataInTableModel(form.GetTaskModel());
            }

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

