package io.itit.mvvmlight.bindingadapter.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import io.itit.mvvmlight.command.ReplyCommand;
import io.itit.mvvmlight.command.ResponseCommand;

/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    private static float sDensity = 0;
    public static int dipToPixel( int nDip,Context context) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (sDensity * nDip);
    }


    @BindingAdapter({"clickCommand"})
    public static void clickCommand(View view, final ReplyCommand clickCommand) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            }
        });
    }

    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    @BindingAdapter({"longClick"})
    public static void longClick(View view, final ReplyCommand clickCommand) {
        view.setOnLongClickListener(view1 -> {
            clickCommand.execute();
            return false;
        });
    }

    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final ReplyCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }

    @BindingAdapter({"onTouchCommand"})
    public static void onTouchCommand(View view, final ResponseCommand<MotionEvent, Boolean> onTouchCommand) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (onTouchCommand != null) {
                    return onTouchCommand.execute(event);
                }
                return false;
            }
        });
    }

    @BindingAdapter("layout_width")
    public static void setLayoutWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = dipToPixel(width,view.getContext());
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("background_color")
    public static void backgroundColor(View view, int color) {
        view.setBackgroundColor(color);
    }



    @BindingAdapter("layout_height")
    public static void setLayoutHeight(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = dipToPixel(width,view.getContext());
        view.setLayoutParams(layoutParams);
    }
}

