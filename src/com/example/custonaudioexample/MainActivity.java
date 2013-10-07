package com.example.custonaudioexample;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnCompletionListener,
		OnClickListener, OnTouchListener {

	MediaPlayer mediaPlayer;
	View theView;
	Button stopButton, startButton;
	int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		stopButton = (Button)findViewById(R.id.StopButton);
		startButton = (Button)findViewById(R.id.StartButton);
		startButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		
		theView = (View)findViewById(R.id.theView);
		theView.setOnTouchListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mediaPlayer = MediaPlayer.create(this, R.raw.deep);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mediaPlayer.stop();
		mediaPlayer.release();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mediaPlayer.start();
		mediaPlayer.seekTo(position);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			if(mediaPlayer.isPlaying()) {
				position = (int)(event.getX() * mediaPlayer.getDuration() / theView.getWidth());
				Log.v("Seek", "" + position);
				mediaPlayer.seekTo(position);
			}
		}
		return true;
	}
	
	@Override
	public void onClick(View v) {
		
		if(v == stopButton) {
			mediaPlayer.pause();
		} else if(v == startButton) {
			mediaPlayer.start();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
