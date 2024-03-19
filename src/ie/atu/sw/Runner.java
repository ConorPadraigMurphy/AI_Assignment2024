package ie.atu.sw;

import static java.lang.System.out;

import javax.swing.SwingUtilities;

public class Runner {
	public static void main(String[] args) throws Exception {
		PilotTraining.main();

		/*
		 * Always run a GUI in a separate thread from the main thread.
		 */
		SwingUtilities.invokeAndWait(() -> { // Sounds like the Command Pattern at work!
			try {
				new GameWindow();
			} catch (Exception e) {
				out.println("[ERROR] Yikes...problem starting up " + e.getMessage());
			}
		});
	}
}