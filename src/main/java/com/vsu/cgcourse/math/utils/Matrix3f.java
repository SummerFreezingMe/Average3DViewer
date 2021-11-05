package com.vsu.cgcourse.math.utils;

public class Matrix3f {
    public Float[][] matrix;

    public Matrix3f(Float[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix3f() {
        this.matrix = new Float[MATRIX_SIZE][MATRIX_SIZE];
    }

    public Matrix3f createSingleMatrix() {
        this.matrix = new Float[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                if (row == col) {
                    matrix[row][col] = 1f;
                } else
                    matrix[row][col] = 0f;
            }
        }
        return new Matrix3f( matrix);
    }
    public Matrix3f createEmptyMatrix() {
        this.matrix = new Float[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                    matrix[row][col] = 0f;
            }
        }
        return new Matrix3f( matrix);
    }
    public final int MATRIX_SIZE = 3;


}
