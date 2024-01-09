import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class CompetitorApp {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private CompetitorList competitorList;

    public CompetitorApp() {
        competitorList = new CompetitorList(); // Initialize your competitor list
        initialize();
    }

    private void initialize() {
        // Set up the main window
        frame = new JFrame("Competitor Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Table model and table setup
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        setupTableModel();
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton btnAddCompetitor = new JButton("Add Competitor");
        JButton btnRemoveCompetitor = new JButton("Remove Competitor");
        buttonPanel.add(btnAddCompetitor);
        buttonPanel.add(btnRemoveCompetitor);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        JButton btnEditCompetitor = new JButton("Edit Competitor");
        buttonPanel.add(btnEditCompetitor);
        btnEditCompetitor.addActionListener(e -> editSelectedCompetitor());


        // Add functionality to buttons
        btnAddCompetitor.addActionListener(e -> addCompetitor());
        btnRemoveCompetitor.addActionListener(e -> removeSelectedCompetitor());

        // Display the window
        frame.setVisible(true);
    }
    // Method to edit details
    private void editSelectedCompetitor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int competitorId = (int) tableModel.getValueAt(selectedRow, 0); // Assuming ID is in the first column
            Competitor selectedCompetitor = competitorList.getCompetitorByNumber(competitorId);

            if (selectedCompetitor != null) {
                // Retrieve current details
                String currentFirstName = selectedCompetitor.getfirstName();
                String currentLastName = selectedCompetitor.getlastName();
                // ... retrieve other fields

                // Prompt for new details
                String newFirstName = JOptionPane.showInputDialog(frame, "Edit First Name:", currentFirstName);
                String newLastName = JOptionPane.showInputDialog(frame, "Edit Last Name:", currentLastName);
                // ... prompt for other fields

                // Update competitor details
                selectedCompetitor.setFirstName(newFirstName);
                selectedCompetitor.setLastName(newLastName);
                // ... update other fields

                // Update the table model
                tableModel.setValueAt(newFirstName, selectedRow, 1); // Assuming first name is in the second column
                tableModel.setValueAt(newLastName, selectedRow, 2); // Assuming last name is in the third column
                // ... update other columns
            }
        }
    }



    private void updateTableRow(int row, Competitor competitor) {
        tableModel.setValueAt(competitor.getfirstName(), row, 1); // Assuming first name is in the second column
        // ... similarly update other columns
    }


    private void setupTableModel() {
        tableModel.addColumn("ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Country");
        tableModel.addColumn("Level");
        tableModel.addColumn("Sport Type");
        tableModel.addColumn("Overall Score");
    }

    private void addCompetitor() {
        try {
            // Prompt for competitor details
            int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Competitor ID:"));
            String firstName = JOptionPane.showInputDialog(frame, "Enter First Name:");
            String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name:");
            int age = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Age:"));
            String gender = JOptionPane.showInputDialog(frame, "Enter Gender:");
            String country = JOptionPane.showInputDialog(frame, "Enter Country:");
            String level = JOptionPane.showInputDialog(frame, "Enter Level:");
            String sportType = JOptionPane.showInputDialog(frame, "Enter Sport Type:");
            String scoresStr = JOptionPane.showInputDialog(frame, "Enter Scores (comma-separated):");

            // Parse scores string to int array
            int[] scores = Arrays.stream(scoresStr.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            // Create a new Competitor object
            Competitor newCompetitor = new Competitor(id, firstName, lastName, age, gender, country, level, sportType, scores);

            // Add the new Competitor to CompetitorList
            competitorList.addCompetitor(newCompetitor);

            // Update the table model
            tableModel.addRow(new Object[]{id, firstName, lastName, age, gender, country, level, sportType, newCompetitor.getOverallScore()});
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input format. Please try again.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error adding competitor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedCompetitor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int competitorId = (int) tableModel.getValueAt(selectedRow, 0); // Assuming ID is in the first column
            competitorList.removeCompetitorByNumber(competitorId);
            tableModel.removeRow(selectedRow);
        }
    }

    private void saveCompetitorListToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(competitorList);
            JOptionPane.showMessageDialog(frame, "Data saved successfully.", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCompetitorListFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            competitorList = (CompetitorList) in.readObject();
            updateTableWithCompetitorList();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTableWithCompetitorList() {
        tableModel.setRowCount(0); // Clear existing data
        for (Competitor competitor : competitorList.getCompetitors()) {
            // Assuming Competitor class has appropriate getter methods
            tableModel.addRow(new Object[]{competitor.getCompetitorId(), competitor.getfirstName(), /* other fields */});
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                CompetitorView view = new CompetitorView();
                CompetitorList model = new CompetitorList();
                CompetitorController controller = new CompetitorController(model, view);
                new CompetitorApp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
