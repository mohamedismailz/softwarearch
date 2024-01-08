import java.io.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CompetitorController {
    private CompetitorList model;
    private CompetitorView view;

    public CompetitorController(CompetitorList model, CompetitorView view) {
        this.model = model;
        this.view = view;
        initView();
    }

    private void initView() {
        view.getAddButton().addActionListener(e -> addCompetitor());
        view.getRemoveButton().addActionListener(e -> removeSelectedCompetitor());
        view.getEditButton().addActionListener(e -> editSelectedCompetitor());
        view.getSaveButton().addActionListener(e -> saveCompetitorList());
        view.getLoadButton().addActionListener(e -> loadCompetitorList());
    }

    private void addCompetitor() {
        // Prompt the user to enter details for the new competitor
        String firstName = JOptionPane.showInputDialog(view.getFrame(), "Enter First Name:");
        String lastName = JOptionPane.showInputDialog(view.getFrame(), "Enter Last Name:");
        String ageStr = JOptionPane.showInputDialog(view.getFrame(), "Enter Age:");
        String gender = JOptionPane.showInputDialog(view.getFrame(), "Enter Gender:");
        String country = JOptionPane.showInputDialog(view.getFrame(), "Enter Country:");
        String level = JOptionPane.showInputDialog(view.getFrame(), "Enter Level:");
        String sportType = JOptionPane.showInputDialog(view.getFrame(), "Enter Sport Type:");
        String scoresStr = JOptionPane.showInputDialog(view.getFrame(), "Enter Scores (comma-separated):");

        if (firstName != null && lastName != null && ageStr != null && gender != null &&
                country != null && level != null && sportType != null && scoresStr != null &&
                !firstName.trim().isEmpty() && !lastName.trim().isEmpty() && !ageStr.trim().isEmpty() &&
                !gender.trim().isEmpty() && !country.trim().isEmpty() && !level.trim().isEmpty() &&
                !sportType.trim().isEmpty() && !scoresStr.trim().isEmpty()) {

            try {
                int age = Integer.parseInt(ageStr);
                int[] scores = Arrays.stream(scoresStr.split(","))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                // Create a new Competitor object
                Competitor newCompetitor = new Competitor(
                        model.getNextCompetitorId(), // Assuming CompetitorList has this method to generate unique IDs
                        firstName, lastName, age, gender, country, level, sportType, scores
                );

                // Add the new Competitor to CompetitorList
                model.addCompetitor(newCompetitor);

                // Update the table model to show the new competitor
                view.getTableModel().addRow(new Object[]{
                        newCompetitor.getCompetitorId(),
                        newCompetitor.getfirstName(),
                        newCompetitor.getlastName(),
                        newCompetitor.getage(),
                        newCompetitor.getgender(),
                        newCompetitor.getcountry(),
                        newCompetitor.getlevel(),
                        newCompetitor.getsportType(),
                        newCompetitor.getOverallScore()
                });
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view.getFrame(), "Invalid number format for age or scores. Please try again.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view.getFrame(), "An error occurred while adding the competitor: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view.getFrame(), "Please fill in all the details.",
                    "Incomplete Data", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void removeSelectedCompetitor() {
        // Get the selected row from the view
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            // If a row is selected, remove the competitor from the model
            int competitorId = (int) view.getTableModel().getValueAt(selectedRow, 0);
            model.removeCompetitorByNumber(competitorId);
            // Remove the row from the table model
            view.getTableModel().removeRow(selectedRow);
        }
    }

    private void editSelectedCompetitor() {
        // Get the selected row from the view
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            // Get the current details of the competitor
            int competitorId = (int) view.getTableModel().getValueAt(selectedRow, 0);
            Competitor competitor = model.getCompetitorByNumber(competitorId);
            if (competitor != null) {
                // Prompt the user for new details
                String newFirstName = JOptionPane.showInputDialog(view.getFrame(), "Edit First Name:", competitor.getfirstName());
                // prompt for other details and update competitor
                competitor.setFirstName(newFirstName);
                view.getTableModel().setValueAt(newFirstName, selectedRow, 1); // Adjust column index as needed
            }
        }
    }

    private void saveCompetitorList() {
        // Open a file chooser to let the user select where to save the data
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            try {
                // Save the competitor list to the selected file
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()));
                oos.writeObject(model.getCompetitors());
                oos.close();
                JOptionPane.showMessageDialog(view.getFrame(), "Data saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view.getFrame(), "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadCompetitorList() {
        // Open a file chooser to let the user select which data file to load
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            try {
                // Load the competitor list from the selected file
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                model.setCompetitors((List<Competitor>) ois.readObject());
                ois.close();
                // Update the view with the loaded data
                updateTableWithCompetitorList();
                JOptionPane.showMessageDialog(view.getFrame(), "Data loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view.getFrame(), "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTableWithCompetitorList() {
        // Clear the existing data in the table model
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);
        // Add each competitor in the model to the table model
        for (Competitor competitor : model.getCompetitors()) {
            tableModel.addRow(new Object[]{ });
        }
    }
}
