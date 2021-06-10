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
        if (ln == null || ln.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (ln.length != CharacterColumns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");

        if (line == null)
            line = new Object[5];

        line = ln;
    }

    public Character(String[] strs) throws IllegalArgumentException
    {
        if (strs == null || strs.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (strs.length != CharacterColumns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");

        if (line == null)
            line = new Object[5];


        line[CharacterColumns.ID.GetId()] = Integer.parseInt(strs[CharacterColumns.ID.GetId()]);
        line[CharacterColumns.Name.GetId()] = strs[CharacterColumns.Name.GetId()];
        line[CharacterColumns.Apperance.GetId()] = strs[CharacterColumns.Apperance.GetId()];
        line[CharacterColumns.Location.GetId()] = strs[CharacterColumns.Location.GetId()];
        line[CharacterColumns.MeetingStatus.GetId()] = strs[CharacterColumns.MeetingStatus.GetId()];

    }


    public Character(int ID, String Name, String Apperance, String Location, String MeetingStatus)
    {
        if (line == null)
            line = new Object[5];
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
        value[CharacterColumns.MeetingStatus.GetId()] = attrs.getNamedItem(CharacterColumns.values()[CharacterColumns.MeetingStatus.GetId()].toString()).getNodeValue();


        line = value;
    }


    String GetApperance()
    {
        return line[CharacterColumns.Apperance.GetId()].toString();
    }

    String GetLocation()
    {
        return line[CharacterColumns.Location.GetId()].toString();
    }

    String GetMeetingStatus()
    {
        return (String) line[CharacterColumns.MeetingStatus.GetId()];
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

}
