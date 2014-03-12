
package apcmag.examples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoActivity extends Activity
{
    private VideoView videoView;
    private MediaController ctrl;
    
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        videoView = (VideoView) findViewById(R.id.videoView);
        ctrl = new MediaController(this);
        ctrl.setMediaPlayer(videoView);
    }
    
    /** Open and play the raw resource directly */
    private void playVideo()
    {
        // location of the android resource
        String path = "android.resource://" + getPackageName() + "/raw/activations";
        
        videoView = (VideoView) findViewById(R.id.videoView);
        
        // Attach a media controller to the video view
        videoView.setMediaController(ctrl);
        videoView.requestFocus();
        videoView.setVideoPath(path);
        ctrl.show();
        videoView.start(); // play video
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        playVideo();
    }
    
    
    /** Stop playback of the video and release resources */
    protected void onPause()
    {
        super.onPause();
        videoView.pause();
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        videoView.stopPlayback();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        videoView.suspend();
        videoView = null;
    }
    

}
