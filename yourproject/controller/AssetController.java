package yourproject.controller;

import yourproject.model.ItemRepository;
import yourproject.view.AssetManagementView;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AssetController {
    private final ItemRepository repository;
    private final AssetManagementView view;

    public AssetController(ItemRepository repository, AssetManagementView view) {
        this.repository = repository;
        this.view = view;

        initController();
    }

    private void initController() {
        // Implement real-time search filtering (matching React filterText state)
        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filterText = view.getSearchField().getText();
                // Filter repository data and refresh table model view
                var filtered = repository.findAll().stream()
                    .filter(eq -> eq.getName().toLowerCase().contains(filterText.toLowerCase()))
                    .toList();
                view.getTableModel().updateData(filtered);
            }
        });

        // Add Button Action (Opens Dialog Form)
        view.getAddButton().addActionListener(e -> {
            String name = JOptionPane.showInputDialog(view, "Enter Equipment Name:");
            if (name != null && !name.trim().isEmpty()) {
                // Create logic through repository model
                // repository.save(...)
                view.getTableModel().updateData(repository.findAll()); // Refresh view
            }
        });
    }
}
