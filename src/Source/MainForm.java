package Source;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import Export.*;
import Import.*;
import Export.TXTExporter;
import Import.TXTImporter;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

public class MainForm extends JFrame{
    //menu bar
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem saveMenuItem,
            openMenuItem,
            newMenuItem,
            exitMenuItem,
            exportXMLMenuItem,
            importXMLMenuItem,
            exportPDFMenuItem,
            exportHTMLMenuItem;

    //toolbar
    private JToolBar toolBar;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addButton;
    private JButton removeButton;
    private JLabel ModeLabel;
    private JComboBox<ProgramMode> modeParamBox;

    //search
    private JLabel searchByLabel;
    private JComboBox<CharacterColumns> searchParamBox;
    private JTextField searchField;
    private JButton searchButton;

    //sort
    private JLabel sortByLabel;
    private JComboBox<CharacterColumns> sortParamBox;

    // characters
    private DefaultTableModel charaterModel;
    private JTable charactersTable;
    private JScrollPane scroll;

    //tasks
    private DefaultTableModel taskModel;
    private JTable taskTable;
    private JScrollPane taskScroll;

    private DefaultTableModel currentModel;

    //panels
    private JPanel charactersPanel;
    private JPanel tasksPanel;
    private JPanel tablesPanel;

    private JButton connectButton;

    ConnectionTable connectionTable;

    CharactersTable data;
    CharactersTable viewData;

    TaskTable taskData;
    TaskTable viewTaskData;

    public MainForm(CharactersTable maindata, TaskTable taskDataMain)
    {
        //init window
        super("Master Characters list");
        setSize(950,400);
        setLocation(200, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //top menu
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");

        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        newMenuItem = new JMenuItem("New");
        exitMenuItem = new JMenuItem("Exit");
        exportXMLMenuItem = new JMenuItem("Export XML");
        importXMLMenuItem = new JMenuItem("Import XML");
        exportPDFMenuItem = new JMenuItem("Export PDF");
        exportHTMLMenuItem = new JMenuItem("Export HTML");


        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exportXMLMenuItem);
        fileMenu.add(importXMLMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(new JLabel("Report"));
        fileMenu.add(exportPDFMenuItem);
        fileMenu.add(exportHTMLMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);


        //add menu
        menuBar.add(fileMenu);
        menuBar.add(Box.createHorizontalGlue());
        setJMenuBar(menuBar);

        //toolbar
        toolBar = new JToolBar("Tool bar");

        //toolbar buttons
        saveButton = new JButton(new ImageIcon("./img/save.png"));
        loadButton = new JButton(new ImageIcon("./img/open.png"));
        addButton = new JButton(new ImageIcon("./img/add.png"));
        removeButton = new JButton(new ImageIcon("./img/remove.png"));
        connectButton = new JButton("Connect");

        ModeLabel = new JLabel("Program mode:");
        modeParamBox = new JComboBox<ProgramMode>(ProgramMode.values());

        saveButton.setBorderPainted(false);
        loadButton.setBorderPainted(false);
        addButton.setBorderPainted(false);
        removeButton.setBorderPainted(false);


        saveButton.setPreferredSize(new Dimension(32,32));
        loadButton.setPreferredSize(new Dimension(32,32));
        addButton.setPreferredSize(new Dimension(32,32));
        removeButton.setPreferredSize(new Dimension(32,32));

        toolBar.add(saveButton);
        toolBar.add(loadButton);
        toolBar.add(addButton);
        toolBar.add(removeButton);
        toolBar.add(ModeLabel);
        toolBar.add(modeParamBox);
        toolBar.add(connectButton);

        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));

        // toolbar-search
        searchByLabel = new JLabel("search by:");
        searchParamBox = new JComboBox<CharacterColumns>(CharacterColumns.values());
        searchField = new JTextField();
        searchButton = new JButton("Search");

