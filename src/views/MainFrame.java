package views;
import javax.swing.*;
import java.awt.*;
import java.net.ContentHandler;

public class MainFrame extends JFrame {
    public static PreviewPanel previewPanel;
    public static ControlPanel controlPanel;
    public static GraphPanel graphPanel;
    public static ScalePanel scalePanel;

    public MainFrame() {

        setTitle("Конструктор графиков и диаграмм");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель предварительного просмотра
        previewPanel = new PreviewPanel();
        add(previewPanel, BorderLayout.EAST);

        // Панель управления
        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        // Панель графиков
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        // Панель масштабирования
        scalePanel = new ScalePanel(graphPanel);
        add(scalePanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
