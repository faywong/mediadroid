
package apcmag.examples;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * This class is the main activity that demonstrates how to play audio, video
 * and stream from You Tube.
 * 
 * @author Rajesh Vasa, 2011
 */
public class MediaDroidActivity extends Activity implements MediaPlayer.OnCompletionListener
{
    private MediaPlayer audioPlayer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void playYouTube(View v)
    {
        String videoID = "ZwzXvRpoPP4";
        String ytURL = "http://www.youtube.com/watch?v=" + videoID;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ytURL));
        startActivity(i);
    }

    public void playVideo(View v)
    {
        Intent i = new Intent(getApplicationContext(), VideoActivity.class);
        startActivity(i);
    }

    /** Create and start the audio media player */
    public void playAudio(View v)
    {
        if (audioPlayer == null)  // not yet created
        {
            audioPlayer = MediaPlayer.create(this, R.raw.hiphop);
            audioPlayer.setOnCompletionListener(this);
        }
        
        // play only if track is not playing
        if (!audioPlayer.isPlaying()) 
            audioPlayer.start();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (audioPlayer != null)
        {
            prepareTrack();
        }
    }
    
    /** Prepare the track for playback */
    private void prepareTrack()
    {
        try
        {
            audioPlayer.prepare();
            audioPlayer.seekTo(0);
        } catch (Throwable t)
        {
            showErrorMessage(t);
        }        
    }

    /** Called when the clip has completed playing */
    public void onCompletion(MediaPlayer mp)
    {
        audioPlayer.stop();
        prepareTrack(); // for next time
    }

    /**
     * Called when activity is paused to ensure we return media player to safe
     * state
     */
    protected void onPause()
    {
        super.onPause();
        if (audioPlayer.isPlaying()) audioPlayer.pause();
    }
    
    protected void onStop()
    {
        super.onStop();
        if (audioPlayer == null)
            return; // nothing to stop
        audioPlayer.stop();
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if (audioPlayer != null)
            audioPlayer.release();
        audioPlayer = null;
    }

    /** In case of errors during launch of media player, show message to user */
    private void showErrorMessage(Throwable t)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error!").setMessage(t.toString()).setPositiveButton("OK", null)
                .show();
    }

}
