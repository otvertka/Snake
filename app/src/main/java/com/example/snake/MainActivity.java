package com.example.snake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.snake.engine.GameEngine;
import com.example.snake.enums.GameState;
import com.example.snake.views.SnakeView;

public class MainActivity extends AppCompatActivity {

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        gameEngine.initGame();

        snakeView = (SnakeView) findViewById(R.id.snakeView);

        StartUpdateHandler();
    }

    private void StartUpdateHandler(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                if (gameEngine.getCurrentGameState() == GameState.Running){
                    handler.postDelayed(this, updateDelay);
                }

                if (gameEngine.getCurrentGameState() == GameState.Lost){
                    onGameLost();
                }
                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();

            }
        }, updateDelay);
    }

    private void onGameLost() {
        Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show();
    }
}
