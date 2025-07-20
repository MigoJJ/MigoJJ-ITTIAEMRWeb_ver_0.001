package com.ittia.gds.ui.components.mainframe.format;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import com.ittia.gds.ui.components.mainframe.buttons.MainFrame_Button_north_south;

public class MainFrameFormat {
    private static final int FRAME_WIDTH = 1275;
    private static final int FRAME_HEIGHT = 1020;
    private static final Font BUTTON_FONT = new Font("Consolas", Font.BOLD, 12); // Changed from Segoe UI to Consolas
    private static final Color HOVER_COLOR = new Color(200, 220, 255, 50); // Soft blue-white hover inspired by Universe's glow
    private static final Color PRESSED_COLOR = new Color(0, 20, 60, 50); // Darker blue for pressed state
    private static final int BUTTON_CORNER_RADIUS = 10;

    public static JTextField createGradientTextField(int columns) {
        return new GradientTextField(columns);
    }

    private JButton createFancyButton(String text) {
        return new JButton(text) {
            private boolean hovered = false;
            private boolean pressed = false;

            {
                setFont(BUTTON_FONT);
                setForeground(Color.WHITE);
                setBorder(new EmptyBorder(5, 15, 5, 15));
                setContentAreaFilled(false);
                setFocusPainted(false);
                setUI(new BasicButtonUI());

                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        hovered = true; repaint();
                    }
                    public void mouseExited(MouseEvent e) {
                        hovered = false; pressed = false; repaint();
                    }
                    public void mousePressed(MouseEvent e) {
                        pressed = true; repaint();
                    }
                    public void mouseReleased(MouseEvent e) {
                        pressed = false; repaint();
                    }
                });
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Shape shape = new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1,
                        BUTTON_CORNER_RADIUS, BUTTON_CORNER_RADIUS);

                // Gradient inspired by Kim Whanki's Universe: deep blues
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0, 51, 102), // Ultramarine blue
                        0, getHeight(), new Color(0, 102, 153)); // Cerulean blue
                g2.setPaint(gradient);
                g2.fill(shape);

                if (hovered) {
                    g2.setColor(HOVER_COLOR);
                    g2.fill(shape);
                }

                if (pressed) {
                    g2.setColor(PRESSED_COLOR);
                    g2.fill(shape);
                }

                g2.setColor(new Color(150, 180, 220, 80)); // Soft blue border
                g2.draw(shape);
                g2.dispose();

                super.paintComponent(g);
            }
        };
    }

    public JPanel createNorthPanel() {
        // Gradient inspired by Universe's cosmic blue palette
        JPanel panel = createGradientPanel(new Color(0, 51, 102), new Color(0, 76, 153));
        panel.setLayout(new GridLayout(1, 13));
        panel.setPreferredSize(new Dimension(FRAME_WIDTH, 50));

        String[] buttons = {"Rescue", "Backup", "Copy", "CE", "Clear", "Exit",
                            "Abbreviation", "ICD-11", "KCD8", "Lab code", "Lab sum", "db", "ittia_support"};

        for (String btn : buttons) {
            JButton button = createFancyButton(btn);
            button.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1)
                        MainFrame_Button_north_south.EMR_B_1entryentry(btn, "north");
                    else if (e.getClickCount() == 2)
                        MainFrame_Button_north_south.EMR_B_2entryentry(btn, "north");
                }
            });
            panel.add(button);
        }

        return panel;
    }

    public JPanel createSouthPanel() {
        // Gradient with deeper blues for a cosmic feel
        JPanel panel = createGradientPanel(new Color(0, 25, 51), new Color(0, 102, 204));
        panel.setLayout(new GridLayout(1, 11));
        panel.setPreferredSize(new Dimension(FRAME_WIDTH, 50));

        String[] buttons = {
            "F/U DM", "F/U HTN", "F/U Chol", "F/U Thyroid", "Osteoporosis",
            "URI", "Allergy", "Injections", "GDS RC", "공단검진", "F/U Edit"
        };

        for (String btn : buttons) {
            JButton button = createFancyButton(btn);
            button.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1)
                        MainFrame_Button_north_south.EMR_B_1entryentry(btn, "south");
                    else if (e.getClickCount() == 2)
                        MainFrame_Button_north_south.EMR_B_2entryentry(btn, "south");
                }
            });
            panel.add(button);
        }

        return panel;
    }

    public JPanel createCenterPanel(JTextArea[] textAreas, String[] titles) {
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        centerPanel.setPreferredSize(new Dimension(900, 1000));

        // Updated gradients inspired by Kim Whanki's Universe color palette
        Color[][] gradients = {
            { new Color(0, 51, 102), new Color(0, 102, 153) }, // Ultramarine to cerulean
            { new Color(0, 25, 51), new Color(0, 76, 153) }, // Deep blue to medium blue
            { new Color(0, 76, 153), new Color(0, 128, 204) }, // Cerulean to brighter blue
            { new Color(0, 51, 102), new Color(0, 153, 204) }, // Ultramarine to light blue
            { new Color(0, 25, 51), new Color(0, 102, 204) }, // Deep blue to vibrant blue
            { new Color(0, 102, 153), new Color(0, 153, 255) }, // Cerulean to sky blue
            { new Color(0, 51, 102), new Color(0, 76, 153) }, // Ultramarine to medium blue
            { new Color(0, 25, 51), new Color(0, 128, 204) }, // Deep blue to brighter blue
            { new Color(0, 76, 153), new Color(0, 102, 204) }, // Cerulean to vibrant blue
            { new Color(0, 51, 102), new Color(0, 153, 255) } // Ultramarine to sky blue
        };

        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new GradientTextArea(gradients[i][0], gradients[i][1]);
            textAreas[i].setText(titles[i] + "	");
            textAreas[i].setForeground(Color.WHITE);
            setupTextAreaProperties(textAreas[i], i);
            JScrollPane scrollPane = new JScrollPane(textAreas[i]);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            centerPanel.add(scrollPane);
        }
        return centerPanel;
    }

    public JPanel createWestPanel(JTextArea outputArea, JTextField inputField) {
        JPanel westPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                // Lighter sky blue gradient, inverted (bottom to top)
                GradientPaint gradient = new GradientPaint(
                    0, height, new Color(173, 216, 230), // Very light sky blue (LightSkyBlue)
                    0, 0, new Color(135, 206, 235) // Slightly deeper sky blue
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };
        westPanel.setPreferredSize(new Dimension(500, FRAME_HEIGHT));

        setupOutputArea(outputArea);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setOpaque(false);
        outputScrollPane.getViewport().setOpaque(false);
        westPanel.add(outputScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        inputPanel.add(inputField, BorderLayout.NORTH);
        westPanel.add(inputPanel, BorderLayout.SOUTH);

        return westPanel;
    }

    private void setupTextAreaProperties(JTextArea textArea, int index) {
        textArea.setOpaque(false);
        textArea.setBorder(null);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13)); // 👈 추가됨
    }


    private void setupOutputArea(JTextArea outputArea) {
        outputArea.setOpaque(false);
        outputArea.setBorder(null);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13)); // 👈 추가됨
    }


    private JPanel createGradientPanel(Color topColor, Color bottomColor) {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth(), height = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, topColor, 0, height, bottomColor));
                g2d.fillRect(0, 0, width, height);
            }
        };
    }
}