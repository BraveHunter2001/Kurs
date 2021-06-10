package Source;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface Line {

    void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException;

    public abstract int GetID();

    public abstract String GetName();

    public abstract Object[] GetData();

    boolean isEqual (int columnIndex, String value) throws IndexOutOfBoundsException;

    public abstract String GetHTMLTable();

    public abstract void ApplyDataToPdfTable(PdfPTable pdfPTable, Font font);

    public abstract Node ApplyDataToXML(Node nod, Document doc);
}
