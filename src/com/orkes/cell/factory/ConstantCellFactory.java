package com.orkes.cell.factory;

import com.orkes.cell.common.Cell;
import com.orkes.cell.specific.ConstantCell;

public class ConstantCellFactory implements CellFactory {
    @Override
    public Cell createCell(Object value) {
        if (value instanceof Integer) {
            return new ConstantCell((int) value);
        } else {
            throw new IllegalArgumentException("Invalid cell value: " + value);
        }
    }
}
