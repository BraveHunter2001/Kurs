package Source;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Class menu create or change charter data
 * @author Ilya
 */
public class CharacterForm extends JFrame
{

    private JLabel NameLabel;
    private JLabel ApperanceLabel;
    private JLabel LocationLabel;
    private JLabel TaskLabel;
    private JLabel MeetingStatusLabel;

    private JTextField NameField;
    private JTextField ApperanceField;
    private JTextField LocationField;
    private JTextField TaskField;

    private JComboBox<MeetingStatus> MeetingStatusParamBox;

    private JButton ButtonOk;
    public Character ChangedCharacter;

    /**
     * Constructer for add character
     * @param title
     * @param model
     * @param data
     */
    public CharacterForm(String title, DefaultTableModel model, CharactersTable data)
    {
        //init
        super(title);
        setSize(500, 200);
        setLocation(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ChangedCharacter = CharactersTable.DefaultCharacter;
        //Name
        NameLabel = new JLabel("Name:");
        NameField = new JTextField();
        NameField.setText(ChangedCharacter.GetName());

        ApperanceLabel = new JLabel("Appearance:");
        ApperanceField = new JTextField();
        ApperanceField.setText(ChangedCharacter.GetApperance());

        LocationLabel = new JLabel("Location:");
        LocationField = new JTextField();
        LocationField.setText(ChangedCharacter.GetLocation());

        TaskLabel = new JLabel("Tasks");
        TaskField = new JTextField();
        TaskField.setText(ChangedCharacter.getTaskString());

        MeetingStatusLabel = new JLabel("MeetingStatus:");
        MeetingStatusParamBox = new JComboBox<MeetingStatus>(MeetingStatus.values());
        MeetingStatusParamBox.setSelectedItem((MeetingStatus)ChangedCharacter.GetMeetingStatus());
        ButtonOk = new JButton("Ok");

        Container panel = this.getContentPane();
        panel.setLayout(new GridLayout(6, 1, 5, 10));

        panel.add(NameLabel);
        panel.add(NameField);

        panel.add(ApperanceLabel);
        panel.add(ApperanceField);

        panel.add(LocationLabel);
        panel.add(LocationField);

        panel.add(MeetingStatusLabel);
        panel.add(MeetingStatusParamBox);

        panel.add(TaskLabel);
        panel.add(TaskField);


        panel.add(ButtonOk);

        setContentPane(panel);

        TaskField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getClickCount() == 2)
                {
                    JOptionPane.showMessageDialog(null, "Test");
                }
            }
        });

        ButtonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add(model, data);
                dispose();

            }
        });

        setVisible(true);
    }

    /**
     * Constructor for change data row
     * @param title
     * @param model
     * @param data
     * @param row
     */
    public CharacterForm(String title, DefaultTableModel model, CharactersTable data, int row)
    {
        //init
        super(title);
        setSize(500, 200);
        setLocation(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ChangedCharacter = data.GetRowAt(row, model);

        //Name
        NameLabel = new JLabel("Name:");
        NameField = new JTextField();
        NameField.setText(ChangedCharacter.GetName());

        ApperanceLabel = new JLabel("Appearance:");
        ApperanceField = new JTextField();
        ApperanceField.setText(ChangedCharacter.GetApperance());

        LocationLabel = new JLabel("Location:");
        LocationField = new JTextField();
        LocationField.setText(ChangedCharacter.GetLocation());

       // TaskLabel = new JLabel("Task:");
        //TaskField = new JTextField();
        //TaskField.setText(ChangedCharacter.GetTask());

        //TaskStatusLabel = new JLabel("TaskStatus:");
       // TaskStatusParamBox = new JComboBox<TaskStatus>(TaskStatus.values());
       // TaskStatusParamBox.setSelectedItem((TaskStatus)ChangedCharacter.GetTaskStatus());

        MeetingStatusLabel = new JLabel("TaskStatus:");
        MeetingStatusParamBox = new JComboBox<MeetingStatus>(MeetingStatus.values());
        MeetingStatusParamBox.setSelectedItem((MeetingStatus)ChangedCharacter.GetMeetingStatus());
        ButtonOk = new JButton("Ok");

        Container panel = this.getContentPane();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        panel.add(NameLabel);
        panel.add(NameField);

        panel.add(ApperanceLabel);
        panel.add(ApperanceField);

        panel.add(LocationLabel);
        panel.add(LocationField);

        panel.add(TaskLabel);
        panel.add(TaskField);

       // panel.add(TaskStatusLabel);
       // panel.add(TaskStatusParamBox);

        panel.add(MeetingStatusLabel);
        panel.add(MeetingStatusParamBox);

        panel.add(ButtonOk);

        setContentPane(panel);

        ButtonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Change(model, data, row);
                dispose();

            }
        });

        setVisible(true);
    }

    /**
     * Add charter in model
     * @param model
     * @param data
     */
    void Add(DefaultTableModel model, CharactersTable data)
    {
        ChangedCharacter.line[CharacterColumns.Name.GetId()] = NameField.getText();
        ChangedCharacter.line[CharacterColumns.Location.GetId()] = LocationField.getText();
        ChangedCharacter.line[CharacterColumns.Apperance.GetId()] = ApperanceField.getText();
        //ChangedCharacter.line[CharacterColumns.Task.GetId()] = TaskField.getText();
       // ChangedCharacter.line[CharacterColumns.TaskStatus.GetId()] = TaskStatusParamBox.getSelectedItem();
        ChangedCharacter.line[CharacterColumns.MeetingStatus.GetId()] = MeetingStatusParamBox.getSelectedItem();

        model.addRow(data.AddRow(ChangedCharacter.GetData()));
    }

    /**
     * Change charter's data in the model
     * @param model
     * @param data
     * @param row
     */
    void Change(DefaultTableModel model, CharactersTable data, int row)
    {
        Object[] lineData = new Object[model.getColumnCount() -1];
        lineData[CharacterColumns.Name.GetId() - 1] = NameField.getText();
        lineData[CharacterColumns.Apperance.GetId() - 1] = ApperanceField.getText();
        lineData[CharacterColumns.Location.GetId() - 1] = LocationField.getText();
        //lineData[CharacterColumns.Task.GetId() - 1] = TaskField.getText();
        //lineData[CharacterColumns.TaskStatus.GetId() - 1] = TaskStatusParamBox.getSelectedItem();
        lineData[CharacterColumns.MeetingStatus.GetId() - 1] = MeetingStatusParamBox.getSelectedItem();

        for (int i = 1; i < model.getColumnCount() - 1; i++)
        {
            data.ChangeRow(row, i, lineData[i - 1]);
        }
        data.InsertDataInTableModel(model);
    }

}

