package com.example.tetris;

import android.graphics.Color;

public class L extends Piece {
    L() {
        super(2, 3, Color.GRAY);
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
        switch (getRotation()) {
            case 0:
                for (int c = 0; c < getColumnSize(); c++) {
                    setPosition(0, c, true);
                }
                setPosition(1, 0, true);
                break;
            case 1:
                for (int r = 0; r < getRowSize(); r++) {
                    setPosition(r, 1, true);
                }
                setPosition(0, 0, true);
                break;
            case 2:
                for (int c = 0; c < getColumnSize(); c++) {
                    setPosition(1, c, true);
                }
                setPosition(0, 2, true);
                break;
            default:
                for (int r = 0; r < getRowSize(); r++) {
                    setPosition(r, 0, true);
                }
                setPosition(2, 1, true);
                break;
        }
        updateBottom();
    }
}
