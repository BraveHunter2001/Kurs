package Source;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Export.*;
import Import.*;
import Export.TXTExporter;
import Import.TXTImporter;

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

    //search
    private JLabel searchByLabel;
    private JComboBox<Columns> searchParamBox;
    private JTextField searchField;
    private JButton searchButton;

    //sort
    private JLabel sortByLabel;
    private JComboBox<Columns> sortParamBox;

    private DefaultTableModel model;
    private JTable charactersTable;
    private JScrollPane scroll;

    CharactersTable data;
    CharactersTable viewData;


    public MainForm(CharactersTable maindata)
    {
        //init window
        super("Master Characters list");
        setSize(900,400);
        setLocation(100, 100);
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

        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));

        // toolbar-search
        searchByLabel = new JLabel("search by:");
        searchParamBox = new JComboBox<Columns>(Columns.values());
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
        sortParamBox = new JComboBox<Columns>(Columns.values());
        toolBar.add(sortByLabel);
        toolBar.add(sortParamBox);
        toolBar.setFloatable(false);

        getContentPane().add(toolBar, BorderLayout.NORTH);


        // init table
        model = new DefaultTableModel(null, Columns.values())
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return  false;
            }
        };
        charactersTable = new JTable(model);
        scroll = new JScrollPane(charactersTable);
        add(scroll, BorderLayout.CENTER);

        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Test");
                NewTable();
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


        charactersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1)
                {
                    Change(row);
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

        if (maindata == null)
            data = new CharactersTable(model);
        else
            data = maindata;

        viewData = data;
        viewData.InsertDataInTableModel(model);
        setVisible(true);
    }


    void Add()
    {
        new CharacterForm("Add",model, data);
    }


    void Delete()
    {
        try {
            data.RemoveRow(charactersTable.getSelectedRow(), model);
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
            viewData.InsertDataInTableModel(model);
        }
        else
        {
            viewData = data.Search(searchParamBox.getSelectedIndex(), searchField.getText());
            viewData.InsertDataInTableModel(model);
        }
    }

    void Sort()
    {
        viewData.Sort(sortParamBox.getSelectedIndex()).InsertDataInTableModel(model);
    }

    void NewTable()
    {
        viewData = data = new CharactersTable();
        viewData.InsertDataInTableModel(model);
    }

    void Save()
    {
        new TXTExporter("Save table", viewData);
    }

    void Load()
    {
        new TXTImporter("Load table",this,model);
        viewData = data;
        Sort();
    }

    void Change(int row)
    {
        new CharacterForm("Change", model, data, row);
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
        viewData.InsertDataInTableModel(model);
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
