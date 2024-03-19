package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jhealy.aicme4j.NetworkBuilderFactory;
import jhealy.aicme4j.net.Activation;
import jhealy.aicme4j.net.Loss;

public class PilotTraining {

	public static void main() throws Exception {
		new PilotTraining().trainModel();
		System.out.println("Pilot Training Main Method");
	}

	public void trainModel() throws Exception {
		System.out.println("In train model method");
		try {
			double[][] data = readInCSV("./resources/trainingData.csv");

			double[][] expected = readInCSV("./resources/lastMoveDirection.csv");

			System.out.println(expected[0].length);
			var net = NetworkBuilderFactory.getInstance().newNetworkBuilder().inputLayer("Input", 5)
					.hiddenLayer("Hidden", Activation.TANH, 5).outputLayer("Output", Activation.TANH, 1)
					.train(data, expected, 0.001, 0.95, 100000, 0.00001, Loss.SSE).save("./resources/pilotModel.data")
					.build();

			System.out.println(net);

		} catch (Exception e) {
			// Handle exceptions here or propagate them up based on your needs
			e.printStackTrace();
		}
	}

	private double[][] readInCSV(String filePath) throws IOException {
		List<double[]> dataList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				double[] rowData = new double[parts.length];
				for (int i = 0; i < parts.length; i++) {
					rowData[i] = Double.parseDouble(parts[i]);
				}
				dataList.add(rowData);

			}
		}
		return dataList.toArray(new double[0][]);
	}

}
