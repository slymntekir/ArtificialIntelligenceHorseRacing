package net.yapayzekason;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Training {
	// learning coefficient
	double LearningCoefficient = 0.4;
	// threshold value
	double Threshold = 0.6;
	// weights
	double[] Weights;
	double[] FirstWeight;
	double[] LastWeight = null;

	// assignment of weights
	public void WeightSetting(int number) {
		Weights = new double[number];
		FirstWeight = new double[number];
		LastWeight = new double[number];
		for (int i = 0; i < number; i++) {
			Weights[i] = 0.5;
		}
	}

	// error, training, such as calculations
	public void Calculate(double[][] data) {
		int rows = data.length;
		int columns = data[0].length - 1;
		while (true) {
			// training algorithm
			for (int i = 0; i < rows; i++) {
				System.out.println("Initial weight : " + Arrays.toString(Weights));
				if (i == 0)
					System.arraycopy(Weights, 0, FirstWeight, 0, columns);
				double TotalWeight = 0;
				for (int j = 0; j < columns; j++) {
					TotalWeight += data[i][j] * Weights[j];
				}
				TotalWeight = sigmoid(TotalWeight) + Threshold;
				double Target_Exit = data[i][columns];
				System.out.println("Target output : " + Target_Exit +
						"\nCalculated output : " + TotalWeight);
				double hata = Target_Exit - TotalWeight;
				System.out.println("Error : " + hata);
				for (int j = 0; j < columns; j++) {
					Weights[j] = LearningCoefficient * hata * data[i][j];
				}
				Threshold += LearningCoefficient * hata;
				System.out.println("New weights : " + Arrays.toString(Weights));
				System.out.println("---------------");
				if (i == rows)
					System.arraycopy(Weights, 0, LastWeight, 0, columns);
			}
			// because I cannot synchronize the system to zero at any time
			// the absolute value of the difference between the initial weight and final weight
			// I aimed to minimize as much as possible.
			double diff1 = LastWeight[0] - FirstWeight[0];
			double diff2 = LastWeight[1] - FirstWeight[1];
			double diff3 = LastWeight[2] - FirstWeight[2];
			double diff4 = LastWeight[3] - FirstWeight[3];
			if (diff1 < 0)
				diff1 *= -0.1;
			if (diff2 < 0)
				diff2 *= -0.1;
			if (diff3 < 0)
				diff3 *= -0.1;
			if (diff4 < 0)
				diff4 *= -0.1;

			if (String.valueOf(diff1).startsWith("0.00") ||
					String.valueOf(diff2).startsWith("0.00") ||
					String.valueOf(diff3).startsWith("0.00") ||
					String.valueOf(diff4).startsWith("0.00")) {
				System.out.println("Final Weights : " + Arrays.toString(Weights));
				System.out.println();
				break;
			}
		}
	}

	// Activation function
	public double sigmoid(double number) {
		return 1.0 / (1 + 1 / (Math.pow(Math.E, number)));
	}

	// Calculating the race information from the parameter which
	// find the first future of the horse (error)
	// (to press the screen)
	public int Resultfound(double[][] data) {
		// row and column of a matrix
		int rows = data.length;
		int columns = data[0].length;
		// result list
		List<Double> results = new ArrayList<>();
		for (double[] datum : data) {
			// Filling the list
			results.add(Weights[0] * datum[0] + Weights[1] * datum[1] +
					Weights[2] * datum[2] + Weights[3] * datum[3]);
		}
		int index;
		// temporary variable
		double temp;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				// sorting algorithm
				if (results.get(j) > results.get(j + 1)) {
					temp = results.get(j);
					results.set(j, results.get(j + 1));
					results.set(j + 1, temp);
				}// If the elaman coming first is larger than the next two are moving
			}// The loop we use to compare successive elements of an array
		}
		// part of the smallest element
		index = results.indexOf(results.get(0));
		return index;
	}
}