package com.vsu.cgcourse;

import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.model.Collision;
import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.GraphicConveyor;
import com.vsu.cgcourse.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Game implements ActionListener {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    String fileObstacle;
    final private float TRANSLATION = 30F;
    LinkedList<Mesh> currentMeshes = new LinkedList<>();
    private final Timer timer = new Timer(200, this);
    int spawner = 0;
    int carPos = 0;
    private final Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);


    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();
            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));
            if (currentMeshes != null) {
                for (Mesh mesh : currentMeshes) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public static void createGame(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(Game.class.getResource("fxml/gameGui.fxml")));
        Scene scene = new Scene(viewport);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("Game");
        stage.setScene(scene);
        stage.show();
    }

    public void startGame() {
        timer.start();
    }

    public void loadCarModel() {
        camera.movePosition(new Vector3f(0, 50, 0));
        camera.movePosition(new Vector3f(0, 0, TRANSLATION / 2));
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }
        Path fileName = Path.of(file.getAbsolutePath());
        try {
            String fileContent = Files.readString(fileName);
            currentMeshes.addFirst(ObjReader.read(fileContent));
            currentMeshes.getFirst().matrix = GraphicConveyor.modelMatrix(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(0, 0, TRANSLATION * (3f / 2)),
                    currentMeshes.getFirst()).matrix;
        } catch (IOException exception) {
            windowCall(exception.getMessage());
        }
    }

    public void loadObstacleModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }
        Path fileName = Path.of(file.getAbsolutePath());
        try {
            fileObstacle = Files.readString(fileName);
            currentMeshes.addLast(ObjReader.read(fileObstacle));
        } catch (IOException exception) {
            windowCall(exception.getMessage());
        }
    }

    public void windowCall(String msg) {
        Button button = new Button();
        button.setText("Ok");
        button.setTranslateY(30);
        Label secondLabel = new Label(msg);
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(button);
        secondaryLayout.getChildren().add(secondLabel);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("Error");
        newWindow.setScene(secondScene);
        newWindow.show();
        button.setOnAction(actionEvent ->
                newWindow.close());
    }

    public void moveLeft() {
        if (carPos > -1) {
            currentMeshes.getFirst().matrix = GraphicConveyor.modelMatrix(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(TRANSLATION / 2, 0, 0),
                    currentMeshes.getFirst()).matrix;
            carPos--;
        }
    }

    public void moveRight() {
        if (carPos < 1) {
            currentMeshes.getFirst().matrix = GraphicConveyor.modelMatrix(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(-TRANSLATION / 2, 0, 0),
                    currentMeshes.getFirst()).matrix;
            carPos++;
        }
    }

    private void moveObstacles(Vector3f scale, Vector3f rotate, Vector3f translate) {
        for (int i = 1; i < currentMeshes.size(); i++) {
            currentMeshes.get(i).matrix = GraphicConveyor.modelMatrix(scale, rotate, translate, currentMeshes.get(i)).matrix;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            for (int i = 1; i < currentMeshes.size(); i++) {
                if (!Collision.testInterSection(currentMeshes.getFirst(), currentMeshes.get(i))) {
                    timer.stop();
                }
            }
            spawner++;
            if (spawner == 8) {
                generateObstacles();
                spawner = 0;

            }
            if (currentMeshes.size() > 5) {
                currentMeshes.remove(1);

            }
            moveObstacles(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(0, 0, 1.5F));
        }


    }


    private void generateObstacles() {
        int obstaclePosition = rnd.nextInt(3);
        Mesh obs = ObjReader.read(fileObstacle);
        generatePosition(obstaclePosition, obs);
        currentMeshes.addLast(obs);
    }

    private void generatePosition(int obstaclePosition, Mesh obs) {
        obs.matrix = GraphicConveyor.modelMatrix(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f((1 - obstaclePosition) * 22.5F, 0, 0), obs).matrix;
    }

    Random rnd = new Random();
}
