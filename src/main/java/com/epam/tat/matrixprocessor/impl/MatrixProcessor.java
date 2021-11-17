package com.epam.tat.matrixprocessor.impl;

import com.epam.tat.matrixprocessor.IMatrixProcessor;
import com.epam.tat.matrixprocessor.exception.MatrixProcessorException;
import com.epam.tat.matrixprocessor.validation.MatrixProcessorValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MatrixProcessor implements IMatrixProcessor {

	@Override
	public double[][] transpose(double[][] matrix) {
		MatrixProcessorValidator.validateForTranspose(matrix);
		double[][] transposedMatrix = new double[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				transposedMatrix[j][i] = matrix[i][j];
			}
		}
		return transposedMatrix;
	}

	@Override
	public double[][] turnClockwise(double[][] matrix) throws MatrixProcessorException {
		MatrixProcessorValidator.validateMatrix(matrix);
		double[][] transposedMatrix;
		transposedMatrix = transpose(matrix);
		if (transposedMatrix[0].length == 1) {
			return transposedMatrix;
		}
		double c;
		for (int i = 0; i < transposedMatrix.length; i++) {
			for (int j = 0; j < transposedMatrix[0].length / 2; j++) {
				c = transposedMatrix[i][j];
				transposedMatrix[i][j] = transposedMatrix[i][transposedMatrix[0].length - 1 - j];
				transposedMatrix[i][transposedMatrix[0].length - 1 - j] = c;
			}
		}
		return transposedMatrix;
	}

	@Override
	public double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
		MatrixProcessorValidator.validateMatrix(firstMatrix);
		MatrixProcessorValidator.validateMatrix(secondMatrix);
		double[][] multiplication = new double[firstMatrix.length][secondMatrix[0].length];
		for (int i = 0; i < firstMatrix.length; i++) {
			for (int j = 0; j < secondMatrix[0].length; j++) {
				for (int q = 0; q < firstMatrix[0].length; q++) {
					multiplication[i][j] = BigDecimal
							.valueOf(multiplication[i][j] + firstMatrix[i][q] * secondMatrix[q][j])
							.setScale(3, RoundingMode.HALF_UP)
							.doubleValue();
				}
			}
		}
		return multiplication;
	}

	@Override
	public double[][] getInverseMatrix(double[][] matrix) {
		double determinant = getMatrixDeterminant(matrix);
		double[][] invertedMatrix = new double[matrix.length][matrix.length];
		if (getMatrixDeterminant(matrix) == 0) {
			throw new MatrixProcessorException("determinant is zero, there is infinite number of solutions");
		}
		double[][] transposedMatrix = transpose(matrix);
		for (int i = 0; i < transposedMatrix.length; i++) {
			for (int j = 0; j < transposedMatrix.length; j++) {
				double[][] minorMatrix = new double[transposedMatrix.length - 1][transposedMatrix.length - 1];
				for (int a = 0; a < transposedMatrix.length; a++) {
					for (int b = 0; b < transposedMatrix.length; b++) {
						if (a < i && b < j) {
							minorMatrix[a][b] = transposedMatrix[a][b];
						}
						if (a < i && b > j) {
							minorMatrix[a][b - 1] = transposedMatrix[a][b];
						}
						if (a > i && b < j) {
							minorMatrix[a - 1][b] = transposedMatrix[a][b];
						}
						if (a > i && b > j) {
							minorMatrix[a - 1][b - 1] = transposedMatrix[a][b];
						}
					}
				}
				invertedMatrix[i][j] = BigDecimal
						.valueOf(Math.pow(-1, (i + j)) * getMatrixDeterminant(minorMatrix) / determinant)
						.setScale(3, RoundingMode.HALF_UP)
						.doubleValue();
			}
		}
		return invertedMatrix;
	}

	@Override
	public double getMatrixDeterminant(double[][] matrix) {
		MatrixProcessorValidator.validateSquareMatrix(matrix);
		double[][] minorMatrix;
		double determinant = 0;
		if (matrix.length == 1) {
			determinant = matrix[0][0];
			return determinant;
		} else if (matrix.length == 2) {
			determinant = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
			return determinant;
		} else {
			for (int i = 0; i < matrix[0].length; i++) {
				minorMatrix = new double[matrix.length - 1][matrix[0].length - 1];
				for (int j = 0; j < matrix.length; j++) {
					for (int q = 1; q < matrix[0].length; q++) {
						if (j > i) {
							minorMatrix[j - 1][q - 1] = matrix[j][q];
						}
						if (j < i) {
							minorMatrix[j][q - 1] = matrix[j][q];
						}
					}
				}
				determinant += matrix[i][0] * Math.pow(-1, i) * getMatrixDeterminant(minorMatrix);
			}
		}
		return (determinant);
	}
}
