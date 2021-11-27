package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.utils.*;
import com.vsu.cgcourse.model.Mesh;

public class GraphicConveyor {
    static VectorUtils utils = new VectorUtils();
    static MatrixUtils utilsM = new MatrixUtils();

    public static Matrix4f modelMatrix(Vector3f scale, Vector3f rotate,Vector3f translate, Mesh mesh) {
        Matrix4f rtsMatrix = new Matrix4f(mesh.matrix);
        rtsMatrix=scale(scale, rtsMatrix);
        rtsMatrix=rotate(rotate, rtsMatrix);
        rtsMatrix = translate(translate, rtsMatrix);
        return rtsMatrix;
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX;
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
       Vector4f v = utilsM.matrixOnVector(matrix,new Vector4f(vertex.x, vertex.y, vertex.z, 1f));
        return new Vector3f(v.getX() / v.getV(), v.getY() / v.getV(), v.getZ() / v.getV());
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F);
    }

    public static Matrix4f rotate(Vector3f rotate, Matrix4f matrix) {
        float sinX = (float) Math.sin(Math.toRadians(rotate.getX()));
        float cosX = (float) Math.cos(Math.toRadians(rotate.getX()));
        float sinY = (float) Math.sin(Math.toRadians(rotate.getY()));
        float cosY = (float) Math.cos(Math.toRadians(rotate.getY()));
        float sinZ = (float) Math.sin(Math.toRadians(rotate.getZ()));
        float cosZ = (float) Math.cos(Math.toRadians(rotate.getZ()));

        Float[][] rotateZMatrix = new Float[][]{
                {cosZ, -sinZ, 0f, 0f},
                {sinZ, cosZ, 0f, 0f},
                {0f, 0f, 1f, 0f},
                {0f, 0f, 0f, 1f}
        };
        Float[][] rotateYMatrix = new Float[][]{
                {cosY, 0f, sinY, 0f},
                {0f, 1f, 0f, 0f},
                {-sinY, 0f, cosY, 0f},
                {0f, 0f, 0f, 1f}
        };
        Float[][] rotateXMatrix = new Float[][]{
                {1f, 0f, 0f, 0f},
                {0f, cosX, -sinX, 0f},
                {0f, sinX, cosX, 0f},
                {0f, 0f, 0f, 1f}
        };
        Matrix4f rotationMatrix = new Matrix4f(rotateZMatrix);
        Matrix4f yAxisRotation = new Matrix4f(rotateYMatrix);
        Matrix4f xAxisRotation = new Matrix4f(rotateXMatrix);
        rotationMatrix=utilsM.matrixMultiply(rotationMatrix,yAxisRotation);
        rotationMatrix=utilsM.matrixMultiply(rotationMatrix,xAxisRotation);
        return utilsM.matrixMultiply(matrix,rotationMatrix);
    }

    public static Matrix4f translate(Vector3f translation, Matrix4f matrix) {
        Float[][] translateMatrix = new Float[][]{
                {1f, 0f, 0f, translation.getX()},
                {0f, 1f, 0f, translation.getY()},
                {0f, 0f, 1f, translation.getZ()},
                {0f, 0f, 0f, 1f}
        };
        Matrix4f translateMatrix4f = new Matrix4f(translateMatrix);
        translateMatrix4f=utilsM.matrixMultiply(translateMatrix4f,matrix);
        return translateMatrix4f;
    }
    public static Matrix4f scale(Vector3f scale, Matrix4f matrix) {
        Float[][] scaleMatrix = new Float[][]{
                {scale.getX(), 0f, 0f, 0f},
                {0f, scale.getY(), 0f, 0f},
                {0f, 0f, scale.getZ(), 0f},
                {0f, 0f,    0f,        1f}
        };
        Matrix4f scaleMatrix4f = new Matrix4f(scaleMatrix);
        return utilsM.matrixMultiply(matrix,scaleMatrix4f);
    }

}
