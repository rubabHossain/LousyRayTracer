package com.raytracer.lib.linalg

import spock.lang.Specification

class MatrixSpec extends Specification{

    def "Constructing matrix from list of ROWS"() {
        given:
        def row0 = [0.0, 0.1, 0.2, 0.3]
        def row1 = [1.0, 1.1, 1.2, 1.3]
        def row2 = [2.0, 2.1, 2.2, 2.3]
        def row3 = [3.0, 3.1, 3.2, 3.3]
        def listOfRows = [row0, row1, row2, row3]

        when:
        def mtx = new Matrix(listOfRows)
        def underlyingArray = mtx.getBackingArray()

        then:
        underlyingArray.get(0).get(0) == 0.0
        underlyingArray.get(0).get(1) == 0.1
        underlyingArray.get(0).get(2) == 0.2
        underlyingArray.get(2).get(3) == 2.3
        underlyingArray.get(2).get(0) == 2.0
        underlyingArray.get(3).get(1) == 3.1
        underlyingArray.get(3).get(3) == 3.3
    }


    def "Matrix EQUALITY"() {
        when:
        def row0 = [0.0d, 0.1d, 0.2d, 0.3d]
        def row1 = [1.0d, 1.1d, 1.2d, 1.3d]
        def row2 = [2.0d, 2.1d, 2.2d, 2.3d]
        def row3 = [3.0d, 3.1d, 3.2d, 3.3d]
        def row3Dup = [3.0d, 3.1d, 3.2d, 3.3d+1E-8]
        def listOfRows = [row0, row1, row2, row3]
        def listOfRowsDup = [row0, row1, row2, row3Dup]

        def listOfRowsDiffOrder = [row1, row0, row3, row2]
        def listOfRowsDiffSize = [row1, row0]
        def listOfRowsDiff = [row1, row1, row1, row1]

        def ogMtx = new Matrix(listOfRows)
        def mtx1Same = new Matrix(listOfRows)
        def mtx2Same = new Matrix(listOfRowsDup)

        def mtx1Diff = new Matrix(listOfRowsDiffOrder)
        def mtx2Diff = new Matrix(listOfRowsDiffSize)
        def mtx3Diff = new Matrix(listOfRowsDiff)

        then:
        ogMtx == ogMtx
        ogMtx == mtx1Same
        ogMtx == mtx2Same

        ogMtx != mtx1Diff
        ogMtx != mtx2Diff
        ogMtx != mtx3Diff
    }


