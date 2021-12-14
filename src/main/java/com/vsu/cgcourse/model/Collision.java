package com.vsu.cgcourse.model;


import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.math.utils.VectorUtils;

public class Collision {
    public static float[] returnValue = new float[2];
    private static float tlast = 1000000;
    private static float tfirst = 0;
    static VectorUtils utils = new VectorUtils();

    public static boolean testInterSection(Mesh mesh, Mesh secondMesh) {
        for (int i = 0; i < mesh.normals.size(); i++) {//
            Vector3f D = mesh.normals.get(i);
            float[] r0, r1;
            r0 = computeInterval(mesh, D);
            float min0 = r0[0], max0 = r0[1];
            r1 = computeInterval(secondMesh, D);
            float min1 = r1[0], max1 = r1[1];
            if (max1 < min0 || max0 < min1) return false;
        }
        for (int i = 0; i < secondMesh.normals.size(); i++) {//
            Vector3f D = secondMesh.normals.get(i);
            float[] r0, r1;
            r0 = computeInterval(mesh, D);
            float min0 = r0[0], max0 = r0[1];
            r1 = computeInterval(secondMesh, D);
            float min1 = r1[0], max1 = r1[1];
            if (max1 < min0 || max0 < min1) return false;
        }
        return true;
    }

    private static float[] computeInterval(Mesh mesh, Vector3f secondMesh) {
        float max, min;
        min = utils.vectorDot(secondMesh, mesh.vertices.get(0));
        max = min;
        for (int i = 1; i < mesh.vertices.size(); i++) {
            float v = utils.vectorDot(secondMesh, mesh.vertices.get(i));

            if (v < min) min = v;
            else if (v > max) max = v;
        }
        float[] pair = new float[2];
        pair[0] = min;
        pair[1] = max;
        return pair;
    }

    public static boolean testInterSection(Mesh mesh, Vector3f v0, Mesh secondMesh, Vector3f v1, float tMax) {
        Vector3f v= utils.vectorSum(v1, v0,-1);
        tfirst = 0;
        tlast = 100000000;
        for (int i = 0; i < mesh.normals.size(); i++) {
            Vector3f D = mesh.normals.get(i);
            float[] r0, r1;
            r0 = computeInterval(mesh, D);
            float min0 = r0[0], max0 = r0[1];
            r1 = computeInterval(secondMesh, D);
            float min1 = r1[0], max1 = r1[1];
            float speed = utils.vectorDot(D, v);
            if (NoIntersect(tMax, speed, min0, max0, min1, max1)) return false;
        }
        for (int i = 0; i < secondMesh.normals.size(); i++) {
            Vector3f D = secondMesh.normals.get(i);
            float[] r0, r1;
            r0 = computeInterval(mesh, D);
            float min0 = r0[0], max0 = r0[1];
            r1 = computeInterval(secondMesh, D);
            float min1 = r1[0], max1 = r1[1];
            float speed = utils.vectorDot(D, v);
            if (NoIntersect(tMax, speed, min0, max0, min1, max1)) return false;
        }
        return true;
    }

    private static boolean NoIntersect(float tmax, float speed, float min0,
                                       float max0, float min1, float max1) {
        float t;
        if (max1 < min0) {
            if (speed <= 0) {
                return true;
            }
            t = (min0 - max1) / speed;
            if (t > tfirst) {
                tfirst = t;
            }
            if (tfirst > tmax) {
                return true;
            }
            t = (max0 - min1) / speed;
            if (t < tlast) {
                tlast = t;
            }
            return tfirst > tlast;
        } else if (max0 < min1) {
            // interval(C1) initially on �right� of interval(C0)
            if (speed >= 0) // intervals moving apart
            {
                return true;
            }
            t = (max0 - min1) / speed;
            if (t > tfirst) {
                tfirst = t;
            }
            if (tfirst > tmax) {
                return true;
            }
            t = (min0 - max1) / speed;
            if (t < tlast) {
                tlast = t;
            }
            return tfirst > tlast;
        } else {
            // interval(C0) and interval(C1) overlap
            if (speed > 0) {
                t = (max0 - min1) / speed;
                if (t < tlast) {
                    tlast = t;
                }
                return tfirst > tlast;
            } else if (speed < 0) {
                t = (min0 - max1) / speed;
                if (t < tlast) {
                    tlast = t;
                }
                return tfirst > tlast;
            }
        }
        return false;
    }


}
