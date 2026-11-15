package com.nb.library.utils;

import com.nb.library.exception.DataLoadException;
import com.nb.library.model.Book;
import com.nb.library.model.DVD;
import com.nb.library.model.Item;
import com.nb.library.model.Magazine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ItemCsvLoader {

    public List<Item> load(String resourceName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (stream == null) {
            throw new DataLoadException("Fichier introuvable : " + resourceName);
        }

        List<Item> items = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                items.add(parseLine(line, lineNumber));
            }
        } catch (IOException e) {
            throw new DataLoadException("Erreur de lecture du fichier " + resourceName, e);
        }

        return items;
    }

    private Item parseLine(String line, int lineNumber) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new DataLoadException("Ligne " + lineNumber + " invalide : " + line);
        }

        String type = parts[0].trim();
        String id = parts[1].trim();
        String title = parts[2].trim();
        String extra = parts[3].trim();

        try {
            return switch (type) {
                case "BOOK" -> new Book(id, title, extra);
                case "DVD" -> new DVD(id, title, Integer.parseInt(extra));
                case "MAGAZINE" -> new Magazine(id, title, Integer.parseInt(extra));
                default -> throw new DataLoadException(
                        "Ligne " + lineNumber + " : type inconnu '" + type + "'");
            };
        } catch (IllegalArgumentException e) {
            throw new DataLoadException("Ligne " + lineNumber + " : " + e.getMessage(), e);
        }
    }
}
