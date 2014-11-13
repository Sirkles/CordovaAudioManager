/*
 * CordovaAudioManager.js
 * Created by Josh Kennedy on 3 November 2014
 *
 * This computer source code is bound under the University of Illinois/NCSA license.
 * See LICENSE.TXT for the full license.
 */

/**
 * Plays sound effects and music using native API's
 * @author Josh Kennedy
 */
function CordovaAudioManager() {
	/**
	 * Loads a sound file and assigns it to an identifier.
	 * @param file The full qualified path to the audio file.
	 * @param id The unique identifier to assign the audio file.
	 */
	this.load = function(file, id) {

	}

	/**
	 * Plays an audio file defined by id.
	 * @param id The audio id to play.
	 * @param loops The number of times to play the audio. To loop infinitely use -1.
	 * @param leftVolume The volume of the left speaker as a floating-point number between 0.0 and 1.0.
	 * @param rightVolume The volume of the right speaker as a floating-point number between 0.0 and 1.0.
	 */
	this.play = function(id, loops, leftVolume, rightVolume) {

	}

	/* Internal methods to hook into the native code. */
	function AudioManager_load(file, audioId, callback) {
		cordova.exec(callback, function(err) { callback("An error has occurred."); },
			"AudioManager", "load", [file, audioId]);
	}

	function AudioManager_play(audioId, loops, leftVolume, rightVolume, callback) {
		cordova.exec(callback, function(err) { callback("An error has occurred."); },
			"AudioManager", "play", [file, audioId, loops, leftVolume, rightVolume]);
	}
}
