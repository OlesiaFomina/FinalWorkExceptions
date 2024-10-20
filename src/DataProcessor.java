import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataProcessor {
    private static final Logger LOGGER = Logger.getLogger(DataProcessor.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();

        try {
            processInput(input);
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.err.println("Ошибка формата даты: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Ошибка записи в файл", e); // Используем LOGGER
        }
    }

    private static void processInput(String input) throws IllegalArgumentException, DateTimeParseException, IOException {
        String[] data = input.split(" ");

        // 1. Проверка количества элементов
        if (data.length != 6) {
            throw new IllegalArgumentException("Неверное количество элементов. Должно быть 6 элементов.");
        }

        // 2. Извлечение данных
        String surname = data[0];
        String name = data[1];
        String patronymic = data[2];
        LocalDate birthDate = LocalDate.parse(data[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        long phoneNumber = Long.parseLong(data[4]); // Не перехватываем NumberFormatException
        String gender = data[5];

        // 3. Проверка формата пола
        if (gender.length() != 1 || !(gender.equalsIgnoreCase("f") || gender.equalsIgnoreCase("m"))) {
            throw new IllegalArgumentException("Неверный формат пола. Допустимые значения: f или m.");
        }

        // 4. Запись данных в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(surname + ".txt", true))) {
            writer.write(String.format("%s%s%s%s %d%s\n", surname, name, patronymic, birthDate, phoneNumber, gender));
        }
    }
}
