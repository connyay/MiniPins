package com.connyay.minipins;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView.HitTestResult;

public class NewPin extends Activity  {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout);

	    MyWebView mwv = (MyWebView) findViewById(R.id.mwv);
	    registerForContextMenu(mwv);
	}

	public boolean onLongClick(View v) {
        openContextMenu(v);
        return true;
    }
	@Override
	protected void onCreateContextMenu(ContextMenu menu) {
	    super.onCreateContextMenu(menu);

	    HitTestResult result = getHitTestResult();

	    MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
	        public boolean onMenuItemClick(MenuItem item) {
	                // do the menu action
	                return true;
	        }
	    };

	    if (result.getType() == HitTestResult.IMAGE_TYPE ||
	            result.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

	        menu.setHeaderTitle(result.getExtra());
	        menu.add(0, ID_SAVEIMAGE, 0, "Save Image").setOnMenuItemClickListener(handler);
	    }
}
