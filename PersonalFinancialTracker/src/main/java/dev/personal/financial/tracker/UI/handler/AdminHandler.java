package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.admin.AdminController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminHandler {
    private final AdminController adminController;
    private final ConsolePrinter printer;

    public void viewUsers() {
        adminController.getAllUsers().forEach(System.out::println);
    }

    public void blockUser() {
        int userId = printer.readInt("Введите id пользователя, которого хотите заблокировать:");
//        if (userId == null) {
//            printer.printInfo("Блокировка пользователя отменена.");
//            return;
//        }
        adminController.blockUser(userId);
        printer.printSuccess("Пользователь заблокирован");
    }

    public void deleteUser() {
        int userId = printer.readInt("Введите id пользователя, которого хотите удалить:");
//        if (userId == null) {
//            printer.printInfo("Удаление пользователя отменено.");
//            return;
//        }
        adminController.deleteUser(userId);
        printer.printSuccess("Пользователь удален");
    }
}