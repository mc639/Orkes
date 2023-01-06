package com.orkes.cell.specific;

import com.orkes.cell.common.Cell;

public class ConstantCell implements Cell {
    private final int value;

    public ConstantCell(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}