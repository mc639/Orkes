package com.orkes;

public class Main {

    public static void main(String[] args) {
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setCellValue("A1", 13);
        spreadsheet.setCellValue("A2", 14);
        System.out.println(spreadsheet.getCellValue("A1")); // 13
        spreadsheet.setCellValue("A3", "=A1+A2");
        System.out.println(spreadsheet.getCellValue("A2")); // 14
        spreadsheet.setCellValue("A4", "=A1+A2+A3");
        System.out.println(spreadsheet.getCellValue("A3")); // 27
        System.out.println(spreadsheet.getCellValue("A4")); // 54
    }
}
