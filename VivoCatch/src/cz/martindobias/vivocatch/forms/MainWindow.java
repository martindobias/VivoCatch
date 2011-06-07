package cz.martindobias.vivocatch.forms;

import cz.martindobias.vivocatch.beans.Camera;
import cz.martindobias.vivocatch.beans.Desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane sideBar;
    private JTable infoTable;
    private JPanel cameraPanel;
    private JTable settingTable;

    private PlayerFactory mediaPlayerFactory;
    private List<CameraPanel> players;

    public MainWindow(Desktop desktop) {
        super("VivoCatch");

        this.mediaPlayerFactory = new PlayerFactory(this);
        this.cameraPanel.setLayout(new GridLayout(0, desktop.getWidth(), 10, 10));
        this.players = new Vector<CameraPanel>(desktop.getWidth() * 3);

        List<Camera> cameras = desktop.getCameras();
        for(Camera camera : cameras) {
            CameraPanel panel = new CameraPanel(camera, this.mediaPlayerFactory);
            this.players.add(panel);
            this.cameraPanel.add(panel.getPanel());
        }

        this.addWindowStateListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             */
            @Override
            public void windowClosed(WindowEvent e) {
                for(CameraPanel panel : players) {
                    panel.shutdown();
                }
            }
        });

        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
