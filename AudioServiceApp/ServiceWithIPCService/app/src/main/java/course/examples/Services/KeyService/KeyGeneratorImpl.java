package course.examples.Services.KeyService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import course.examples.Services.KeyCommon.KeyGenerator;

public class KeyGeneratorImpl extends Service {

	// Media player for playing audio clips
	private MediaPlayer mPlayer;
	// List of audio clip resources and the clip name
	private List<Integer> mClip;
	private List<String> mClipName;
	private static final int NOTIFICATION_ID = 1;

	// Notification channel ID
	private static final String CHANNEL_ID = "Music player style" ;

	// Method called when the service is created
	@Override
	public void onCreate() {
		super.onCreate();

		this.createNotificationChannel();

		// Create a notification area notification so the user
		Intent notificationIntent = new Intent(this, KeyGeneratorImpl.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this,
				0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

		// Build notification
		Notification notification;
		notification = new NotificationCompat.Builder(this, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_launcher)
				.setOngoing(true)	// Cannot be dismissed by user
				.setContentTitle("Music Player")
				.setContentText("Playing audio clip")	// Launched user clicks card
				.setContentIntent(pendingIntent)
				.build();

		// Add audio clips to list
		mClip = new ArrayList<>();
		mClip.add(R.raw.clip1);
		mClip.add(R.raw.clip2);
		mClip.add(R.raw.clip3);
		mClip.add(R.raw.clip4);
		mClip.add(R.raw.clip5);

		// Add clip names to list
		mClipName = new ArrayList<>();
		mClipName.add("clip1");
		mClipName.add("clip2");
		mClipName.add("clip3");
		mClipName.add("clip4");
		mClipName.add("clip5");

		// Start the service in the foreground with notification
		int androidVersion = Build.VERSION.SDK_INT;
		if (androidVersion >= 29) {
			startForeground(NOTIFICATION_ID, notification,
					ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
		}
		else {
			// Before Android 10, startForground() just had only two arguments.
			startForeground(NOTIFICATION_ID, notification) ;
		}
	}

	// Method to create notification channel
	@RequiresApi(api = Build.VERSION_CODES.O)
	private void createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		CharSequence name = "Music player notification";
		String description = "The channel for music player notifications";
		int importance = NotificationManager.IMPORTANCE_DEFAULT;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel ;
			channel = new NotificationChannel(CHANNEL_ID, name, importance);
			channel.setDescription(description);

			// Register the channel with the system
			NotificationManager manager = getSystemService(NotificationManager.class);
			manager.createNotificationChannel(channel);

		}
	}

	// Binder object for clients to interact with the service
	private final KeyGenerator.Stub mBinder = new KeyGenerator.Stub() {

		// Return the Stub defined above
		public void playClip(String clipName) {
			int clipIndex = mClipName.indexOf(clipName);
			if (clipIndex != -1) {
				// Release any existing MediaPlayer instance
				if (mPlayer != null) {
					mPlayer.release();
				}

				// Initialize a new MediaPlayer instance
				mPlayer = MediaPlayer.create(KeyGeneratorImpl.this, mClip.get(clipIndex));

				// Set a completion listener to stop the service when playback completes
				mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						stopClip();
					}
				});
				// Start playback
				mPlayer.start();
			}
		}


		// Pause the playback
		public void pauseClip() {
			if (mPlayer != null && mPlayer.isPlaying()) {
				mPlayer.pause();
			}
		}

		// Resume playback from the paused state
		public void resumeClip() {
			if (mPlayer != null && !mPlayer.isPlaying()) {
				mPlayer.start();
			}
		}

		// Stop the playback and release MediaPlayer resources
		public void stopClip() {
			if (mPlayer != null) {
				mPlayer.stop();
				mPlayer.release();
				mPlayer = null;
			}
		}
	};

	// Method called when a client binds to the service
	@Override
	public IBinder onBind (Intent intent) {
		return mBinder;
	}

	// Method called when the service is destroyed
	@Override
	public void onDestroy() {
		// Release MediaPlayer resources
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		}
		super.onDestroy() ;
	}
}
