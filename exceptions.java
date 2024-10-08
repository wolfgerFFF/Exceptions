
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserInfoApp {
    public static void main(String[] args) {
// Создание объекта Scanner для ввода данных от пользователя
    Scanner scanner = new Scanner(System.in);
// Запрос данных у пользователя
    System.out.println("Enter your data (Surname Name MiddleNameDateOfBirth PhoneNumber Gender):");
    String input = scanner.nextLine(); // Чтение строки ввода
    scanner.close(); // Закрытие сканера
try {
// Разделение строки ввода по пробелам
    String[] parts = input.split(" ");
// Проверка на количество введенных данных
    if (parts.length != 6) {
        throw new IllegalArgumentException("Invalid number of arguments. Expected 6.");
    }
// Извлечение и валидация каждой части
    String surname = parts[0];
    String name = parts[1];
    String middleName = parts[2];
// Валидация даты рождения
    LocalDate dateOfBirth = parseDate(parts[3]);
// Валидация номера телефона
    long phoneNumber = parsePhoneNumber(parts[4]);
// Валидация пола
    char gender = parseGender(parts[5]);
// Запись данных в файл
    writeToFile(surname, name, middleName, dateOfBirth,phoneNumber, gender);
	
   } catch (IllegalArgumentException e) {
// Обработка исключения для неправильного количестваданных или неверного формата
    System.err.println("Error: " + e.getMessage());
   } catch (DateTimeParseException e) {
// Обработка исключения для неверного формата даты
   System.err.println("Error: Invalid date format. Please use dd.MM.yyyy.");
   } catch (IOException e) {
// Вывод стектрейса в случае ошибок ввода/вывода
   e.printStackTrace();
   }
}
// Метод для разбора и валидации даты
private static LocalDate parseDate(String dateStr) throws
     DateTimeParseException {
     DateTimeFormatter formatter =
     DateTimeFormatter.ofPattern("dd.MM.yyyy");
      return LocalDate.parse(dateStr, formatter);
    }
// Метод для разбора и валидации номера телефона
private static long parsePhoneNumber(String phoneNumberStr) {
    try {
          return Long.parseLong(phoneNumberStr);
    } catch (NumberFormatException e) {
    throw new IllegalArgumentException("Invalid phone number format.");
   }
}
// Метод для разбора и валидации пола
private static char parseGender(String genderStr) {
// Проверка длины строки и допустимых значений
   if (genderStr.length() != 1 ||!(genderStr.equalsIgnoreCase("f") || genderStr.equalsIgnoreCase("m"))) {
        throw new IllegalArgumentException("Invalid gender.Expected 'f' or 'm'.");
    }
  return genderStr.toLowerCase().charAt(0);
}
// Метод для записи данных в файл
private static void writeToFile(String surname, String name,String middleName, LocalDate dateOfBirth,long phoneNumber, char gender)
   throws IOException {
      String filename = surname + ".txt"; // Формирование имени файла
// Формирование строки для записи
      String line = String.format("%s %s %s %s %d %c", surname,name, middleName, dateOfBirth, phoneNumber, gender);
// Запись строки в файл
    try (BufferedWriter writer = new BufferedWriter(newFileWriter(filename, true))) {
       writer.write(line);
       writer.newLine(); // Переход на новую строку
    }
  }
}
