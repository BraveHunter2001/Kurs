package Export;

import Source.*;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class for export in html report
 * @author Ilya
 */
public class HTMLExporter extends JFrame {
    /**
     * Constructor create html report from data
     * @param data - reported
     */
    public HTMLExporter(DataTable data)
    {
        FileDialog fd = new FileDialog(this, "Export HTML", FileDialog.SAVE);
        fd.setFile("*.html");
        fd.setVisible(true);
        String fullnamefile = fd.getDirectory() + fd.getFile();

        if (fullnamefile != null && fullnamefile != "nullnull") {
            PrintWriter pw = null;
            try
            {
                pw = new PrintWriter(new FileWriter(fullnamefile));
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            pw.println("<html><head><meta charset=\"utf-8\"> <title>Report charaters</title>");
            pw.println("<style type=\"text/css\">table { width: 70%; border: 4px solid black;border-collapse: collapse;text-align: center;margin: auto;}th { background: #ccc;padding: 5px;border: 1px solid black; }td { padding: 5px; border: 1px solid black;}</style></head>");
            pw.println("<body><table>");
            pw.println("<tr>");
            for (int i = 0; i < Columns.values().length; i++)
            {
                pw.println("<th>"+ Columns.values()[i].toString()+"</th>");
            }
            pw.println("</tr>");

            for (int i = 0; i < data.Rows().size(); i++)
            {
                pw.println(data.Rows().get(i).GetHTMLTable());
            }
            pw.println(" </table>" + "</body>" +"</html>");
            pw.close();
        }
    }
}
