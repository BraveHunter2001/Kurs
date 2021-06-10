package Source;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.*;


public class CharactersTable implements DataTable {

    private static String[] DBcolumns = {"id", "name", "apperance", "location", "meetingstatus"};
    public static Character DefaultCharacter = new Character(0, "Tel", "Human", "Village", "Met");
    List<Character> rows = new ArrayList<Character>();

    public CharactersTable(DefaultTableModel model)
    {
        try {
            for (int i = 0; i < model.getRowCount(); i++) {
                rows.add(GetRowAt(i, model));
            }
        } catch ( IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    public CharactersTable(List<Character> lines)
    {
        rows = lines;
    }

    public CharactersTable() { }

    public Object[] AddRow(Object[] values)
    {
        int newId = 0;

        for (Character row : rows) {
            int id = row.GetID();
            if (id > newId)
                newId = id;
        }
        newId++;
        Object[] newRow = new Object[CharacterColumns.values().length];
        int len = values.length;
        System.arraycopy(values, 0, newRow,0, len);
        newRow[CharacterColumns.ID.GetId()] = newId;
        var ch = new Character(newRow);
        ch.InsertIntoDB();
        rows.add(ch);
        return newRow;
    }

    public void ChangeRow(int rowId, int column, Object newValue)
    {
        for(int i = 0; i < rows.size(); i++)
        {
            if (rows.get(i).GetID() == rowId)
            {
                rows.get(i).SetValue(column, newValue);
            }
        }
    }

    public void RemoveRow(int index, DefaultTableModel model) throws IndexOutOfBoundsException
    {
        if (index >= CharacterColumns.values().length || index < 0 )
            throw  new IndexOutOfBoundsException(index);

        model.removeRow(index);
        rows.get(index).DeleteFromDB();
        rows.remove(index);
    }

    public CharacterColumns[] GetColumns()
    {
        return CharacterColumns.values();
    }

    private Character GetRowAt(int row, DefaultTableModel model)
    {
        Object[] result = new Object[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++)
        {
            result[i] = model.getValueAt(row, i);
        }

        return new Character(result);
    }

    private void ClearTable(DefaultTableModel model)
    {
        try {
            Main.db.ExcecuteQuery("TRUNCATE characters CASCADE");
        } catch (DBFacade.DBNotConnectedException e) {
            // records were empty
            //e.printStackTrace();
        }
        if(model != null)
        {
            while (model.getRowCount() > 0)
                model.removeRow(0);
        }
    }

    public CharactersTable Search(int columnIndex, String value) throws IndexOutOfBoundsException
    {
        if (columnIndex >= CharacterColumns.values().length|| columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);

        List<Character> res = new ArrayList<Character>();

        for (int i =0; i < rows.size(); i++)
        {
            if(rows.get(i).isEqual(columnIndex, value))
            {
                res.add(rows.get(i));
            }
        }

        return new CharactersTable(res);
    }

    public void InsertDataInTableModel (DefaultTableModel model)
    {
        if (rows == null)
        {
            model.setDataVector(null, CharacterColumns.values());
            return;
        }

        Object[][] array = new Object[rows.size()][];
        for(int i =0; i< rows.size(); i++)
        {
            array[i] = rows.get(i).GetData();
        }

        model.setDataVector(array, CharacterColumns.values());
    }

    public CharactersTable Sort(int columnIndex)
    {
        if(rows.size() > 0)
        {
            List<Character> res = new ArrayList<Character>(rows);

            Collections.sort(res, new Comparator<Character>() {
                @Override
                public int compare(Character o1, Character o2) {
                    Object data1 = o1.GetData()[columnIndex], data2 = o2.GetData()[columnIndex];

                    if (data1 == null)
                        if (data2 == null)
                            return  0;
                        else
                            return -1;
                    else
                    if (data2 == null)
                        return 1;
                    else {
                        if (data1.getClass().getSimpleName().equals("Integer"))
                            return (int) data1 - (int) data2;
                        return data1.toString().compareTo(data2.toString());
                    }
                }
            });

            return  new CharactersTable(res);
        }
        return new CharactersTable();
    }

    public List<Character> Rows(){
        return rows;
    }

    @Override
    public Character GetDefault() {
        return DefaultCharacter;
    }

    public CharactersTable GetConnectionItemById(List<Integer> ids)
    {
        List<Character> res = new ArrayList<>();
        if (ids == null)
        {
            res = null;
            return new CharactersTable(res);
        }

        for (int i = 0; i < rows.size(); i++)
        {
            for (int j =0; j < ids.size(); j++)
            {
                if (rows.get(i).GetID() == ids.get(j))
                    res.add(rows.get(i));
            }
        }

        return new CharactersTable(res);
    }

    public static CharactersTable GetFromDB(){
        try {
            var rs = Main.db.ExcecuteQuery("SELECT "+String.join(", ", DBcolumns)+" FROM characters");
            ArrayList<Character> lns = new ArrayList<>();
            while(rs.next())
            {
                var vals = new Object[DBcolumns.length];
                for(int i = 0; i < DBcolumns.length; i++)
                    vals[i] = rs.getObject(i+1);
                var ln = new Character(vals);
                lns.add(ln);
            }
            return new CharactersTable(lns);
        } catch (DBFacade.DBNotConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void SaveDB() {
        try {
            Main.db.ExcecuteQuery("TRUNCATE characters CASCADE");
        } catch (DBFacade.DBNotConnectedException e) {
            // records were empty
            //e.printStackTrace();
        }
        for(var ln : rows)
            ln.InsertIntoDB();
    }
}
