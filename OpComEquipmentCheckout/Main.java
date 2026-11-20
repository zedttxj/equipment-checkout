package OpComEquipmentCheckout;

import java.util.List;
import OpComEquipmentCheckout.model.Item;
import OpComEquipmentCheckout.model.InMemoryItemRepository;
import OpComEquipmentCheckout.model.ItemRepository;

public class Main {
  public static void main(String[] args) {
    ItemRepository repo = new InMemoryItemRepository();

    // 1. CREATE
    System.out.println("=== 1. CREATE RECORDS ===");
    Item e1 = repo.save(new Item(0, "Digital Oscilloscope", "Tektronix 100MHz 4-Channel"));
    System.out.println("Created -> " + e1);

    Item e2 = repo.save(new Item(0, "Bench Multimeter", "Fluke True-RMS Digital Multimeter"));
    System.out.println("Created -> " + e2);

    Item e3 = repo.save(new Item(0, "DC Power Supply", "Rigol 0-30V 5A Triple Output"));
    System.out.println("Created -> " + e3);

    // Test validation on bad input
    System.out.println("\n=== VALIDATION CHECK ===");
    try {
      repo.save(new Item(0, "   ", "Invalid blank name"));
    } catch (IllegalArgumentException ex) {
      System.out.println("Rejected invalid equipment as expected: " + ex.getMessage());
    }

    // 2. LIST (READ ALL)
    System.out.println("\n=== 2. LIST ALL RECORDS ===");
    printItems(repo.findAll());

    // 3. UPDATE
    System.out.println("\n=== 3. UPDATE RECORD ===");
    Item toUpdate = repo.findById(e2.getId());
    if (toUpdate != null) {
      toUpdate.setName("Calibrated Bench Multimeter");
      toUpdate.setDescription("Fluke 8845A - Calibrated Nov 2026");
      Item updated = repo.save(toUpdate);
      System.out.println("Updated -> " + updated);
    }

    System.out.println("\n=== LIST AFTER UPDATE ===");
    printItems(repo.findAll());

    // 4. DELETE
    System.out.println("\n=== 4. DELETE RECORD ===");
    int deleteId = e1.getId();
    repo.deleteById(deleteId);
    System.out.println("Deleted ID #" + deleteId + ". Lookup result: " + repo.findById(deleteId));

    System.out.println("\n=== LIST AFTER DELETE ===");
    printItems(repo.findAll());
  }

  private static void printItems(List<Item> items) {
    if (items.isEmpty()) {
      System.out.println("  (No records found)");
      return;
    }
    for (Item item : items) {
      System.out.println("  " + item);
    }
  }
}
