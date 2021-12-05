package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.entities.animatedEntities.Bomber;
import uet.oop.bomberman.entities.buffItems.Bomb;
import uet.oop.bomberman.entities.buffItems.Flame;
import uet.oop.bomberman.entities.buffItems.Speed;
import uet.oop.bomberman.entities.animatedEntities.Brick;
import uet.oop.bomberman.entities.staticEntities.Grass;
import uet.oop.bomberman.entities.staticEntities.Portal;
import uet.oop.bomberman.entities.staticEntities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.soundEffect.Sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class BombermanGame extends Application {
    
    public static final int WINDOW_WIDTH = 20;
    public static final int WINDOW_HEIGHT = 14;
    public static final long TIME_UNIT = 10_000_000; // 10 ms
    public static final int MOVING_UNIT = 2;
    public static final int HEIGHT = 13;
    public static int width = 30;

    private Group root = new Group(); // root container

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> updateQueue = new ArrayList<>();
    public static int lives = 3;
    private Bomber bomberman;
    private static int score;
    private Canvas canvas;
    private GraphicsContext gc;
    private TextField scoreBoard;

    public static boolean condition = false;
    public static int currentLevel = 1;
    public static int nextLevel = 2;
    public static boolean isBomberOnThePortal = false;
    public static boolean isInGame = false;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static void increaseScore(int point) {
        score += point;
    }

    @Override
    public void start(Stage stage) {
        //Sound.levelStart();
        Sound.inGame();
        // score board
        score = 0;
        scoreBoard = new TextField();
        scoreBoard.setEditable(false);
        scoreBoard.setFocusTraversable(false);
        scoreBoard.setPrefWidth(WINDOW_WIDTH * Sprite.SCALED_SIZE - 70);
        scoreBoard.setPrefHeight((WINDOW_HEIGHT - HEIGHT) * Sprite.SCALED_SIZE);
        scoreBoard.setFont(Font.font(18));
        scoreBoard.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff;");

        // button
        Button pauseButton = new Button("Next level");
        pauseButton.setPrefHeight((WINDOW_HEIGHT - HEIGHT) * Sprite.SCALED_SIZE);
        pauseButton.setPrefWidth(70);
        pauseButton.setLayoutX(WINDOW_WIDTH * Sprite.SCALED_SIZE - 70);
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction(actionEvent -> {
            System.out.println("Next level");
            waitingScreen(nextLevel);
        });

        root.getChildren().add(scoreBoard);
        root.getChildren().add(pauseButton);

        createMap(1);
        // Tao scene
        Scene scene = new Scene(root,
                Sprite.SCALED_SIZE * WINDOW_WIDTH,
                Sprite.SCALED_SIZE * WINDOW_HEIGHT);
        scene.setFill(Color.BLACK);
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

                    if (lives < 1) {
                        this.stop();
                        endingAlert();
                        return;
                    }

                    update();
                    render();

                    if (bomberman.getX() >= (WINDOW_WIDTH * Sprite.SCALED_SIZE) / 2 &&
                            bomberman.getX() <= (width - WINDOW_WIDTH / 2) * Sprite.SCALED_SIZE) {
                        double distance = (WINDOW_WIDTH * Sprite.SCALED_SIZE) / 2 - bomberman.getX();
                        root.setLayoutX(distance);
                        scoreBoard.setLayoutX(-distance);
                        pauseButton.setLayoutX(-distance + (WINDOW_WIDTH * Sprite.SCALED_SIZE - 70));
                    }
                }
            }
        };
        timer.start();

    }

    public void createMap(int level) {
        Integer convert_level = level;
        String filename = "res//levels//Level" + convert_level.toString() + ".txt";
        int read_level;
        int height = HEIGHT;
        String []map = new String[height];
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            read_level = myReader.nextInt();
            height = myReader.nextInt();
            width = myReader.nextInt();
            map = new String[height];
            int i = 0;
            myReader.nextLine();
            while (i < height) {
                map[i] = new String();
                map[i] = myReader.nextLine();
                i += 1;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * width, Sprite.SCALED_SIZE * height);
        canvas.setLayoutY((WINDOW_HEIGHT - height) * Sprite.SCALED_SIZE);
        gc = canvas.getGraphicsContext2D();
        entities = new ArrayList<>();
        stillObjects = new ArrayList<>();

        int player_x = 1;
        int player_y = 1;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Entity object = new Grass(i, j);
                String type_entity = Character.toString(map[j].charAt(i));
                if (type_entity.equals("p")) {
                    player_x = i;
                    player_y = j;
                }
                if (type_entity.equals("#")) {
                    object = new Wall(i, j);
                } else if(type_entity.equals("x")) {
                    //portal
                    object = new Portal(i, j);
                    stillObjects.add(new Grass(i, j));
                }
                stillObjects.add(object);
            }
        }
        if (Objects.isNull(bomberman)) {
            bomberman = new Bomber(player_x, player_y);
        }
        bomberman.setX(player_x);
        bomberman.setY(player_y);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Entity object = new Balloom(i, j, Sprite.balloom_right2.getFxImage(), MOVING_UNIT * 1.0 / 2);
                boolean check = true;
                String type_entity = Character.toString(map[j].charAt(i));
                if (type_entity.equals("2")) {
                    object = new Oneal(i, j, Sprite.oneal_right1.getFxImage(), MOVING_UNIT / 1.5, bomberman);
                } else if (type_entity.equals("5")) {
                    object = new Doll(i, j, Sprite.doll_left1.getFxImage(), MOVING_UNIT / 2.0);
                } else if(type_entity.equals("4")) {
                    object = new Minvo(i, j, Sprite.minvo_right2.getFxImage(), MOVING_UNIT / 1.75, bomberman);
                }
                else if (type_entity.equals("3")) {
                    object = new Kondoria(i, j, Sprite.kondoria_right1.getFxImage(), MOVING_UNIT / 2.0, bomberman);
                }
                else if (type_entity.equals("b")) {
                    object = new Bomb(i, j);
                    entities.add(new Brick(i,j));
                }
                else if (type_entity.equals("f")) {
                    //flame item
                    object = new Flame(i, j);
                    entities.add(new Brick(i,j));
                }
                else if (type_entity.equals("s")) {
                    //speed item
                    object = new Speed(i, j);
                    entities.add(new Brick(i,j));
                }
                else if (type_entity.equals("*") || type_entity.equals("x")) {
                    object = new Brick(i, j);
                }
                else if (! type_entity.equals("1")) {
                    check = false;
                }
                if(check) {
                    entities.add(object);
                }
            }
        }
        entities.add(bomberman);
        root.getChildren().add(canvas);
    }

    public void endingAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Your score is " + score + "! Click OK to exit.");

        alert.setOnHidden(evt -> Platform.exit());

        alert.show();
    }

    public void waitingScreen(int level) {
        root.getChildren().remove(canvas);
        TextField notif = new TextField("Level " + level);
        notif.setEditable(false);
        notif.setFocusTraversable(false);
        notif.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff");
        notif.setFont(Font.font(50));
        notif.setLayoutY((160));
        notif.setPrefWidth(WINDOW_WIDTH * Sprite.SCALED_SIZE);
        notif.setAlignment(Pos.CENTER);
        root.getChildren().add(notif);

        Button next = new Button("click to continue");
        next.setStyle("-fx-background-color: #000000");
        next.setTextFill(Color.WHITE);
        next.setLayoutY(240);
        next.setLayoutX(253);
        next.setOnAction(actionEvent1 -> {
            createMap(level);
            Sound.levelStart();
        });
        root.getChildren().add(next);
    }


    // check if portal isn't under the brick
    public boolean checkPortal() {
        int xPortal = 0, yPortal = 0;
        for (Entity entity: stillObjects) {
            String className = entity.getClass().getTypeName();
            if (className.contains("Portal")) {
                xPortal = entity.getX();
                yPortal = entity.getY();
            }
        }

        for (Entity entity: entities) {
            String className = entity.getClass().getTypeName();
            if (entity.existOnSquare(xPortal, yPortal) && className.contains("Brick") && entity.isVisible()) {
                return false;
            }
        }

        return true;
    }

    public boolean checkCondition() {
        for (Entity entity: entities) {
            String className = entity.getClass().getTypeName();
            if (className.contains("Enemy") && entity.isVisible()) {
                return false;
            }
        }
        if (checkPortal() && isBomberOnThePortal) {
            return true;
        }

        return false;
    }

    public void update() {
        //change level if condition = true
        if (checkCondition()) {
            nextLevel = ++currentLevel;
            createMap(nextLevel);
            currentLevel++;
            Sound.levelComplete();
        }

        // update scoreBoard
        scoreBoard.setText("Score: " + score + "      Lives: " + lives);

        // relive bomberman if necessary
        if (!bomberman.isVisible() && lives > 0) {
            bomberman = new Bomber(1, 1);
            updateQueue.add(bomberman);
            lives--;
        }

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
        /** render in order:
         * static entities
         * flames and items
         * other animate entities*/
        stillObjects.forEach(g -> {
            if (g.isVisible()) {
                g.render(gc);
            }
        });
        entities.forEach(g -> {
            String className = g.getClass().getTypeName();
            if (g.isVisible()
                && (className.contains("flames")
                    || className.contains("buffItems"))) {
                g.render(gc);
            }
        });
        entities.forEach(g -> {
            String className = g.getClass().getTypeName();
            if (g.isVisible()
                && !className.contains("flames")
                && !className.contains("buffItems")) {
                g.render(gc);
            }
        });
    }
}
