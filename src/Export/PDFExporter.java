package Export;

import Source.*;

import java.awt.FileDialog;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFrame;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;


public class PDFExporter extends JFrame {
    public static final Logger logger = Logger.getLogger(PDFExporter.class);

    public PDFExporter(CharactersTable data) {
        FileDialog fileDialog = new FileDialog(this, "Export PDF", FileDialog.SAVE);
        fileDialog.setFile("*.pdf");
        fileDialog.setVisible(true);

        String fullnamefile = fileDialog.getDirectory() + fileDialog.getFile();

        if (fullnamefile != null && fullnamefile != "nullnull") {
            Document document = new Document(PageSize.A4, 5, 5, 50, 50);
            PdfPTable pdfTable = new PdfPTable(Columns.values().length);

            try{
                PdfWriter.getInstance(document, new FileOutputStream(fullnamefile));

            } catch (FileNotFoundException e) {
                logger.error("File not found", e);
                e.printStackTrace();
            } catch (DocumentException e) {
                logger.error("Document Exception", e);
                e.printStackTrace();
            }
            BaseFont bf = null;
            BaseFont bfBold = null;
            try
            {
                bf = BaseFont.createFont("./Fonts/PTSans-Regular.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                bfBold = BaseFont.createFont("./Fonts/PTSans-Bold.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }

            Font font = new Font(bf, 11);
            Font fontBold = new Font(bfBold, 11);

            for (int i = 0; i < Columns.values().length; i++)
            {
                pdfTable.addCell(new PdfPCell(new Phrase(Columns.values()[i].toString(),fontBold)));
            }

            for (int i =0; i < data.Rows().size(); i++)
            {
                data.Rows().get(i).ApplyDataToPdfTable(pdfTable, font);
            }

            document.open();
            try {
                document.add(pdfTable);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            logger.debug("PDF exported successfully");
            document.close();
        }
    }
}

