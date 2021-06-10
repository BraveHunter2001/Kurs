package Source;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Task implements Line{


    Object[] line;
    public Task() { super(); }

    public Task(Object[] ln) throws IllegalArgumentException
    {
        if (ln == null || ln.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (ln.length != TasksColumns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");

        if (line == null)
            line = new Object[3];

        line = ln;
    }

    public Task(int ID, String Name, String TaskStatus)
    {
        if (line == null)
            line = new Object[3];

        line[TasksColumns.ID.GetId()] = ID;
        line[TasksColumns.Name.GetId()] = Name;
        line[TasksColumns.TaskStatus.GetId()] = TaskStatus;

    }

    public Task(String[] strs) throws IllegalArgumentException
    {
        if (strs == null || strs.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (strs.length != TasksColumns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");

        if (line == null)
            line = new Object[3];

        line[TasksColumns.ID.GetId()] = Integer.parseInt(strs[TasksColumns.ID.GetId()]);
        line[TasksColumns.Name.GetId()] = strs[TasksColumns.Name.GetId()];
        line[TasksColumns.TaskStatus.GetId()] = strs[TasksColumns.TaskStatus.GetId()];

    }

    public Task(Node nod)
    {
        Object[] value = new Object[TasksColumns.values().length];
        NamedNodeMap attrs = nod.getAttributes();

        for (int i = 0; i < TasksColumns.values().length; i++)
        {
            if (i == TasksColumns.ID.GetId())
                value[i] = Integer.parseInt(attrs.getNamedItem(TasksColumns.values()[i].toString()).getNodeValue());
            else
                value[i] = attrs.getNamedItem(TasksColumns.values()[i].toString()).getNodeValue();
        }

        line = value;
    }

    String GetTaskStatus()
    {
        return (String) line[TasksColumns.TaskStatus.GetId()];
    }

    public Node ApplyDataToXML(Node nod, Document doc)
    {
        Element rec = doc.createElement("Task");
        nod.appendChild(rec);
        for (int i = 0; i < TasksColumns.values().length; i++)
        {
            String dat = (line[i]==null?"": line[i].toString());
            rec.setAttribute(TasksColumns.values()[i].toString(),dat);
        }
        return nod;
    }

    @Override
    public void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException {
        if (columnIndex >= TasksColumns.values().length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);

        line[columnIndex] = value;
    }

    @Override
    public int GetID() {
        return Integer.parseInt(line[TasksColumns.ID.GetId()].toString());
    }

    @Override
    public String GetName() {
        return line[TasksColumns.Name.GetId()].toString();
    }

    @Override
    public Object[] GetData() {
        return line;
    }

    @Override
    public boolean isEqual(int columnIndex, String value) throws IndexOutOfBoundsException {
        if (columnIndex >= TasksColumns.values().length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);

        if(line[columnIndex] == null && value == null)
            return true;
        {
            if(line[columnIndex] == null && value != null || line[columnIndex] != null && value == null)
                return false;
            if (line[columnIndex].toString().equals(value))
                return true;
        }
        return false;
    }

    public String GetHTMLTable()
    {
        String res = "<tr>";
        for (int i =0 ; i < line.length; i++)
        {
            String dat  = (line[i] == null ?"": line[i].toString());
            res+= "<td>" + dat +"</td>";
        }
        res += "</tr>";
        return res;
    }

    @Override
    public void ApplyDataToPdfTable(PdfPTable pdfPTable, Font font) {
        for(int i = 0; i < TasksColumns.values().length; i++)
        {
            String dat  = (line[i] == null ?"": line[i].toString());
            pdfPTable.addCell(new Phrase(dat, font));
        }
    }


}
