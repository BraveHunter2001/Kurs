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

    List<Character> characters;
    public Task() { super(); }

    public Task(Object[] ln) throws IllegalArgumentException
    {
        if (ln == null || ln.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (ln.length != TasksColumns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");
        line = ln;
        if ((List<Character>)ln[TasksColumns.Characters.GetId()] != null)
            characters = (List<Character>)ln[TasksColumns.Characters.GetId()];
    }

    public Task(int ID, String Name, TaskStatus TaskStatus)
    {
        if (line == null)
            line = new Object[4];
        if (characters == null)
            characters = new ArrayList<>();
        line[TasksColumns.ID.GetId()] = ID;
        line[TasksColumns.Name.GetId()] = Name;
        line[TasksColumns.TaskStatus.GetId()] = TaskStatus;
        line[TasksColumns.Characters.GetId()] = characters;
    }

    public Task(String[] strs) throws IllegalArgumentException
    {
        if (strs == null || strs.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (strs.length != TasksColumns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");

        if (line == null)
            line = new Object[4];

        line[TasksColumns.ID.GetId()] = Integer.parseInt(strs[TasksColumns.ID.GetId()]);
        line[TasksColumns.Name.GetId()] = strs[TasksColumns.Name.GetId()];
        line[TasksColumns.TaskStatus.GetId()] = TaskStatus.getStatusByStr(strs[TasksColumns.TaskStatus.GetId()]);
        line[TasksColumns.Characters.GetId()] = characters;
    }


    public Task(Node nod)
    {
        Object[] value = new Object[TasksColumns.values().length];
        NamedNodeMap attrs = nod.getAttributes();

        for (int i = 0; i < TasksColumns.values().length - 2; i++)
        {
            value[i] = attrs.getNamedItem(TasksColumns.values()[i].toString()).getNodeValue();
        }

        value[TasksColumns.TaskStatus.GetId()] = TaskStatus.getStatusByStr(attrs.getNamedItem(TasksColumns.values()[TasksColumns.TaskStatus.GetId()].toString()).getNodeValue());
        line = value;
    }

    void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException
    {
        if (columnIndex >= TasksColumns.values().length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);

            line[columnIndex] = value;
    }

    public int GetID()
    {
        return Integer.parseInt(line[0].toString());
    }

    String GetName()
    {
        return line[TasksColumns.Name.GetId()].toString();
    }

    TaskStatus GetTaskStatus()
    {
        return (TaskStatus) line[TasksColumns.TaskStatus.GetId()];
    }

    List<Character> GetCharaters()
    {
        return characters;
    }

    public void SetCharacters(List<Character> chs)
    {

        characters = chs;
        line[TasksColumns.Characters.GetId()] = characters;
    }

    public void addCharacter(Character ch)
    {
        for (int i = 0; i < characters.size(); i++)
        {
            int idThis = characters.get(i).GetID();
            int idCh = ch.GetID();
            if (idThis == idCh)
                return;
        }
        characters.add(ch);
       // ch.AddTask(this);
    }
    public void removeCharacter(Character ch) {
        for (int i = 0; i < characters.size(); i++)
        {
            if (ch.GetID() == characters.get(i).GetID())
            {
                characters.remove(i);
               // ch.RemoveTask(this);
            }
        }


    }
    public void RemoveAllCharacter()
    {
        for (int i =0; i < characters.size(); i++)
            removeCharacter(characters.get(i));
    }

    public String getCharactersString(){
        String res ="";

        if (characters == null)
            return res;
        for (int i=0; i< characters.size(); i++)
        {
            String temp = Integer.toString(characters.get(i).GetID()) + ','
                    + characters.get(i).GetName() + ','
                    + characters.get(i).GetApperance() + ','
                    + characters.get(i).GetLocation() + ','
                    + characters.get(i).GetLocation() + ',';
            if(i != characters.size() - 1)
                temp += ':';
            res += temp;
        }

        return res;
    }

    boolean isEqual (int columnIndex, String value) throws IndexOutOfBoundsException
    {

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
