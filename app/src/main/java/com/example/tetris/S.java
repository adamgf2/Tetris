package com.example.tetris;

import android.graphics.Color;

public class S extends Piece {
    S() {
        super(2, 3, Color.GREEN);
    }

    public void updatePosition() {
        if (getRotation() == 0 || getRotation() == 2) {
            setRowSize(2);
            setColumnSize(3);
        } else {
            setRowSize(3);
            setColumnSize(2);
        }
        shape = new boolean[getRowSize()][getColumnSize()];
        if (getRotation() == 0 || getRotation() == 2) {
            for (int c = 0; c < getColumnSize() - 1; c++) {
                setPosition(1, c, true);
            }
            for (int c = 1; c < getColumnSize(); c++) {
                setPosition(0, c, true);
            }
        } else {
            for (int r = 0; r < getRowSize() - 1; r++) {
                setPosition(r, 0, true);
            }
            for (int r = 1; r < getRowSize(); r++) {
                setPosition(r, 1, true);
            }
        }
        updateBottom();
    }
}
