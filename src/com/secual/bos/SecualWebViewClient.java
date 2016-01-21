package com.secual.bos;

import java.util.Hashtable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SecualWebViewClient extends WebViewClient{
	private String TAG = "SecualWebViewClient";

    //String hash = "jio00f7z";
    Activity activity = null;
    WebView webView;
    public static final int REQUEST_CODE = 0;
    public static final int QR_REQUEST_CODE = 1;
    
    

    private Hashtable<String, String> attributeSet = new Hashtable<String, String>();

    public SecualWebViewClient(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //String contents = data.getStringExtra("SCAN_RESULT");
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "TAB BAR URL - " + url);

        if (url.contains("target=_blank")) {
            openExternalBrowser(this.activity, url);
        } else if (url.startsWith("local/")) {
            webView.loadUrl("file:///android_asset/" + url.replaceAll("local/", ""));
        } else {
            view.loadUrl(url);
            view.requestFocus();
        }

        return true;
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        Log.v(TAG, String.valueOf(event.getKeyCode()));
        return true;
    }

    public void setData(String action) {
        if (action.equals("score")) {
        }
    }

    public void setAttribute(String string, String string2) {
        Hashtable<String, String> hashtable = this.attributeSet;
        hashtable.put(string, string2);
    }

    public static void openExternalBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(i);
    }
}
