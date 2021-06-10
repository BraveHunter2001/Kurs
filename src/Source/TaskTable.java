package Source;

import javax.swing.table.DefaultTableModel;
import java.util.*;

public class TaskTable implements DataTable {
    public static Task DefaultTask = new Task(0, "Found 10 mashrooms", "Taken");

    public static List<Task> DefaultTasks = Arrays.asList(new Task[]{DefaultTask});

    List<Task> rows = new ArrayList<Task>();

    public TaskTable(DefaultTableModel model)
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

    public TaskTable(List<Task> lines)
    {
        rows = lines;
    }

    public TaskTable() { }

    public Object[] AddRow(Object[] values)
    {
        int newId = 0;

        for (Task row : rows) {
            int id = row.GetID();
            if (id > newId)
                newId = id;
        }
        newId++;
        Object[] newRow = new Object[TasksColumns.values().length];
        int len = values.length;
        System.arraycopy(values, 0, newRow,0, len);
        newRow[TasksColumns.ID.GetId()] = newId;
        rows.add(new Task(newRow));
        return newRow;
    }

    public void RemoveRow(int index, DefaultTableModel model) throws IndexOutOfBoundsException
    {
        if (index >= TasksColumns.values().length || index < 0 )
            throw  new IndexOutOfBoundsException(index);

        model.removeRow(index);
        rows.remove(index);
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

    private Task GetRowAt(int row, DefaultTableModel model)
    {
        Object[] result = new Object[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++)
        {
            result[i] = model.getValueAt(row, i);
        }

        return new Task(result);
    }

    private void ClearTable(DefaultTableModel model)
    {
        if(model != null)
        {
            while (model.getRowCount() > 0)
                model.removeRow(0);
        }
    }

    public TaskTable Search(int columnIndex, String value) throws IndexOutOfBoundsException
    {
        if (columnIndex >= TasksColumns.values().length|| columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);

        List<Task> res = new ArrayList<Task>();

        for (int i =0; i < rows.size(); i++)
        {
            if(rows.get(i).isEqual(columnIndex, value))
            {
                res.add(rows.get(i));
            }
        }

        return new TaskTable(res);
    }

    public void InsertDataInTableModel (DefaultTableModel model)
    {
        if (rows == null)
        {
            model.setDataVector(null, TasksColumns.values());
            return;
        }
        Object[][] array = new Object[rows.size()][];
        for(int i =0; i< rows.size(); i++)
        {
            array[i] = rows.get(i).GetData();
        }


        model.setDataVector(array, TasksColumns.values());
    }

    public TaskTable Sort(int columnIndex)
    {
        if(rows.size() > 0)
        {
            List<Task> res = new ArrayList<Task>(rows);

            Collections.sort(res, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
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

            return  new TaskTable(res);
        }
        return new TaskTable();
    }

    public List<Task> Rows(){
        return rows;
    }

    public TaskTable GetConnectionItemById(List<Integer> ids)
    {
        List<Task> res = new ArrayList<>();
        if (ids == null)
        {
            res = null;
            return new TaskTable(res);
        }

        for (int i = 0; i < rows.size(); i++)
        {
            for (int j =0; j < ids.size(); j++)
            {
                if (rows.get(i).GetID() == ids.get(j))
                    res.add(rows.get(i));
            }
        }

        return new TaskTable(res);
    }
}
