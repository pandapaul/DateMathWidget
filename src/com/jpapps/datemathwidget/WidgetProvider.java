package com.jpapps.datemathwidget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

	public static final String KEY_DATE_FORMAT = "KEY_DATE_FORMAT";
	public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

	public static final String KEY_DATE_FIELD = "KEY_DATE_FIELD";
	public static final int DEFAULT_DATE_FIELD = Calendar.DATE;

	public static final String KEY_DATE_VALUE = "KEY_DATE_VALUE";
	public static final int DEFAULT_DATE_VALUE = -17;

	public static final String ACTION_CLICKED_SYNC = "com.jpapps.datemathwidget.action.ACTION_CLICKED_SYNC";

	public static final String EXTRA_APP_WIDGET_ID = "EXTRA_APP_WIDGET_ID";

	private static final String logLabel = "DateMathWidget";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
			updateWidget(context, appWidgetManager, appWidgetId, options);
		}
	}

	private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle options) {

		Log.d(logLabel, "updating widget with id " + appWidgetId);

		SimpleDateFormat formatter = new SimpleDateFormat(options.getString(KEY_DATE_FORMAT,
				DEFAULT_DATE_FORMAT));
		int dateValue = options.getInt(KEY_DATE_VALUE, DEFAULT_DATE_VALUE);
		int dateField = options.getInt(KEY_DATE_FIELD, DEFAULT_DATE_FIELD);
		Calendar c = Calendar.getInstance();
		c.add(dateField, dateValue);

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
		// views.setOnClickPendingIntent(R.layout.appwidget,
		// getPendingSelfIntent(context, ACTION_CLICKED_SYNC, appWidgetId,
		// options));
		views.setTextViewText(R.id.dateOfInterest, formatter.format(c.getTime()));
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
			int appWidgetId, Bundle newOptions) {
		updateWidget(context, appWidgetManager, appWidgetId, newOptions);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(logLabel, "onReceive called");
		if (ACTION_CLICKED_SYNC.equals(intent.getAction())) {
			Log.d(logLabel, "action matched");
			Bundle extras = intent.getExtras();
			if (extras != null) {
				updateWidget(context, AppWidgetManager.getInstance(context),
						extras.getInt(EXTRA_APP_WIDGET_ID), extras);
			}
		}
		super.onReceive(context, intent);
	}

	private PendingIntent getPendingSelfIntent(Context context, String action, int appWidgetId,
			Bundle options) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		intent.putExtra(EXTRA_APP_WIDGET_ID, appWidgetId);
		intent.putExtras(options);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
}
