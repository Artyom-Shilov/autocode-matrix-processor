package com.epam.tat.matrixprocessor.validation;

import com.epam.tat.matrixprocessor.exception.MatrixProcessorException;

public class MatrixProcessorValidator {

    public static void validateMatrix(double[][] matrix) {
        if (matrix == null) {
            throw new MatrixProcessorException("null as argument");
        }
        if (matrix.length <= 0 || matrix.length >= 10) {
            throw new MatrixProcessorException("number of matrix rows is not proper");
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i].length <= 0 || matrix[i].length >= 10) {
                    throw new MatrixProcessorException("number of elements in row " + i + " is not proper");
                }
                if (matrix[i].length != matrix[0].length) {
                    throw new MatrixProcessorException("number of elements in rows is not equal");
                }
            }
        }
    }

    public static void validateSquareMatrix(double[][] squareMatrix) {
        if (squareMatrix == null) {
            throw new MatrixProcessorException("null as argument");
        }
        if (squareMatrix.length <= 0 || squareMatrix.length >= 10) {
            throw new MatrixProcessorException("number of matrix rows is not proper");
        }
        for (int i = 0; i < squareMatrix.length; i++) {
            for (int j = 0; j < squareMatrix[i].length; j++) {
                if (squareMatrix[i].length != squareMatrix.length) {
                    throw new MatrixProcessorException("matrix is not square");
                }
            }
        }
    }

    public static void validateForTranspose(double[][] matrix) {
        if (matrix == null) {
            throw new MatrixProcessorException("null as argument");
        }
        if (matrix.length <= 0 || matrix.length >= 10) {
            throw new MatrixProcessorException("number of matrix rows is not proper -" + " " + matrix.length);
        }
    }
}
