package Source;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface DataTable {

    public abstract Object[] AddRow(Object[] values);

    public abstract void RemoveRow(int index) throws IndexOutOfBoundsException;

    public abstract void ChangeRow(int rowId, int column, Object newValue);

    private Line GetRowAt(int row, DefaultTableModel model){return null;};

    public abstract DataTable Search(int columnIndex, String value) throws IndexOutOfBoundsException;

    public abstract DataTable Sort(int columnIndex);

    public abstract List Rows();

    public abstract Object[] GetColumns();

    public abstract Line GetDefault();

    default void ClearTable(DefaultTableModel model) { }

    public abstract void InsertDataInTableModel (DefaultTableModel model);

    public abstract DataTable GetConnectionItemById(List<Integer> ids);

    //public abstract DataTable GetFromDB();
    public abstract void SaveDB();
}
