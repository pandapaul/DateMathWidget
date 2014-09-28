package com.jpapps.datemathwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class SettingsActivity extends Activity {

	private int appWidgetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setAppWidgetId();
	}

	private void setAppWidgetId() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
	}

	@Override
	public void finish() {
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		setResult(RESULT_OK, resultValue);
		super.finish();
	}

	public void save() {
		configure();
		updateAppWidget();
	}

	private void configure() {

	}

	private void updateAppWidget() {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.appwidget);
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}

}
