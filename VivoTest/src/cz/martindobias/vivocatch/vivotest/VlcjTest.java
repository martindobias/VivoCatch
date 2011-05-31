package cz.martindobias.vivocatch.vivotest;

import com.sun.jna.Native;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.VideoMetaData;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VlcjTest extends JFrame {
    private JPanel mainPanel;
    private JButton button1;

    private final MediaPlayerFactory mediaPlayerFactory;
    private final EmbeddedMediaPlayer mediaPlayer;


    public VlcjTest() {
        super("VLCj test");

        final Canvas canvas;
        if(RuntimeUtil.isWindows()) {
           // If running on Windows and you want the mouse/keyboard event hack...
           canvas = new WindowsCanvas();
         }
         else {
           canvas = new Canvas();
         }
        canvas.setMinimumSize(new Dimension(600, 400));
        canvas.setPreferredSize(new Dimension(600, 400));
        mainPanel.add(canvas, BorderLayout.CENTER);

        DefaultFullScreenStrategy fullScreenStrategy = new DefaultFullScreenStrategy(this);
        mediaPlayerFactory = new MediaPlayerFactory(new String[]{"--no-plugins-cache", "--no-video-title-show", "--no-snapshot-preview", "--plugin-path=c:\\Users\\martin.dobias\\Documents\\Projects\\_private\\VivoCatch\\lib\\vlc\\plugins"});

        mediaPlayer = mediaPlayerFactory.newMediaPlayer(fullScreenStrategy);
        mediaPlayer.setVideoSurface(canvas);

        mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void metaDataAvailable(MediaPlayer mediaPlayer, VideoMetaData videoMetaData) {
                Dimension size = mediaPlayer.getVideoDimension();
                if(size != null) {
                    canvas.setSize(size.width, size.height);
                }
            }
        });

        button1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.playMedia("rtsp://10.1.20.128/live.sdp");
            }
        });
    }

    public static void main(String[] args) {
        Native.setProtected(false);

        JFrame frame = new JFrame("VlcjTest");
        final VlcjTest vlcjTest = new VlcjTest();
        frame.setContentPane(vlcjTest.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                vlcjTest.mediaPlayer.release();
                vlcjTest.mediaPlayerFactory.release();
            }
        });

        frame.setVisible(true);
    }
}
