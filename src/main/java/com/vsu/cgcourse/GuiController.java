package com.vsu.cgcourse;

import com.vsu.cgcourse.math.utils.Matrix4f;
import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.obj_reader.PolygonsDelete;
import com.vsu.cgcourse.obj_writer.ObjWriter;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.CameraStatus;
import com.vsu.cgcourse.render_engine.GraphicConveyor;
import com.vsu.cgcourse.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.text.html.ListView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GuiController {

    final private float TRANSLATION = 1.5F;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;
    @FXML
    public Button button;

    List<Mesh> models = new ArrayList<>();
    @FXML
    ListView listView;
    @FXML
    Pane root;
    private String fileContentModel;
    TextField deleteArray = new TextField();
    private Mesh unmoddedMesh = null;
    private CameraStatus status = CameraStatus.MOVE;

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
            if (models != null) {
                for (Mesh mesh : models) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void move(KeyCode keyCode) {
        Vector3f getVector = keyHandling(keyCode);
        if (status == CameraStatus.SCALE) {
            scaleRotateTranslate(getVector, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
        }
        if (status == CameraStatus.ROTATE) {
            scaleRotateTranslate(new Vector3f(1, 1, 1), getVector, new Vector3f(0, 0, 0));
        }
        if (status == CameraStatus.TRANSLATE) {
            scaleRotateTranslate(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), getVector);
        }
    }


    private Vector3f keyHandling(KeyCode event) {
        if (event == KeyCode.DOWN) {
            if (status != CameraStatus.SCALE) {
                return new Vector3f(TRANSLATION, 0, 0);
            }
            return new Vector3f(TRANSLATION, 1, 1);
        }
        if (event == KeyCode.UP) {
            if (status != CameraStatus.SCALE) {
                return new Vector3f(-TRANSLATION, 0, 0);
            }
            return new Vector3f(1 / TRANSLATION, 1, 1);
        }
        if (event == KeyCode.LEFT) {
            if (status != CameraStatus.SCALE) {
                return new Vector3f(0, TRANSLATION, 0);
            }
            return new Vector3f(1, TRANSLATION, 1);
        }
        if (event == KeyCode.RIGHT) {
            if (status != CameraStatus.SCALE) {
                return new Vector3f(0, -TRANSLATION, 0);
            }
            return new Vector3f(1, 1 / TRANSLATION, 1);
        }
        if (event == KeyCode.W) {
            if (status != CameraStatus.SCALE) {
                return new Vector3f(0, 0, TRANSLATION);
            }
            return new Vector3f(1, 1, TRANSLATION);
        }
        if (event == KeyCode.S) {
            if (status != CameraStatus.SCALE) {
                return new Vector3f(0, 0, -TRANSLATION);
            }
            return new Vector3f(1, 1, 1 / TRANSLATION);
        }
        if (status != CameraStatus.SCALE) {
            return new Vector3f(0, 0, 0);
        }
        return new Vector3f(1, 1, 1);
    }

    @FXML
    private void onOpenModelMenuItemClick() {
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
            fileContentModel = fileContent;
            models.add(ObjReader.read(fileContent));
            unmoddedMesh = ObjReader.read(fileContent);
        } catch (IOException exception) {
            windowCall(exception.getMessage());
        }
    }

    @FXML
    private void onOpenModelMenuItemClick2() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.txt)", "*.txt"));
        fileChooser.setTitle("Choose file with polygons for delete");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }
        Path fileName = Path.of(file.getAbsolutePath());
        try {
            String fileContent = Files.readString(fileName);
            if (models != null) {
                for (Mesh mesh : models) {
                    mesh = PolygonsDelete.readingAndDeletingPolygons(fileContentModel, fileContent);
                }
            } else {
                windowCall("Error: Model or file with polygons for delete not found");
            }
        } catch (IOException exception) {
            windowCall(exception.getMessage());
        }
    }

    public void windowCall(String msg) {
        button = new Button();
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

    @FXML
    public void windowCallDeletePolygon() {
        button = new Button();
        button.setText("Ok");
        button.setTranslateY(30);
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(button);
        secondaryLayout.getChildren().add(deleteArray);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("Input array");
        newWindow.setScene(secondScene);
        newWindow.show();
        button.setOnAction(actionEvent -> {
            for (Mesh mesh : models) {
                PolygonsDelete.createDeleteArray(mesh, deleteArray.getText());
            }
        });
    }

    public void onSaveModelMenuItemClick() {
        if (models != null) {
            for (Mesh mesh : models) {
                Matrix4f matrix4f = new Matrix4f(mesh.matrix);
                for (int i = 0; i < mesh.vertices.size(); i++) {
                    mesh.vertices.set(i, GraphicConveyor.multiplyMatrix4ByVector3(matrix4f, mesh.vertices.get(i)));
                }
                mesh.matrix = new float[][]{
                        {1f, 0f, 0f, 0f},
                        {0f, 1f, 0f, 0f},
                        {0f, 0f, 1f, 0f},
                        {0f, 0f, 0f, 1f}
                };
                ObjWriter.saveOutput(mesh, "NewModel");
            }
        } else {
            windowCall("Error: Model not found");

        }
    }

    @FXML
    public void handleCameraForward() {
        if (status == CameraStatus.MOVE) {
            camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
        } else {
            KeyCode keyCode = KeyCode.UP;
            move(keyCode);
        }
    }

    @FXML
    public void handleCameraBackward() {
        if (status == CameraStatus.MOVE) {
            camera.movePosition(new Vector3f(0, 0, TRANSLATION));
        } else {
            KeyCode keyCode = KeyCode.DOWN;
            move(keyCode);
        }
    }

    @FXML
    public void handleCameraLeft() {
        if (status == CameraStatus.MOVE) {
            camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
        } else {
            KeyCode keyCode = KeyCode.LEFT;
            move(keyCode);
        }
    }

    @FXML
    public void handleCameraRight() {
        if (status == CameraStatus.MOVE) {
            camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
        } else {
            KeyCode keyCode = KeyCode.RIGHT;
            move(keyCode);
        }
    }

    @FXML
    public void handleCameraUp() {
        if (status == CameraStatus.MOVE) {
            camera.movePosition(new Vector3f(0, TRANSLATION, 0));
        } else {
            KeyCode keyCode = KeyCode.W;
            move(keyCode);
        }
    }

    @FXML
    public void handleCameraDown() {
        if (status == CameraStatus.MOVE) {
            camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
        } else {
            KeyCode keyCode = KeyCode.S;
            move(keyCode);
        }
    }

    @FXML
    public void setScale() {
        status = CameraStatus.SCALE;
    }

    @FXML
    public void setRotate() {
        status = CameraStatus.ROTATE;
    }

    @FXML
    public void setTranslate() {
        status = CameraStatus.TRANSLATE;

    }

    @FXML
    public void setMove() {
        status = CameraStatus.MOVE;
    }

    private void scaleRotateTranslate(Vector3f scale, Vector3f rotate, Vector3f translate) {
        for (Mesh mesh : models) {
            mesh.matrix = GraphicConveyor.modelMatrix(scale, rotate, translate, mesh).matrix;
        }
    }

    public void onSaveUnmoddedModelMenuItemClick() {
        if (models != null) {
            ObjWriter.saveOutput(unmoddedMesh, "NewModel");
        } else {
            windowCall("Error: Model not found");

        }
    }

    public void startGame() {
        try {
            Game.createGame(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}