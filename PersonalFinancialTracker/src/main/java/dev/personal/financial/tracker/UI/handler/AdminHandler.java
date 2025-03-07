package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.admin.AdminController;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class AdminHandler {
    private final AdminController adminController;
    private Scanner sc;

    public void viewUsers() {
        adminController.getAllUsers().forEach(System.out::println);
    }

    public void blockUser() {
        System.out.println("Введите id пользователя, которого хотите заблокировать:");
        String userId = sc.next();
        adminController.blockUser(userId);
    }

    public void deleteUser() {
        System.out.println("Введите id пользователя, которого хотите удалить:");
        String userId = sc.next();
        adminController.deleteUser(userId);
    }

}