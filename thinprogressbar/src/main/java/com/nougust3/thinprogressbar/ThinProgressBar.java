package com.nougust3.thinprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class ThinProgressBar extends RelativeLayout {

    private FrameLayout progressView;
    private TextView hintView;

    private int max;
    private int progress;

    private String hint = " %";

    private enum HintPosition { LEFT, RIGHT, CENTER }
    private HintPosition hintPosition = HintPosition.CENTER;

    private int color;

    private static final String INSTANCE_STATE = "state";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_COLOR = "color";
    private static final String INSTANCE_HINT = "hint";
    private static final String INSTANCE_HINT_POSITION = "hint_position";

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
        createHintView();

        setMax(0);
        setProgress(0);
    }

    private void createProgressView() {
        progressView = new FrameLayout(getContext());

        progressView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        progressView.setBackgroundColor(0x6655ff55);

        this.addView(progressView);
    }

    private void createHintView() {
        hintView = new TextView(getContext());

        hintView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        hintView.setTextColor(0x6655ff55);

        this.addView(hintView);
    }

    @SuppressLint("DefaultLocale")
    private void updateProgress() {
        if(max == 0) {
            return;
        }

        if(Objects.equals(hint, " %")) {
            hintView.setText(String.format("%d%s", progress / (max / 100), hint));
        }
        else {
            hintView.setText(String.format("%d%s", progress, hint));
        }

        progressView.getLayoutParams().width = (getWidth() - hintView.getWidth()) * progress / max;
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

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHintPosition(HintPosition position) {
        hintPosition = position;
    }

    public HintPosition getHintPosition() {
        return hintPosition;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();

        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());

        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putInt(INSTANCE_COLOR, getColor());
        bundle.putString(INSTANCE_HINT, getHint());

        switch (getHintPosition()) {
            case LEFT:
                bundle.putInt(INSTANCE_HINT, 1);
                break;
            case RIGHT:
                bundle.putInt(INSTANCE_HINT, 3);
                break;
            default:
                bundle.putInt(INSTANCE_HINT, 3);
                break;
        }

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;

            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setColor(bundle.getInt(INSTANCE_COLOR));
            setHint(bundle.getString(INSTANCE_HINT));

            switch (bundle.getInt(INSTANCE_HINT_POSITION)) {
                case 1:
                    setHintPosition(HintPosition.LEFT);
                    break;
                case 2:
                    setHintPosition(HintPosition.RIGHT);
                    break;
                default:
                    setHintPosition(HintPosition.CENTER);
                    break;
            }

            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));

            return;
        }

        super.onRestoreInstanceState(state);
    }

    public void setOnProgressListener(OnProgressListener listener) {
        this.listener = listener;
    }

}
