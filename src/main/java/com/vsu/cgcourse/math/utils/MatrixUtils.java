package com.vsu.cgcourse.math.utils;

public class MatrixUtils {

    public Matrix3f matrixSum(final Matrix3f m1, Matrix3f m2) {
        Float[][] m3 = new Float[m1.MATRIX_SIZE][m2.MATRIX_SIZE];
        for (int row = 0; row < m1.MATRIX_SIZE; row++) {
            for (int col = 0; col < m2.MATRIX_SIZE; col++) {
                m3[row][col] = m1.matrix[row][col] + m2.matrix[row][col];
            }
        }
        return new Matrix3f(m3);
    }

    public Matrix4f matrixSum(final Matrix4f m1, final Matrix4f m2) {
        float[][] m3 = new float[m1.MATRIX_SIZE][m2.MATRIX_SIZE];
        for (int row = 0; row < m1.MATRIX_SIZE; row++) {
            for (int col = 0; col < m2.MATRIX_SIZE; col++) {
                m3[row][col] = m1.matrix[row][col] + m2.matrix[row][col];
            }
        }
        return new Matrix4f(m3);
    }

    public Matrix3f matrixDeduction(final Matrix3f m1, final Matrix3f m2) {
        Float[][] m3 = new Float[m1.MATRIX_SIZE][m1.MATRIX_SIZE];
        for (int row = 0; row < m1.MATRIX_SIZE; row++) {
            for (int col = 0; col < m2.MATRIX_SIZE; col++) {
                m3[row][col] = m1.matrix[row][col] - m2.matrix[row][col];
            }
        }
        return new Matrix3f(m3);
    }

    public Matrix4f matrixDeduction(final Matrix4f m1, final Matrix4f m2) {
        float[][] m3 = new float[m1.MATRIX_SIZE][m2.MATRIX_SIZE];
        for (int row = 0; row < m1.MATRIX_SIZE; row++) {
            for (int col = 0; col < m2.MATRIX_SIZE; col++) {
                m3[row][col] = m1.matrix[row][col] - m2.matrix[row][col];
            }
        }
        return new Matrix4f(m3);
    }

    public Vector3f matrixOnVector(final Matrix3f m, final Vector3f v) {
        return new Vector3f(m.matrix[0][0] * v.getX() + m.matrix[0][1] * v.getY() + m.matrix[0][2] * v.getZ(),
                m.matrix[1][0] * v.getX() + m.matrix[1][1] * v.getY() + m.matrix[1][2] * v.getZ(),
                m.matrix[2][0] * v.getX() + m.matrix[2][1] * v.getY() + m.matrix[2][2] * v.getZ()
        );
    }

    public Vector4f matrixOnVector(final Matrix4f m, final Vector4f v) {
        return new Vector4f(m.matrix[0][0] * v.getX() + m.matrix[0][1] * v.getY() + m.matrix[0][2] * v.getZ() + m.matrix[0][3] * v.getV(),
                m.matrix[1][0] * v.getX() + m.matrix[1][1] * v.getY() + m.matrix[1][2] * v.getZ() + m.matrix[1][3] * v.getV(),
                m.matrix[2][0] * v.getX() + m.matrix[2][1] * v.getY() + m.matrix[2][2] * v.getZ() + m.matrix[2][3] * v.getV(),
                m.matrix[3][0] * v.getX() + m.matrix[3][1] * v.getY() + m.matrix[3][2] * v.getZ() + m.matrix[3][3] * v.getV()
        );
    }


    public Matrix3f matrixTranspiration(final Matrix3f m) {
        Float[][] t = new Float[m.MATRIX_SIZE][m.MATRIX_SIZE];
        for (int row = 0; row < m.MATRIX_SIZE; row++) {
            for (int col = 0; col < m.MATRIX_SIZE; col++) {
                t[row][col] = m.matrix[col][row];
            }
        }
        return new Matrix3f(t);
    }

    public Matrix4f matrixTranspiration(final Matrix4f m) {
        float[][] t = new float[m.MATRIX_SIZE][m.MATRIX_SIZE];
        for (int row = 0; row < m.MATRIX_SIZE; row++) {
            for (int col = 0; col < m.MATRIX_SIZE; col++) {
                t[row][col] = m.matrix[col][row];
            }
        }
        return new Matrix4f(t);
    }

    public float countDeterminate(final Matrix3f m) {
        return m.matrix[0][0] * m.matrix[1][1] * m.matrix[2][2] + m.matrix[0][1] * m.matrix[1][2] * m.matrix[2][0] +
                m.matrix[1][0] * m.matrix[2][1] * m.matrix[0][2] - m.matrix[2][0] * m.matrix[1][1] * m.matrix[0][2] -
                m.matrix[0][1] * m.matrix[1][0] * m.matrix[2][2] - m.matrix[0][0] * m.matrix[1][2] * m.matrix[2][1];
    }

