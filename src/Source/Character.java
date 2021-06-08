package Source;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class Character extends Line{


    public Character() { super(); }

    public Character(Object[] ln) throws IllegalArgumentException
    {
        super(ln);
    }

    public Character(String[] strs) throws IllegalArgumentException
    {
        if (strs == null || strs.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (strs.length != Columns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");

        if (line == null)
            line = new Object[7];


        line[Columns.ID.GetId()] = Integer.parseInt(strs[Columns.ID.GetId()]);
        line[Columns.Name.GetId()] = strs[Columns.Name.GetId()];
        line[Columns.Apperance.GetId()] = strs[Columns.Apperance.GetId()];
        line[Columns.Location.GetId()] = strs[Columns.Location.GetId()];
        line[Columns.Task.GetId()] = strs[Columns.Task.GetId()];
        line[Columns.TaskStatus.GetId()] = TaskStatus.getStatusByStr(strs[Columns.TaskStatus.GetId()]);
        line[Columns.MeetingStatus.GetId()] = MeetingStatus.getStatusByStr(strs[Columns.MeetingStatus.GetId()]);
    }


    public Character(int ID, String Name, String Apperance, String Location, String Task, TaskStatus TaskStatus, MeetingStatus MeetingStatus)
    {
        if (line == null)
            line = new Object[7];
        line[Columns.ID.GetId()] = ID;
        line[Columns.Name.GetId()] = Name;
        line[Columns.Apperance.GetId()] = Apperance;
        line[Columns.Location.GetId()] = Location;
        line[Columns.Task.GetId()] = Task;
        line[Columns.TaskStatus.GetId()] = TaskStatus;
        line[Columns.MeetingStatus.GetId()] = MeetingStatus;
    }

    public Character(Node nod)
    {
        Object[] value = new Object[Columns.values().length];
        NamedNodeMap attrs = nod.getAttributes();

        for (int i = 0; i < Columns.values().length - 2; i++)
        {
            value[i] = attrs.getNamedItem(Columns.values()[i].toString()).getNodeValue();
        }

        value[Columns.TaskStatus.GetId()] = TaskStatus.getStatusByStr(attrs.getNamedItem(Columns.values()[Columns.TaskStatus.GetId()].toString()).getNodeValue());
        value[Columns.MeetingStatus.GetId()] = MeetingStatus.getStatusByStr(attrs.getNamedItem(Columns.values()[Columns.MeetingStatus.GetId()].toString()).getNodeValue());
        line = value;
    }

    void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException
    {
        if (columnIndex >= Columns.values().length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);


        line[columnIndex] = value;
    }


    String GetName()
    {
        return line[Columns.Name.GetId()].toString();
    }

    String GetApperance()
    {
        return line[Columns.Apperance.GetId()].toString();
    }

    String GetLocation()
    {
        return line[Columns.Location.GetId()].toString();
    }

    String GetTask()
    {
        return  line[Columns.Task.GetId()].toString();
    }

    TaskStatus GetTaskStatus()
    {
        return (TaskStatus) line[Columns.TaskStatus.GetId()];
    }

    MeetingStatus GetMeetingStatus()
    {
        return (MeetingStatus) line[Columns.MeetingStatus.GetId()];
    }

    boolean isEqual (int columnIndex, String value) throws IndexOutOfBoundsException
    {

        if (columnIndex >= Columns.values().length || columnIndex < 0)
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

    public Node ApplyDataToXML(Node nod, Document doc)
    {
        Element rec = doc.createElement("record");
        nod.appendChild(rec);
        for (int i =0; i < Columns.values().length; i++)
        {
            String dat = (line[i]==null?"": line[i].toString());
            rec.setAttribute(Columns.values()[i].toString(),dat);
        }
        return nod;
    }

    public void ApplyDataToPdfTable(PdfPTable pdfPTable, Font font)
    {
        for(int i = 0; i < line.length; i++)
        {
            String dat  = (line[i] == null ?"": line[i].toString());
            pdfPTable.addCell(new Phrase(dat, font));
        }
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
}
