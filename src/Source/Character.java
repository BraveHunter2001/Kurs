package Source;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Character implements Line {
    Object[] line;
    private static String[] DBcolumns = {"id", "name", "apperance", "location", "meetingstatus"};


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

        for (int i = 0; i < CharacterColumns.values().length ; i++)
        {
            if (i == CharacterColumns.ID.GetId())
                value[i] = Integer.parseInt(attrs.getNamedItem(CharacterColumns.values()[i].toString()).getNodeValue());
            else
                value[i] = attrs.getNamedItem(CharacterColumns.values()[i].toString()).getNodeValue();
        }

        line = value;
    }
    private void CorrectDataTypes()
    {
        for(var dt : line)
            if(dt.toString().equals("") || dt.toString().length() > 50)
                throw new IllegalArgumentException();
        line[CharacterColumns.ID.GetId()] = Integer.parseInt(line[0].toString());
        line[CharacterColumns.Name.GetId()] = line[CharacterColumns.Name.GetId()].toString();
        line[CharacterColumns.Apperance.GetId()] = line[CharacterColumns.Apperance.GetId()].toString();
        line[CharacterColumns.Location.GetId()] = line[CharacterColumns.Location.GetId()].toString();
        line[CharacterColumns.MeetingStatus.GetId()] = line[CharacterColumns.MeetingStatus.GetId()].toString();
    }

    @Override
    public void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException {
        if (columnIndex >= CharacterColumns.values().length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);

        line[columnIndex] = value;
        CorrectDataTypes();
        UpdateIntoDB();
    }

    @Override
    public int GetID() {
        return Integer.parseInt(line[CharacterColumns.ID.GetId()].toString());
    }

    @Override
    public String GetName() {
        return line[CharacterColumns.Name.GetId()].toString();
    }

    @Override
    public Object[] GetData() {
        return line;
    }

    @Override
    public boolean isEqual(int columnIndex, String value) throws IndexOutOfBoundsException {
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
        for(int i = 0; i < CharacterColumns.values().length; i++)
        {
            String dat  = (line[i] == null ?"": line[i].toString());
            pdfPTable.addCell(new Phrase(dat, font));
        }
    }

    public Node ApplyDataToXML(Node nod, Document doc)
    {
        Element rec = doc.createElement("character");
        nod.appendChild(rec);
        for (int i = 0; i < CharacterColumns.values().length; i++)
        {
            String dat = (line[i]==null?"": line[i].toString());
            rec.setAttribute(CharacterColumns.values()[i].toString(),dat);
        }

        return nod;
    }

    private void UpdateIntoDB()
    {
        try {
            String query = "UPDATE characters SET ";
            for(int i =0; i < DBcolumns.length; i++)
            {
                query+=DBcolumns[i]+"=";
                if(line[i] instanceof String)
                    query += "\'" + line[i].toString() + "\'";
                else
                    query += line[i].toString();
                if(i != DBcolumns.length - 1)
                    query+=", ";
            }
            query += "WHERE characters.id = " + line[0].toString()+ ";";
            Main.db.GetStatement().execute(query);
        } catch (DBFacade.DBNotConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void InsertIntoDB() {
        try {
            var questionsLine = new String(new char[DBcolumns.length]).replace("\0", "?,");
            var lineQuery = Main.db.InsertQuery("insert into characters("
                    + String.join(", ", DBcolumns)
                    + ") values("
                    + questionsLine.substring(0, questionsLine.length() - 1)
                    + ")", line);
            lineQuery.execute();
        } catch (DBFacade.DBNotConnectedException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteFromDB() {
        try {
            String query = "delete from characters where id = " + line[CharacterColumns.ID.GetId()].toString();
            Main.db.GetStatement().executeUpdate(query);
        } catch (DBFacade.DBNotConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
