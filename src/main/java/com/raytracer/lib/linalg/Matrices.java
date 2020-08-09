package com.raytracer.lib.linalg;

import java.util.ArrayList;
import java.util.List;

/**
 * Matrix Factory Class
 */
public class Matrices {

    public static Matrix Zero(int n) {
        /*
        |0      0       0       0|  }
        |0      0       0       0|  }   nxn matrix
        |0      0       0       0|  }
        |0      0       0       0|  }
        */
        return new Matrix(get2DListOfSize(n));
    }


    public static Matrix Identity(int n) {
        /*
        |1      0       0       0|  }
        |0      1       0       0|  }   nxn matrix
        |0      0       1       0|  }
        |0      0       0       1|  }
        */
        List<List<Double>> backingArray = get2DListOfSize(n);
        for(int i = 0; i < n; i++)
            backingArray.get(i).set(i, 1d);
        return new Matrix(backingArray);
    }


    public static Matrix translation(double x, double y, double z) {
        List<List<Double>> backingArray = get2DListOfSize(4);
        /*
        |1      0       0       x|
        |0      1       0       y|
        |0      0       1       z|
        |0      0       0       1|
        */

        backingArray.get(0).set(3, x);
        backingArray.get(1).set(3, y);
        backingArray.get(2).set(3, z);

        backingArray.get(0).set(0, 1d);
        backingArray.get(1).set(1, 1d);
        backingArray.get(2).set(2, 1d);
        backingArray.get(3).set(3, 1d);

        return new Matrix(backingArray);
    }


    public static Matrix scale(double x, double y, double z) {
        List<List<Double>> backingArray = get2DListOfSize(4);
        /*
        |x      0       0       0|  }
        |0      y       0       0|  }   nxn matrix
        |0      0       z       0|  }
        |0      0       0       1|  }
        */
        backingArray.get(0).set(0, x);
        backingArray.get(1).set(1, y);
        backingArray.get(2).set(2, z);
        backingArray.get(3).set(3, 1d);
        return new Matrix(backingArray);
    }


    /**
     * left handed rotation about x axis.
     * @param rad # radians to rotate
     * @return Matrix that will rotate by that amount
     */
    public static Matrix rotateX(double rad) {
        List<List<Double>> backingArray = get2DListOfSize(4);
        /*
        |1      0       0       0|
        |0      cosr    -sinr   0|
        |0      sinr    cosr    0|
        |0      0       0       1|
        */
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        backingArray.get(0).set(0, 1d);
        backingArray.get(1).set(1, cos);
        backingArray.get(1).set(2, -1*sin);
        backingArray.get(2).set(1, sin);
        backingArray.get(2).set(2, cos);
        backingArray.get(3).set(3, 1d);

        return new Matrix(backingArray);
    }


    /**
     * left handed rotation about y axis.
     * @param rad # radians to rotate
     * @return Matrix that will rotate by that amount
     */
    public static Matrix rotateY(double rad) {
        List<List<Double>> backingArray = get2DListOfSize(4);
        /*
        |cos    0       sin     0|
        |0      1       0       0|
        |-sin   0       cos     0|
        |0      0       0       1|
        */
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        backingArray.get(0).set(0, cos);
        backingArray.get(0).set(2, sin);
        backingArray.get(1).set(1, 1d);
        backingArray.get(2).set(0, -1*sin);
        backingArray.get(2).set(2, cos);
        backingArray.get(3).set(3, 1d);

        return new Matrix(backingArray);
    }


    /**
     * left handed rotation about z axis.
     * @param rad # radians to rotate
     * @return Matrix that will rotate by that amount
     */
    public static Matrix rotateZ(double rad) {
        List<List<Double>> backingArray = get2DListOfSize(4);
        /*
        |cosr   -sinr   0       0|
        |sinr   cosr    0       0|
        |0      0       1       0|
        |0      0       0       1|
        */
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        backingArray.get(0).set(0, cos);
        backingArray.get(0).set(1, -1*sin);
        backingArray.get(1).set(0, sin);
        backingArray.get(1).set(1, cos);
        backingArray.get(2).set(2, 1d);
        backingArray.get(3).set(3, 1d);

        return new Matrix(backingArray);
    }

    public static Matrix shear(double xy, double xz,
                               double yx, double yz,
                               double zx, double zy) {
        List<List<Double>> backingArray = get2DListOfSize(4);
        /*
        |1      xy      xz       0|
        |yx     1       yz       0|
        |zx     zy      1       0|
        |0      0       0       1|
        */

        backingArray.get(0).set(0, 1d);
        backingArray.get(1).set(1, 1d);
        backingArray.get(2).set(2, 1d);
        backingArray.get(3).set(3, 1d);

        backingArray.get(0).set(1, xy);
        backingArray.get(0).set(2, xz);
        backingArray.get(1).set(0, yx);
        backingArray.get(1).set(2, yz);
        backingArray.get(2).set(0, zx);
        backingArray.get(2).set(1, zy);

        return new Matrix(backingArray);
    }


    private static List<List<Double>> get2DListOfSize(int n) {
        List<List<Double>> listOfRows = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>();
            for(int j = 0; j < n; j++) {
                row.add(0d);
            }
            listOfRows.add(row);
        }
        return listOfRows;
    }

}
