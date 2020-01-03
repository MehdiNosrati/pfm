package io.mns.mpfm.utils;

import android.graphics.Color;
import android.view.View;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("backgroundString")
    public static void setBackground(View v, String backgorund) {
        v.setBackgroundColor(Color.parseColor(backgorund));
    }
}
