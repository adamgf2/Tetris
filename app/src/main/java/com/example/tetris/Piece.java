package com.example.tetris;

public abstract class Piece {
    private int rotation, rowSize, columnSize, color, rowPivot, columnPivot;
    private int[] bottom;
    protected boolean[][] shape;

    Piece(int r, int c, int setColor) {
        rowSize = r;
        columnSize = c;
        color = setColor;
        shape = new boolean[r][c];
        bottom = new int[c];
        rotation = 0;
        rowPivot = 23;
        columnPivot = 4;
        updatePosition();
        updateBottom();
    }

    public void rotateLeft() {
        if (rotation == 0) {
            rotation = 3;
        } else {
            rotation--;
        }
        updatePosition();
    }

    public void rotateRight() {
        rotation = (rotation + 1) % 4;
        updatePosition();
    }

    public int getRotation() {
        return rotation;
    }

    public int getColor() {
        return color;
    }

    public void setRowPivot(int r) {
        rowPivot = r;
    }

    public int getRowPivot() {
        return rowPivot;
    }

    public void setColumnPivot(int c) {
        columnPivot = c;
    }

    public int getColumnPivot() {
        return columnPivot;
    }

    public void setRowSize(int r) {
        rowSize = r;
    }

    public void setColumnSize(int c) {
        columnSize = c;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setPosition(int r, int c, boolean state) {
        shape[r][c] = state;
    }

    public boolean getPosition(int r, int c) {
        return shape[r][c];
    }

    public void updateBottom() {
        bottom = new int[columnSize];
        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < columnSize; c++) {
                if (shape[r][c]) {
                    bottom[c] = r;
                }
            }
        }
    }

    public int[] getBottom() {
        return bottom;
    }

    public abstract void updatePosition();
}
