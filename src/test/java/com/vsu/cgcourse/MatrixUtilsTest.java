
package com.vsu.cgcourse;
import com.vsu.cgcourse.math.utils.*;
import com.vsu.cgcourse.math.utils.Matrix4f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

class MatrixUtilsTest {
    private final int MATRIX_3F_LENGTH = 3;
    private final int MATRIX_4F_LENGTH = 4;

    @Test
    void testMatrixSum3() {
        MatrixUtils util = new MatrixUtils();
        Matrix3f expected = new Matrix3f();
        expected = expected.createSingleMatrix();
        Float[] array1 = {4f, 10f, 6f, 1f, 2f, 1f, 3f, 3f, 3f};
        Float[][] first = new Float[MATRIX_3F_LENGTH][MATRIX_3F_LENGTH];
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                first[row][col] = array1[row * MATRIX_3F_LENGTH + col];
            }
        }
        Float[] array2 = {-3f, -10f, -6f, -1f, -1f, -1f, -3f, -3f, -2f};
        Float[][] second = new Float[MATRIX_3F_LENGTH][MATRIX_3F_LENGTH];
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                second[row][col] = array2[row * MATRIX_3F_LENGTH + col];
            }
        }
        Matrix3f a = new Matrix3f(first);
        Matrix3f b = new Matrix3f(second);
        Matrix3f sum = util.matrixSum(a, b);
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                Assertions.assertEquals(sum.matrix[row][col], expected.matrix[row][col]);
            }
        }
    }

    @Test
    void testMatrixSum4() {
        MatrixUtils util = new MatrixUtils();
        Matrix4f expected = new Matrix4f();
        expected = expected.createSingularMatrix();
        Float[] array1 = {-2f, -3f, -3f, -3f,
                -3f, -2f, -3f, -3f,
                -3f, -3f, -2f, -3f,
                -3f, -3f, -3f, -2f,};
        Float[][] first = new Float[MATRIX_4F_LENGTH][MATRIX_4F_LENGTH];
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                first[row][col] = array1[row * 4 + col];
            }
        }
        Float[][] second = new Float[MATRIX_4F_LENGTH][MATRIX_4F_LENGTH];
        for (Float[] row : second) {
            Arrays.fill(row, 3f);

        }
        Matrix4f a = new Matrix4f(first);
        Matrix4f b = new Matrix4f(second);
        Matrix4f sum = util.matrixSum(a, b);
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                Assertions.assertEquals(sum.matrix[row][col], expected.matrix[row][col]);
            }
        }
    }

    @Test
    void testMatrixDeduction3() {
        MatrixUtils util = new MatrixUtils();
        Matrix3f expected = new Matrix3f();
        expected = expected.createSingleMatrix();
        Float[] array1 = {4f, 10f, 6f, 1f, 2f, 1f, 3f, 3f, 3f};
        Float[][] first = new Float[MATRIX_3F_LENGTH][MATRIX_3F_LENGTH];
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                first[row][col] = array1[row * MATRIX_3F_LENGTH + col];
            }
        }
        Float[] array2 = {3f, 10f, 6f,
                1f, 1f, 1f,
                3f, 3f, 2f};
        Float[][] second = new Float[MATRIX_3F_LENGTH][MATRIX_3F_LENGTH];
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                second[row][col] = array2[row * MATRIX_3F_LENGTH + col];
            }
        }
        Matrix3f a = new Matrix3f(first);
        Matrix3f b = new Matrix3f(second);
        Matrix3f sum = util.matrixDeduction(a, b);
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                Assertions.assertEquals(sum.matrix[row][col], expected.matrix[row][col]);
            }
        }
    }

    @Test
    void testMatrixDeduction4() {
        MatrixUtils util = new MatrixUtils();
        Matrix4f expected = new Matrix4f();
        expected = expected.createSingularMatrix();
        Float[] array1 = {1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f};
        Float[][] first = new Float[MATRIX_4F_LENGTH][MATRIX_4F_LENGTH];
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                first[row][col] = array1[row * MATRIX_4F_LENGTH + col];
            }
        }
        Float[] array2 = {0f, 2f, 3f, 4f,
                5f, 5f, 7f, 8f,
                9f, 10f, 10f, 12f,
                13f, 14f, 15f, 15f};
        Float[][] second = new Float[MATRIX_4F_LENGTH][MATRIX_4F_LENGTH];
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                second[row][col] = array2[row * MATRIX_4F_LENGTH + col];
            }
        }
        Matrix4f a = new Matrix4f(first);
        Matrix4f b = new Matrix4f(second);
        Matrix4f sum = util.matrixDeduction(a, b);
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                Assertions.assertEquals(sum.matrix[row][col], expected.matrix[row][col]);
            }
        }
    }

    @Test
    void testMatrixOnVector3() {
        MatrixUtils util = new MatrixUtils();
        Matrix3f test = new Matrix3f();
        test = test.createSingleMatrix();
        Vector3f expectedResult = new Vector3f(1, 2, 3);
        Vector3f v = new Vector3f(1, 2, 3);
        Vector3f result = util.matrixOnVector(test, v);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());
    }

    @Test
    void testMatrixOnVector4() {
        MatrixUtils util = new MatrixUtils();
        Matrix4f test = new Matrix4f();
        test = test.createSingularMatrix();
        Vector4f expectedResult = new Vector4f(1, 2, 3, 4);
        Vector4f v = new Vector4f(1, 2, 3, 4);
        Vector4f result = util.matrixOnVector(test, v);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());
        Assertions.assertEquals(result.getV(), expectedResult.getV());
    }

    @Test
    void testTranspiration3() {
        MatrixUtils util = new MatrixUtils();

        Float[] arr1 = {1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 9f};
        Float[][] first = new Float[MATRIX_3F_LENGTH][MATRIX_3F_LENGTH];
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                first[row][col] = arr1[row * MATRIX_3F_LENGTH + col];
            }
        }
        Float[] second = {1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f};
        Float[][] test = new Float[MATRIX_3F_LENGTH][MATRIX_3F_LENGTH];
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                test[row][col] = second[row * MATRIX_3F_LENGTH + col];
            }
        }
        Matrix3f expected = new Matrix3f(test);
        Matrix3f a = new Matrix3f(first);
        Matrix3f sum = util.matrixTranspiration(a);
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                Assertions.assertEquals(sum.matrix[row][col], expected.matrix[row][col]);
            }
        }
    }

    @Test
    void testTranspiration4() {
        MatrixUtils util = new MatrixUtils();

        Float[] arr1 = {1f, 5f, 9f, 13f,
                2f, 6f, 10f, 14f,
                3f, 7f, 11f, 15f,
                4f, 8f, 12f, 16f};
        Float[][] first = new Float[MATRIX_4F_LENGTH][MATRIX_4F_LENGTH];
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                first[row][col] = arr1[row * MATRIX_4F_LENGTH + col];
            }
        }
        Float[] second = {1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f};
        Float[][] test = new Float[MATRIX_4F_LENGTH][MATRIX_4F_LENGTH];
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                test[row][col] = second[row * MATRIX_4F_LENGTH + col];
            }
        }
        Matrix4f expected = new Matrix4f(test);
        Matrix4f a = new Matrix4f(first);
        Matrix4f sum = util.matrixTranspiration(a);
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                Assertions.assertEquals(sum.matrix[row][col], expected.matrix[row][col]);
            }
        }
    }

    @Test
    void testCountDeterminate3() {
        MatrixUtils util = new MatrixUtils();
        Float[] arr1 = {1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 1f};
        Float[][] first = new Float[MATRIX_3F_LENGTH][MATRIX_3F_LENGTH];
        for (int row = 0; row < MATRIX_3F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_3F_LENGTH; col++) {
                first[row][col] = arr1[row * MATRIX_3F_LENGTH + col];
            }
        }
        float expectedResult = 24f;
        float result = util.countDeterminate(new Matrix3f(first));
        Assertions.assertEquals(result, expectedResult);
    }


    @Test
    void testCountDeterminate4() {
        MatrixUtils util = new MatrixUtils();
        Float[] arr1 = {1f, 3f, 3f, -7f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 1f};
        Float[][] first = new Float[MATRIX_4F_LENGTH][MATRIX_4F_LENGTH];
        for (int row = 0; row < MATRIX_4F_LENGTH; row++) {
            for (int col = 0; col < MATRIX_4F_LENGTH; col++) {
                first[row][col] = arr1[row * MATRIX_4F_LENGTH + col];
            }
        }
        float expectedResult = -120f;
        float result = util.countDeterminate(new Matrix4f(first));
        Assertions.assertEquals(result, expectedResult);
    }
}