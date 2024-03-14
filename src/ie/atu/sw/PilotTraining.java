package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import jhealy.aicme4j.NetworkBuilderFactory;
import jhealy.aicme4j.net.Activation;
import jhealy.aicme4j.net.LayerSize;
import jhealy.aicme4j.net.Loss;
import jhealy.aicme4j.net.Output;

public class PilotTraining {

    public void trainModel() throws Exception {
        try {
            double[][] data = readAndLabelCSV("./savedData/trainingData.csv");
            double[][] expected = {}; // You might want to read expected values as well

            var net = NetworkBuilderFactory.getInstance().newNetworkBuilder()
                    .inputLayer("Input", 2)
                    .hiddenLayer("Hidden1", Activation.TANH, LayerSize.SUM)
                    .outputLayer("Output", Activation.TANH, 1)
                    .train(data, expected, 0.01, 0.95, 100000, 0.00001, Loss.SSE)
                    .save("./savedData/pilotModel.data")
                    .build();

            System.out.println(net);
            
        } catch (Exception e) {
            // Handle exceptions here or propagate them up based on your needs
            e.printStackTrace();
        }
    }

    public double[][] readAndLabelCSV(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] values = line.split(",");

                // Parse each value as a double
                for (String value : values) {
                    try {
                        double doubleValue = Double.parseDouble(value.trim());
                        // Label the data based on your criteria
                        String label = (doubleValue == 0.0) ? "dead" : "ok";
                        // Process the data or store it as needed
                        System.out.println("Value: " + doubleValue + ", Label: " + label);
                    } catch (NumberFormatException e) {
                        // Handle invalid numeric values gracefully (e.g., skip or log them)
                        System.err.println("Invalid numeric value: " + value);
                    }
                }
            }
        }
        return null;
    }
}
