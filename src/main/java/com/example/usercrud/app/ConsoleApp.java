package com.example.usercrud.app;

import com.example.usercrud.dao.Dao;
import com.example.usercrud.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApp {
    private final Scanner scanner;
    private final Dao<User> userDao;
    private static final Logger log = LoggerFactory.getLogger(ConsoleApp.class);

    public ConsoleApp(Scanner scanner, Dao<User> userDao) {
        this.scanner = scanner;
        this.userDao = userDao;
    }

    public void run() {
        System.out.println("""
                Приветствуем в приложении для осуществления CRUD операций над сущностью User!
                """);

        String input;

        do {
            System.out.println("""
                    1. [CREATE] Создать сущность User
                    2. [READ] Получить весь список сохраненных пользователей
                    3. [READ] Получить сущность User по Id
                    4. [UPDATE] Обновить сущность User
                    5. [DELETE] Удалить сущность User
                    
                    0. Выйти
                    """);

            input = scanner.nextLine().trim();

            try {
                switch (input) {
                    case "1":
                        User userToSave = getInputUser();
                        userDao.save(userToSave);
                        System.out.printf("Пользователь успешно сохранен: %s%n", userToSave);
                        break;
                    case "2":
                        List<User> users = userDao.findAll();
                        if (users.isEmpty())
                            System.out.println("Список пользователей пуст");
                        else {
                            for (int i = 0; i < users.size(); i++) {
                                System.out.printf("%2d. %s%n", i + 1, users.get(i));
                            }
                        }
                        break;
                    case "3":
                        System.out.println("Какого пользователя вы хотите найти?");
                        Long idToFind = getInputUserId();
                        Optional<User> userById = userDao.findById(idToFind);
                        if (userById.isPresent())
                            System.out.printf("Пользователь успешно найден: %s%n", userById.get());
                        else {
                            log.warn("User not found: {}", idToFind);
                            System.out.printf("Пользователь с Id=%s не найден%n", idToFind);
                        }
                        break;
                    case "4":
                        System.out.println("Какого пользователя вы хотите обновить?");
                        Long idToUpdate = getInputUserId();
                        Optional<User> userToUpdate = userDao.findById(idToUpdate);

                        if (userToUpdate.isPresent()) {
                            User userUpdates = getInputUserUpdates();

                            String name = userUpdates.getName();
                            String email = userUpdates.getEmail();
                            int age = userUpdates.getAge();

                            if (name != null)
                                userToUpdate.get().setName(name);
                            if (email != null)
                                userToUpdate.get().setEmail(email);
                            if (age != 0)
                                userToUpdate.get().setAge(age);

                            User updatedUser = userDao.update(userToUpdate.get());
                            System.out.printf("Пользователь успешно обновлен: %s%n", updatedUser);
                        }
                        else {
                            log.warn("User not found: {}", idToUpdate);
                            System.out.printf("Пользователь с Id=%s не найден%n", idToUpdate);
                        }
                        break;
                    case "5":
                        System.out.println("Какого пользователя вы хотите удалить?");
                        Long idToDelete = getInputUserId();
                        Optional<User> userToDelete = userDao.findById(idToDelete);
                        if (userToDelete.isPresent()) {
                            userDao.delete(userToDelete.get());
                            System.out.printf("Пользователь успешно удален: %s%n", userToDelete.get());
                        }
                        else {
                            log.warn("User not found: {}", idToDelete);
                            System.out.printf("Пользователь с Id=%s не найден%n", idToDelete);
                        }
                        break;
                }
            } catch (Exception e) {
                log.error("Операция не выполнена", e);
                System.out.println("Ошибка работы с БД. Попробуйте снова. Если проблема появляется снова, попробуйте позже");
            }
        } while (!input.equals("0"));
    }

    private User getInputUser() {
        while (true) {
            System.out.print("Введите сущность User в формате name;email;age: ");

            String input = scanner.nextLine().trim();
            String[] fields = input.split(";", -1);

            if (fields.length != 3) {
                System.out.println("Некорректный формат. Попробуйте снова");
                continue;
            }

            User user = new User();

            try {
                user.setName(fields[0].trim());
                user.setEmail(fields[1].trim());
                user.setAge(Integer.parseInt(fields[2].trim()));
            }  catch (NumberFormatException e) {
                System.out.println("Возраст должен быть числом. Попробуйте снова");
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Попробуйте снова");
                continue;
            }

            return user;
        }
    }

    private Long getInputUserId() {
        while (true) {
            System.out.print("Введите Id пользователя: ");

            String input = scanner.nextLine().trim();

            Long id = null;
            try {
                id = Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Id должен быть числом. Попробуйте снова");
                continue;
            }

            return id;
        }
    }

    private User getInputUserUpdates() {
        System.out.print("""
                Примеры:
                    Обновление возраста: ";;20"
                    Обновление имени и почты: "newName;newmail@mail.com;"
                """);

        while (true) {
            System.out.print("Введите обновленные поля сущности User в формате name;email;age: ");

            String input = scanner.nextLine().trim();
            String[] fields = input.split(";", -1);

            if (fields.length != 3) {
                System.out.println("Некорректный формат. Попробуйте снова");
                continue;
            }

            String name = fields[0].trim();
            String email = fields[1].trim();
            String age = fields[2].trim();

            if (name.isEmpty() && email.isEmpty() && age.isEmpty()) {
                System.out.println("Ни одно поле не указано. Попробуйте снова");
                continue;
            }

            User userUpdates = new User();
            try {
                if (!name.isEmpty())
                    userUpdates.setName(name);
                if (!email.isEmpty())
                    userUpdates.setEmail(email);
                if (!age.isEmpty())
                    userUpdates.setAge(Integer.parseInt(age));

            }  catch (NumberFormatException e) {
                System.out.println("Возраст должен быть числом. Попробуйте снова");
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Попробуйте снова");
                continue;
            }

            return userUpdates;
        }
    }
}
