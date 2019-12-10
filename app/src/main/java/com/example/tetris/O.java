package com.example.tetris;

import android.graphics.Color;

public class O extends Piece {
    O() {
        super(2, 2, Color.YELLOW);
    }

    public void updatePosition() {
        for (int r = 0; r < getRowSize(); r++) {
            for (int c = 0; c < getColumnSize(); c++) {
                setPosition(r, c, true);
            }
        }
        updateBottom();
    }
}
