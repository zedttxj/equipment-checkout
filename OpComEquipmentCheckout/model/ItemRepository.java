package OpComEquipmentCheckout.model;

import java.util.List;

public interface ItemRepository {
  List<Item> findAll();

  List<Item> findAvailable();

  Item findById(int id);

  Item save(Item item); // insert if id == 0, else update

  void deleteById(int id);

  List<Reservation> findAllReservations();

  List<Reservation> findActiveReservations();

  List<Reservation> findPastReservations();

  void addReservation(Reservation reservation);
}
