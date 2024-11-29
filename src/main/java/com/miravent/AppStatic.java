package com.miravent;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppStatic extends Application {


    private static final int NUM_CELDAS = 35;

    private static int WIDTH = 500;

    private static final int cellSize = WIDTH / NUM_CELDAS;
    // tiempo entre frames medido en nanosegundos. 1 segundo = 1,000,000,000 nanosegundos
    private static final long TIME_BETWEEN_FRAMES = 100000000;

    private GraphicsContext graphics;
    private Label nPoblacion;
    private Label nGeneracion;


    public static void main(String[] args) {
        if (WIDTH != cellSize * NUM_CELDAS){
            WIDTH = cellSize * NUM_CELDAS;
        }

        launch();
    }

    @Override
    public void start(Stage stage) {

        VBox root = new VBox(10);
        Scene scene = new Scene(root, WIDTH, WIDTH + 100);
        final Canvas canvas = new Canvas(WIDTH, WIDTH);

        Button reset = new Button("Reset");
        Button step = new Button("Step");
        Button run = new Button("Run");
        Button stop = new Button("Stop");

        root.getChildren().addAll(canvas, new HBox(10, reset, step, run, stop));

        nPoblacion = new Label();
        root.getChildren().add(new HBox(new Label("Población: "), nPoblacion));

        nGeneracion = new Label();
        root.getChildren().add(new HBox(new Label("Generación: "), nGeneracion));

        stage.setScene(scene);
        stage.show();


        graphics = canvas.getGraphicsContext2D();

        Life.inicializa(NUM_CELDAS, NUM_CELDAS);

        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // only update once every second
                if ((now - lastUpdate) >= TIME_BETWEEN_FRAMES) {
                    Life.siguienteGeneracion();
                    lastUpdate = now;
                    draw();
                }
            }
        };

        reset.setOnAction(l -> this.iniciar());
        run.setOnAction(  l -> runAnimation.start());
        step.setOnAction( l -> this.tick());
        stop.setOnAction( l -> runAnimation.stop());

        // Creamos la primera iteración
        this.iniciar();

    }

    public void iniciar() {
        Life.init();
        draw();
    }

    public void tick() {
        Life.tick();
        draw();
    }

    public void draw() {
// clear graphics
        graphics.setFill(Color.LAVENDER);
        graphics.fillRect(0, 0, WIDTH, WIDTH);

        for (int i = 0; i < Life.getGrid().length; i++) {
            for (int j = 0; j < Life.getGrid()[i].length; j++) {
                if (Life.getGrid()[i][j]) {
                    // first rect will end up becoming the border
                    graphics.setFill(Color.gray(0.5, 0.5));
                    graphics.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    graphics.setFill(Color.PURPLE);
                    graphics.fillRect((i * cellSize) + 1, (j * cellSize) + 1, cellSize - 2, cellSize - 2);
                }else {
                    graphics.setFill(Color.gray(0.5, 0.5));
                    graphics.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    graphics.setFill(Color.LAVENDER);
                    graphics.fillRect((i * cellSize) + 1, (j * cellSize) + 1, cellSize - 2, cellSize - 2);
                }
            }
        }

        nPoblacion.setText(String.valueOf(Life.getPopulation()));
        nGeneracion.setText(String.valueOf(Life.getGeneration()));

    }

}
