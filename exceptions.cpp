#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <stdexcept>
#include <ctime>
#include <iomanip>

class UserInfoApp {
public:
    static void User() {
        // Создание объекта для ввода данных от пользователя
        std::string input;
        
        // Запрос данных у пользователя
        std::cout << "Enter your data (Surname, Name, MiddleName, DateOfBirth, PhoneNumber, Gender):" << std::endl;
        std::getline(std::cin, input); // Чтение строки ввода

        try {
            // Разделение строки ввода по пробелам
            std::vector<std::string> parts = split(input, ' ');

            // Проверка на количество введенных данных
            if (parts.size() != 6) {
                throw std::invalid_argument("Invalid number of arguments. Expected 6!");
            }

            // Извлечение и валидация каждой части
            std::string surname = parts[0];
            std::string name = parts[1];
            std::string middleName = parts[2];

            // Валидация даты рождения
            std::tm dateOfBirth = parseDate(parts[3]);

            // Валидация номера телефона
            long phoneNumber = parsePhoneNumber(parts[4]);

            // Валидация пола
            char gender = parseGender(parts[5]);

            // Запись данных в файл
            writeToFile(surname, name, middleName, dateOfBirth, phoneNumber, gender);
        } catch (const std::invalid_argument& e) {
            // Обработка исключения для неправильного количества данных или неверного формата
            std::cerr << "Error: " << e.what() << std::endl;
        } catch (const std::exception& e) {
            // Обработка исключения для неверного формата даты
            std::cerr << "Error: " << e.what() << std::endl;
        }
    }

private:
    // Метод для разбора и валидации даты
    static std::tm parseDate(const std::string& dateStr) {
        std::tm date = {};
        std::istringstream ss(dateStr);
        ss >> std::get_time(&date, "%d.%m.%Y");
        if (ss.fail()) {
            throw std::invalid_argument("Invalid date format. Please use dd.MM.yyyy.");
        }
        return date;
    }

    // Метод для разбора и валидации номера телефона
    static long parsePhoneNumber(const std::string& phoneNumberStr) {
        try {
            return std::stol(phoneNumberStr);
        } catch (const std::invalid_argument&) {
            throw std::invalid_argument("Invalid phone number format.");
        }
    }

    // Метод для разбора и валидации пола
    static char parseGender(const std::string& genderStr) {
        if (genderStr.length() != 1 || !(genderStr == "f" || genderStr == "m")) {
            throw std::invalid_argument("Invalid gender. Expected 'f' or 'm'.");
        }
        return tolower(genderStr[0]);
    }

    // Метод для записи данных в файл
    static void writeToFile(const std::string& surname, const std::string& name,
        const std::string& middleName, const std::tm& dateOfBirth, long phoneNumber, char gender) {
        std::string filename = surname + ".txt"; // Формирование имени файла
        // Формирование строки для записи
        std::ostringstream line;
        line << surname << " " << name << " " << middleName << " "
            << std::put_time(&dateOfBirth, "%d.%m.%Y") << " "
            << phoneNumber << " " << gender;

        // Запись строки в файл
        std::ofstream writer(filename, std::ios::app);
        if (writer.is_open()) {
            writer << line.str() << std::endl; // Переход на новую строку
        }
    }

    // Метод для разделения строки
    static std::vector<std::string> split(const std::string& str, char delimiter) {
        std::vector<std::string> tokens;
        std::istringstream tokenStream(str);
        std::string token;
        while (std::getline(tokenStream, token, delimiter)) {
            tokens.push_back(token);
        }
        return tokens;
    }
};

// Entry point for the application
int main()
{
    setlocale(LC_ALL,"rus");
    UserInfoApp::User();
    system("pause");
    return 0;
}
