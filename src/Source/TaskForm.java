package Source;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TaskForm extends JFrame {

    private JLabel NameLabel;
    private JLabel TaskStatusLabel;
    private JLabel CharacterLabel;

    private JTextField NameField;
    private JTextField CharacterField;

    private JComboBox<TaskStatus> TaskStatusParamBox;


    private JButton ButtonOk;
    public Task thisTask;
    CharactersTable CharacterTsk;


}
