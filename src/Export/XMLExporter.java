package Export;


//import org.w3c.dom.Attr;
import Source.CharactersTable;
import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import Source.DataTable;
import Source.Line;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class export data to xml
 * @author Ilya
 */
public class XMLExporter extends JFrame
{
    /**
     * Constructor saving DataTable data
     * @param data
     * @throws ParserConfigurationException
     */
    public XMLExporter(DataTable data) throws ParserConfigurationException
    {
        FileDialog fileDialog = new FileDialog(this, "Export XML", FileDialog.SAVE);
        fileDialog.setFile("*.xml");
        fileDialog.setVisible(true);
        String fullname = fileDialog.getDirectory() + fileDialog.getFile();

        if (fullname != null && !fullname.equals("nullnull"))
        {
            Document document;
            try
            {
                document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            }catch (ParserConfigurationException e)
            {
                e.printStackTrace();
                throw e;
            }

            Node recList = document.createElement("dataList");
            document.appendChild(recList);
            for (int i = 0; i < data.Rows().size(); i++)
            {
                Line line = (Line)data.Rows().get(i);
                line.ApplyDataToXML(recList, document);
            }

            try
            {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                var fs = new FileOutputStream(fullname);
                transformer.transform(new DOMSource(document), new StreamResult(fs));
                fs.close();
            } catch (TransformerException | IOException e) {e.printStackTrace();}

        }
    }
}
