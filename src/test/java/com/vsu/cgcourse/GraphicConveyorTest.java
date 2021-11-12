package com.vsu.cgcourse;

import com.vsu.cgcourse.math.utils.Matrix4f;
import com.vsu.cgcourse.math.utils.Point2f;
import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.render_engine.GraphicConveyor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GraphicConveyorTest {

    private static final int MATRIX_4F_LENGTH = 4;

    @Test
    void rotateScaleTranslate() {
        float[] husk = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        Float[][] expectedMatrix = new Float[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                expectedMatrix[row][col] = husk[row * 4 + col];
            }
        }
        Matrix4f expectedResult = new Matrix4f(expectedMatrix);
        Matrix4f result = GraphicConveyor.rotateScaleTranslate();
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                Assertions.assertEquals(result.matrix[row][col], expectedResult.matrix[row][col]);
            }
        }

    }


    @Test
    void testLookAt() {
        Vector3f eye = new Vector3f(0f, 0f, 100f);
        Vector3f target = new Vector3f(0f, 0f, 0f);
        Vector3f up = new Vector3f(0f, 1f, 0f);
        float[] husk = new float[]{
                -1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, -1f, 0f,
                -0f, -0f, 100f, 1f};
        Float[][] expectedMatrix = new Float[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                expectedMatrix[row][col] = husk[row * 4 + col];
            }
        }
        Matrix4f expectedResult = new Matrix4f(expectedMatrix);
        Matrix4f result = GraphicConveyor.lookAt(eye, target, up);
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                Assertions.assertEquals(result.matrix[row][col], expectedResult.matrix[row][col]);
            }
        }
    }

    @Test
    void perspective() {
        float fov = 1f;
        float aspectRatio = 1f;
        float nearPlane = 0.01f;
        float farPlane = 100f;
        float[] husk = new float[]{
                1.8304877f, 0f, 0f, 0f,
                0f, 1.8304877f, 0f, 0f,
                0f, 0f, 1.0002f, 1f,
                0f, 0f, -0.020002f, 0f};
        Float[][] expectedMatrix = new Float[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                expectedMatrix[row][col] = husk[row * 4 + col];
            }
        }
        Matrix4f expectedResult = new Matrix4f(expectedMatrix);
        Matrix4f result = GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                Assertions.assertEquals(result.matrix[row][col], expectedResult.matrix[row][col]);
            }
        }
    }

    @Test
    void multiplyMatrix4ByVector3() {
        Vector3f vertex = new Vector3f(-5.005512f, 119.40806f, 8.953617f);
        float[] husk = new float[]{
                -1.2361735f, 0f, 0f, 0f,
                0f, 1.8304877f, 0f, 0f,
                0f, 0f, -1.0002f, -1f,
                0f, 0f, 100f, 100f};
        Float[][] matrix = new Float[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                matrix[row][col] = husk[row * 4 + col];
            }
        }
        Vector3f result = GraphicConveyor.multiplyMatrix4ByVector3(new Matrix4f(matrix), vertex);
        Vector3f expectedResult = new Vector3f(0.06796186f, 2.4006991f, 0.99998033f);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());

    }

    @Test
    void vertexToPoint() {
        Vector3f devoid = new Vector3f(2, 2, 8);
        Point2f result = GraphicConveyor.vertexToPoint(devoid, 4, 2);
        Point2f expectedResult = new Point2f(10f, -3f);
        //todo change access modifier
        Assertions.assertEquals(expectedResult.x, result.x);
        Assertions.assertEquals(expectedResult.y, result.y);
    }
}