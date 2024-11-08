package views;

import javax.swing.*;
import java.awt.*;

public class ScalePanel extends JPanel {
    public JSlider scaleSlider = null;
    public ScalePanel(GraphPanel graphPanel) {
        setLayout(new FlowLayout());
        JLabel scaleLabel = new JLabel("Масштаб");
        scaleSlider = new JSlider(1, 10, 1);  // Значение 1 - без увеличения, 10 - максимальное увеличение
        JButton resetButton = new JButton("Сбросить масштаб");

        // Слушатели событий для слайдера и кнопки сброса
        scaleSlider.addChangeListener(e -> {
            int scaleValue = scaleSlider.getValue();
            graphPanel.updateScale(scaleValue);  // Обновляем масштаб графика
        });

        resetButton.addActionListener(e -> scaleSlider.setValue(1));

        add(scaleLabel);
        add(scaleSlider);
        add(resetButton);
    }
}
