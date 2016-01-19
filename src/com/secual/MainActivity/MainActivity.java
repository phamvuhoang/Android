package com.secual.MainActivity;

import com.secual.bos.Constants;
import com.secual.bos.SecualWebViewClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	private WebView webView;
	private Constants constants;
	private String URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		constants = new Constants();
		URL = constants.geturl();
		//Get webview 
		webView = (WebView) findViewById(R.id.webview);
		startWebView(URL);
		
	}
	
	private void startWebView(String url) {
        
        //Create new webview Client to show progress dialog
        //When opening a url or click on link
         
        webView.setWebViewClient(new SecualWebViewClient(this, webView) {     
            ProgressDialog progressDialog;
          
            //If you will not use this method url links are opeen in new brower not in webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {             
                view.loadUrl(url);
                return true;
            }
        
            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
             
        });
          
         // Javascript inabled on webview 
        webView.getSettings().setJavaScriptEnabled(true);
         
        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */
         
        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */
        
        webView.addJavascriptInterface(new JavascriptBridge(), "jsAndroiInterface");
        
        webView.setWebChromeClient(new WebChromeClient());
        
        //Load url in webview
        webView.loadUrl(url);
          
          
    }
	
	final class JavascriptBridge{
		public String getData(String address, String dns){
			
			String rst = "Server address: " + address + "\n";
			rst = rst + "DNS: " + dns + "";
			
			Toast.makeText(MainActivity.this, rst, Toast.LENGTH_LONG).show();
			
			constants = new Constants();
			webView.loadUrl(constants.getWifiUrl());
			return rst;
		}
	}
	
	public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}