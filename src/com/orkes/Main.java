package com.orkes;

public class Main {

    public static void main(String[] args) {
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setCellValue("A1", 5);
        spreadsheet.setCellValue("A2", 10);
        spreadsheet.setCellValue("A3", "= (A2 / A1) * A2 + 23");
        System.out.println(spreadsheet.getCellValue("A3")); // 43.0
    }
}
