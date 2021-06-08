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
            line = new Object[6];




        line[CharacterColumns.ID.GetId()] = Integer.parseInt(strs[CharacterColumns.ID.GetId()]);
        line[CharacterColumns.Name.GetId()] = strs[CharacterColumns.Name.GetId()];
        line[CharacterColumns.Apperance.GetId()] = strs[CharacterColumns.Apperance.GetId()];
        line[CharacterColumns.Location.GetId()] = strs[CharacterColumns.Location.GetId()];
        line[CharacterColumns.MeetingStatus.GetId()] = MeetingStatus.getStatusByStr(strs[CharacterColumns.MeetingStatus.GetId()]);
        line[CharacterColumns.Tasks.GetId()] = Separate(strs[CharacterColumns.Tasks.GetId()]);
    }


    public Character(int ID, String Name, String Apperance, String Location, MeetingStatus MeetingStatus)
    {
        if (line == null)
            line = new Object[6];
        line[CharacterColumns.ID.GetId()] = ID;
        line[CharacterColumns.Name.GetId()] = Name;
        line[CharacterColumns.Apperance.GetId()] = Apperance;
        line[CharacterColumns.Location.GetId()] = Location;
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
        value[CharacterColumns.MeetingStatus.GetId()] = MeetingStatus.getStatusByStr(attrs.getNamedItem(CharacterColumns.values()[CharacterColumns.MeetingStatus.GetId()].toString()).getNodeValue());


        value[CharacterColumns.Tasks.GetId()] = null;
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

    List<Task> GetTasks() {return (List<Task>)line[CharacterColumns.Tasks.GetId()];}

    public void SetTasks(List<Task> tasks) {line[CharacterColumns.Tasks.GetId()] = tasks;}
    public String getTaskString(){
        String res ="";
        List<Task> tasks = (List<Task>)line[CharacterColumns.Tasks.GetId()];
        for (int i=0; i< tasks.size(); i++)
        {
            String temp = Integer.toString(tasks.get(i).GetID()) + ',' + tasks.get(i).GetName() + ',' +tasks.get(i).GetTaskStatus().toString();
            if(i != tasks.size() - 1)
                temp += ':';
            res += temp;
        }

        return res;
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
        Element rec = doc.createElement("Character");
        nod.appendChild(rec);
        for (int i = 0; i < CharacterColumns.values().length - 1; i++)
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

    private List<Task> Separate(String sep)
    {
        List<Task> res = new ArrayList<>();

        String[] splitted = sep.split(":");
        for(int i = 0; i < splitted.length; i++)
        {
                String[] temp = splitted[i].split(",");
                res.add(new Task(temp));
        }

        return res;
    }
}
