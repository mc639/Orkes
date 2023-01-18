package com.orkes.cell.factory;

import com.orkes.cell.common.Cell;
import com.orkes.cell.specific.ConstantCell;

public class ConstantCellFactory implements CellFactory {
    @Override
    public Cell createCell(Object value) {
        if (value instanceof Integer) {
            return new ConstantCell((int) value);
        } else if (value instanceof Double || value instanceof Float) {
            return new ConstantCell((double) value);
        }else {
            throw new IllegalArgumentException("Invalid cell value: " + value);
        }
    }
}