    public float countDeterminate(final Matrix4f m) {
        float det = 0f;
        for (int loc = 0; loc < m.MATRIX_SIZE; loc++) {
            float element = m.matrix[0][loc];
            int mRow = 0;
            int mCol = 0;
            Float[][] minor = new Float[m.MATRIX_SIZE - 1][m.MATRIX_SIZE - 1];
            for (int row = 0; row < m.MATRIX_SIZE; row++) {
                for (int col = 0; col < m.MATRIX_SIZE; col++) {
                    if (row != 0 && col != loc) {
                        minor[mRow][mCol] = m.matrix[row][col];
                        mCol++;
                        if (mCol == 3) {
                            mCol = 0;
                            mRow++;
                        }
                    }
                }
            }
            det += element * countDeterminate(new Matrix3f(minor)) * Math.pow(-1, loc + 2);
        }
        return det;
    }
    public Matrix4f matrixMultiply(final Matrix4f m1, final Matrix4f m2) {
        float[][] m3 = new float[m1.MATRIX_SIZE][m1.MATRIX_SIZE];
        m3[0][0]=m1.matrix[0][0]*m2.matrix[0][0]+m1.matrix[0][1]*m2.matrix[1][0]+m1.matrix[0][2]*m2.matrix[2][0]+m1.matrix[0][3]*m2.matrix[3][0];
        m3[0][1]=m1.matrix[0][0]*m2.matrix[0][1]+m1.matrix[0][1]*m2.matrix[1][1]+m1.matrix[0][2]*m2.matrix[2][1]+m1.matrix[0][3]*m2.matrix[3][1];
        m3[0][2]=m1.matrix[0][0]*m2.matrix[0][2]+m1.matrix[0][1]*m2.matrix[1][2]+m1.matrix[0][2]*m2.matrix[2][2]+m1.matrix[0][3]*m2.matrix[3][2];
        m3[0][3]=m1.matrix[0][0]*m2.matrix[0][3]+m1.matrix[0][1]*m2.matrix[1][3]+m1.matrix[0][2]*m2.matrix[2][3]+m1.matrix[0][3]*m2.matrix[3][3];
        m3[1][0]=m1.matrix[1][0]*m2.matrix[0][0]+m1.matrix[1][1]*m2.matrix[1][0]+m1.matrix[1][2]*m2.matrix[2][0]+m1.matrix[1][3]*m2.matrix[3][0];
        m3[1][1]=m1.matrix[1][0]*m2.matrix[0][1]+m1.matrix[1][1]*m2.matrix[1][1]+m1.matrix[1][2]*m2.matrix[2][1]+m1.matrix[1][3]*m2.matrix[3][1];
        m3[1][2]=m1.matrix[1][0]*m2.matrix[0][2]+m1.matrix[1][1]*m2.matrix[1][2]+m1.matrix[1][2]*m2.matrix[2][2]+m1.matrix[1][3]*m2.matrix[3][2];
        m3[1][3]=m1.matrix[1][0]*m2.matrix[0][3]+m1.matrix[1][1]*m2.matrix[1][3]+m1.matrix[1][2]*m2.matrix[2][3]+m1.matrix[1][3]*m2.matrix[3][3];
        m3[2][0]=m1.matrix[2][0]*m2.matrix[0][0]+m1.matrix[2][1]*m2.matrix[1][0]+m1.matrix[2][2]*m2.matrix[2][0]+m1.matrix[2][3]*m2.matrix[3][0];
        m3[2][1]=m1.matrix[2][0]*m2.matrix[0][1]+m1.matrix[2][1]*m2.matrix[1][1]+m1.matrix[2][2]*m2.matrix[2][1]+m1.matrix[2][3]*m2.matrix[3][1];
        m3[2][2]=m1.matrix[2][0]*m2.matrix[0][2]+m1.matrix[2][1]*m2.matrix[1][2]+m1.matrix[2][2]*m2.matrix[2][2]+m1.matrix[2][3]*m2.matrix[3][2];
        m3[2][3]=m1.matrix[2][0]*m2.matrix[0][3]+m1.matrix[2][1]*m2.matrix[1][3]+m1.matrix[2][2]*m2.matrix[2][3]+m1.matrix[2][3]*m2.matrix[3][3];
        m3[3][0]=m1.matrix[3][0]*m2.matrix[0][0]+m1.matrix[3][1]*m2.matrix[1][0]+m1.matrix[3][2]*m2.matrix[2][0]+m1.matrix[3][3]*m2.matrix[3][0];
        m3[3][1]=m1.matrix[3][0]*m2.matrix[0][1]+m1.matrix[3][1]*m2.matrix[1][1]+m1.matrix[3][2]*m2.matrix[2][1]+m1.matrix[3][3]*m2.matrix[3][1];
        m3[3][2]=m1.matrix[3][0]*m2.matrix[0][2]+m1.matrix[3][1]*m2.matrix[1][2]+m1.matrix[3][2]*m2.matrix[2][2]+m1.matrix[3][3]*m2.matrix[3][2];
        m3[3][3]=m1.matrix[3][0]*m2.matrix[0][3]+m1.matrix[3][1]*m2.matrix[1][3]+m1.matrix[3][2]*m2.matrix[2][3]+m1.matrix[3][3]*m2.matrix[3][3];
        return new Matrix4f(m3);
    }

}

