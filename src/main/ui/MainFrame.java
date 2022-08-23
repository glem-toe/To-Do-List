package ui;

import model.Event;
import model.EventLog;
import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

// main graphical user interface
public class MainFrame extends JFrame implements ListSelectionListener {

    private static final String FILE = "./data/todolist.json";

    private static int VERTICAL_SPLITTER_DISTANCE = 400;
    private static int HORIZONTAL_SPLITTER_DISTANCE = 515;
    private static int MAIN_PANEL_WIDTH = 800;
    private static int MAIN_PANEL_HEIGHT = 600;

    private JPanel listPanel;
    private JPanel statsPanel;
    private JLabel nameLabel;
    private JLabel statusLabel;
    private JSplitPane horizontalSplitPane;
    private JSplitPane verticalSplitPane;
    private JScrollPane scrollPane;
    private Toolbar toolbar;

    private ToDoList tasks;
    private JList<Task> list = new JList<>();
    private DefaultListModel<Task> model = new DefaultListModel<>();

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs the main JFrame that contains all of to do list's elements
    public MainFrame() {
        super("To Do List App");
        instantiateFields();
        setSplitDefaults();
        listenToCommands();

        listPanel.setLayout(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);

        statsPanel.setLayout(new BorderLayout());
        statsPanel.add(nameLabel, BorderLayout.CENTER);
        statsPanel.add(statusLabel, BorderLayout.AFTER_LINE_ENDS);
        updateStats();

        setVisible(true);
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        addClosingOperation();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

    }

    // MODIFIES: this
    // EFFECTS: adds a WindowListener that is processes methods when application closes
    public void addClosingOperation() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
    }

    // EFFECTS: prints all the log events in the console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

    // EFFECTS: listens to the different strings sent by the Toolbar
    public void listenToCommands() {
        toolbar.setStringListener(text -> {
            switch (text) {
                case "remove task":
                    removeTask();
                    break;
                case "complete task":
                    completeTask();
                    break;
                case "save":
                    saveToDoList();
                    break;
                case "load":
                    loadToDoList();
                    break;
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: marks selected task as complete
    public void completeTask() {
        int index = getSelectedIndexInList();
        model.get(index).markAsComplete();
        tasks.getTask(index).markAsComplete();
        updateStatsManual();
    }

    // MODIFIES: this
    // EFFECTS: saves todolist to FILE
    private void saveToDoList() {
        try {
//            jlistToToDoList();
            jsonWriter.open();
            jsonWriter.write(tasks);
            jsonWriter.close();
//            System.out.println("Saved To-Do List to " + FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + FILE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todolist from FILE
    private void loadToDoList() {
        try {
            tasks = jsonReader.read();
            toDoListToJList();
            toolbar.getRemoveButton().setEnabled(false);
            toolbar.getCompleteButton().setEnabled(false);
//            System.out.println("Loaded To Do List from " + FILE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + FILE);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the stats in the stats panel
    public void updateStats() {
        list.getSelectionModel().addListSelectionListener(e -> {
            Task t = list.getSelectedValue();
            if (t != null) {
                nameLabel.setText("Name: " + t.getName());
                statusLabel.setText("Completed?: " + t.getStatus());
                toolbar.getRemoveButton().setEnabled(true);
                toolbar.getCompleteButton().setEnabled(true);
            } else {
                nameLabel.setText("");
                statusLabel.setText("");
                toolbar.getRemoveButton().setEnabled(false);
                toolbar.getCompleteButton().setEnabled(false);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: updates the stats in the stats panel manually
    public void updateStatsManual() {
        Task t = list.getSelectedValue();
        nameLabel.setText("Name: " + t.getName());
        statusLabel.setText("Completed?: " + t.getStatus());
        toolbar.getRemoveButton().setEnabled(true);
    }

    // MODIFIES: this, Toolbar
    // EFFECTS: instantiates the fields
    public void instantiateFields() {
        statsPanel = new JPanel();
        listPanel = new JPanel();
        verticalSplitPane = new JSplitPane();
        horizontalSplitPane = new JSplitPane();
        scrollPane = new JScrollPane(list);
        nameLabel = new JLabel();
        statusLabel = new JLabel();
        toolbar = new Toolbar();
        toolbar.setMainFrame(this);

        tasks = new ToDoList();

        list.setModel(model);

//        model.addElement(new Task("Brush Teeth"));
//        model.addElement(new Task("Sleep"));

        jsonWriter = new JsonWriter(FILE);
        jsonReader = new JsonReader(FILE);

    }

    // MODIFIES: this
    // EFFECTS: sets the default settings for both the horizontal and vertical panel components
    public void setSplitDefaults() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(verticalSplitPane);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(horizontalSplitPane);

        horizontalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        horizontalSplitPane.setDividerLocation(HORIZONTAL_SPLITTER_DISTANCE);
        horizontalSplitPane.setBottomComponent(toolbar);
        horizontalSplitPane.setTopComponent(verticalSplitPane);

        verticalSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        verticalSplitPane.setLeftComponent(listPanel);
        verticalSplitPane.setRightComponent(statsPanel);
        verticalSplitPane.setDividerLocation(VERTICAL_SPLITTER_DISTANCE);
    }

    // MODIFIES: this
    // EFFECTS: removes the selected task
    public void removeTask() {
        //This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = getSelectedIndexInList();
        getModel().remove(index);
        tasks.removeTask(index);

        int size = getModel().getSize();

        if (size == 0) {
            toolbar.getRemoveButton().setEnabled(false);
            toolbar.getCompleteButton().setEnabled(false);
        } else {
            if (index == getModel().getSize()) {
                //removed item in last position
                index--;
            }

            getList().setSelectedIndex(index);
            getList().ensureIndexIsVisible(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: copies jlist to tasks
    private void jlistToToDoList() {
        tasks.clearList();
        int index = model.getSize();
        for (int i = 0; i < index; i++) {
            tasks.addTask(model.get(i));
        }
    }

    // MODIFIES: this
    // EFFECTS: copies tasks to jlist
    private void toDoListToJList() {
        model.clear();
        int index = tasks.getSize();
        for (int i = 0; i < index; i++) {
            model.addElement(tasks.getTask(i));
        }
    }

    // EFFECTS: returns the JList of tasks
    public JList<Task> getList() {
        return list;
    }

    // EFFECTS: returns the list model of tasks
    public DefaultListModel<Task> getModel() {
        return model;
    }

    // EFFECTS: returns the index of the selected task
    public int getSelectedIndexInList() {
        return list.getSelectedIndex();
    }

    // EFFECTS: returns tasks
    public ToDoList getTasks() {
        return tasks;
    }

    // MODIFIES: this, Toolbar
    // EFFECTS: deactivates the remove button if there is no selected task, enables the remove button otherwise
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove Button.
                toolbar.getRemoveButton().setEnabled(false);
            } else {
                //Selection, enable the remove Button.
                toolbar.getRemoveButton().setEnabled(true);
            }
        }
    }
}
