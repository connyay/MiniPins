package com.connyay.minipins;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MiniPinsActivity extends Activity {
	final Activity activity = this;
	WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set up progress in title bar
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// Makes Progress bar Visible
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);
		setContentView(R.layout.pins);

		// Change titlebar color
		View titleView = getWindow().findViewById(android.R.id.title);
		if (titleView != null) {
			ViewParent parent = titleView.getParent();
			if (parent != null && (parent instanceof View)) {
				View parentView = (View) parent;
				parentView.setBackgroundColor(Color.rgb(203, 35, 39));
			}
		}

		// set up webview
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setAppCacheMaxSize(1 * 1024 * 9);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);

		// Add progress bar to the top of phone
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				activity.setTitle("Working...");
				activity.setProgress(progress * 100);

				if (progress == 100) {
					activity.setTitle(R.string.app_name);
					getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
							Window.PROGRESS_VISIBILITY_OFF);
				}
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// Handle the error
			}

			// this method prevents the application from loading in a ext
			// browser
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// special case with youtube videos [open in youtube app]
				if (url.contains("youtube")) {

					Uri uri1 = Uri.parse(url);
					String vid = uri1.getQueryParameter("v");

					Intent i = new Intent(Intent.ACTION_VIEW, Uri
							.parse("vnd.youtube:" + vid));
					startActivity(i);
					return false;

				} else

					view.loadUrl(url);
				return true;
			}
		});
		// load the site!
		webView.loadUrl("https://m.pinterest.com");

	}

	// create menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Load different views
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle menu selection
		// 1 = load all
		// 2 = load popular
		// 3 = select categories
		// 4 = load videos
		// 5 = load gifts
		// 6 = search
		switch (item.getItemId()) {
			case R.id.item1 :
				webView.loadUrl("https://m.pinterest.com/all");
				return true;
			case R.id.item2 :
				webView.loadUrl("https://m.pinterest.com/popular");
				return true;
			case R.id.item3 :
				final String[] categories = {"Architecture", "Art",
						"Cars & Motorcycles", "Design", "DIY & Crafts",
						"Education", "Film, Music & Books", "Fitness",
						"Food & Drink", "Gardening", "Geek", "Hair & Beauty",
						"History", "Holidays", "Home", "Humor", "Kids",
						"MyLife", "Women Apparel", "Men Apparel", "Outdoors",
						"People", "Pets", "Photography", "Prints Posters",
						"Products", "Science", "Sports", "Technology",
						"Travel Places", "Wedding Events", "Other"};
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Pick a category");
				builder.setItems(categories,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								String choice = categories[item];

								// format category string
								webView.loadUrl("https://m.pinterest.com/all/?category="
										+ choice.replace(", ", "_")
												.replace(" & ", "_")
												.replace(" ", "_")
												.toLowerCase());
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
				return true;
			case R.id.item4 :
				webView.loadUrl("https://m.pinterest.com/videos");
				return true;
			case R.id.item5 :
				final String[] gifts = {"All", "$1-$20", "$20-$50", "$50-$100",
						"$100-$200", "$200-$500", "$500+"};
				AlertDialog.Builder gift_builder = new AlertDialog.Builder(this);
				gift_builder.setTitle("Gifts Price Range:");
				gift_builder.setItems(gifts,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								String gift_choice = gifts[item];

								if (item == 0)
									webView.loadUrl("https://m.pinterest.com/gifts");
								else if (item == 1)
									webView.loadUrl("https://m.pinterest.com/gifts/?price_start=1&price_end=20");
								else if (item == 2)
									webView.loadUrl("https://m.pinterest.com/gifts/?price_start=20&price_end=50");
								else if (item == 3)
									webView.loadUrl("https://m.pinterest.com/gifts/?price_start=50&price_end=100");
								else if (item == 4)
									webView.loadUrl("https://m.pinterest.com/gifts/?price_start=100&price_end=200");
								else if (item == 5)
									webView.loadUrl("https://m.pinterest.com/gifts/?price_start=200&price_end=500");
								else if (item == 6)
									webView.loadUrl("https://m.pinterest.com/gifts/?price_start=500&price_end=1000000");
							}
						});
				AlertDialog gift_alert = gift_builder.create();
				gift_alert.show();
				return true;
			case R.id.item6 :

				final String[] searchFor = {"Pins", "Boards", "People"};
				AlertDialog.Builder search_builder = new AlertDialog.Builder(
						this);
				search_builder.setTitle("Searching for...");
				search_builder.setItems(searchFor,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								String search_choice = searchFor[item];

								if (item == 0) {

									AlertDialog.Builder alert0 = new AlertDialog.Builder(
											MiniPinsActivity.this);

									alert0.setTitle("Search Pins:");

									// Set an EditText view to get user input
									final EditText input = new EditText(
											MiniPinsActivity.this);
									alert0.setView(input);

									alert0.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													Editable value = input
															.getText();
													// Do something with value!
													webView.loadUrl("https://m.pinterest.com/search/?q="
															+ value);
												}
											});

									alert0.setNegativeButton(
											"Cancel",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// Canceled.
												}
											});

									alert0.show();

								}
								if (item == 1) {

									AlertDialog.Builder alert0 = new AlertDialog.Builder(
											MiniPinsActivity.this);

									alert0.setTitle("Search Boards:");

									// Set an EditText view to get user input
									final EditText input = new EditText(
											MiniPinsActivity.this);
									alert0.setView(input);

									alert0.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													Editable value = input
															.getText();
													// Do something with value!
													webView.loadUrl("https://m.pinterest.com/search/boards/?q="
															+ value);
												}
											});

									alert0.setNegativeButton(
											"Cancel",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// Canceled.
												}
											});

									alert0.show();

								}
								if (item == 2) {

									AlertDialog.Builder alert0 = new AlertDialog.Builder(
											MiniPinsActivity.this);

									alert0.setTitle("Search People:");

									// Set an EditText view to get user input
									final EditText input = new EditText(
											MiniPinsActivity.this);
									alert0.setView(input);

									alert0.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													Editable value = input
															.getText();
													// Do something with value!
													webView.loadUrl("https://m.pinterest.com/search/people/?q="
															+ value);
												}
											});

									alert0.setNegativeButton(
											"Cancel",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// Canceled.
												}
											});

									alert0.show();

								}

							}
						});
				AlertDialog search_choice_alert = search_builder.create();
				search_choice_alert.show();
				return true;

			default :
				return super.onOptionsItemSelected(item);

		}

	}

	// back button goes back on webview instead of closing application
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.stopLoading();
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// clear app cache on quit
	@Override
	protected void onStop() {
		super.onStop();
		webView.clearCache(true);
	}
}
