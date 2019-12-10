package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startNewGame = findViewById(R.id.startNewGame);
        startNewGame.setOnClickListener(unused -> startActivity(new Intent(this, GameActivity.class)));
    }
}
