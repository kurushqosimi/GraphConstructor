package views;

import javax.swing.*;
import java.awt.*;
import java.util.function.DoubleUnaryOperator;

public class GraphPanel extends JPanel {
    private String functionType = "y = x";
    private String lineStyle = "Линия";
    private Color lineColor = Color.BLACK;
    private Color gridColor = Color.GRAY;
    private boolean showGrid = false;
    private double xMin = -10;
    private double xMax = 10;
    private double yMin = -10;
    private double yMax = 10;
    private double initialXMin, initialXMax, initialYMin, initialYMax;
    private double currentXMin, currentXMax, currentYMin, currentYMax;

    private final int padding = 50;  // Оптимальный отступ для нумерации и отрисовки осей

    public GraphPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        // Инициализация начальных значений
        initialXMin = xMin;
        initialXMax = xMax;
        initialYMin = yMin;
        initialYMax = yMax;

        // Устанавливаем текущие значения диапазонов в начальные
        currentXMin = xMin;
        currentXMax = xMax;
        currentYMin = yMin;
        currentYMax = yMax;
    }


    public void updateSettings(String functionType, String lineStyle, Color lineColor, Color gridColor, boolean showGrid, double xMin, double xMax, double yMin, double yMax) {
        this.functionType = functionType;
        this.lineStyle = lineStyle;
        this.lineColor = lineColor;
        this.gridColor = gridColor;
        this.showGrid = showGrid;

        // Обновляем текущие диапазоны
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;

        // Обновляем начальные значения для масштабирования
        this.initialXMin = xMin;
        this.initialXMax = xMax;
        this.initialYMin = yMin;
        this.initialYMax = yMax;

        // Также обновляем текущие диапазоны для корректного масштабирования
        this.currentXMin = xMin;
        this.currentXMax = xMax;
        this.currentYMin = yMin;
        this.currentYMax = yMax;

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Проверка доступного пространства с учетом отступов
        int width = getWidth() - 2 * padding;
        int height = getHeight() - 2 * padding;

        // Рисуем сетку, если она включена
        if (showGrid) {
            drawGrid(g2d, width, height);
        }

        // Рисуем оси
        drawAxes(g2d, width, height);

        // Рисуем функцию
        g2d.setColor(lineColor);
        drawFunction(g2d, getFunction(), width, height);
    }

    private void drawGrid(Graphics2D g, int width, int height) {
        g.setColor(gridColor);

        double xRange = xMax - xMin;
        double yRange = yMax - yMin;

        // Рисуем вертикальные линии сетки
        for (double x = xMin; x <= xMax; x += 1.0) {
            int xPixel = padding + (int) ((x - xMin) / xRange * width);
            g.drawLine(xPixel, padding, xPixel, height + padding);
        }

        // Рисуем горизонтальные линии сетки
        for (double y = yMin; y <= yMax; y += 1.0) {
            int yPixel = height + padding - (int) ((y - yMin) / yRange * height);
            g.drawLine(padding, yPixel, width + padding, yPixel);
        }
    }

    private void drawAxes(Graphics2D g, int width, int height) {
        g.setColor(Color.BLACK);

        double xRange = xMax - xMin;
        double yRange = yMax - yMin;

        // Ось X
        int originY = height + padding;
        g.drawLine(padding, originY, width + padding, originY);
        for (double x = xMin; x <= xMax; x += 1.0) {
            int xPixel = padding + (int) ((x - xMin) / xRange * width);
            g.drawLine(xPixel, originY - 5, xPixel, originY + 5);
            g.drawString(String.format("%.1f", x), xPixel - 10, originY + 20);
        }

        // Ось Y
        int originX = padding;
        g.drawLine(originX, padding, originX, height + padding);
        for (double y = yMin; y <= yMax; y += 1.0) {
            int yPixel = height + padding - (int) ((y - yMin) / yRange * height);
            g.drawLine(originX - 5, yPixel, originX + 5, yPixel);
            g.drawString(String.format("%.1f", y), originX - 30, yPixel + 5);
        }
    }

    private void drawFunction(Graphics2D g, DoubleUnaryOperator function, int width, int height) {
        double xRange = xMax - xMin;
        double yRange = yMax - yMin;

        int previousY = Integer.MIN_VALUE;  // Для хранения предыдущей координаты y
        boolean previousInBounds = false;   // Флаг, указывающий, была ли предыдущая точка в пределах видимой области
        int markerInterval = 15;            // Увеличиваем интервал для маркеров (каждый 15-й пиксель)
        int markerSize = 6;                 // Размер маркеров для лучшей видимости

        for (int xPixel = 0; xPixel < width - 1; xPixel++) {
            double xValue = xMin + (xMax - xMin) * xPixel / width;
            double yValue = function.applyAsDouble(xValue);

            // Проверка на выход за установленные пределы по оси Y
            if (yValue > yMax || yValue < yMin) {
                previousY = Integer.MIN_VALUE;  // Сброс предыдущего значения
                previousInBounds = false;       // Устанавливаем флаг, что предыдущая точка вне видимой области
                continue;                       // Пропускаем рисование для текущей точки
            }

            int yPixel = height + padding - (int) ((yValue - yMin) / yRange * height);

            // Рисуем линию только если предыдущая точка была в пределах видимой области
            if (previousInBounds && previousY != Integer.MIN_VALUE) {
                if ("Линия".equals(lineStyle) || "Маркер+Линия".equals(lineStyle)) {
                    g.drawLine(xPixel + padding - 1, previousY, xPixel + padding, yPixel);
                }
            }

            // Рисуем маркер, если выбран стиль и соблюдается интервал
            if (("Маркер".equals(lineStyle) || "Маркер+Линия".equals(lineStyle)) && xPixel % markerInterval == 0) {
                g.fillOval(xPixel + padding - markerSize / 2, yPixel - markerSize / 2, markerSize, markerSize);  // Рисуем маркер большего размера
            }

            // Обновляем предыдущие значения
            previousY = yPixel;
            previousInBounds = true;  // Устанавливаем флаг, что текущая точка в пределах видимой области
        }
    }

    public void updateScale(int scaleValue) {
        // Если значение слайдера равно 1, возвращаем диапазоны к исходным
        if (scaleValue == 1) {
            xMin = initialXMin;
            xMax = initialXMax;
            yMin = initialYMin;
            yMax = initialYMax;
        } else {
            double scaleFactor = 1.0 / scaleValue;  // Чем больше значение слайдера, тем меньше диапазон

            // Рассчитываем новые диапазоны относительно начальных значений
            double initialXRange = initialXMax - initialXMin;
            double initialYRange = initialYMax - initialYMin;

            // Масштабируем диапазоны по фиксированным начальным значениям
            double newRangeX = initialXRange * scaleFactor;
            double newRangeY = initialYRange * scaleFactor;

            // Центрируем новый диапазон относительно начального центра
            double centerX = (initialXMax + initialXMin) / 2;
            double centerY = (initialYMax + initialYMin) / 2;

            // Обновляем минимальные и максимальные значения на основе нового диапазона
            xMin = centerX - newRangeX / 2;
            xMax = centerX + newRangeX / 2;
            yMin = centerY - newRangeY / 2;
            yMax = centerY + newRangeY / 2;
        }

        repaint();  // Перерисовываем график с новым масштабом
    }

    private DoubleUnaryOperator getFunction() {
        switch (functionType) {
            case "y = x^2":
                return x -> x * x;
            case "y = sin(x)":
                return Math::sin;
            case "y = 2*cos(x)":
                return x -> 2 * Math.cos(x);
            case "y = ln(x)":
                return Math::log;
            case "y = e^x":
                return Math::exp;
            case "y = x^3":
                return x -> x * x * x;
            case "y = |x|":
                return Math::abs;
            case "y = tan(x)":
                return Math::tan;
            case "y = ctan(x)":
                return x -> 1 / Math.tan(x);
            case "y = 1/x":
                return x -> 1 / x;
            case "y = -1/x":
                return x -> -1 / x;
            default: // y = x
                return x -> x;
        }
    }
}
