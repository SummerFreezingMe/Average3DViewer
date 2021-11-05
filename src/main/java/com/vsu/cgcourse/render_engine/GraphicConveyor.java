package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.utils.*;

public class GraphicConveyor {
    static VectorUtils utils = new VectorUtils();
    public static Matrix4f rotateScaleTranslate() {
        float[] husk = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        Float[][] matrix = new Float[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                matrix[row][col] = husk[row * 4 + col];
            }
        }
        return new Matrix4f(matrix);
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX = new Vector3f(0,0,0);
        Vector3f resultY;
        Vector3f resultZ;

        resultZ=utils.vectorSum(target, eye,-1);
        resultX=utils.vectorMultiplication(up, resultZ);
        resultY=utils.vectorMultiplication(resultZ, resultX);

        utils.normalisation(resultX);
        utils.normalisation(resultY);
        utils.normalisation(resultZ);

        Float[][] matrix = new Float[4][4];
              matrix[0][0]=  resultX.x;
                      matrix[0][1]=    resultY.x;
                      matrix[0][2]=resultZ.x;
                      matrix[0][3]=0F;
        matrix[1][0]=  resultX.y;
                matrix[1][1]=   resultY.y;
                matrix[1][2]=  resultZ.y;
                matrix[1][3]=    0F;
                matrix[2][0]= resultX.z;
                matrix[2][1]=  resultY.z;
                matrix[2][2]=    resultZ.z;
                matrix[2][3]=     0F;
                matrix[3][0]=  -utils.vectorDot(resultX,eye);
                matrix[3][1]=     -utils.vectorDot(resultY,eye);
                matrix[3][2]= -utils.vectorDot(resultZ,eye);
                matrix[3][3]= 1F;
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        result.createMatrixOfZeros();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.matrix[0][0] = tangentMinusOnDegree / aspectRatio;
        result.matrix[1][1]  = tangentMinusOnDegree;
        result.matrix[2][2]  = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.matrix[2][3]  = 1.0F;
        result.matrix[3][2]  = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex) {
        final float x = (vertex.x * matrix.matrix[0][0] ) + (vertex.y * matrix.matrix[1][0] ) + (vertex.z * matrix.matrix[2][0] ) + matrix.matrix[3][0] ;
        final float y = (vertex.x * matrix.matrix[0][1] ) + (vertex.y * matrix.matrix[1][1] ) + (vertex.z * matrix.matrix[2][1] ) + matrix.matrix[3][1] ;
        final float z = (vertex.x * matrix.matrix[0][2] ) + (vertex.y * matrix.matrix[1][2] ) + (vertex.z * matrix.matrix[2][2] ) + matrix.matrix[3][2] ;
        final float w = (vertex.x * matrix.matrix[0][3] ) + (vertex.y * matrix.matrix[1][3] ) + (vertex.z * matrix.matrix[2][3] ) + matrix.matrix[3][3] ;
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F);
    }
}
