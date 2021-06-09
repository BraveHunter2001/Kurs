package Source;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MeetingCellRender extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof MeetingStatus) {
            MeetingStatus status = (MeetingStatus) value;
            setText(status.toString());
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getSelectionForeground());
        }

        return this;
    }
}
