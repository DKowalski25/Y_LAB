package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.admin.AdminController;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class AdminHandler {
    private final AdminController adminController;
    private final Scanner sc;

    public void viewUsers() {
        adminController.getAllUsers().forEach(System.out::println);
    }

    public void blockUser() {
        System.out.println("Введите id пользователя, которого хотите заблокировать:");
        String userId = sc.next();
        adminController.blockUser(userId);
        System.out.println("Пользователь заблокирован");
    }

    public void deleteUser() {
        System.out.println("Введите id пользователя, которого хотите удалить:");
        String userId = sc.next();
        adminController.deleteUser(userId);
        System.out.println("Пользователь удален");
    }

}