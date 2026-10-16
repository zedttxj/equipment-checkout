package yourproject.controller;

import yourproject.model.Equipment;
import yourproject.model.ItemRepository;
import yourproject.view.ItemView;

import javax.swing.*;
import java.util.List;

public class ItemController {
    private final ItemRepository repository;
    private final ItemView view;

    public ItemController(ItemRepository repository, ItemView view) {
        this.repository = repository;
        this.view = view;

        initController();
    }

    private void initController() {
        // Populate initial table data
        refreshTable();

        // Handle Row Selection to load values into form fields for Update
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = view.getTable().convertRowIndexToModel(selectedRow);
                    List<Equipment> items = repository.findAll();
                    if (modelRow < items.size()) {
                        Equipment eq = items.get(modelRow);
                        view.setNameInput(eq.getName());
                        view.setDescInput(eq.getDescription());
                    }
                }
            }
        });

        // Create Button Action
        view.getCreateButton().addActionListener(e -> {
            try {
                String name = view.getNameInput();
                String desc = view.getDescInput();
                
                Equipment eq = new Equipment(0, name, desc);
                repository.save(eq);
                
                refreshTable();
                clearForm();
                view.setStatus("Created successfully.");
            } catch (Exception ex) {
                view.setStatus("Error: " + ex.getMessage());
            }
        });

        // Update Button Action
        view.getUpdateButton().addActionListener(e -> {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow == -1) {
                view.setStatus("Nothing selected.");
                return;
            }
            try {
                int modelRow = view.getTable().convertRowIndexToModel(selectedRow);
                List<Equipment> items = repository.findAll();
                Equipment eq = items.get(modelRow);

                eq.setName(view.getNameInput());
                eq.setDescription(view.getDescInput());
                repository.save(eq);

                refreshTable();
                clearForm();
                view.setStatus("Updated successfully.");
            } catch (Exception ex) {
                view.setStatus("Error: " + ex.getMessage());
            }
        });

        // Delete Button Action
        view.getDeleteButton().addActionListener(e -> {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow == -1) {
                view.setStatus("Nothing selected.");
                return;
            }
            int modelRow = view.getTable().convertRowIndexToModel(selectedRow);
            List<Equipment> items = repository.findAll();
            Equipment eq = items.get(modelRow);

            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete " + eq.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                repository.deleteById(eq.getId());
                refreshTable();
                clearForm();
                view.setStatus("Deleted.");
            }
        });

        // Clear Button Action
        view.getClearButton().addActionListener(e -> {
            clearForm();
            view.getTable().clearSelection();
            view.setStatus("Cleared.");
        });
    }

    private void refreshTable() {
        view.getTableModel().updateData(repository.findAll());
    }

    private void clearForm() {
        view.setNameInput("");
        view.setDescInput("");
    }
}
