package com.ittia.gds.ui.components.mainframe.format;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import com.ittia.gds.ui.components.mainframe.buttons.MainFrame_Button_north_south;

/**
 * Compact UI formatting for GDS EMR interface with Kim Whanki's "Universe" color palette.
 */
public class MainFrameFormat {
    private static final int FRAME_WIDTH = 1275;
    private static final int FRAME_HEIGHT = 1020;
    private static final Font CONSOLAS_FONT = new Font("Consolas", Font.PLAIN, 11);
    private static final Color HOVER_COLOR = new Color(255, 255, 255, 150);
    private static final Color PRESSED_COLOR = new Color(200, 200, 255, 200);
    private static final int BUTTON_CORNER_RADIUS = 20;
    private static final Color UNIVERSE_BLUE_DARK = new Color(0, 0, 60);
    private static final Color UNIVERSE_BLUE_MID = new Color(0, 0, 120);
    private static final Color UNIVERSE_BLUE_LIGHT = new Color(100, 150, 255);
    private static final Color UNIVERSE_ACCENT_LIGHT = new Color(220, 230, 255);
    private static final Color UNIVERSE_ACCENT_SUBTLE_WARM = new Color(255, 230, 180);
    private static final Color TEXT_COLOR = new Color(240, 240, 255);

    private JButton createFancyButton(String text, String panelType) {
        JButton button = new JButton(text) {
            private boolean hovered, pressed;

            {
                setFont(CONSOLAS_FONT);
                setForeground(TEXT_COLOR);
                setBorder(new EmptyBorder(8, 20, 8, 20));
                setContentAreaFilled(false);
                setFocusPainted(false);
                setUI(new BasicButtonUI());
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { hovered = pressed = false; repaint(); }
                    @Override
                    public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                    @Override
                    public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        MainFrame_Button_north_south.EMR_B_1entryentry(text, panelType);
                        if (e.getClickCount() == 2) {
                            MainFrame_Button_north_south.EMR_B_2entryentry(text, panelType);
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                Shape shape = new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, BUTTON_CORNER_RADIUS, BUTTON_CORNER_RADIUS);
                g2.setPaint(new GradientPaint(0, 0, UNIVERSE_BLUE_LIGHT, 0, getHeight(), UNIVERSE_BLUE_MID));
                g2.fill(shape);
                if (hovered) g2.setColor(HOVER_COLOR);
                if (pressed) g2.setColor(PRESSED_COLOR);
                if (hovered || pressed) g2.fill(shape);
                g2.setColor(UNIVERSE_ACCENT_LIGHT);
                g2.draw(shape);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        return button;
    }

    private JPanel createGradientPanel(int height, int gridColumns, String[] buttonLabels, String panelType) {
        JPanel panel = new JPanel(new GridLayout(1, gridColumns)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, UNIVERSE_BLUE_LIGHT, 0, getHeight(), UNIVERSE_BLUE_DARK));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setPreferredSize(new Dimension(FRAME_WIDTH, height));
        for (String label : buttonLabels) {
            panel.add(createFancyButton(label, panelType));
        }
        return panel;
    }

    public JPanel createNorthPanel() {
        String[] buttons = {"Rescue", "Backup", "Copy", "CE", "Clear", "Exit", "Abbreviation", "ICD-11", "KCD8", "Lab code", "Lab sum", "db", "ittia_support"};
        return createGradientPanel(60, 13, buttons, "north");
    }

    public JPanel createSouthPanel() {
        String[] buttons = {"F/U DM", "F/U HTN", "F/U Chol", "F/U Thyroid", "Osteoporosis", "URI", "Allergy", "Injections", "GDS RC", "공단검진", "F/U Edit"};
        return createGradientPanel(60, 11, buttons, "south");
    }

    public JPanel createCenterPanel(JTextArea[] textAreas, String[] titles) {
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        centerPanel.setPreferredSize(new Dimension(900, 1000));
        Color[][] gradients = {
            {UNIVERSE_BLUE_DARK, UNIVERSE_BLUE_LIGHT}, {UNIVERSE_BLUE_MID, UNIVERSE_ACCENT_LIGHT},
            {UNIVERSE_BLUE_DARK, UNIVERSE_ACCENT_LIGHT}, {UNIVERSE_BLUE_MID, UNIVERSE_BLUE_LIGHT},
            {UNIVERSE_BLUE_LIGHT, UNIVERSE_ACCENT_LIGHT}, {UNIVERSE_ACCENT_LIGHT, UNIVERSE_BLUE_DARK},
            {UNIVERSE_BLUE_DARK, UNIVERSE_ACCENT_SUBTLE_WARM}, {UNIVERSE_BLUE_MID, UNIVERSE_ACCENT_LIGHT},
            {UNIVERSE_BLUE_LIGHT, UNIVERSE_ACCENT_SUBTLE_WARM}, {UNIVERSE_BLUE_DARK, UNIVERSE_ACCENT_LIGHT}
        };

        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new GradientTextArea(gradients[i][0], gradients[i][1]);
            textAreas[i].setText(titles[i] + "\t");
            textAreas[i].setForeground(TEXT_COLOR);
            textAreas[i].setFont(CONSOLAS_FONT);
            textAreas[i].setOpaque(false);
            textAreas[i].setBorder(new EmptyBorder(10, 10, 10, 10));
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
                g2d.setPaint(new GradientPaint(0, 0, UNIVERSE_BLUE_LIGHT, 0, getHeight(), UNIVERSE_BLUE_DARK));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        westPanel.setPreferredSize(new Dimension(500, FRAME_HEIGHT));

        outputArea.setForeground(TEXT_COLOR);
        outputArea.setFont(CONSOLAS_FONT);
        outputArea.setOpaque(false);
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        outputArea.setEditable(false);

        inputField.setForeground(TEXT_COLOR);
        inputField.setFont(CONSOLAS_FONT);
        inputField.setOpaque(false);
        inputField.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputScrollPane.setOpaque(false);
        outputScrollPane.getViewport().setOpaque(false);
        outputScrollPane.setBorder(null);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        inputPanel.add(inputField, BorderLayout.CENTER);

        westPanel.add(outputScrollPane, BorderLayout.CENTER);
        westPanel.add(inputPanel, BorderLayout.SOUTH);
        return westPanel;
    }

    public static JTextField createGradientTextField(int columns) {
        JTextField field = new GradientTextField(columns);
        field.setForeground(TEXT_COLOR);
        field.setFont(CONSOLAS_FONT);
        field.setOpaque(false);
        field.setBorder(new EmptyBorder(10, 10, 10, 10));
        return field;
    }
}