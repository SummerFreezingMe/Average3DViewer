package com.vsu.cgcourse.math.utils;

public class Matrix4f {
    public  Float[][] matrix;
    public final int MATRIX_SIZE = 4;

    public Matrix4f() {
        this.matrix = new Float[MATRIX_SIZE][MATRIX_SIZE];
    }

    public Matrix4f createSingularMatrix() {
        this.matrix = new Float[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                if (row == col) {
                    matrix[row][col] = 1f;
                } else
                    matrix[row][col] = 0f;
            }
        }
        return new Matrix4f( matrix);
    }
    public Matrix4f createMatrixOfZeros() {
        this.matrix = new Float[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                matrix[row][col] = 0f;
            }
        }
        return new Matrix4f( matrix);
    }

    public Matrix4f(Float[][] test) {
        this.matrix = test;
    }
}
