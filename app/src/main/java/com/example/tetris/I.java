package com.example.tetris;

import android.graphics.Color;

public class I extends Piece {
    I() {
        super(4, 1, Color.CYAN);
    }

    public void updatePosition() {
        if (getRotation() == 0 || getRotation() == 2) {
            setRowSize(4);
            setColumnSize(1);
        } else {
            setRowSize(1);
            setColumnSize(4);
        }
        shape = new boolean[getRowSize()][getColumnSize()];
        for (int r = 0; r < getRowSize(); r++) {
            for (int c = 0; c < getColumnSize(); c++) {
                setPosition(r, c, true);
            }
        }
        updateBottom();
    }
}
