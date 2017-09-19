package com.zgq.wokao.ui.widget;

/**
 * Created by zgq on 2017/2/22.
 */

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import com.zgq.wokao.R;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.END;
import static android.view.Gravity.START;
import static android.view.Gravity.TOP;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.zgq.wokao.ui.widget.SlideUp.State.HIDDEN;
import static com.zgq.wokao.ui.widget.SlideUp.State.SHOWED;

public class SlideUp implements View.OnTouchListener, ValueAnimator.AnimatorUpdateListener, AnimatorListener {
    private final static String TAG = "SlideUp";

    private final static String KEY_START_GRAVITY = TAG + "_start_gravity";
    private final static String KEY_DEBUG = TAG + "_debug";
    private final static String KEY_TOUCHABLE_AREA = TAG + "_touchable_area";
    private final static String KEY_STATE = TAG + "_state";
    private final static String KEY_AUTO_SLIDE_DURATION = TAG + "_auto_slide_duration";
    private final static String KEY_HIDE_SOFT_INPUT = TAG + "_hide_soft_input";

    /**
     * <p>Available start states</p>
     */
    public enum State implements Parcelable, Serializable {

        /**
         * State hidden is equal {@link View#GONE}
         */
        HIDDEN,

        /**
         * State showed is equal {@link View#VISIBLE}
         */
        SHOWED;

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return State.values()[in.readInt()];
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };
    }

    @IntDef(value = {START, END, TOP, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    private @interface StartVector {
    }

    private State startState;
    private State currentState;
    private View sliderView;
    private float touchableArea;
    private int autoSlideDuration;
    private Listener listener;

    private ValueAnimator valueAnimator;
    private float slideAnimationTo;

    private float startPositionY;
    private float startPositionX;
    private float viewStartPositionY;
    private float viewStartPositionX;
    private boolean canSlide = true;
    private float density;
    private float maxSlidePosition;
    private float viewHeight;
    private float viewWidth;
    private boolean hideKeyboard;
    private TimeInterpolator interpolator;

    private boolean gesturesEnabled;

    private boolean isRTL;

    private int startGravity;

    private boolean debug;

    /**
     * <p>Interface to listen to all handled events taking place in the slider</p>
     */
    public interface Listener {

        /**
         * @param percent percents of complete slide <b color="#EF6C00">(100 = HIDDEN, 0 = SHOWED)</b>
         */
        void onSlide(float percent);

        /**
         * @param visibility (<b>GONE</b> or <b>VISIBLE</b>)
         */
        void onVisibilityChanged(int visibility);


        void onAnimatorStarted(int direction);
    }

    /**
     * <p>Default constructor for SlideUp</p>
     */
    public final static class Builder {
        private View sliderView;
        private float density;
        private float touchableArea;
        private boolean isRTL;
        private State startState = HIDDEN;
        private Listener listener;
        private boolean debug = false;
        private int autoSlideDuration = 300;
        private int startGravity = TOP;
        private boolean gesturesEnabled = true;
        private boolean hideKeyboard = false;
        private TimeInterpolator interpolator = new DecelerateInterpolator();

        /**
         * <p>Construct a SlideUp by passing the view or his child to use for the generation</p>
         */
        public Builder(@NonNull View sliderView) {
            this.sliderView = sliderView;
            density = sliderView.getResources().getDisplayMetrics().density;
            isRTL = sliderView.getResources().getBoolean(R.bool.is_right_to_left);
            touchableArea = 300 * density;
        }

        /**
         * <p>Define a start state on screen</p>
         *
         * @param startState <b>(default - <b color="#EF6C00">{@link State#HIDDEN}</b>)</b>
         */
        public Builder withStartState(@NonNull State startState) {
            this.startState = startState;
            return this;
        }

        /**
         * <p>Define a start gravity, <b>this parameter affects the motion vector slider</b></p>
         *
         * @param gravity <b>(default - <b color="#EF6C00">{@link android.view.Gravity#BOTTOM}</b>)</b>
         */
        public Builder withStartGravity(@StartVector int gravity) {
            startGravity = gravity;
            return this;
        }

        /**
         * <p>Define a {@link Listener} for this SlideUp</p>
         */
        public Builder withListener(@NonNull Listener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * <p>Turning on/off debug logging for all handled events</p>
         *
         * @param enabled <b>(default - <b color="#EF6C00">false</b>)</b>
         */
        public Builder withLoggingEnabled(boolean enabled) {
            debug = enabled;
            return this;
        }

        /**
         * <p>Define duration of animation (whenever you use {@link #hide()} or {@link #show()} methods)</p>
         *
         * @param duration <b>(default - <b color="#EF6C00">300</b>)</b>
         */
        public Builder withAutoSlideDuration(int duration) {
            autoSlideDuration = duration;
            return this;
        }

        /**
         * <p>Define touchable area <b>(in dp)</b> for interaction</p>
         *
         * @param area <b>(default - <b color="#EF6C00">300dp</b>)</b>
         */
        public Builder withTouchableArea(float area) {
            touchableArea = area * density;
            return this;
        }

        /**
         * <p>Turning on/off sliding on touch event</p>
         *
         * @param enabled <b>(default - <b color="#EF6C00">true</b>)</b>
         */
        public Builder withGesturesEnabled(boolean enabled) {
            gesturesEnabled = enabled;
            return this;
        }

        /**
         * <p>Define behavior of soft input</p>
         *
         * @param hide <b>(default - <b color="#EF6C00">false</b>)</b>
         */
        public Builder withHideSoftInputWhenDisplayed(boolean hide) {
            hideKeyboard = hide;
            return this;
        }

        /**
         * <p>Define interpolator for animation (whenever you use {@link #hide()} or {@link #show()} methods)</p>
         *
         * @param interpolator <b>(default - <b color="#EF6C00">Decelerate interpolator</b>)</b>
         */
        public Builder withInterpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        /**
         * <p>Build the SlideUp and add behavior to view</p>
         */
        public SlideUp build() {
            return new SlideUp(this);
        }

    }

    /**
     * <p>Trying hide soft input from window</p>
     *
     * @see InputMethodManager#hideSoftInputFromWindow(IBinder, int)
     */
    public void hideSoftInput() {
        ((InputMethodManager) sliderView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(sliderView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private SlideUp(Builder builder) {
        startGravity = builder.startGravity;
        listener = builder.listener;
        sliderView = builder.sliderView;
        startState = builder.startState;
        density = builder.density;
        touchableArea = builder.touchableArea;
        autoSlideDuration = builder.autoSlideDuration;
        debug = builder.debug;
        isRTL = builder.isRTL;
        gesturesEnabled = builder.gesturesEnabled;
        hideKeyboard = builder.hideKeyboard;
        interpolator = builder.interpolator;
        init();
    }

    private void init() {
        sliderView.setOnTouchListener(this);
        createAnimation();
        sliderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewHeight = sliderView.getHeight();
                viewWidth = sliderView.getWidth();
                switch (startGravity) {
                    case TOP:
                        sliderView.setPivotY(viewHeight);
                        break;
                }
                updateToCurrentState();
                ViewTreeObserver observer = sliderView.getViewTreeObserver();
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    observer.removeGlobalOnLayoutListener(this);
                } else {
                    observer.removeOnGlobalLayoutListener(this);
                }
            }
        });
        updateToCurrentState();
    }

    private void updateToCurrentState() {
        switch (startState) {
            case HIDDEN:
                hideImmediately();
                break;
            case SHOWED:
                showImmediately();
                break;
        }
    }

    /**
     * <p>Returns the visibility status for this view.</p>
     *
     * @return true if view have status {@link View#VISIBLE}
     */
    public boolean isVisible() {
        return sliderView.getVisibility() == VISIBLE;
    }


    /**
     * <p>Returns running status of animation</p>
     *
     * @return true if animation is running
     */
    public boolean isAnimationRunning() {
        return valueAnimator != null && valueAnimator.isRunning();
    }

    /**
     * <p>Show view with animation</p>
     */
    public void show() {
        show(false);
    }

    /**
     * <p>Hide view with animation</p>
     */
    public void hide() {
        hide(false);
    }

    /**
     * <p>Hide view without animation</p>
     */
    public void hideImmediately() {
        hide(true);
    }

    /**
     * <p>Show view without animation</p>
     */
    public void showImmediately() {
        show(true);
    }


    /**
     * <p>Saving current parameters of SlideUp</p>
     *
     * @return {@link Bundle} with saved parameters of SlideUp
     */
    public Bundle onSaveInstanceState(@Nullable Bundle savedState) {
        if (savedState == null) savedState = Bundle.EMPTY;
        savedState.putInt(KEY_START_GRAVITY, startGravity);
        savedState.putBoolean(KEY_DEBUG, debug);
        savedState.putFloat(KEY_TOUCHABLE_AREA, touchableArea / density);
        savedState.putParcelable(KEY_STATE, currentState);
        savedState.putInt(KEY_AUTO_SLIDE_DURATION, autoSlideDuration);
        savedState.putBoolean(KEY_HIDE_SOFT_INPUT, hideKeyboard);
        return savedState;
    }

    private void endAnimation() {
        if (valueAnimator.getValues() != null)
            valueAnimator.end();
    }

    private void hide(boolean immediately) {
        if (isAnimationRunning()) return;
        if (immediately) {
            if (sliderView.getHeight() > 0) {
                sliderView.setTranslationY(-viewHeight);
                sliderView.setVisibility(GONE);
                notifyVisibilityChanged(GONE);
            } else {
                startState = HIDDEN;
            }
        } else {
            this.slideAnimationTo = sliderView.getHeight();
            valueAnimator.setFloatValues(sliderView.getTranslationY(), slideAnimationTo);
            valueAnimator.start();
        }
    }

    private void show(boolean immediately) {
        if (isAnimationRunning()) return;
        if (immediately) {
            if (sliderView.getHeight() > 0) {
                sliderView.setTranslationY(0);
                sliderView.setVisibility(VISIBLE);
                notifyVisibilityChanged(VISIBLE);
            } else {
                startState = SHOWED;
            }
        } else {
            this.slideAnimationTo = 0;
            valueAnimator.setFloatValues(-sliderView.getTranslationY(), slideAnimationTo);
            valueAnimator.start();
        }
    }

    private void createAnimation() {
        valueAnimator = ValueAnimator.ofFloat();
        valueAnimator.setDuration(autoSlideDuration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);
    }

    @Override
    public final boolean onTouch(View v, MotionEvent event) {
        if (!gesturesEnabled) return false;
        if (isAnimationRunning()) return false;
        return onTouchUpToDown(event);
    }

    private boolean onTouchUpToDown(MotionEvent event) {
        float touchedArea = event.getRawY() - sliderView.getBottom();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                viewHeight = sliderView.getHeight();
                startPositionY = event.getRawY();
                viewStartPositionY = sliderView.getTranslationY();
                maxSlidePosition = viewHeight;
                if (touchableArea < touchedArea) {
                    canSlide = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float difference = event.getRawY() - startPositionY;
                float moveTo = viewStartPositionY + difference;
                float percents = moveTo * 100 / -sliderView.getHeight();

                if (moveTo < 0 && canSlide) {
                    notifyPercentChanged(percents);
                    sliderView.setTranslationY(moveTo);
                }
                if (event.getRawY() < maxSlidePosition) {
                    maxSlidePosition = event.getRawY();
                }
                break;
            case MotionEvent.ACTION_UP:
                float slideAnimationFrom = -sliderView.getTranslationY();
                if (slideAnimationFrom == viewStartPositionY) return false;
                boolean mustShow = maxSlidePosition < event.getRawY();
                boolean scrollableAreaConsumed = sliderView.getTranslationY() < -sliderView.getHeight() / 5;

                if (scrollableAreaConsumed && !mustShow) {
                    slideAnimationTo = sliderView.getHeight() + sliderView.getTop();
                } else {
                    slideAnimationTo = 0;
                }
                valueAnimator.setFloatValues(slideAnimationFrom, slideAnimationTo);
                valueAnimator.start();
                canSlide = true;
                maxSlidePosition = 0;
                break;
        }
        return true;
    }

    @Override
    public final void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        onAnimationUpdateUpToDown(value);
    }

    private void onAnimationUpdateUpToDown(float value) {
        sliderView.setTranslationY(-value);
        float visibleDistance = sliderView.getTop() - sliderView.getY();
        float percents = (visibleDistance) * 100 / viewHeight;
        notifyPercentChanged(percents);
    }

    private void notifyPercentChanged(float percent) {
        percent = percent > 100 ? 100 : percent;
        percent = percent < 0 ? 0 : percent;
        if (slideAnimationTo == 0 && hideKeyboard)
            hideSoftInput();
        listener.onSlide(percent);
    }

    private void notifyVisibilityChanged(int visibility) {
        listener.onVisibilityChanged(visibility);
        switch (visibility) {
            case VISIBLE:
                currentState = SHOWED;
                break;
            case GONE:
                currentState = HIDDEN;
                break;
        }
    }


    public static int toBottom = 1;
    public static int toUp = 2;

    private void notifyStartAnimate(int direction) {
        listener.onAnimatorStarted(direction);
    }

    @Override
    public final void onAnimationStart(Animator animator) {
        if (sliderView.getVisibility() != VISIBLE) {
            sliderView.setVisibility(VISIBLE);
            notifyVisibilityChanged(VISIBLE);
            notifyStartAnimate(toBottom);
        } else {
            if (slideAnimationTo != 0)
                notifyStartAnimate(toUp);
        }
    }

    @Override
    public final void onAnimationEnd(Animator animator) {
        if (slideAnimationTo != 0) {
            if (sliderView.getVisibility() != GONE) {
                sliderView.setVisibility(GONE);
                notifyVisibilityChanged(GONE);
            }
        }
    }

    @Override
    public final void onAnimationCancel(Animator animator) {
    }

    @Override
    public final void onAnimationRepeat(Animator animator) {
    }
}
