package com.orkes;

import com.orkes.cell.common.Cell;
import com.orkes.cell.factory.CellFactory;
import com.orkes.cell.factory.ConstantCellFactory;
import com.orkes.cell.factory.FormulaCellFactory;

import java.util.HashMap;
import java.util.Map;

public class Spreadsheet {
    private final Map<String, Cell> cells;
    private final Map<Class<?>, CellFactory> cellFactories;

    public Spreadsheet(){
        this.cells = new HashMap<>();
        this.cellFactories = new HashMap<>();
        registerCellFactory(Integer.class, new ConstantCellFactory());
        registerCellFactory(String.class, new FormulaCellFactory(cells));
    }

    public void registerCellFactory(Class<?> type, CellFactory factory) {
        cellFactories.put(type, factory);
    }

    public void setCellValue(String cellId, Object value) {
        // Get the cell factory for the value's type
        CellFactory factory = cellFactories.get(value.getClass());
        if (factory == null) {
            throw new IllegalArgumentException("Invalid cell value type: " + value.getClass());
        }

        // Create the cell
        Cell cell = factory.createCell(value);

        // Add the cell to the cells map
        cells.put(cellId, cell);
    }

    public int getCellValue(String cellId) {
        // Get the cell
        Cell cell = cells.get(cellId);
        if (cell == null) {
            throw new IllegalArgumentException("Invalid cell ID: " + cellId);
        }

        // Return the cell value
        return cell.getValue();
    }
}