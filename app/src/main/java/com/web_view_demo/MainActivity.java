package com.web_view_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private static WebView web;
	private static ProgressDialog dialog;
	private static EditText enter_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		web = (WebView) findViewById(R.id.webview);
		enter_url = (EditText) findViewById(R.id.url);

		// Implement click listener over button
		findViewById(R.id.open_url).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String url = enter_url.getText().toString();// Get editext text
															// into string

				// Check if string url is not null
				if (url.equals("") || url.length() == 0)

					// If null then display toast
					Toast.makeText(MainActivity.this, "Please enter a url.",
							Toast.LENGTH_SHORT).show();

				else if // Check if entered url is valid or not and if it is not
						// valid then show toast
				(!Patterns.WEB_URL.matcher(url).matches())
					Toast.makeText(MainActivity.this,
							"Entered URL is not Valid.", Toast.LENGTH_SHORT)
							.show();
				else {
					// Check if interent is there or not
					if (isConnectingToInternet())
						// if both above conditions falls then load url
						loadUrl(url);
					else
						Toast.makeText(MainActivity.this,
								"There is no Interent connection.",
								Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	// Load url method in webview
	private void loadUrl(String url) {
		// Progressdialog to show when url is loading
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setMessage("Please wait..\nWhile loading URL");
		dialog.setCancelable(true);// Make it cancelable true so that user can
									// dismiss it anytime
		web.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {

				// On page finished loading dismiss dialog
				if (dialog.isShowing()) {
					dialog.dismiss();
				}

			}
		});

		dialog.show();// Show dialog

		web.loadUrl(url);// Load url into webview

		web.getSettings().setJavaScriptEnabled(true);// Enable javascript
	}

	// Return status of internet connection
	private boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}
}
