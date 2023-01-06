package com.orkes.cell.factory;

import com.orkes.cell.common.Cell;
import com.orkes.cell.specific.FormulaCell;

import java.util.Map;

public class FormulaCellFactory implements CellFactory {
    private final Map<String, Cell> cells;

    public FormulaCellFactory(Map<String, Cell> cells) {
        this.cells = cells;
    }

    @Override
    public FormulaCell createCell(Object value) {
        if (value instanceof String) {
            return new FormulaCell((String) value, cells);
        } else {
            throw new IllegalArgumentException("Invalid cell value: " + value);
        }
    }
}