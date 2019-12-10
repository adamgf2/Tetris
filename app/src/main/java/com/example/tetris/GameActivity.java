package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private Board board;
    private Timer timer;
    private TimerTask task;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        board = new Board();
        board.startGame();
        updateBoard();
        findViewById(R.id.moveLeft).setOnClickListener(unused -> moveLeft());
        findViewById(R.id.rotateLeft).setOnClickListener(unused -> rotateLeft());
        findViewById(R.id.moveDown).setOnClickListener(unused -> drop());
        findViewById(R.id.rotateRight).setOnClickListener(unused -> rotateRight());
        findViewById(R.id.moveRight).setOnClickListener(unused -> moveRight());
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                drop();
            }
        };
        timer.schedule(task, 0, 500);
    }

    public void updateBoard() {
        board.updateColorBoard();
        for (int r = board.BOARD_ROWS - 1; r >= 0; r--) {
            for (int c = 0; c < board.BOARD_COLUMNS; c++) {
                String cell = "r" + r + "_" + c;
                int tempID = getResources().getIdentifier(cell, "id", getPackageName());
                TextView temp = findViewById(tempID);
                temp.setBackgroundColor(board.getColor(r, c));
            }
        }
    }

    public void moveLeft() {
        board.moveLeft();
        updateBoard();
    }

    public void rotateLeft() {
        board.rotateLeft();
        updateBoard();
    }

    public void drop() {
        if (board.getGameState()) {
            board.fall();
            updateBoard();
        } else {
            endGame();
        }
    }

    public void rotateRight() {
        board.rotateRight();
        updateBoard();
    }

    public void moveRight() {
        board.moveRight();
        updateBoard();
    }

    private void endGame() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        updateBoard();
        timer.cancel();
        findViewById(R.id.moveLeft).setOnClickListener(unused -> drop());
        findViewById(R.id.rotateLeft).setOnClickListener(unused -> drop());
        findViewById(R.id.moveDown).setOnClickListener(unused -> drop());
        findViewById(R.id.rotateRight).setOnClickListener(unused -> drop());
        findViewById(R.id.moveRight).setOnClickListener(unused -> drop());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Score: " + board.getScore());
        builder.setOnDismissListener(unused -> finish());
        builder.create().show();
    }
}
