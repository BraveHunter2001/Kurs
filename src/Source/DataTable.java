package Source;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class DataTable {

    List<Line> rows = new ArrayList<Line>();

    public DataTable() { }

    public DataTable(DefaultTableModel model) { }

    public DataTable(List<Line> lines)
    {
        rows = lines;
    }


    public void DataTable(int index, DefaultTableModel model) throws IndexOutOfBoundsException {
    }

    public void DataTable(int rowId, int column, Object newValue) {
    }

    private void ClearDataTable(DefaultTableModel model)
    {
        if(model != null)
        {
            while (model.getRowCount() > 0)
                model.removeRow(0);
        }
    }

    public void InsertDataInTableModel (DefaultTableModel model)
    {
        Object[][] array = new Object[rows.size()][];
        for(int i =0; i< rows.size(); i++)
        {
            array[i] = rows.get(i).GetData();
        }


        model.setDataVector(array, Columns.values());
    }

    public List<Line> Rows(){
        return rows;
    }
}
