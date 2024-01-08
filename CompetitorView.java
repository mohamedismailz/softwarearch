import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CompetitorView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton loadButton;

    public CompetitorView() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Competitor Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tableModel = new DefaultTableModel();
        setupTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton = new JButton("Add Competitor");
        removeButton = new JButton("Remove Competitor");
        editButton = new JButton("Edit Competitor");
        saveButton = new JButton("Save Data");
        loadButton = new JButton("Load Data");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void setupTableModel() {
        tableModel.addColumn("ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("age");
        tableModel.addColumn("gender");
        tableModel.addColumn("country");
        tableModel.addColumn("level");
        tableModel.addColumn("sportType");
        tableModel.addColumn("scores");
    }

    // Getters for buttons so the controller can attach action listeners
    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTable getTable() {
        return null;
    }
}
