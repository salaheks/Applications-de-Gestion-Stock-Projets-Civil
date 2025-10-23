package ma.projet.util;

import java.util.ArrayList;
import java.util.List;

public class TableFormatter {
    private static final String HORIZONTAL_LINE = "─";
    private static final String VERTICAL_LINE = "│";
    private static final String TOP_LEFT = "┌";
    private static final String TOP_RIGHT = "┐";
    private static final String BOTTOM_LEFT = "└";
    private static final String BOTTOM_RIGHT = "┘";
    private static final String T_DOWN = "┬";
    private static final String T_UP = "┴";
    private static final String T_RIGHT = "├";
    private static final String T_LEFT = "┤";
    private static final String CROSS = "┼";

    public static void printTable(String[] headers, List<String[]> rows) {
        if (headers == null || headers.length == 0 || rows == null) {
            return;
        }

        // Calculer la largeur maximale pour chaque colonne
        int[] colWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null) {
                    colWidths[i] = Math.max(colWidths[i], row[i].length());
                }
            }
        }

        // Imprimer la ligne supérieure
        printLine(colWidths, TOP_LEFT, TOP_RIGHT, T_DOWN, HORIZONTAL_LINE);

        // Imprimer les en-têtes
        printRow(headers, colWidths);

        // Imprimer la ligne de séparation
        printLine(colWidths, T_RIGHT, T_LEFT, CROSS, HORIZONTAL_LINE);

        // Imprimer les données
        for (String[] row : rows) {
            printRow(row, colWidths);
        }

        // Imprimer la ligne inférieure
        printLine(colWidths, BOTTOM_LEFT, BOTTOM_RIGHT, T_UP, HORIZONTAL_LINE);
    }

    private static void printLine(int[] colWidths, String left, String right, String separator, String horizontal) {
        System.out.print(left);
        for (int i = 0; i < colWidths.length; i++) {
            System.out.print(horizontal.repeat(colWidths[i] + 2));
            System.out.print(i < colWidths.length - 1 ? separator : right);
        }
        System.out.println();
    }

    private static void printRow(String[] row, int[] colWidths) {
        System.out.print(VERTICAL_LINE);
        for (int i = 0; i < row.length; i++) {
            String cell = row[i] != null ? row[i] : "";
            System.out.print(" " + String.format("%-" + colWidths[i] + "s", cell) + " ");
            System.out.print(VERTICAL_LINE);
        }
        System.out.println();
    }
}