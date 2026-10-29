#ifndef EQUIPMENT_H
#define EQUIPMENT_H

#include <string>
#include <stdexcept>
#include <algorithm>

class Equipment {
private:
    int id;
    std::string name;
    std::string description;

public:
    Equipment(int id, std::string name, std::string description)
        : id(id), description(description) {
        setName(name);
    }

    int getId() const { return id; }
    void setId(int id) { this->id = id; }

    std::string getName() const { return name; }
    void setName(const std::string& name) {
        // Simple trim check equivalent
        std::string trimmed = name;
        trimmed.erase(trimmed.begin(), std::find_if(trimmed.begin(), trimmed.end(), [](unsigned char ch) {
            return !std::isspace(ch);
        }));
        if (trimmed.empty()) {
            throw std::invalid_argument("Name cannot be empty");
        }
        this->name = name;
    }

    std::string getDescription() const { return description; }
    void setDescription(const std::string& description) { this->description = description; }

    std::string toString() const {
        return name;
    }
};

#endif // EQUIPMENT_H
