package Source;

import java.awt.*;
import java.awt.event.*;
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
    private JComboBox<CharacterColumns> searchCharacterParamBox;
    private JComboBox<TasksColumns> searchTaskParamBox;
    private JTextField searchField;
    private JButton searchButton;

    //sort
    private JLabel sortByLabel;
    private JComboBox<CharacterColumns> sortCharacterParamBox;
    private JComboBox<TasksColumns> sortTaskParamBox;
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
    private JButton unConnectionButton;

    ConnectionTable connectionTable;

    CharactersTable characterData;
    CharactersTable viewCharacterData;


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
        unConnectionButton = new JButton("Un connect");

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
        toolBar.add(unConnectionButton);

        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));

        // toolbar-search
        searchByLabel = new JLabel("search by:");
        searchCharacterParamBox = new JComboBox<CharacterColumns>(CharacterColumns.values());
        searchTaskParamBox = new JComboBox<>(TasksColumns.values());
        searchField = new JTextField();
        searchButton = new JButton("Search");

        toolBar.add(searchByLabel);
        toolBar.add(searchCharacterParamBox);
        toolBar.add(searchTaskParamBox);
        toolBar.add(searchField);
        toolBar.add(searchButton);

        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));

        //toolbar-sort

        sortByLabel = new JLabel("sort by: ");
        sortCharacterParamBox = new JComboBox<CharacterColumns>(CharacterColumns.values());
        sortTaskParamBox = new JComboBox<>(TasksColumns.values());
        toolBar.add(sortByLabel);
        toolBar.add(sortCharacterParamBox);
        toolBar.add(sortTaskParamBox);
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

        unConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UnConnectChTsk();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeParamBox.getSelectedItem() == ProgramMode.Characters)
                    AddCharacterRow();
                if (modeParamBox.getSelectedItem() == ProgramMode.Tasks)
                    AddTaskRow();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeParamBox.getSelectedItem() == ProgramMode.Characters)
                    DeleteCharacterRow();
                if (modeParamBox.getSelectedItem() == ProgramMode.Tasks)
                    DeleteTaskRow();
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

        sortCharacterParamBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    SortCharacter();
            }
        });

        sortTaskParamBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    SortTask();
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
            characterData = new CharactersTable(charaterModel);
        else
            characterData = maindata;

        // create tables Tasks
        if (taskDataMain == null)
            taskData = new TaskTable(charaterModel);
        else
            taskData = taskDataMain;

        viewCharacterData = characterData;
        viewCharacterData.InsertDataInTableModel(charaterModel);

        viewTaskData = taskData;
        viewTaskData.InsertDataInTableModel(taskModel);


        searchByLabel.setVisible(false);
        searchField.setVisible(false);
        searchButton.setVisible(false);
        searchCharacterParamBox.setVisible(false);
        searchTaskParamBox.setVisible(false);

        sortByLabel.setVisible(false);
        sortCharacterParamBox.setVisible(false);
        sortTaskParamBox.setVisible(false);

        addButton.setVisible(false);
        removeButton.setVisible(false);

        loadButton.setVisible(false);
        saveButton.setVisible(false);

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
            unConnectionButton.setVisible(false);

            searchByLabel.setVisible(true);
            searchField.setVisible(true);
            searchButton.setVisible(true);
            searchCharacterParamBox.setVisible(false);
            searchTaskParamBox.setVisible(true);

            sortByLabel.setVisible(true);
            sortCharacterParamBox.setVisible(false);
            sortTaskParamBox.setVisible(true);

            addButton.setVisible(true);
            removeButton.setVisible(true);

            loadButton.setVisible(true);
            saveButton.setVisible(true);

            setVisible(true);
        }
        else if (modeParamBox.getSelectedItem() == ProgramMode.Characters)
        {
            setVisible(false);
            tablesPanel.remove(tasksPanel);
            tablesPanel.remove(charactersPanel);

            tablesPanel.add(charactersPanel);
            tablesPanel.add(tasksPanel);
            characterData.InsertDataInTableModel(charaterModel);

            connectButton.setVisible(false);
            unConnectionButton.setVisible(false);

            searchByLabel.setVisible(true);
            searchField.setVisible(true);
            searchButton.setVisible(true);
            searchCharacterParamBox.setVisible(true);
            searchTaskParamBox.setVisible(false);

            sortByLabel.setVisible(true);
            sortCharacterParamBox.setVisible(true);
            sortTaskParamBox.setVisible(false);

            addButton.setVisible(true);
            removeButton.setVisible(true);

            loadButton.setVisible(true);
            saveButton.setVisible(true);

            setVisible(true);

            currentModel = charaterModel;
        } else if (modeParamBox.getSelectedItem() == ProgramMode.Both)
        {
            setVisible(false);
            tablesPanel.remove(tasksPanel);
            tablesPanel.remove(charactersPanel);

            tablesPanel.add(charactersPanel);
            tablesPanel.add(tasksPanel);

            characterData.InsertDataInTableModel(charaterModel);
            taskData.InsertDataInTableModel(taskModel);
            currentModel = null;

            connectButton.setVisible(true);
            unConnectionButton.setVisible(true);

            searchByLabel.setVisible(false);
            searchField.setVisible(false);
            searchButton.setVisible(false);
            searchCharacterParamBox.setVisible(false);
            searchTaskParamBox.setVisible(false);

            sortByLabel.setVisible(false);
            sortCharacterParamBox.setVisible(false);
            sortTaskParamBox.setVisible(false);

            addButton.setVisible(false);
            removeButton.setVisible(false);

            loadButton.setVisible(false);
            saveButton.setVisible(false);

            setVisible(true);
        }

    }

    void AddCharacterRow()
    {
        charaterModel.addRow(characterData.AddRow(CharactersTable.DefaultCharacter.GetData()));;
    }
    void AddTaskRow()
    {
        taskModel.addRow(taskData.AddRow(TaskTable.DefaultTask.GetData()));;
    }

    void DeleteCharacterRow()
    {
        try {
            int indexRowCharacter = charactersTable.getSelectedRow();
            int idCharacter = (int) charaterModel.getValueAt(indexRowCharacter, 0);

            connectionTable.UnConnectAllForCharacter(idCharacter);
            characterData.RemoveRow(indexRowCharacter, charaterModel);
        } catch ( IndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null, "Select the row to delete!");
        }

    }

    void DeleteTaskRow()
    {
        try {
            int indexRowTask = taskTable.getSelectedRow();
            int idTask = (int) taskModel.getValueAt(indexRowTask, 0);

            connectionTable.UnConnectAllForTask(idTask);

            taskData.RemoveRow(indexRowTask, taskModel);
        } catch ( IndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null, "Select the row to delete!");
        }

    }

    void Search()
    {
        String text = searchField.getText();

        if (modeParamBox.getSelectedItem() == ProgramMode.Characters) {
            if (text == null || text.equals("")) {
                viewCharacterData = characterData;
                viewCharacterData.InsertDataInTableModel(charaterModel);
            } else {
                viewCharacterData = characterData.Search(searchCharacterParamBox.getSelectedIndex(), searchField.getText());
                viewCharacterData.InsertDataInTableModel(charaterModel);
            }
        }else if (modeParamBox.getSelectedItem() == ProgramMode.Tasks)
        {
            if (text == null || text.equals("")) {
                viewTaskData = taskData;
                viewTaskData.InsertDataInTableModel(taskModel);
            } else {
                viewTaskData = taskData.Search(searchTaskParamBox.getSelectedIndex(), searchField.getText());
                viewTaskData.InsertDataInTableModel(taskModel);
            }
        }
    }



    void SortCharacter()
    {
        viewCharacterData.Sort(sortCharacterParamBox.getSelectedIndex()).InsertDataInTableModel(charaterModel);
    }

    void SortTask()
    {
        viewTaskData.Sort(sortTaskParamBox.getSelectedIndex()).InsertDataInTableModel(taskModel);
    }

    void NewTable()
    {
        viewCharacterData = characterData = new CharactersTable();
        viewTaskData = taskData = new TaskTable();
        viewCharacterData.InsertDataInTableModel(charaterModel);
        viewTaskData.InsertDataInTableModel(taskModel);

    }

    void Save()
    {
        new TXTExporter("Save table", viewCharacterData);
    }

    void Load()
    {
        new TXTImporter("Load table",this, charaterModel);
        viewCharacterData = characterData;
        SortCharacter();
    }

    void ChangeCharaterRow(int row, int column)
    {
        if(row >= 0 && column >= 0)
        {
            Object newValue = charaterModel.getValueAt(row, column);
            int id = Integer.parseInt(charaterModel.getValueAt(row, 0).toString());
             characterData.ChangeRow(id, column, newValue);

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
       viewTaskData = taskData.GetConnectionItemById(hisTasks);
       viewTaskData.InsertDataInTableModel(taskModel);
    }

    void ShowCharacters(int row)
    {
        int idTask = (int)taskModel.getValueAt(row, 0);
        var hisCharacter = connectionTable.GetCharactersForTask(idTask);
        viewCharacterData = characterData.GetConnectionItemById(hisCharacter);
        viewCharacterData.InsertDataInTableModel(charaterModel);
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

    void UnConnectChTsk()
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
            JOptionPane.showMessageDialog(null, "Select the rows to unconnect!");
        }

        if (indexRowCharacter >= 0 && indexRowTask >= 0) {
            int idCharacter = (int) charaterModel.getValueAt(indexRowCharacter, 0);
            int idTask = (int) taskModel.getValueAt(indexRowTask, 0);

            connectionTable.UnConnect(idCharacter, idTask);
        }
    }

    void ExportXML()
    {
        try {
            new XMLExporter(characterData);
        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void ApplyData(CharactersTable dat)
    {
        viewCharacterData = characterData = dat;
        viewCharacterData.InsertDataInTableModel(charaterModel);
    }

    public void SetData(CharactersTable sdata)
    {
        characterData = sdata;
    }

    public CharactersTable GetData()
    {
        return characterData;
    }

    void ImportXML()
    {
        new XMLImporter(this);
    }

    void ExportPDF()
    {
        new PDFExporter(characterData);
    }
    void ExportHTML()
    {
        new HTMLExporter(characterData);
    }
}
