package yourproject.model;

import java.util.Date;

public class Reservation {
  private int id;
  private int userId;
  private int equipmentId;
  private Date startDate;
  private Date endDate;
  private String reservationType;
  private String description;

  public Reservation(int id, int userId, int equipmentId, Date startDate, Date endDate, String reservationType,
      String description) {
    this.id = id;
    this.userId = userId;
    this.equipmentId = equipmentId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.reservationType = reservationType;
    this.description = description;
  }

  // Getters
  public int getId() {
    return id;
  }

  public int getUserId() {
    return userId;
  }

  public int getEquipmentId() {
    return equipmentId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getReservationType() {
    return reservationType;
  }

  public String getDescription() {
    return description;
  }

  // Setters
  public void setId(int id) {
    this.id = id;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setEquipmentId(int equipmentId) {
    this.equipmentId = equipmentId;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setReservationType(String reservationType) {
    this.reservationType = reservationType;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Reservation #" + id + " (Equipment ID: " + equipmentId + ")";
  }
}
