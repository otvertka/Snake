package com.example.snake.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.snake.enums.TileType;

/**
 * Created by Дмитрий on 02.08.2017.
 */

public class SnakeView extends View {

    private static final String LOG_TAG = "myLog";

    private Paint mPaint = new Paint();
    private TileType snakeViewMap[][];

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSnakeViewMap(TileType[][] map){
        this.snakeViewMap = map;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (snakeViewMap != null){
            //Log.d(LOG_TAG, " - =" + snakeViewMap.length + " [0] = " + snakeViewMap[0].length);

            //почему [0] - это y??
            float tileSizeX = canvas.getWidth() / snakeViewMap.length;
            float tileSizeY = canvas.getHeight() / snakeViewMap[0].length;

            //Log.d(LOG_TAG, "tileSizeX = " + tileSizeX);
            //Log.d(LOG_TAG, "tileSizeY = " + tileSizeY);

            float circleSize = Math.min(tileSizeX, tileSizeY) / 2;
            //Log.d(LOG_TAG, "circleSize = " + circleSize);

            for (int x = 0; x < snakeViewMap.length; x++) {
                for (int y = 0; y < snakeViewMap[x].length; y++) {
                    switch (snakeViewMap[x][y]){

                        case Nothing:
                            mPaint.setColor(Color.WHITE);
                            break;
                        case Wall:
                            mPaint.setColor(Color.GREEN);
                            break;
                        case SnakeHead:
                            mPaint.setColor(Color.RED);
                            break;
                        case SnakeTail:
                            mPaint.setColor(Color.GREEN);
                            break;
                        case Apple:
                            mPaint.setColor(Color.RED);
                            break;
                    }

                    //хз что тут понаписано
                    canvas.drawCircle(x * tileSizeX + tileSizeX / 2f + circleSize / 2, y * tileSizeY + tileSizeY / 2f + circleSize, circleSize, mPaint);
                }
            }
        }
    }
}
