package com.example.snake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
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

    //my fi4is
    AppCompatButton button;
    AppCompatTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //раньше это было в onCreate()
        gameEngine = new GameEngine();
        gameEngine.initGame();

        snakeView = (SnakeView) findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

        button = (AppCompatButton) findViewById(R.id.btn);
        textView = (AppCompatTextView) findViewById(R.id.tvCount);

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
        // а здесб только Тоаст
        Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show();
        button.setVisibility(View.VISIBLE);
        textView.setText("Яблочек скушано: " + gameEngine.count);
        textView.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                onStart();
            }
        });
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
