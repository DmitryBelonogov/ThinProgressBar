package com.nougust3.thinprogressbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class ThinProgressBar extends RelativeLayout {

    private FrameLayout progressView;

    private int max;
    private int progress;

    private int color;

    private static final String INSTANCE_STATE = "state";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_COLOR = "color";

    private OnProgressListener listener;

    public ThinProgressBar(Context context) {
        super(context);
        setupView();
    }

    public ThinProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public ThinProgressBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    private void setupView() {
        setGravity(Gravity.START);

        createProgressView();

        setMax(0);
        setProgress(0);
    }

    private void createProgressView() {
        progressView = new FrameLayout(getContext());

        progressView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        progressView.setBackgroundColor(0x6655ff55);

        this.addView(progressView);
    }

    private void updateProgress() {
        if(max == 0) {
            return;
        }

        progressView.getLayoutParams().width = getWidth() * progress / max;
        progressView.requestLayout();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setProgress(int progress) {
        if(progress > max) {
            this.progress = max;
        }
        else {
            this.progress = progress;
        }

        updateProgress();
    }

    public int getProgress() {
        return progress;
    }

    public void setColor(int color) {
        this.color = color;

        progressView.setBackgroundColor(color);
    }

    public int getColor() {
        return color;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putInt(INSTANCE_COLOR, getColor());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setColor(bundle.getInt(INSTANCE_COLOR));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }

        super.onRestoreInstanceState(state);
    }

    public void setOnProgressListener(OnProgressListener listener) {
        this.listener = listener;
    }

}
