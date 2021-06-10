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
    public TXTExporter(String str, DataTable data)
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
                    Line line = (Line)data.Rows().get(i);
                    var lineData = line.GetData();

                    for(int j = 0; j < lineData.length; j++)
                    {
                        writer.write(lineData[j].toString());
                        if(j != lineData.length -1 )
                            writer.write("|");
                    }


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
