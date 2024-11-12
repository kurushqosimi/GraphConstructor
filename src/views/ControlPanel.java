package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static views.MainFrame.*;

public class ControlPanel extends JPanel {
    private final JTextField xMinInterval;
    private final JTextField xMaxInterval;
    private double markerSpacing = 1.0;
    private final JCheckBox gridCheckBox;
    private Color gridColor = Color.GRAY;
    private Color lineColor = Color.BLACK;

    public ControlPanel() {
        setLayout(new FlowLayout());

        JLabel functionLabel = new JLabel("Тип функции:");
        JComboBox<String> functionBox = new JComboBox<>(new String[]{"y = x",
                "y = x^2",
                "y = sin(x)",
                "y = 2*cos(x)",
                "y = ln(x)",
                "y = e^x",
                "y = x^3",
                "y = |x|",
                "y = tan(x)",
                "y = ctan(x)",
                "y = 1/x",
                "y = -1/x",
        });

        JLabel lineStyleLabel = new JLabel("Стиль линии:");
        JComboBox<String> lineStyleBox = new JComboBox<>(new String[]{
                "Линия",
                "Маркер",
                "Маркер+Линия",
        });

        JLabel xIntervalLabel = new JLabel("Интервал X:");
        xMinInterval = new JTextField(5);
        xMaxInterval = new JTextField(5);
        xMinInterval.setText("-10");
        xMaxInterval.setText("10");

        JButton colorButton = new JButton("Выбрать цвет");
        JButton gridColorButton = new JButton("Выбрать цвет сетки");
        gridCheckBox = new JCheckBox("Показать сетку");
        JButton plotButton = new JButton("Построить график");

        add(functionLabel);
        add(functionBox);
        add(lineStyleLabel);
        add(lineStyleBox);
        add(xIntervalLabel);
        add(xMinInterval);
        add(xMaxInterval);
        add(colorButton);
        add(gridColorButton);
        add(gridCheckBox);
        add(plotButton);

        // Обработчик изменения цвета
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosenColor = JColorChooser.showDialog(null, "Выберите цвет", Color.BLACK);
                if (chosenColor != null) {
                    previewPanel.setLineColor(chosenColor);
                    lineColor = chosenColor;
                }
            }
        });

        gridColorButton.addActionListener(e -> {
            Color selectedGridColor = JColorChooser.showDialog(null, "Выберите цвет сетки", Color.GRAY);
            if (selectedGridColor != null) {
                previewPanel.setGridColor(selectedGridColor);
                gridColor = selectedGridColor;
            }
        });

        // Обработчик изменения стиля линии
        lineStyleBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStyle = (String) lineStyleBox.getSelectedItem();
                previewPanel.setLineStyle(selectedStyle);
            }
        });

        // Обработчик отображения сетки
        gridCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewPanel.setShowGrid(gridCheckBox.isSelected());
            }
        });

        plotButton.addActionListener(e -> {
            double xMin = Double.parseDouble(xMinInterval.getText());
            double xMax = Double.parseDouble(xMaxInterval.getText());
            scalePanel.scaleSlider.setValue(1);
            graphPanel.updateSettings((String) functionBox.getSelectedItem(), (String) lineStyleBox.getSelectedItem(), lineColor, gridColor, gridCheckBox.isSelected(), xMin, xMax);
        });
    }

    public double getMarkerSpacing() {
        return markerSpacing;
    }

    public void setMarkerSpacing(double markerSpacing) {
        this.markerSpacing = markerSpacing;
    }
}