    def "Matrix x Matrix MULTIPLICATION"() {
        given:
        def row0 = [1d,     2d,     3d,     4.1d]
        def row1 = [5d,     6d,     7d,     1.3d]
        def row2 = [2d,     2.1d,   2.2d,   2.3d]
        def row3 = [3.0d,   3.1d,   3.2d,   3.3d]
        def mtxArray4x4_1 = [row0, row1, row2, row3]  // 4x4
        def mtxArray4x4_2 = [row0, row2, row3, row1]  // 4x4
        def mtxArray4x4_1x2_CORRECT =
                [[34.5d,	40.1d,	45.7d,	23.93d],
                 [44.5d,	52.1d,	59.7d,	59.09d],
                 [24.3d,	29.03d,	33.76d,	23.28d],
                 [35.3d,	42.23d,	49.16d,	34.28d]]

        def row4 = [5d,     6d,     7d]
        def mtxArray4x3_1 = [row4, row4, row4, row4]  // 4 x 3
        def mtxArray4x3_1x1_CORRECT =
                [[50.5d,    60.6d,  70.7d],
                 [96.5d,    115.8d, 135.1d],
                 [43d,      51.6d,  60.2d],
                 [63d,      75.6d,  88.2d]]

        def anotherArray =
                [[ 1d, 2d, 3d, 4d ],
                 [ 5d, 6d, 7d, 8d ],
                 [ 9d, 8d, 7d, 6d ],
                 [ 5d, 4d, 3d, 2d ]]
        def anotherArray2 =
                [[ -2d, 1d, 2d, 3d ],
                 [ 3d, 2d, 1d, -1d ],
                 [ 4d, 3d, 6d, 5d ],
                 [ 1d, 2d, 7d, 8d ]]
        def anotherSolution =
                [[ 20d, 22d, 50d, 48d ],
                 [ 44d, 54d, 114d, 108d ],
                 [ 40d, 58d, 110d, 102d ],
                 [ 16d, 26d, 46d, 42d ]]
        when:
        def mtx4x4_1 = new Matrix(mtxArray4x4_1)
        def mtx4x4_2 = new Matrix(mtxArray4x4_2)
        def mtx4x3_1 = new Matrix(mtxArray4x3_1)

        def mtx4x4_1x2_CORRECT = new Matrix(mtxArray4x4_1x2_CORRECT)
        def mtx4x3_1x1_CORRECT = new Matrix(mtxArray4x3_1x1_CORRECT)

        def anotherMtx = new Matrix(anotherArray)
        def anotherMtx2 = new Matrix(anotherArray2)
        def anotherMtxSoln = new Matrix(anotherSolution)

        then:
        mtx4x4_1.mult(mtx4x4_2) == mtx4x4_1x2_CORRECT
        mtx4x4_1.mult(mtx4x3_1) == mtx4x3_1x1_CORRECT
        anotherMtx.mult(anotherMtx2) == anotherMtxSoln
    }


    def "Matrix x Vector MULTIPLICATION"() {
        given:
        def mtxArray =
                [[ 1d, 2d, 3d, 4d ],
                 [ 5d, 6d, 7d, 8d ],
                 [ 9d, 8d, 7d, 6d ],
                 [ 5d, 4d, 3d, 2d ]]
        def vctrArray = [1d, 2d, 3d, 4d]

        def mtxVctrProdArray = [30d, 70d, 70d, 30d]

        when:
        def matrix = new Matrix(mtxArray)
        def vector = new ColumnVector(vctrArray)
        def soln = new ColumnVector(mtxVctrProdArray)
        then:
        matrix.mult(vector) == soln
    }


    def "Matrix TRANSPOSE" () {
        when:
        def identity4 = Matrices.Identity(4);
        def identity1 = Matrices.Identity(1);

        def mtxArray =
                [[ 1d, 2d, 3d, 4d ],
                 [ 5d, 6d, 7d, 8d ],
                 [ 9d, 8d, 7d, 6d ],
                 [ 5d, 4d, 3d, 2d ]]
        def mtxArrayTranspose =
                [[ 1d, 5d, 9d, 5d ],
                 [ 2d, 6d, 8d, 4d ],
                 [ 3d, 7d, 7d, 3d ],
                 [ 4d, 8d, 6d, 2d ]]

        def mtx = new Matrix(mtxArray)
        def mtxTranspose = new Matrix(mtxArrayTranspose)

        then:
        identity1.transpose() == identity1
        identity4.transpose() == identity4

        mtx.transpose() == mtxTranspose
        mtxTranspose.transpose() == mtx

    }


    def "Matrix DETERMINANT"() {
        given:
        def mtx1_2x2 = new Matrix([[1d, 2d],
                                   [3d, 4d]])

        def mtx2_4x4 = new Matrix([[ -2d , -8d ,  3d ,  5d ],
                                   [ -3d ,  1d ,  7d ,  3d ],
                                   [  1d ,  2d , -9d ,  6d ],
                                   [ -6d ,  7d ,  7d , -9d ]])


        def mtx3_3x3 = new Matrix([[  1d ,  2d ,  6d ],
                                   [ -5d ,  8d , -4d ],
                                   [  2d ,  6d ,  4d ]])


        when:
        def det1 = mtx1_2x2.determinant()
        def det2 = mtx2_4x4.determinant()
        def det3 = mtx3_3x3.determinant()

        then:
        det1 == -2
        det2 == -4071
        det3 == -196
    }


