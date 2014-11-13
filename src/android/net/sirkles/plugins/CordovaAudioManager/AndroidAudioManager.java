/*
 * CordovaAudioManager.java
 * Created by Josh Kennedy on 3 November 2014
 *
 * This computer source code is bound under the University of Illinois/NCSA license.
 * See LICENSE.TXT for the full license.
 */

package net.sirkles.plugins.CordovaAudioManager;

// Cordova API's
import org.apache.cordova.*;

// Required for marshaling calls between Android userspace and Cordova.
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// For the Android audio API's
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

// For various Android API's (non audio related)
import android.net.Uri;

// For HashMap.
import java.util.*;

/**
 * Manages and plays audio from JavaScript without platform limitations.
 * @author Josh Kennedy
 */
public class CordovaAudioManager extends CordovaPlugin {
	static HashMap<String, Integer> soundMap;
	static HashMap<String, MediaPlayer> musicMap;

	static SoundPool soundPool;

	static
	{
		// Instantiate the audio maps, before AudioManager gets instantiated.
		soundMap = new HashMap<String, Integer>();
		musicMap = new HashMap<String, MediaPlayer>();

		// Instantiate the SoundPool too.
		soundPool = new SoundPool(99, AudioManager.STREAM_MUSIC, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("load")) {
			String file = args.getString(0); 
			String audioId = args.getString(1);
			String audioType = args.getString(2);

			this.load(file, audioId, audioType, callbackContext);
						
			return true;
		}

		if (action.equals("play")) {
			String audioId = args.getString(0);
			int loops = args.getInt(1);
			float leftVolume = (float)args.getDouble(2);
			float rightVolume = (float)args.getDouble(3);

			this.play(audioId, loops, leftVolume, rightVolume, callbackContext);

			return true;
		}


		return false;
	}

	/**
	 * Loads an audio file, and caches it into a HashMap.
	 *
	 * @param audioFile Local audio file to load. It may need to start with "file://"
	 * @param audioId Unique identifier to assign the audio file.
	 * @param audioType The type of audio to load.
	 */
	private void load(String audioFile, String audioId, String audioType, CallbackContext callbackContext) {
		// Ensure parameters are not null or empty.
		if (!parameterChecker(audioFile)) {
			callbackContext.error("audioFile parameter is null or empty!");
		}

		if (!parameterChecker(audioId)) {
			callbackContext.error("audioId parameter is null or empty!");
		}

		if (!parameterChecker(audioType)) {
			callbackContext.error("audioType parameter is null or empty!");
		}

		if (audioType.equals("music")) {
			// TODO: Implement
			callbackContext.error("This functionality has not been implemented yet!");
		} else if (audioType.equals("sound")) {
			int soundEffect = soundPool.load(audioFile, 1);
			soundMap.put(audioId, soundEffect);
		}
	}

	/**
	 * Plays an audio file.
	 * @param audioId The identifier of the audio to play.
	 * @param loops The number of times to repeat the playback. Supply -1 to have loop indefinitely.
	 * @param leftVolume The volume of playback on the left speaker, between a range of 0.0 and 1.0
	 * @param rightVolume The volume of playback on the right speaker, between a range of 0.0 and 1.0
	 */
	private void play(String audioId, int loops, float leftVolume, float rightVolume, CallbackContext callbackContext) {
		// First, check to see if the audioId key exists in both HashMaps.
		if (!soundMap.containsKey(audioId) || !musicMap.containsKey(audioId)) {
			callbackContext.error("audioId is not a valid key.");
		} else {
			if (soundMap.containsKey(audioId)) {
				soundPool.play(soundMap.get(audioId), leftVolume, rightVolume, 0, loops, 1.0f);

				callbackContext.success();
			} else if (musicMap.containsKey(audioId)) {
				// TODO: Implement
				callbackContext.error("This functionality has not been implemented yet!");
			}
		}
	}

	/**
	 * Checks the string parameter to see if it is null or empty.
	 *
	 * @param parameter The parameter to check.
	 * @return True if the parameter seems to be OK, false otherwise.
	 */
	private static boolean parameterChecker(String parameter) {
		return (parameter != null && parameter.length() > 0);
	}

	/**
	 * Ensures that a volume parameter is in the correct range (between 0.0 and 1.0)
	 *
	 * @param parameter Requested volume as a floating-point number.
	 * @return The parameter if it falls in to the range, or 1.0 if it doesn't.
	 */
	private static float ensureVolumeRange(float parameter) {
		return ((parameter > 1.0f || parameter < 0.0) ? 1.0f : parameter);
	}

	/**
	 * Ensures the loops parameter is -1 or greater.
	 * @param parameter Requested number of loops as an integer.
	 * @return The parameter if it is >= -1, or 0 if it does not.
	 */
	private static int ensureLoops(int parameter) {
		return (parameter >= -1 ? parameter : 0);
	}
}
