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

public class Task implements Line{
    private static String[] DBcolumns = {"id", "name", "taskstatus"};

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

    private void CorrectDataTypes()
    {
        for(var dt : line)
            if(dt.toString().equals("") || dt.toString().length() > 50)
                throw new IllegalArgumentException();
        line[TasksColumns.ID.GetId()] = Integer.parseInt(line[0].toString());
        line[TasksColumns.Name.GetId()] = line[TasksColumns.Name.GetId()].toString();
        line[TasksColumns.TaskStatus.GetId()] = line[TasksColumns.TaskStatus.GetId()].toString();

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

    private void UpdateIntoDB()
    {
        try {
            String query = "UPDATE tasks SET ";
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
            query += "WHERE tasks.id = " + line[0].toString()+ ";";
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
            var lineQuery = Main.db.InsertQuery("insert into tasks("
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
            String query = "delete from tasks where id = " + line[TasksColumns.ID.GetId()].toString();
            Main.db.GetStatement().executeUpdate(query);
        } catch (DBFacade.DBNotConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException {
        if (columnIndex >= TasksColumns.values().length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);
        line[columnIndex] = value;
        CorrectDataTypes();
        UpdateIntoDB();
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
            String dat  = (line[i] == null ? "": line[i].toString());
            pdfPTable.addCell(new Phrase(dat, font));
        }
    }


}
