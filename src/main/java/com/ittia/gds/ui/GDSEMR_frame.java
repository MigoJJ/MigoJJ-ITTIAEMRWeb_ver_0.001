package com.ittia.gds.ui;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.ittia.gds.ui.components.mainframe.changestring.EMRProcessor;
import com.ittia.gds.ui.components.mainframe.format.MainFrameFormat;
/**
 * Main application window for the GDS EMR interface.
 * Acts as the backbone for assembling the UI components and managing core interactions.
 */
public class GDSEMR_frame {
    private static final int FRAME_WIDTH = 1275;
    private static final int FRAME_HEIGHT = 1020;
    public static JFrame frame;
    public static JTextArea[] textAreas;
    public static JTextArea tempOutputArea;
    private JTextField gradientInputField;
    public static final String[] TEXT_AREA_TITLES = {
            "CC>", "PI>", "ROS>", "PMH>", "S>",
            "O>", "Physical Exam>", "A>", "P>", "Comment>"
    };
    /**
     * Constructor for GDSEMR_frame. Initializes core UI components.
     */
    public GDSEMR_frame() {
        frame = new JFrame("GDS EMR Interface for Physician");
        textAreas = new JTextArea[TEXT_AREA_TITLES.length];
        tempOutputArea = new JTextArea();
        gradientInputField = MainFrameFormat.createGradientTextField(20);
    }
    /**
     * Creates and displays the GUI. This method orchestrates the assembly of the frame and its panels.
     */
    public void createAndShowGUI() {
        configureFrame();
        initializePanels();
        registerListeners();
        frame.setVisible(true);
    }
    /**
     * Configures the main JFrame properties.
     */
    private void configureFrame() {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocation(348, 60);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * Initializes and adds all major panels to the frame.
     */
    private void initializePanels() {
        MainFrameFormat format = new MainFrameFormat();
        frame.add(format.createNorthPanel(), BorderLayout.NORTH);
        frame.add(format.createSouthPanel(), BorderLayout.SOUTH);
        frame.add(format.createCenterPanel(textAreas, TEXT_AREA_TITLES), BorderLayout.CENTER);
        frame.add(format.createWestPanel(tempOutputArea, gradientInputField), BorderLayout.WEST);
    }
    /**
     * Registers all necessary listeners for text areas.
     */
    private void registerListeners() {
        for (int i = 0; i < textAreas.length; i++) {
            if (textAreas[i] != null) {
                textAreas[i].getDocument().addDocumentListener(new EMRProcessor.EMRDocumentListener(textAreas, tempOutputArea));
                textAreas[i].addMouseListener(new DoubleClickMouseListener());
                textAreas[i].addKeyListener(new FunctionKeyPress());
            }
        }
    }
    /**
     * Updates the text of a specific text area.
     * @param index The index of the text area to update.
     * @param text The text to append.
     */
    public static void setTextAreaText(int index, String text) {
        if (textAreas == null || index < 0 || index >= textAreas.length || textAreas[index] == null) {
            System.err.println("Invalid text area index or text areas not initialized for setTextAreaText.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            textAreas[index].append(text);
        });
    }
    /**
     * Updates the temporary output area.
     * @param text The text to set in the output area.
     */
    public static void updateTempOutputArea(String text) {
        if (tempOutputArea != null) {
            SwingUtilities.invokeLater(() -> {
                tempOutputArea.setText(text);
            });
        }
    }
    /**
     * Handles function key press events.
     */
    private static class FunctionKeyPress extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12) {
                String functionKeyMessage = "F" + (keyCode - KeyEvent.VK_F1 + 1) + " key pressed - Action executed.";
            }
        }
    }
    /**
     * Handles double-click events on text areas.
     */
    private static class DoubleClickMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JTextArea source = (JTextArea) e.getSource();
                String text = source.getText();
            }
        }
    }
    /**
     * Launches the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GDSEMR_frame emrFrame = new GDSEMR_frame();
            try {
                emrFrame.createAndShowGUI();
            } catch (Exception e) {
                System.err.println("An error occurred during application startup:");
                e.printStackTrace();
            }
        });
    }
}