package com.headsup.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

	private final String SHARED_PREF_NAME = "teams_app_prefs";
	private SharedPreferences mSharedPreference;
	
	
	public SharedPreferenceManager(Context ctx) {
		mSharedPreference = ctx.getSharedPreferences(SHARED_PREF_NAME, 
				Context.MODE_PRIVATE);
	}
	
	/**
	 * This method is used to set String values in Preference file.
	 * @param key
	 * @param value - String value to be set.
	 */
	public void setStringValue(String key, String value){
		mSharedPreference.edit().putString(key, value).commit();
	}
	
	/**
	 * This method is used to get the String value from Preference file.
	 * @param key
	 * @return value
	 * @throws NullPointerException
	 */
	public String getStringValue(String key) throws NullPointerException{
		return mSharedPreference.getString(key, null);
	}
	
	/**
	 * This method is used to set Int values in Preference file.
	 * @param key
	 * @param value - Int value to be set.
	 */
	public void setIntValue(String key, int value){
		mSharedPreference.edit().putInt(key, value).commit();
	}
	
	/**
	 * This method is used to get the Int value from Preference file. If key is not found, -1 will be returned.
	 * @param key
	 * @return value
	 */
	public int getIntValue(String key) {
		return mSharedPreference.getInt(key, -1);
	}
	
	/**
	 * This method is used to set Boolean values in Preference file.
	 * @param key
	 * @param value - Boolean value to be set.
	 */
	public void setBooleanValue(String key, boolean value){
		mSharedPreference.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * This method is used to get the Boolean value from Preference file. If key is not found, false will be returned.
	 * @param key
	 * @return value
	 */
	public boolean getBooleanValue(String key) {
		return mSharedPreference.getBoolean(key, false);
	}
	
}
