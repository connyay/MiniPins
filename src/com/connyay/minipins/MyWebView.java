package com.connyay.minipins;

import android.content.Context;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.webkit.WebView;

public class MyWebView extends WebView {
    NewPin theListener;
    Context context;
    GestureDetector gd;

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