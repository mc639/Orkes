package com.orkes.cell.specific;

import com.orkes.cell.common.Cell;

public class ConstantCell implements Cell {
    private final double value;

    public ConstantCell(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }
}