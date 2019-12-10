package com.example.tetris;

import android.graphics.Color;

import java.util.ArrayList;

public class Board {
    private boolean[][] board;
    private int[][] boardColor;
    public final int BOARD_ROWS = 24;
    public final int BOARD_COLUMNS = 10;
    private int score;
    private Piece piece;
    private boolean gameState;

    Board() {
        board = new boolean[BOARD_ROWS][BOARD_COLUMNS];
        boardColor = new int[BOARD_ROWS][BOARD_COLUMNS];
    }

    public void startGame() {
        gameState = true;
        addPiece();
    }

    private void addPiece() {
        for (int c = 0; c < BOARD_COLUMNS; c++) {
            if (board[BOARD_ROWS - 1][c]) {
                endGame();
                break;
            }
        }
        switch((int) (Math.random() * 6)) {
            case 0:
                piece = new I();
                break;
            case 1:
                piece = new O();
                break;
            case 2:
                piece = new T();
                break;
            case 3:
                piece = new L();
                break;
            case 4:
                piece = new J();
                break;
            case 5:
                piece = new Z();
                break;
            default:
                piece = new S();
                break;
        }
        for (int c = piece.getColumnPivot(); c < piece.getBottom().length + piece.getColumnPivot(); c++) {
            if (board[piece.getRowPivot() - piece.getBottom()[c - piece.getColumnPivot()]][c]) {
                endGame();
                break;
            }
        }
        int tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (piece.getPosition(tempR, tempC)) {
                    board[r][c] = true;
                }
                tempC++;
            }
            tempR++;
        }
        updateColorBoard();
    }

    private void updateBoard() {
        ArrayList<Integer> rows = new ArrayList<>();
        for (int r = 0; r < BOARD_ROWS; r++) {
            boolean temp = true;
            for (int c = 0; c < BOARD_COLUMNS; c++) {
                if (!board[r][c]) {
                    temp = false;
                    break;
                }
            }
            if (temp) {
                rows.add(0, r);
            }
        }
        if (rows.size() == 0) {
            addPiece();
            updateColorBoard();
            return;
        }
        for (int r = 0; r < rows.size(); r++) {
            for (int c = 0; c < BOARD_COLUMNS; c++) {
                board[rows.get(r)][c] = false;
            }
        }
        int tempScore = rows.size() * rows.size();
        score += tempScore;
        boolean valid = true;
        do {
            for (int r = 1; r < BOARD_ROWS; r++) {
                for (int c = 0; c < BOARD_COLUMNS; c++) {
                    if (board[r][c] && !board[r - 1][c]) {
                        board[r][c] = false;
                        board[r - 1][c] = true;
                    }
                }
            }
            for (int c = 0; c < BOARD_COLUMNS; c++) {
                if (board[0][c]) {
                    valid = false;
                    break;
                }
            }
        } while (valid);
        addPiece();
        updateColorBoard();
    }

    public void fall() {
        if (piece.getRowPivot() - piece.getRowSize() < 0) {
            updateBoard();
            return;
        }
        int[] bottom = piece.getBottom();
        boolean valid = true;
        int i = 0;
        for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
            if (board[piece.getRowPivot() - bottom[c - piece.getColumnPivot()] - 1][c]) {
                valid = false;
            }
            i++;
        }
        if (!valid) {
            updateBoard();
            return;
        }
        int tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (piece.getPosition(tempR, c - piece.getColumnPivot())) {
                    board[r][c] = false;
                }
            }
            tempR++;
        }
        piece.setRowPivot(piece.getRowPivot() - 1);
        tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (piece.getPosition(tempR, tempC)) {
                    board[r][c] = true;
                }
                tempC++;
            }
            tempR++;
        }
        valid = true;
        i = 0;
        for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
            if (piece.getRowPivot() - bottom[c - piece.getColumnPivot()] <= 0 || board[piece.getRowPivot() - bottom[c - piece.getColumnPivot()] - 1][c]) {
                valid = false;
            }
            i++;
        }
        if (!valid) {
            updateBoard();
            return;
        }
        updateColorBoard();
    }

    public void moveLeft() {
        if (piece.getColumnPivot() - 1 < 0 || board[piece.getRowPivot()][piece.getColumnPivot() - 1]) {
            return;
        }
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                board[r][c - 1] = board[r][c];
                if (c == piece.getColumnPivot() + piece.getColumnSize() - 1) {
                    board[r][c] = false;
                }
            }
        }
        piece.setColumnPivot(piece.getColumnPivot() - 1);
        updateColorBoard();
    }

    public void moveRight() {
        if (piece.getColumnPivot() + piece.getColumnSize() >= BOARD_COLUMNS || board[piece.getRowPivot()][piece.getColumnPivot() + piece.getColumnSize()]) {
            return;
        }
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            for (int c = piece.getColumnPivot() + piece.getColumnSize(); c >= piece.getColumnPivot(); c--) {
                if (c != 0) {
                    board[r][c] = board[r][c - 1];
                } else {
                    board[r][c] = false;
                }
            }
        }
        piece.setColumnPivot(piece.getColumnPivot() + 1);
        updateColorBoard();
    }

    public void rotateLeft() {
        int tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (!piece.getPosition(tempR, tempC) && board[r][c]) {
                    board[r][c] = true;
                } else {
                    board[r][c] = false;
                }
                tempC++;
            }
            tempR++;
        }
        piece.rotateLeft();
        boolean canRotate = true;
        if (piece.getColumnPivot() + piece.getColumnSize() >= BOARD_COLUMNS || piece.getColumnPivot() - piece.getColumnSize() < 0) {
            canRotate = false;
        }
        tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (piece.getPosition(tempR, tempC) && board[r][c]) {
                    canRotate = false;
                    break;
                }
                tempC++;
            }
            if (!canRotate) {
                break;
            }
            tempR++;
        }
        if (!canRotate) {
            piece.rotateRight();
        }
        tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (piece.getPosition(tempR, tempC)) {
                    board[r][c] = true;
                }
                tempC++;
            }
            tempR++;
        }
    }

    public void rotateRight() {
        int tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (!piece.getPosition(tempR, tempC) && board[r][c]) {
                    board[r][c] = true;
                } else {
                    board[r][c] = false;
                }
                tempC++;
            }
            tempR++;
        }
        piece.rotateRight();
        boolean canRotate = true;
        if (piece.getColumnPivot() + piece.getColumnSize() >= BOARD_COLUMNS || piece.getColumnPivot() - piece.getColumnSize() < 0) {
            canRotate = false;
        }
        tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (piece.getPosition(tempR, tempC) && board[r][c]) {
                    canRotate = false;
                    break;
                }
                tempC++;
            }
            if (!canRotate) {
                break;
            }
            tempR++;
        }
        if (!canRotate) {
            piece.rotateLeft();
        }
        tempR = 0;
        for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
            int tempC = 0;
            for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                if (piece.getPosition(tempR, tempC)) {
                    board[r][c] = true;
                }
                tempC++;
            }
            tempR++;
        }
    }

    public void updateColorBoard() {
        if (gameState) {
            for (int r = BOARD_ROWS - 1; r >= 0; r--) {
                for (int c = 0; c < BOARD_COLUMNS; c++) {
                    if (board[r][c]) {
                        boardColor[r][c] = Color.DKGRAY;
                    } else {
                        boardColor[r][c] = Color.WHITE;
                    }
                }
            }
            int tempR = 0;
            for (int r = piece.getRowPivot(); r > piece.getRowPivot() - piece.getRowSize(); r--) {
                for (int c = piece.getColumnPivot(); c < piece.getColumnPivot() + piece.getColumnSize(); c++) {
                    if (board[r][c] && !piece.getPosition(tempR, c - piece.getColumnPivot())) {
                        boardColor[r][c] = Color.DKGRAY;
                    } else if (board[r][c]) {
                        boardColor[r][c] = piece.getColor();
                    } else {
                        boardColor[r][c] = Color.WHITE;
                    }
                }
                tempR++;
            }
        } else {
            for (int r = 0; r < BOARD_ROWS; r++) {
                for (int c = 0; c < BOARD_COLUMNS; c++) {
                    if (board[r][c]) {
                        boardColor[r][c] = Color.BLACK;
                    } else {
                        boardColor[r][c] = Color.WHITE;
                    }
                }
            }
        }
    }

    public int getColor(int r, int c) {
        return boardColor[r][c];
    }

    public int getScore() {
        return score;
    }

    public boolean getPosition(int r, int c) {
        return board[r][c];
    }

    public boolean getGameState() {
        return gameState;
    }

    public void endGame() {
        gameState = false;
    }
}
