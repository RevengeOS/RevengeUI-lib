package com.revengeos.revengeui.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.revengeos.revengeui.R;

import java.util.ArrayList;

public class ThemeUtils {
    public final static int MODE_LIGHT = 0;
    public final static int MODE_DARK = 1;
    public final static int MODE_BATTERY_SAVER = 2;
    public final static int MODE_FOLLOW_SYSTEM = 3;

    public static final String PREFS = "preferences";
    public final static String THEME_MODE_PREF = "theme_mode";

    public static void showThemeDialog(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(context.getString(R.string.menu_theme));
        ArrayList<String> items = new ArrayList<String>();
        items.add(context.getString(R.string.light_theme));
        items.add(context.getString(R.string.dark_theme));
        items.add(context.getString(R.string.based_on_battery));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            items.add(context.getString(R.string.based_on_system));
        }
        alertDialog.setSingleChoiceItems(items.toArray(new CharSequence[items.size()]),
                getThemeModeValue(context), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int value) {
                setThemeMode(value);
                setThemeModePref(((Dialog) dialog).getContext(), value);
                dialog.dismiss();
            }
        });
        /*alertDialog.setSingleChoiceItems(items.toArray(new CharSequence[items.size()]),
                getThemeModeValue(context), (dialog, value) -> {
                    setThemeMode(value);
                    setThemeModePref(((Dialog) dialog).getContext(), value);
                    dialog.dismiss();
                });*/
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public static void setThemeMode(int value) {
        switch (value) {
            case MODE_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case MODE_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case MODE_BATTERY_SAVER:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                break;
            case MODE_FOLLOW_SYSTEM:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    private static void setThemeModePref(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
        prefs.edit().putInt(THEME_MODE_PREF, value).apply();
    }

    public static int getThemeModeValue(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFS, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return pref.getInt(THEME_MODE_PREF, 3);
        } else {
            return pref.getInt(THEME_MODE_PREF, 0);
        }
    }
}
