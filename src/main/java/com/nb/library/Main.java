package com.nb.library;

import com.nb.library.exception.DataLoadException;
import com.nb.library.model.Item;
import com.nb.library.model.Member;
import com.nb.library.service.Library;
import com.nb.library.ui.ConsoleMenu;
import com.nb.library.utils.ItemCsvLoader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Library library = new Library();
            ItemCsvLoader loader = new ItemCsvLoader();
            for (Item item : loader.load("items.csv")) {
                library.addItem(item);
            }

            Scanner scanner = new Scanner(System.in);
            Member member = askMember(scanner);

            new ConsoleMenu(library, member, scanner).run();
        } catch (DataLoadException e) {
            System.out.println("Impossible de charger les données : " + e.getMessage());
        }


    }
    private static Member askMember(Scanner scanner) {
        while (true) {
            System.out.print("Votre nom : ");
            try {
                return new Member(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }
}

