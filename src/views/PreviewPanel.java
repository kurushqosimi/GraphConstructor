package views;

import javax.swing.*;
import java.awt.*;

public class PreviewPanel extends JPanel {
    private Color lineColor = Color.BLACK; // По умолчанию чёрный цвет линии
    private Color gridColor = Color.GRAY;
    private String lineStyle = "Линия";    // По умолчанию - линия
    private boolean showGrid = false;      // По умолчанию сетка не показывается

    public PreviewPanel() {
        setLayout(new BorderLayout());
        JLabel previewLabel = new JLabel("Предварительный просмотр");
        JPanel previewArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPreview(g);
            }
        };

        previewArea.setPreferredSize(new Dimension(180, 100));
        previewArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(previewLabel, BorderLayout.NORTH);
        add(previewArea, BorderLayout.CENTER);
    }

    // Метод для обновления цвета линии
    public void setLineColor(Color color) {
        this.lineColor = color;
        repaint();
    }

    // Метод для обновления цвета линии
    public void setGridColor(Color color) {
        this.gridColor = color;
        repaint();
    }

    // Метод для обновления стиля линии
    public void setLineStyle(String style) {
        this.lineStyle = style;
        repaint();
    }

    // Метод для отображения или скрытия сетки
    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
        repaint();
    }

    // Метод для отрисовки предварительного просмотра
    private void drawPreview(Graphics g) {
        // Отображение сетки, если выбрано
        if (showGrid) {
            g.setColor(gridColor);  // Установка цвета для сетки
            for (int i = 20; i <= 160; i += 20) {
                g.drawLine(i, 10, i, 90);  // Вертикальные линии сетки
            }
            for (int i = 10; i <= 90; i += 20) {
                g.drawLine(20, i, 160, i);  // Горизонтальные линии сетки
            }
        }

        // Установка цвета линии и маркеров
        g.setColor(lineColor);

        // Отрисовка линии с выбранным стилем
        if ("Линия".equals(lineStyle)) {
            g.drawLine(20, 50, 160, 50);
        } else if ("Маркер".equals(lineStyle)) {
            for (int i = 20; i <= 160; i += 10) {
                g.fillOval(i - 2, 48, 5, 5);  // Рисуем маркеры
            }
        } else if ("Маркер+Линия".equals(lineStyle)) {
            g.drawLine(20, 50, 160, 50);  // Рисуем линию
            for (int i = 20; i <= 160; i += 10) {
                g.fillOval(i - 2, 48, 5, 5);  // Рисуем маркеры
            }
        }
    }
}

