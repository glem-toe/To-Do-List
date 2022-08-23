package ui;

import model.Task;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a panel with buttons that acts like a toolbar
public class Toolbar extends JPanel implements ActionListener {
    private ImageIcon addButtonIcon = createImageIcon("images/plus.gif");
    private ImageIcon completeButtonIcon = createImageIcon("images/checkmark.gif");
    private ImageIcon removeButtonIcon = createImageIcon("images/x_mark.gif");

    private JButton removeButton;
    private JTextField taskName;
    private JButton addButton;
    private JButton completeButton;
    private JButton saveButton;
    private JButton loadButton;

    private MainFrame mainFrame;

    private StringListener textListener;

    // EFFECTS: constructs a toolbar containing all the buttons in the to do list app
    public Toolbar() {
        instantiateButtons();

        buttonConfig();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        addButtonsToPanel();
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    // MODIFIES: this
    // EFFECTS: instantiates the buttons
    public void instantiateButtons() {
        removeButton = new JButton("REMOVETASK", removeButtonIcon);
        taskName = new JTextField(10);
        addButton = new JButton("ADDTASK", addButtonIcon);
        completeButton = new JButton("COMPLETETASK", completeButtonIcon);
        saveButton = new JButton("SAVE TODOLIST");
        loadButton = new JButton("LOAD TODOLIST");
    }

    // MODIFIES: this
    // EFFECTS: configures the buttons
    public void buttonConfig() {
        removeButton.setVerticalTextPosition(AbstractButton.CENTER);
        removeButton.setHorizontalTextPosition(AbstractButton.LEADING);
        removeButton.addActionListener(this);
        removeButton.setEnabled(false);

        addButton.setVerticalTextPosition(AbstractButton.CENTER);
        addButton.setHorizontalTextPosition(AbstractButton.LEADING);
        AddListener addListener = new AddListener(addButton);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        taskName.addActionListener(addListener);
        taskName.getDocument().addDocumentListener(addListener);

        completeButton.setVerticalTextPosition(AbstractButton.CENTER);
        completeButton.setHorizontalTextPosition(AbstractButton.LEADING);
        completeButton.addActionListener(this);
        completeButton.setEnabled(false);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to panel
    public void addButtonsToPanel() {
        add(completeButton);
        add(removeButton);
        add(Box.createHorizontalStrut(5));
        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(5));
        add(taskName);
        add(addButton);
        add(saveButton);
        add(loadButton);
    }

    // EFFECTS: processes user event
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton pressed = (JButton) e.getSource();

        if (pressed == saveButton) {
            save();
        } else if (pressed == loadButton) {
            load();
        } else if (pressed == completeButton) {
            complete();
        } else if (pressed == removeButton) {
            removeTask();
        }
    }

    // MODIFIES: this
    // EFFECTS: enables the remove button if the size of the list model is not empty
    public void updateRemove() {
        if (mainFrame.getModel().getSize() > 0) {
            removeButton.setEnabled(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: if textListener is empty, then sends remove task textListener
    public void removeTask() {
        if (textListener != null) {
            textListener.textEmitted("remove task");
        }
    }

    // MODIFIES: this
    // EFFECTS: if textListener is empty, then sends save textListener
    public void save() {
        if (textListener != null) {
            textListener.textEmitted("save");
        }
    }

    // MODIFIES: this
    // EFFECTS: if textListener is empty, then sends load textListener
    public void load() {
        if (textListener != null) {
            textListener.textEmitted("load");
        }
    }

    // MODIFIES: this
    // EFFECTS: if textListener is empty, then sends complete textListener
    public void complete() {
        if (textListener != null) {
            textListener.textEmitted("complete task");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the textListener as listener
    public void setStringListener(StringListener listener) {
        this.textListener = listener;
    }

    // EFFECTS: Returns an ImageIcon, or null if the path was invalid.
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Toolbar.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // represents a type of listener that listens to an action event (button click) and a document event (text field)
    private class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // EFFECTS: constructs an AddListener that listens to button
        public AddListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: MainFrame, Toolbar
        // EFFECTS: adds a task of given name in textField and makes it visible to the frame
        @Override
        public void actionPerformed(ActionEvent e) {

            int index = mainFrame.getSelectedIndexInList(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            Task newTask = new Task(taskName.getText());
            mainFrame.getModel().addElement(newTask);
            mainFrame.getTasks().addTask(newTask);

            //Reset the text field.
            taskName.requestFocusInWindow();
            taskName.setText("");

            //Select the new item and make it visible.
            mainFrame.getList().setSelectedIndex(index);
            mainFrame.getList().ensureIndexIsVisible(index);

            updateRemove();

        }

        // EFFECTS: updates when a text is added to the empty text field
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: updates when text field is empty
        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // EFFECTS: updates when the text field changes (ex. character added or removed)
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECTS: turns add button to true if alreadyEnabled is false
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: return true if text box is empty, false otherwise
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // EFFECTS: returns the remove button
    public JButton getRemoveButton() {
        return removeButton;
    }

    // EFFECTS: returns the complete button
    public JButton getCompleteButton() {
        return completeButton;
    }

    // MODIFIES: this
    // EFFECTS: sets the main frame of toolbar as variable mainFrame
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
