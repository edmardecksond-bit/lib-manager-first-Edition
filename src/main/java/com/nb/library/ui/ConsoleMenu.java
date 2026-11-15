package com.nb.library.ui;

import com.nb.library.exception.BorrowLimitExceededException;
import com.nb.library.exception.ItemNotFoundException;
import com.nb.library.model.Item;
import com.nb.library.model.Member;
import com.nb.library.service.Library;

import java.util.Scanner;

public class ConsoleMenu {

    private final Library library;
    private final Member member;
    private final Scanner scanner;

    public ConsoleMenu(Library library, Member member, Scanner scanner) {
        this.library = library;
        this.member = member;
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> library.displayCatalog();
                case "2" -> borrow();
                case "3" -> giveBack();
                case "4" -> showMyBorrows();
                case "0" -> running = false;
                default -> System.out.println("Choix invalide.");
            }
        }
        System.out.println("Au revoir !");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Bibliothèque (" + member.getName() + ") ===");
        System.out.println("1. Afficher le catalogue");
        System.out.println("2. Emprunter un document");
        System.out.println("3. Rendre un document");
        System.out.println("4. Voir mes emprunts");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private void borrow() {
        System.out.print("Identifiant du document à emprunter : ");
        String id = scanner.nextLine().trim();
        try {
            library.borrowItem(member, id);
            System.out.println("Emprunt réussi.");
        } catch (ItemNotFoundException | BorrowLimitExceededException | IllegalStateException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void giveBack() {
        System.out.print("Identifiant du document à rendre : ");
        String id = scanner.nextLine().trim();
        try {
            library.returnItem(member, id);
            System.out.println("Retour enregistré.");
        } catch (ItemNotFoundException | IllegalStateException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void showMyBorrows() {
        if (member.getBorrowedItems().isEmpty()) {
            System.out.println("Vous n'avez aucun emprunt.");
            return;
        }
        for (Item item : member.getBorrowedItems()) {
            System.out.println(item.describe());
        }
    }

}