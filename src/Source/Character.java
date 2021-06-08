package Source;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class Character extends Line {


    public Character() { super(); }

    public Character(Object[] ln) throws IllegalArgumentException
    {
        super(ln);
    }

    public Character(String[] strs) throws IllegalArgumentException
    {
        if (strs == null || strs.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (strs.length != CharacterColumns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");

        if (line == null)
            line = new Object[7];


        line[CharacterColumns.ID.GetId()] = Integer.parseInt(strs[CharacterColumns.ID.GetId()]);
        line[CharacterColumns.Name.GetId()] = strs[CharacterColumns.Name.GetId()];
        line[CharacterColumns.Apperance.GetId()] = strs[CharacterColumns.Apperance.GetId()];
        line[CharacterColumns.Location.GetId()] = strs[CharacterColumns.Location.GetId()];
        line[CharacterColumns.Task.GetId()] = strs[CharacterColumns.Task.GetId()];
        line[CharacterColumns.TaskStatus.GetId()] = TaskStatus.getStatusByStr(strs[CharacterColumns.TaskStatus.GetId()]);
        line[CharacterColumns.MeetingStatus.GetId()] = MeetingStatus.getStatusByStr(strs[CharacterColumns.MeetingStatus.GetId()]);
    }


    public Character(int ID, String Name, String Apperance, String Location, String Task, TaskStatus TaskStatus, MeetingStatus MeetingStatus)
    {
        if (line == null)
            line = new Object[7];
        line[CharacterColumns.ID.GetId()] = ID;
        line[CharacterColumns.Name.GetId()] = Name;
        line[CharacterColumns.Apperance.GetId()] = Apperance;
        line[CharacterColumns.Location.GetId()] = Location;
        line[CharacterColumns.Task.GetId()] = Task;
        line[CharacterColumns.TaskStatus.GetId()] = TaskStatus;
        line[CharacterColumns.MeetingStatus.GetId()] = MeetingStatus;
    }

    public Character(Node nod)
    {
        Object[] value = new Object[CharacterColumns.values().length];
        NamedNodeMap attrs = nod.getAttributes();

        for (int i = 0; i < CharacterColumns.values().length - 2; i++)
        {
            value[i] = attrs.getNamedItem(CharacterColumns.values()[i].toString()).getNodeValue();
        }

        value[CharacterColumns.TaskStatus.GetId()] = TaskStatus.getStatusByStr(attrs.getNamedItem(CharacterColumns.values()[CharacterColumns.TaskStatus.GetId()].toString()).getNodeValue());
        value[CharacterColumns.MeetingStatus.GetId()] = MeetingStatus.getStatusByStr(attrs.getNamedItem(CharacterColumns.values()[CharacterColumns.MeetingStatus.GetId()].toString()).getNodeValue());
        line = value;
    }

    void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException
    {
        if (columnIndex >= CharacterColumns.values().length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);


        line[columnIndex] = value;
    }


    String GetName()
    {
        return line[CharacterColumns.Name.GetId()].toString();
    }

    String GetApperance()
    {
        return line[CharacterColumns.Apperance.GetId()].toString();
    }

    String GetLocation()
    {
        return line[CharacterColumns.Location.GetId()].toString();
    }

    String GetTask()
    {
        return  line[CharacterColumns.Task.GetId()].toString();
    }

    TaskStatus GetTaskStatus()
    {
        return (TaskStatus) line[CharacterColumns.TaskStatus.GetId()];
    }

    MeetingStatus GetMeetingStatus()
    {
        return (MeetingStatus) line[CharacterColumns.MeetingStatus.GetId()];
    }

    boolean isEqual (int columnIndex, String value) throws IndexOutOfBoundsException
    {

        if (columnIndex >= CharacterColumns.values().length || columnIndex < 0)
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
        for (int i = 0; i < CharacterColumns.values().length; i++)
        {
            String dat = (line[i]==null?"": line[i].toString());
            rec.setAttribute(CharacterColumns.values()[i].toString(),dat);
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
