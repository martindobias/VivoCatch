package cz.martindobias.vivocatch.forms;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.awt.*;

public class PlayerFactory {

    private DefaultFullScreenStrategy fullScreenStrategy;
    private MediaPlayerFactory mediaPlayerFactory;

    public PlayerFactory(Window window, int rtpCaching, int rtspCaching, int realRtspCaching) {
        fullScreenStrategy = new DefaultFullScreenStrategy(window);
        mediaPlayerFactory = new MediaPlayerFactory(new String[]{
                "--no-plugins-cache",
                "--no-video-title-show",
                "--no-snapshot-preview",
                "--plugin-path=lib\\vlc\\plugins",
                "--rtp-caching=" + rtpCaching,
                "--rtsp-caching=" + rtspCaching,
                "--realrtsp-caching=" + realRtspCaching});
    }

    public EmbeddedMediaPlayer createPlayer() {
        return this.mediaPlayerFactory.newMediaPlayer(this.fullScreenStrategy);
    }
}
