#ifndef RESERVATION_H
#define RESERVATION_H

#include <string>
#include <optional>

class Reservation {
private:
    int id;
    int userId;
    int equipmentId;
    std::string startDate;
    std::optional<std::string> endDate; // C++ equivalent of nullable Date
    std::string reservationType;
    std::string description;

public:
    Reservation(int id, int userId, int equipmentId, std::string startDate, 
                std::optional<std::string> endDate, std::string reservationType, std::string description)
        : id(id), userId(userId), equipmentId(equipmentId), startDate(startDate), 
          endDate(endDate), reservationType(reservationType), description(description) {}

    int getId() const { return id; }
    void setId(int id) { this->id = id; }

    int getUserId() const { return userId; }
    void setUserId(int userId) { this->userId = userId; }

    int getEquipmentId() const { return equipmentId; }
    void setEquipmentId(int equipmentId) { this->equipmentId = equipmentId; }

    std::string getStartDate() const { return startDate; }
    void setStartDate(const std::string& startDate) { this->startDate = startDate; }

    std::optional<std::string> getEndDate() const { return endDate; }
    void setEndDate(std::optional<std::string> endDate) { this->endDate = endDate; }

    std::string getReservationType() const { return reservationType; }
    void setReservationType(const std::string& reservationType) { this->reservationType = reservationType; }

    std::string getDescription() const { return description; }
    void setDescription(const std::string& description) { this->description = description; }

    std::string toString() const {
        return "Reservation #" + std::to_string(id) + " (Equipment ID: " + std::to_string(equipmentId) + ")";
    }
};

#endif // RESERVATION_H