    def "Matrix SUBMATRIX"() {
        given:
        def mtxArray1_2x2 = [[1d, 2d], [3d, 4d]]
        def mtx1_2x2 = new Matrix(mtxArray1_2x2)
        def mtx1_ans = new Matrix([[3d]]);

        def mtxArray2_4x4 =
                [[ 1d, 2d, 3d, 4d ],
                 [ 5d, 6d, 7d, 8d ],
                 [ 9d, 8d, 7d, 6d ],
                 [ 5d, 4d, 3d, 2d ]]

        def mtx2_4x4 = new Matrix(mtxArray2_4x4)
        def mtx2_ans = new Matrix([[5d, 7d], [9d, 7d]])


        when:
        def mtx1_1x1 = mtx1_2x2.subMatrix(0,1)
        def mtx2_2x2 = mtx2_4x4.subMatrix(0, 3).subMatrix(2, 1)

        then:
        mtx1_1x1 == mtx1_ans
        mtx2_2x2 == mtx2_ans
    }


    def "Matrix MINOR"() {
        given:
        def mtxArray = [[ 3d ,  5d ,  0d ],
                        [ 2d , -1d , -7d ],
                        [ 6d , -1d ,  5d ]]
        def mtx = new Matrix(mtxArray)

        when:
        def mtxMinor = mtx.minor(1,0)

        then:
        mtxMinor == 25
    }

    def "Matrix COFACTOR"() {
        given:
        def mtxArray = [[ 3d ,  5d ,  0d ],
                        [ 2d , -1d , -7d ],
                        [ 6d , -1d ,  5d ]]
        def mtx = new Matrix(mtxArray)

        when:
        def mtxCofactor00 = mtx.cofactor(0,0)
        def mtxCofactor10 = mtx.cofactor(1,0)

        then:
        mtxCofactor00 == -12
        mtxCofactor10 == -25
    }

    def "Matrix INVERSE"() {
        when:
        def mtx1 = new Matrix([[ 8d , -5d ,  9d ,  2d ],
                               [  7d ,  5d ,  6d ,  1d ],
                               [ -6d ,  0d ,  9d ,  6d ],
                               [ -3d ,  0d , -9d , -4d ]])
        def mtx1Inverse = new Matrix([[ -0.15385d , -0.15385d , -0.28205d , -0.53846d ],
                                      [ -0.07692d ,  0.12308d ,  0.02564d ,  0.03077d ],
                                      [  0.35897d ,  0.35897d ,  0.43590d ,  0.92308d ],
                                      [ -0.69231d , -0.69231d , -0.76923d , -1.92308d ]])

        def mtx2 = new Matrix([[ 9d ,  3d ,  0d ,  9d ],
                               [ -5d , -2d , -6d , -3d ],
                               [ -4d ,  9d ,  6d ,  4d ],
                               [ -7d ,  6d ,  6d ,  2d ]])
        def mtx2Inverse = new Matrix([[ -0.04074d , -0.07778d ,  0.14444d , -0.22222d ],
                                      [ -0.07778d ,  0.03333d ,  0.36667d , -0.33333d ],
                                      [ -0.02901d , -0.14630d , -0.10926d ,  0.12963d ],
                                      [  0.17778d ,  0.06667d , -0.26667d ,  0.33333d ]])

        then:
        mtx1.inverse() == mtx1Inverse
        mtx1Inverse.inverse() == mtx1
        mtx1.inverse().inverse().inverse().inverse() == mtx1

        mtx2.inverse() == mtx2Inverse
        mtx2Inverse.inverse() == mtx2
        mtx2.inverse().inverse().inverse().inverse() == mtx2

        mtx1.mult(mtx2).mult(mtx2.inverse()) == mtx1
        mtx2.mult(mtx1).mult(mtx1.inverse()) == mtx2
    }

}
