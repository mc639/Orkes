package com.orkes.cell.specific;

import com.orkes.cell.common.Cell;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaCell implements Cell {
    private final String formula;
    private final Map<String, Cell> cells;

    public FormulaCell(String formula, Map<String, Cell> cells) {
        this.formula = formula;
        this.cells = cells;
    }

    @Override
    public int getValue() {
        // Parse the formula
        Matcher matcher = Pattern.compile("[A-Z]+[1-9][0-9]*").matcher(formula);
        int result = 0;
        while (matcher.find()) {
            // Get the cell reference
            String cellReference = matcher.group();

            // Get the cell value
            Cell cell = cells.get(cellReference);
            if (cell == null) {
                throw new IllegalArgumentException("Invalid cell reference: " + cellReference);
            }

            int cellValue = cell.getValue();

            // Add the cell value to the result
            result += cellValue;
        }
        return result;
    }
}
