package com.xueyan.personal.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
	public static final String FILE_NAME = "Personal";

	public static String getData(Context context, String key,
								 String defaultValue) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
				.getString(key, defaultValue);
	}

	public static boolean saveData(Context context, String key, String value) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		return editor.commit();
	}

	public static int getData(Context context, String key, int defaultValue) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
				.getInt(key, defaultValue);
	}

	public static boolean saveData(Context context, String key, int value) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	public static boolean getData(Context context, String key,
								  boolean defaultValue) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(key, defaultValue);
	}

	public static boolean saveData(Context context, String key, boolean value) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	public static float getData(Context context, String key, float defaultVaule) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
				.getFloat(key, defaultVaule);
	}

	public static boolean saveData(Context context, String key, float value) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putFloat(key, value);
		return editor.commit();
	}
}
