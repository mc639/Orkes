package com.orkes.cell.factory;

import com.orkes.cell.common.Cell;

public interface CellFactory {
    Cell createCell(Object value);
}
