package sample;


import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class Main extends Application {

    // размер пол¤
    private static final int width = 800;
    private static final int height = 600;

    // ширина и высота ракетка
    private static final int RACKET_WIDTH = 10;
    private static final int RACKET_HEIGHT = 90;

    // радиус м¤ча
    private static final int BALL_RAD = 30;

    // начальные координаты ракетки игрока
    double playerX = 0;
    double playerY = height / 2;

    // начальные координаты ракетки компа
    double compX = width - RACKET_WIDTH;
    double compY = height / 2;

    // координаты м¤ча
    double ballX = width / 2;
    double ballY = height / 2;

    // инструмент рисовани¤
    GraphicsContext gc;

    // скорость м¤ча
    double ballSpeed = 3;

    double directionX = 1;
    double directionY  = 1;

    // игровой цикл
    boolean gameStarted;

    private void drawTable() {
        // рисуем поле
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, width, height);
        // рисуем разделительную линию
        gc.setFill(Color.YELLOW);
        gc.fillRect(width / 2, 0, 2, height);
        // рисуем м¤ч
        if (gameStarted) {

            if(ballY >= height )
            {
                directionY = -1;
            }

            if(ballX >= width)
            {
                directionX = -1;
            }

            if(ballY <= 0 )
            {
                directionY = 1;
            }

            if(ballX <= 0)
            {
                directionX = 1;
            }

            ballX += directionX * ballSpeed;
            ballY += directionY * ballSpeed;

            // логика - комп отбивает м¤ч
            if (ballX < width - width / 4) {
                compY = ballY - RACKET_HEIGHT / 2;
            }

            gc.fillOval(ballX, ballY, BALL_RAD, BALL_RAD);

        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to start", width / 2, height / 2);
        }

        // рисуем ракетки
        gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
        gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);


        //Top
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, width, 2);

        //Bottom
        gc.setFill(Color.BLUE);
        gc.fillRect(0, height - 2, width, 2);

        //Left
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, 2, height);

        //Right
        gc.setFill(Color.BLUE);
        gc.fillRect(width - 2, 0, 2, height);
    }

    @Override
    public void start(Stage root) {
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        drawTable();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> drawTable()));
        t1.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseClicked(e -> gameStarted = true);
        canvas.setOnMouseMoved(e -> playerY = e.getY());

        root.setScene(new Scene(new StackPane(canvas)));
        root.setTitle("Ping-pong");
        root.show();
        t1.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
