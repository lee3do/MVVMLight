package io.itit.mvvmlight.bindingadapter.swiperefresh;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import io.itit.mvvmlight.command.ReplyCommand;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by kelin on 16-4-26.
 */
public class ViewBindingAdapter {
    private static float sDensity = 0;

    public static int dipToPixel(Context context, int nDip) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) context.getSystemService(Context
                    .WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (sDensity * nDip);
    }

    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand
            onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }

    @BindingAdapter("refresh_label")
    public static void setRefreshLabel(PtrClassicFrameLayout rotateHeaderWebViewFrame, String
            label) {
        StoreHouseHeader header = new StoreHouseHeader(rotateHeaderWebViewFrame.getContext());
        header.setPadding(0, dipToPixel(rotateHeaderWebViewFrame.getContext(), 15), 0, 0);
        header.initWithString(label);
        header.setTextColor(Color.rgb(20, 54, 95));
        rotateHeaderWebViewFrame.setHeaderView(header);
        rotateHeaderWebViewFrame.addPtrUIHandler(header);
        rotateHeaderWebViewFrame.disableWhenHorizontalMove(true);
    }


    @BindingAdapter("after_refresh")
    public static void afterRefresh(PtrClassicFrameLayout rotateHeaderWebViewFrame, final
    ReplyCommand command) {
        rotateHeaderWebViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                boolean canRefresh = PtrDefaultHandler.checkContentCanBePulledDown(frame,
                        content, header);
                return canRefresh;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (command != null) {
                    command.execute(frame);
                }
            }
        });
    }
}
