package com.rakuten.assessment.component;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.core.view.ViewCompat;

import com.rakuten.assessment.R;
import com.rakuten.assessment.utils.Log;

public class QuickReturnLayout extends FrameLayout {
  private static final String TAG = Log.tag(QuickReturnLayout.class);
  private static final float ANIMATION_DURATION = 500;

  private View childView;
  private View headerView;
  private int headerHeight;
  //  private MarginLayoutParams childViewLayoutParams;
  private ValueAnimator headerAnimation;
  private OnClickListener onReturnClick;

  public QuickReturnLayout(Context context) {
    super(context);
    init(context, null);
  }

  public QuickReturnLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    TypedArray a = context.getTheme().obtainStyledAttributes(
      attrs,
      R.styleable.QuickReturnLayout,
      0, 0);

    try {
      if (a.hasValue(R.styleable.QuickReturnLayout_headerLayout)) {
        this.inflateHeaderView(a.getResourceId(R.styleable.QuickReturnLayout_headerLayout, 0));
      }
    } finally {
      a.recycle();
    }

    setNestedScrollingEnabled(true);
  }

  public void inflateHeaderView(@LayoutRes int res) {
    View.inflate(getContext(), res, this);
    headerView = getChildAt(0);
    headerView.findViewById(R.id.return_icon).setOnClickListener(v -> {
      if (QuickReturnLayout.this.onReturnClick != null) {
        QuickReturnLayout.this.onReturnClick.onClick(v);
      }
    });
  }

  @Override
  public void addView(View child, int index, ViewGroup.LayoutParams params) {
    if (getChildCount() > 1) {
      throw new IllegalStateException("QuickReturnLayout can host only one direct child");
    }

    childView = child;
    super.addView(child, index, params);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);

    if (headerView != null) {
      headerHeight = headerView.getMeasuredHeight();
    }

    View child = getChildAt(1);
    if (child != null) {
      child.setTranslationY(headerHeight);
    }
  }

  @Override
  public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//    Log.d(TAG, "onStartNestedScroll: %s, %s, %d",
//      child.getClass().getSimpleName(), target.getClass().getSimpleName(), nestedScrollAxes);
    cancelHeaderAnimation();
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  @Override
  public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//    Log.d(TAG, "onNestedPreScroll target: %s, dx: %d, dy: %d",
//      target.getClass().getSimpleName(), dx, dy);

    // scroll up:   dy > 0, getScrollY increase
    // scroll down: dy < 0, getScrollY decrease
    if (dy > 0 && getScrollY() < headerHeight) {
      int consume = Math.min(headerHeight - getScrollY(), dy);
      scrollBy(0, consume);
      consumed[1] = consume;
    } else if (dy < 0 && getScrollY() > 0) {
      int consume = Math.min(getScrollY(), -dy);
      scrollBy(0, -consume);
      consumed[1] = -consume;
    }
    super.onNestedPreScroll(target, dx, dy, consumed);
  }

  @Override
  public void onStopNestedScroll(View child) {
    super.onStopNestedScroll(child);
    finishHeaderAnimation();
  }

  private void finishHeaderAnimation() {
//    Log.d(TAG, "getScrollY: %d", getScrollY());
    cancelHeaderAnimation();

    float startValue = getScrollY();
    float endValue = (getScrollY() < headerHeight / 2) ? 0 : headerHeight;
    headerAnimation = ValueAnimator.ofFloat(startValue, endValue);

    long duration = (long) (Math.abs(endValue - startValue) / (float) headerHeight * ANIMATION_DURATION);
    headerAnimation.setDuration(duration);
    headerAnimation.addUpdateListener(animation -> scrollTo(0, (int) ((float) animation.getAnimatedValue())));
    headerAnimation.start();
  }

  private void cancelHeaderAnimation() {
    if (headerAnimation != null) {
      headerAnimation.cancel();
      headerAnimation = null;
    }
  }

  public void setOnReturnClick(OnClickListener onReturnClick) {
    this.onReturnClick = onReturnClick;
  }

  public void setTitle(@IdRes int textViewResId, @IdRes int strResId) {
    String title = getResources().getString(strResId);
    setTitle(textViewResId, title);
  }

  public void setTitle(@IdRes int textViewResId, String title) {
    if (headerView != null) {
      TextView view = findViewById(textViewResId);
      if (view != null) {
        view.setText(title);
      }
    }
  }
}
