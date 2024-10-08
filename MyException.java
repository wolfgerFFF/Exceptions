import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInfoApp {
    public static void main(String[] args) {
        User();
    }

    public static void User() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter your data (Surname,Name,MiddleName,DateOfBirth,PhoneNumber,Gender):");
        String input = scanner.nextLine(); 
        try {
            List<String> parts = split(input, ' ');
                if (parts.size() != 6) {
                throw new IllegalArgumentException("Invalid number of arguments. Expected 6.");
            }
            String surname = parts.get(0);
            String name = parts.get(1);
            String middleName = parts.get(2);
            
            Date dateOfBirth = parseDate(parts.get(3));
            long phoneNumber = parsePhoneNumber(parts.get(4));
            char gender = parseGender(parts.get(5));
            
            writeToFile(surname, name, middleName, dateOfBirth, phoneNumber, gender);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
           System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

      private static Date parseDate(String dateStr) throws IllegalArgumentException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(false);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use dd.MM.yyyy.");
        }
    }
       private static long parsePhoneNumber(String phoneNumberStr) throws IllegalArgumentException {
        try {
            return Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }
    }

    private static char parseGender(String genderStr) throws IllegalArgumentException {
        if (genderStr.length() != 1 || !(genderStr.equals("f") || genderStr.equals("m"))) {
            throw new IllegalArgumentException("Invalid gender. Expected 'f' or 'm'.");
        }
        return Character.toLowerCase(genderStr.charAt(0));
    }

    private static void writeToFile(String surname, String name, String middleName, Date dateOfBirth, long phoneNumber, char gender) {
        String filename = surname + ".txt"; 
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String line = surname + " " + name + " " + middleName + " " + sdf.format(dateOfBirth) + " " + phoneNumber + " " + gender;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine(); 
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static List<String> split(String str, char delimiter) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == delimiter) {
                tokens.add(token.toString());
                token.setLength(0); 
            } else {
                token.append(c);
            }
        }
        tokens.add(token.toString());
      return tokens;
    }
}

