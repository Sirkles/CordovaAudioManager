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
this.CordovaAudioManager = (function() {
	function CordovaAudioManager() {
		console.log("Initialized CordovaAudioManager.");
	}

	/**
	 * Loads a sound file and assigns it to an identifier.
	 * @param file The full qualified path to the audio file.
	 * @param id The unique identifier to assign the audio file.
	 * @param type The type of audio of the file. Currently supported: "music" and "sound"
	 */
	this.load = function(file, id, type) {
		CordovaAudioManager_load(file, id, type, CordovaAudioManager_SuccessCallback, CordovaAudioManager_ErrorCallback);
	}

	/**
	 * Plays an audio file defined by id.
	 * @param id The audio id to play.
	 * @param loops The number of times to play the audio. To loop infinitely use -1.
	 * @param leftVolume The volume of the left speaker as a floating-point number between 0.0 and 1.0.
	 * @param rightVolume The volume of the right speaker as a floating-point number between 0.0 and 1.0.
	 */
	this.play = function(id, loops, leftVolume, rightVolume) {
		CordovaAudioManager_play(id, loops, leftVolume, rightVolume, CordovaAudioManager_SuccessCallback, CordovaAudioManager_ErrorCallback);
	}

	function CordovaAudioManager_SuccessCallback() {
		console.log("CordovaAudioManager call was completed sucessfully!");
	}

	function CordovaAudioManager_ErrorCallback() {
		console.log("CordovaAudioManager call was not completed sucessfully. :-(");
	}

	/* Internal methods to hook into the native code. */
	function CordovaAudioManager_load(file, audioId, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "AudioManager", "load", [file, audioId]);
	}

	function CordovaAudioManager_play(audioId, loops, leftVolume, rightVolume, callback) {
		cordova.exec(successCallback, errorCallback, "AudioManager", "play", [file, audioId, loops, leftVolume, rightVolume]);
	}
})();
