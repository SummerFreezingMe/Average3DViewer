package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.utils.Matrix4f;
import com.vsu.cgcourse.math.utils.MatrixUtils;
import com.vsu.cgcourse.math.utils.Point2f;
import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.model.Mesh;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

import static com.vsu.cgcourse.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;
import static com.vsu.cgcourse.render_engine.GraphicConveyor.vertexToPoint;

public class RenderEngine {
    static MatrixUtils utils = new MatrixUtils();

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Mesh mesh,
            final int width,
            final int height) {
        Matrix4f modelMatrix = utils.matrixTranspiration(new Matrix4f(mesh.matrix));
        Matrix4f viewMatrix = utils.matrixTranspiration(camera.getViewMatrix());
        Matrix4f projectionMatrix = utils.matrixTranspiration(camera.getProjectionMatrix());
        Matrix4f modelViewProjectionMatrix = new Matrix4f(projectionMatrix.matrix);
        modelViewProjectionMatrix = utils.matrixMultiply(modelViewProjectionMatrix, viewMatrix);
        modelViewProjectionMatrix = utils.matrixMultiply(modelViewProjectionMatrix, modelMatrix);


        final int nPolygons = mesh.polygonVertexIndices.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygonVertexIndices.get(polygonInd).size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygonVertexIndices.get(polygonInd).get(vertexInPolygonInd));
                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y);
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y);
        }
    }
}