package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.entities.animatedEntities.Bomber;
import uet.oop.bomberman.entities.buffItems.Bomb;
import uet.oop.bomberman.entities.buffItems.Flame;
import uet.oop.bomberman.entities.staticEntities.Grass;
import uet.oop.bomberman.entities.staticEntities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

/** TO DO:
 * detach entities to "bomberman", "enemies" and "others". */

public class BombermanGame extends Application {
    
    public static final int WINDOW_WIDTH = 20;
    public static final int WINDOW_HEIGHT = 16;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 15;
    public static final long TIME_UNIT = 10_000_000; // 10 ms
    public static final int MOVING_UNIT = 2;
    public static double PLAYERSPEED = 1.0;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> updateQueue = new ArrayList<>();
    private Bomber bomberman;
    private static int score;
    private GraphicsContext gc;
    private Canvas canvas;
    private TextField scoreBoard;

    public static void increaseScore(int point) {
        score += point;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        canvas.setLayoutY((WINDOW_HEIGHT - HEIGHT) * Sprite.SCALED_SIZE);
        gc = canvas.getGraphicsContext2D();

        // score board
        score = 0;
        scoreBoard = new TextField();
        scoreBoard.setEditable(false);
        scoreBoard.setFocusTraversable(false);
        scoreBoard.setPrefWidth(WINDOW_WIDTH * Sprite.SCALED_SIZE - 70);
        scoreBoard.setPrefHeight((WINDOW_HEIGHT - HEIGHT) * Sprite.SCALED_SIZE);
        scoreBoard.setFont(Font.font(18));
        scoreBoard.setStyle("-fx-background-color: #a9a8a8; -fx-text-fill: black;");

        // button
        Button pauseButton = new Button("Pause");
        pauseButton.setPrefHeight((WINDOW_HEIGHT - HEIGHT) * Sprite.SCALED_SIZE);
        pauseButton.setPrefWidth(70);
        pauseButton.setLayoutX(WINDOW_WIDTH * Sprite.SCALED_SIZE - 70);
//        pauseButton.setStyle("-fx-background-color: #a9a8a8;");
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction(actionEvent -> {
            System.out.println("helo");
        });

        createCharacters();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(scoreBoard);
        root.getChildren().add(pauseButton);
//        Button helo = new Button("helo");
//        helo.setFocusTraversable(false);
//        helo.setOnAction(event -> {
//            System.out.println("clicky");
//        });
//        root.getChildren().add(helo);

        // Tao scene
        Scene scene = new Scene(root,
                Sprite.SCALED_SIZE * WINDOW_WIDTH,
                Sprite.SCALED_SIZE * WINDOW_HEIGHT);
        scene.setOnKeyPressed(keyEvent -> {
            bomberman.controlPressing(keyEvent.getCode());
        });
        scene.setOnKeyReleased(keyEvent -> {
            bomberman.controlReleasing(keyEvent.getCode());
        });

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            @Override
            public void handle(long l) {
                /** update and render every TIME_UNIT. */
                if (l - lastUpdate >= TIME_UNIT) {
                    lastUpdate = l;
                    update();
                    render();

                    if (bomberman.getX() >= (WINDOW_WIDTH * Sprite.SCALED_SIZE) / 2 &&
                    bomberman.getX() <= (WIDTH - WINDOW_WIDTH / 2) * Sprite.SCALED_SIZE) {
                        double distance = (WINDOW_WIDTH * Sprite.SCALED_SIZE) / 2 - bomberman.getX();
                        root.setLayoutX(distance);
                        scoreBoard.setLayoutX(-distance);
                        pauseButton.setLayoutX(-distance + (WINDOW_WIDTH * Sprite.SCALED_SIZE - 70));
                    }

                }

            }
        };
        timer.start();

        createMap();
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else if (2 <= i && i < WIDTH - 2 &&
                        2 <= j && j < HEIGHT &&
                        i % 2 == 0 && j % 2 == 0) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                } else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    private void createCharacters() {

        /** testing */
        entities.add(new Bomb(4, 5));
        entities.add(new Flame(8, 5));
        /** end test */

        // Tao bomberman
        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        //tao enemy luu y toc do tung con
        Balloom balloom = new Balloom(13, 13, Sprite.balloom_right2.getFxImage(), MOVING_UNIT * 1.0 / 2);
        Oneal oneal = new Oneal(5, 5, Sprite.oneal_right1.getFxImage(), MOVING_UNIT * 1.0 / 2, bomberman);
        Doll doll = new Doll(11, 11, Sprite.doll_left1.getFxImage(), MOVING_UNIT * 1.0);
        Kondoria kondoria = new Kondoria(7, 7, Sprite.kondoria_right1.getFxImage(), MOVING_UNIT * 1.0/4, bomberman);
        Minvo minvo = new Minvo(3, 3, Sprite.minvo_right2.getFxImage(), MOVING_UNIT * 1.0 / 2, bomberman);
        entities.add(minvo);
        entities.add(kondoria);
        entities.add(oneal);
        entities.add(balloom);
        entities.add(doll);

    }

    public void update() {
        // update scoreBoard
        scoreBoard.setText("Score: " + score);

        // add waiting entities to this.entities
        updateQueue.forEach(entity -> entities.add(entity));
        updateQueue.clear();

        // update each entity
        entities.forEach(entity -> {
            if (entity.isVisible()) {
                entity.update();
            }
        });
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> {
            if (g.isVisible()) {
                g.render(gc);
            }
        });
        entities.forEach(g -> {
            if (g.isVisible()) {
                g.render(gc);
            }
        });
    }
}
