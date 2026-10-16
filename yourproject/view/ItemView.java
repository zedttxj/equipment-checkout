package yourproject.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import yourproject.model.Equipment;

public class ItemView extends JFrame {
    private final JTable table;
    private final ItemTableModel tableModel;

    private final JTextField nameField = new JTextField(15);
    private final JTextField descField = new JTextField(15);

    private final JButton createButton = new JButton("Create");
    private final JButton updateButton = new JButton("Update");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton clearButton = new JButton("Clear");

    private final JLabel statusLabel = new JLabel("Ready.");

    public ItemView(List<Equipment> initialItems) {
        setTitle("Lab Equipment Checkout");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Table Setup
        tableModel = new ItemTableModel(initialItems);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form & Button Panel (South)
        JPanel southPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(statusLabel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    public JTable getTable() { return table; }
    public ItemTableModel getTableModel() { return tableModel; }
    public String getNameInput() { return nameField.getText(); }
    public String getDescInput() { return descField.getText(); }
    public void setNameInput(String name) { nameField.setText(name); }
    public void setDescInput(String desc) { descField.setText(desc); }
    public void setStatus(String status) { statusLabel.setText(status); }

    public JButton getCreateButton() { return createButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getClearButton() { return clearButton; }
}
