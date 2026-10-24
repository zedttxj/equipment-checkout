package yourproject.model;

import java.util.ArrayList;
import java.util.List;

public class InMemoryItemRepository implements ItemRepository {
  private final List<Equipment> items = new ArrayList<>();
  private int nextId = 1;
  private List<Reservation> reservations = new ArrayList<>();

  @Override
  public List<Reservation> findAllReservations() {
    return new ArrayList<>(reservations);
  }

  @Override
  public void addReservation(Reservation reservation) {
    reservations.add(reservation);
  }

  @Override
  public List<Equipment> findAll() {
    return new ArrayList<>(items); // Return a defensive copy (Unit 7)
  }

  @Override
  public List<Equipment> findAvailable() {
    // 1. Get IDs of equipment that have an active reservation (endDate is null)
    List<Integer> activeReservedIds = reservations.stream()
        .filter(r -> r.getEndDate() == null)
        .map(Reservation::getEquipmentId)
        .toList();

    // 2. Return items whose IDs are not currently actively reserved
    return items.stream()
        .filter(item -> !activeReservedIds.contains(item.getId()))
        .toList();
  }

  @Override
  public List<Reservation> findActiveReservations() {
    return reservations.stream()
        .filter(r -> r.getEndDate() == null)
        .toList();
  }

  @Override
  public List<Reservation> findPastReservations() {
    return reservations.stream()
        .filter(r -> r.getEndDate() != null)
        .toList();
  }

  @Override
  public Equipment findById(int id) {
    return items.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
  }

  @Override
  public Equipment save(Equipment item) {
    if (item.getId() == 0) {
      item.setId(nextId++);
      items.add(item);
    } else {
      deleteById(item.getId());
      items.add(item);
    }
    return item;
  }

  @Override
  public void deleteById(int id) {
    items.removeIf(i -> i.getId() == id);
  }
}