        toolBar.add(searchByLabel);
        toolBar.add(searchParamBox);
        toolBar.add(searchField);
        toolBar.add(searchButton);

        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));

        //toolbar-sort

        sortByLabel = new JLabel("sort by: ");
        sortParamBox = new JComboBox<CharacterColumns>(CharacterColumns.values());
        toolBar.add(sortByLabel);
        toolBar.add(sortParamBox);
        toolBar.setFloatable(false);

        getContentPane().add(toolBar, BorderLayout.NORTH);

        tablesPanel = new JPanel();
        charactersPanel = new JPanel();

        tasksPanel = new JPanel();

        connectionTable = new ConnectionTable();

        // init table
        charaterModel = new DefaultTableModel(null, CharacterColumns.values())
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return  column != 0;
            }
        };

        //init task table

        taskModel = new DefaultTableModel(null, TasksColumns.values()){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return  column != 0;
            }
        };

        charactersTable = new JTable(charaterModel);

        scroll = new JScrollPane(charactersTable);
        charactersPanel.add(scroll,BorderLayout.CENTER);


        taskTable = new JTable(taskModel);
        taskScroll = new JScrollPane(taskTable);
        tasksPanel.add(taskScroll,BorderLayout.CENTER);


        GridLayout layout = new GridLayout(1, 2, 0, 1);
        tablesPanel.setLayout(layout);

        tablesPanel.add(charactersPanel);
        tablesPanel.add(tasksPanel);

        add(tablesPanel);


        // Listeners
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "New Table created");
                NewTable();
            }

        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectChTsk();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search();
            }
        });

        sortParamBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    Sort();
            }
        });

        modeParamBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED);
                    ChangeMode();
            }
        });

        charaterModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                ChangeCharaterRow(e.getFirstRow(), e.getColumn());
            }
        });

        taskModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                ChangeTaskRow(e.getFirstRow(), e.getColumn());
            }
        });

        //show tasks
        charactersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 1 && table.getSelectedRow() != -1 && modeParamBox.getSelectedItem() == ProgramMode.Characters)
                {
                    ShowTasks(row);

                }
            }
        });

        // show characters
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 1 && table.getSelectedRow() != -1 && modeParamBox.getSelectedItem() == ProgramMode.Tasks)
                {
                    ShowCharacters(row);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }
        });

        exportXMLMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportXML();
            }
        });

        importXMLMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImportXML();
            }
        });

        exportPDFMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportPDF();
            }
        });

        exportHTMLMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportHTML();
            }
        });

        //create tables Characters
        if (maindata == null)
            data = new CharactersTable(charaterModel);
        else
            data = maindata;

        // create tables Tasks
        if (taskDataMain == null)
            taskData = new TaskTable(charaterModel);
        else
            taskData = taskDataMain;

        viewData = data;
        viewData.InsertDataInTableModel(charaterModel);

        viewTaskData = taskData;
        viewTaskData.InsertDataInTableModel(taskModel);


        setVisible(true);
    }


    void ChangeMode()
    {
        if (modeParamBox.getSelectedItem() == ProgramMode.Tasks)
        {
            setVisible(false);
            tablesPanel.remove(charactersPanel);
            tablesPanel.remove(tasksPanel);

            tablesPanel.add(tasksPanel);
            tablesPanel.add(charactersPanel);

            taskData.InsertDataInTableModel(taskModel);

            currentModel = taskModel;
            connectButton.setVisible(false);
            setVisible(true);
        }
        else if (modeParamBox.getSelectedItem() == ProgramMode.Characters)
        {
            setVisible(false);
            tablesPanel.remove(tasksPanel);
            tablesPanel.remove(charactersPanel);

            tablesPanel.add(charactersPanel);
            tablesPanel.add(tasksPanel);
            data.InsertDataInTableModel(charaterModel);

            connectButton.setVisible(false);

            setVisible(true);

            currentModel = charaterModel;
        } else if (modeParamBox.getSelectedItem() == ProgramMode.Both)
        {
            setVisible(false);
            tablesPanel.remove(tasksPanel);
            tablesPanel.remove(charactersPanel);

            tablesPanel.add(charactersPanel);
            tablesPanel.add(tasksPanel);

            data.InsertDataInTableModel(charaterModel);
            taskData.InsertDataInTableModel(taskModel);
            currentModel = null;
            connectButton.setVisible(true);
            setVisible(true);
        }

    }

    void Add()
    {
        charaterModel.addRow(data.AddRow(CharactersTable.DefaultCharacter.GetData()));;
    }

    void Delete()
    {
        try {
            data.RemoveRow(charactersTable.getSelectedRow(), charaterModel);
        } catch ( IndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null, "Select the row to delete!");
        }

    }

    void Search()
    {
        String text = searchField.getText();

        if(text == null || text.equals(""))
        {
            viewData = data;
            viewData.InsertDataInTableModel(charaterModel);
        }
        else
        {
            viewData = data.Search(searchParamBox.getSelectedIndex(), searchField.getText());
            viewData.InsertDataInTableModel(charaterModel);
        }

    }

    void Sort()
    {
        viewData.Sort(sortParamBox.getSelectedIndex()).InsertDataInTableModel(charaterModel);

    }

    void NewTable()
    {
        viewData = data = new CharactersTable();
        viewData.InsertDataInTableModel(charaterModel);

    }

    void Save()
    {
        new TXTExporter("Save table", viewData);
    }

    void Load()
    {
        new TXTImporter("Load table",this, charaterModel);
        viewData = data;
        Sort();
    }

    void ChangeCharaterRow(int row, int column)
    {
        if(row >= 0 && column >= 0)
        {
            Object newValue = charaterModel.getValueAt(row, column);
            int id = Integer.parseInt(charaterModel.getValueAt(row, 0).toString());
             data.ChangeRow(id, column, newValue);

        }
    }

    void ChangeTaskRow(int row, int column)
    {
        if(row >= 0 && column >= 0)
        {
            Object newValue = taskModel.getValueAt(row, column);
            int id = Integer.parseInt(taskModel.getValueAt(row, 0).toString());
            taskData.ChangeRow(id, column, newValue);

        }
    }

    void ShowTasks(int row)
    {
       int idCharacter = (int)charaterModel.getValueAt(row, 0);
       var hisTasks = connectionTable.GetTasksForCharacter(idCharacter);
       viewTaskData = taskData.GetTaskTableById(hisTasks);
       viewTaskData.InsertDataInTableModel(taskModel);
    }

    void ShowCharacters(int row)
    {

    }

    void ConnectChTsk()
    {
        int indexRowCharacter = charactersTable.getSelectedRow();
        int indexRowTask = taskTable.getSelectedRow();

        try {
            if (indexRowCharacter < 0) {
                throw new IndexOutOfBoundsException(indexRowCharacter);
            } else if (indexRowTask < 0) {
                throw new IndexOutOfBoundsException(indexRowTask);
            }
        }catch ( IndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null, "Select the rows to connect!");
        }

        if (indexRowCharacter >= 0 && indexRowTask >= 0) {
            int idCharacter = (int) charaterModel.getValueAt(indexRowCharacter, 0);
            int idTask = (int) taskModel.getValueAt(indexRowTask, 0);

            connectionTable.Connect(idCharacter, idTask);
        }
    }

    void ExportXML()
    {
        try {
            new XMLExporter(data);
        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void ApplyData(CharactersTable dat)
    {
        viewData = data = dat;
        viewData.InsertDataInTableModel(charaterModel);
    }

    public void SetData(CharactersTable sdata)
    {
        data = sdata;
    }

    public CharactersTable GetData()
    {
        return data;
    }

    void ImportXML()
    {
        new XMLImporter(this);
    }

    void ExportPDF()
    {
        new PDFExporter(data);
    }
    void ExportHTML()
    {
        new HTMLExporter(data);
    }
}
