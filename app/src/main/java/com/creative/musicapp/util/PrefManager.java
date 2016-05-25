package com.creative.musicapp.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.StringTokenizer;


public class PrefManager {
	private static final String TAG = PrefManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "com.creative.musicapp";

	// Google's username
	private static final String KEY_HIGHSCORE = "highscore";

	// Google's username
	private static final String KEY_REMAINING_COIN = "remaining_coin";

	private static final String KEY_CURRENT_LEVEL = "current_level";



	public PrefManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

	}

	/**
	 * Storing google username
	 * */
	public void setHighScore(int score) {
		editor = pref.edit();

		editor.putInt(KEY_HIGHSCORE,score);

		// commit changes
		editor.commit();
	}

	public int getHighScore() {
		return pref.getInt(KEY_HIGHSCORE, 0);
	}


	public void setRemainingCoin(int amount_remaining) {
		editor = pref.edit();

		editor.putInt(KEY_REMAINING_COIN,amount_remaining);

		// commit changes
		editor.commit();
	}

	public int getRemainingCoin() {
		return pref.getInt(KEY_REMAINING_COIN, 60);
	}




	public void setCurrentLevel(int currentLevel) {
		editor = pref.edit();

		editor.putInt(KEY_CURRENT_LEVEL,currentLevel);

		// commit changes
		editor.commit();
	}

	public int getCurrentLevel() {
		return pref.getInt(KEY_CURRENT_LEVEL, 0);
	}




	public void setIntArray(int[] array){
		editor = pref.edit();
		editor.putInt("Count", array.length);
		int count = 0;
		for (int i: array){
			editor.putInt("IntValue_" + count++, i);
		}
		editor.commit();
	}
	public int[] getIntArray(){
		int[] ret;

		int count = pref.getInt("Count", 0);
		ret = new int[count];
		for (int i = 0; i < count; i++){
			ret[i] = pref.getInt("IntValue_" + i, i);
		}
		return ret;
	}



}