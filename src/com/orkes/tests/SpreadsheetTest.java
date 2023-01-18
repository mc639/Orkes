package com.orkes.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.orkes.Spreadsheet;
import org.junit.jupiter.api.Test;

class SpreadsheetTest {

    @Test
    void testCellValue() {
        Spreadsheet spreadsheet = new Spreadsheet();

        // Test setting a cell value to a constant
        spreadsheet.setCellValue("A3", 15);
        double value = spreadsheet.getCellValue("A3");
        assertEquals(15, value);

        spreadsheet.setCellValue("A1", 42);
        value = spreadsheet.getCellValue("A1");
        assertEquals(42, value);

        // Test setting a cell value to a formula
        spreadsheet.setCellValue("A2", "= ((A1+A3) / A3) > 34");
        value = spreadsheet.getCellValue("A2");
        assertEquals(0, value);
    }

}
