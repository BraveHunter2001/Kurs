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

public class Task extends Line{


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


}
