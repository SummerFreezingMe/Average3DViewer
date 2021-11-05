package com.vsu.cgcourse.obj_writer;

import com.vsu.cgcourse.math.utils.Vector2f;
import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.model.Mesh;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ObjWriter {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    protected static File createFile(String name) {
        return new File(name + ".obj");
    }

    protected static void saveOutput(Mesh model, String name) {
        File object = createFile(name);
        ArrayList<String> result = new ArrayList<>();
        saveVectors(result, model);
        saveFinals(result, model);
        writeOutput(object, result);
    }


    protected static void saveVectors(ArrayList<String> result, Mesh model) {
        ArrayList<Vector3f> vertex = model.vertices;
        ArrayList<Vector3f> normals = model.normals;
        ArrayList<Vector2f> textureVertex = model.textureVertices;
        for (Vector3f vfm : vertex) {// VFM - Vectors From Model, and so on
            result.add(OBJ_VERTEX_TOKEN + " " + vfm.x + " " + vfm.y + " " + vfm.z);
        }
        for (Vector3f nfm : normals) {
            result.add(OBJ_NORMAL_TOKEN + " " + nfm.x + " " + nfm.y + " " + nfm.z);
        }
        for (Vector2f tfm : textureVertex) {
            result.add(OBJ_TEXTURE_TOKEN + " " + tfm.getX() + " " + tfm.getY());
        }
    }

    protected static void saveFinals(ArrayList<String> result, Mesh model) {

        for (int i = 0; i < model.polygonVertexIndices.size(); i++) {
            String finale = OBJ_FACE_TOKEN;
            ArrayList<Integer> pVertex = model.polygonVertexIndices.get(i);
            ArrayList<Integer> pNormals = model.polygonNormalIndices.get(i);
            ArrayList<Integer> pTextureVertex = model.polygonTextureVertexIndices.get(i);
            for (int j = 0; j < pVertex.size(); j++) {
                finale = finale.concat(" " + (pVertex.get(j) + 1));
                if (pTextureVertex.size() > 0) {
                    finale = finale.concat("/" + (pTextureVertex.get(j) + 1));
                }
                if (pNormals.size() > 0) {
                    finale = finale.concat("/" + (pNormals.get(j) + 1));
                }
            }
            result.add(finale);
        }
    }

    protected static void writeOutput(File object, ArrayList<String> result) {
        try {
            PrintWriter print = new PrintWriter(object);
            for (String strings : result) {
                print.print(strings + "\n");
            }
            print.close();
        } catch (
                IOException e) {
            System.out.println("Couldn't write result");
        }

    }
}
