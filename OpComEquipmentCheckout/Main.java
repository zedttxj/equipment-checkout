package OpComEquipmentCheckout;

import OpComEquipmentCheckout.model.Equipment;
import OpComEquipmentCheckout.model.InMemoryItemRepository;
import OpComEquipmentCheckout.model.ItemRepository;
import OpComEquipmentCheckout.model.Reservation;

import java.util.Date;

public class Main {
  public static void main(String[] args) {
    ItemRepository repo = new InMemoryItemRepository();

    // 1. CREATE (id == 0 triggers auto-ID assignment in save)
    Equipment camera = repo.save(new Equipment(0, "DSLR Camera", "Canon EOS body with kit lens"));
    Equipment tripod = repo.save(new Equipment(0, "Tripod", "Lightweight aluminum tripod"));
    System.out
        .println("Created: id=" + camera.getId() + ", name=" + camera.getName() + ", desc=" + camera.getDescription());
    System.out
        .println("Created: id=" + tripod.getId() + ", name=" + tripod.getName() + ", desc=" + tripod.getDescription());

    // 2. LIST (Read all)
    System.out.println("\n--- All Equipment ---");
    for (Equipment eq : repo.findAll()) {
      System.out.println("ID: " + eq.getId() + " | Name: " + eq.getName() + " | Desc: " + eq.getDescription());
    }

    // 3. UPDATE (pass existing id so save replaces it)
    Equipment updatedCamera = new Equipment(camera.getId(), "DSLR Camera Pro", "Updated description");
    repo.save(updatedCamera);
    Equipment fetchedUpdated = repo.findById(camera.getId());
    System.out.println("\nUpdated Equipment -> ID: " + fetchedUpdated.getId()
        + " | Name: " + fetchedUpdated.getName()
        + " | Desc: " + fetchedUpdated.getDescription());

    // 4. DELETE
    System.out.println("\nDeleting Equipment ID " + tripod.getId() + "...");
    repo.deleteById(tripod.getId());
    System.out.println("Find deleted by ID: " + repo.findById(tripod.getId()));

    System.out.println("\n--- Remaining Equipment ---");
    for (Equipment eq : repo.findAll()) {
      System.out.println("ID: " + eq.getId() + " | Name: " + eq.getName() + " | Desc: " + eq.getDescription());
    }

    // Optional: Test a reservation & availability check
    repo.addReservation(new Reservation(1, 101, camera.getId(), new Date(), null, "Standard", "Weekend shoot"));
    System.out.println("\nActive Reservations: " + repo.findActiveReservations());
    System.out.println("Available Equipment: " + repo.findAvailable());
  }
}
