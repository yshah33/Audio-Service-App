package course.examples.Services.KeyClient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import course.examples.Services.KeyCommon.KeyGenerator;

public class KeyServiceUser extends Activity {

	protected static final String TAG = "AudioClient";
	protected static final int PERMISSION_REQUEST = 0;

	// Reference to the KeyGenerator service interface
	private KeyGenerator mclip;

	// Indicates whether the service is bound
	private boolean mIsBound = false;

	// UI elements (TextView, Spinner, Button)
	TextView welcome;
	Spinner output;
	Button start, playButton, pauseButton, resumeButton, stopButton, stop;
	String[] clips = {"clip1", "clip2", "clip3", "clip4", "clip5"};
	String clipIndexStr;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		// Initialize UI elements
		welcome = findViewById(R.id.welcome);
		output = findViewById(R.id.clipSpinner);
		start = findViewById(R.id.start);
		playButton = findViewById(R.id.play);
		pauseButton = findViewById(R.id.pause);
		resumeButton = findViewById(R.id.resume);
		stopButton = findViewById(R.id.stopClip);
		stop = findViewById(R.id.stop);

		// Create adapter for the clip spinner
		ArrayAdapter<String> clipAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clips);
		clipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		output.setAdapter(clipAdapter);

		// Set initial visibility of UI elements
		visibality();

		// Start button click listener
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Update the visibility of the UI elements
				start.setVisibility(View.INVISIBLE);
				stop.setVisibility(View.VISIBLE);
				output.setVisibility(View.VISIBLE);
				playButton.setVisibility(View.VISIBLE);

				// Check binding status and bind to service
				checkBindingAndBind();
			}
		});

		// Clip spinner item selection listener
		output.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				clipIndexStr = (String) parent.getItemAtPosition(position);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			// Nothing
			}
		});

		// Play button click listener
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsBound) {
					if (clipIndexStr != null && !clipIndexStr.isEmpty()) {
						try {
							// Update the visibility of the UI elements
							stopButton.setVisibility(View.VISIBLE);
							pauseButton.setVisibility(View.VISIBLE);

							// Call the playClip function to play the music
							mclip.playClip(clipIndexStr);
						} catch (RemoteException e) {
							Log.e(TAG, e.toString());
						}
					}
				}
			}
		});

		// Pause button click listener
		pauseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsBound) {
					try {
						// Update the visibility of the UI element
						resumeButton.setVisibility(View.VISIBLE);

						// Call the pauseClip function to pause the music
						mclip.pauseClip();
					} catch (RemoteException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});

		// Resume button click listener
		resumeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsBound) {
					try {
						// Update the visibility of the UI element
						resumeButton.setVisibility(View.INVISIBLE);

						// Call the resumeClip function to resume the music
						mclip.resumeClip();
					} catch (RemoteException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});

		// Stop button click listener
		stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsBound) {
					try {
						// Update the visibility of the UI elements
						playButton.setVisibility(View.INVISIBLE);
						pauseButton.setVisibility(View.INVISIBLE);
						resumeButton.setVisibility(View.INVISIBLE);

						// Call the stopClip function to stop the music
						mclip.stopClip();
//						if (mIsBound) {
//							unbindService(mConnection); // Unbind from the service
//							mIsBound = false;
//						}
					} catch (RemoteException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});

		// Stop service button click listener
		stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsBound) {
					try {
						// Update the visibility of the UI element
						start.setVisibility(View.VISIBLE);
						visibality();

						// Call the stopClip function to stop the service
						mclip.stopClip();

						// Unbind from the service
						if (mIsBound) {
							unbindService(mConnection);
							mIsBound = false;
						}
					} catch (RemoteException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
	}

	// Method to set visibility of UI elements
	public void visibality () {
		output.setVisibility(View.INVISIBLE);
		playButton.setVisibility(View.INVISIBLE);
		pauseButton.setVisibility(View.INVISIBLE);
		resumeButton.setVisibility(View.INVISIBLE);
		stopButton.setVisibility(View.INVISIBLE);
		stop.setVisibility(View.INVISIBLE);
	}

	// Bind to KeyGenerator Service
	@Override
	protected void onStart() {
		super.onStart();

		// Request permission if not granted already
		if (checkSelfPermission("course.examples.Services.KeyService.GEN_ID")
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{"course.examples.Services.KeyService.GEN_ID"},
					PERMISSION_REQUEST);
		} else {
			// Check binding status and bind to service
			checkBindingAndBind();
		}
	}

	// Method to check binding status and bind to service if needed
	protected void checkBindingAndBind() {
		if (!mIsBound) {

			boolean b;
			Intent i = new Intent(KeyGenerator.class.getName());

			Context c = null;
			try {
				c = createPackageContext("course.examples.Services.KeyService", 0);
			} catch (PackageManager.NameNotFoundException e) {
				throw new RuntimeException(e);
			}

			ResolveInfo info = getPackageManager().resolveService(i, PackageManager.MATCH_ALL);
			i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

			b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
		}
	}

	// Handle permission request result
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST: {

				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// Permission granted, go ahead and bind to service
					checkBindingAndBind();
				}
			}
			default: {
				// do nothing
			}
		}
	}

	// Unbind from KeyGenerator Service
	@Override
	protected void onStop() {
		super.onStop();
		if (mIsBound) {
			unbindService(this.mConnection);
			mIsBound = false;
		}
	}

	// Service connection callbacks
	private final ServiceConnection mConnection = new ServiceConnection() {

		// Called when the connection with the service is established
		public void onServiceConnected(ComponentName className, IBinder iservice) {
			mclip = KeyGenerator.Stub.asInterface(iservice);
			mIsBound = true;
		}

		// Called when the connection with the service disconnects
		public void onServiceDisconnected(ComponentName className) {
			mclip = null;
			mIsBound = false;

		}
	};

	// Called when the activity is resumed
	@Override
	public void onResume() {
		super.onResume();
	}

	// Called when the activity is paused
	@Override
	public void onPause() {
		super.onPause();
	}
}