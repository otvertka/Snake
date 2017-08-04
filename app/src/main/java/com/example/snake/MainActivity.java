package com.example.snake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.snake.engine.GameEngine;
import com.example.snake.enums.Direction;
import com.example.snake.enums.GameState;
import com.example.snake.views.SnakeView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 125;

    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        gameEngine.initGame();

        snakeView = (SnakeView) findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = motionEvent.getX();
                prevY = motionEvent.getY();

                break;
            case MotionEvent.ACTION_UP:
                float newX = motionEvent.getX();
                float newY = motionEvent.getY();

                // Calculate where we swiped
                if ( Math.abs(prevX - newX) > Math.abs(prevY - newY)){
                    // LEFT - RIGHT direction
                    if (newX > prevX){
                        //RIGHT
                        gameEngine.UpdateDirection(Direction.East);
                    } else {
                        //LEFT
                        gameEngine.UpdateDirection(Direction.West);
                    }
                } else {
                    // UP - DOWN direction
                    if (newY > prevY){
                        //DOWN
                        gameEngine.UpdateDirection(Direction.South);
                    } else {
                        //UP
                        gameEngine.UpdateDirection(Direction.North);
                    }
                }
                break;
        }

        return true;
    }
}
