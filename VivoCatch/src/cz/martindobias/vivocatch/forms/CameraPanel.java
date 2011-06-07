package cz.martindobias.vivocatch.forms;

import cz.martindobias.vivocatch.beans.Camera;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CameraPanel {
    private JPanel panel;
    private JLabel label;
    private JPanel cameraHolder;
    private EmbeddedMediaPlayer player;
    private Camera camera;

    public CameraPanel(Camera camera, PlayerFactory mediaPlayerFactory) {
        this.camera = camera;
        this.label.setText(camera.getName());

        final Canvas canvas;
        if(RuntimeUtil.isWindows()) {
            // If running on Windows and you want the mouse/keyboard event hack...
            canvas = new WindowsCanvas();
        } else {
            canvas = new Canvas();
        }

        this.player = mediaPlayerFactory.createPlayer();
        this.player.setVideoSurface(canvas);

        this.cameraHolder.add(canvas, BorderLayout.CENTER);

        label.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                CameraPanel.this.player.playMedia(CameraPanel.this.camera.getStream());
            }
        });
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public void shutdown() {
        this.player.release();
    }
}
