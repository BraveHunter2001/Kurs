package Export;

import Source.*;
import Source.CharactersTable;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;


/**
 * Class save data in txt file
 * @author Ilya
 */
public class TXTExporter extends  JFrame
{
    public TXTExporter(String str, CharactersTable data)
    {
        FileDialog fileDialog = new FileDialog(this, str, FileDialog.SAVE);
        fileDialog.setFile("*.txt");
        fileDialog.setVisible(true);

        String fileFullName = fileDialog.getDirectory() + fileDialog.getFile();

        if (fileFullName != null)
        {
            try{
                BufferedWriter writer = new BufferedWriter( new FileWriter(fileFullName));
                for (int i = 0; i < data.Rows().size(); i++)
                {
                    var line = data.Rows().get(i).GetData();
                    for(int j = 0; j < line.length-1; j++)
                    {
                        writer.write(line[j].toString());
                        writer.write("|");
                    }
                    writer.write( data.Rows().get(i).getTaskString());

                    writer.write("\r\n");
                }
                writer.close();

            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
