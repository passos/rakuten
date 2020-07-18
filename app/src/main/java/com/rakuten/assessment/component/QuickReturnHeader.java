package com.rakuten.assessment.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

public class QuickReturnHeader extends NestedScrollView {
  private int offset;


  public QuickReturnHeader(Context context) {
    super(context);
    init();
  }

  public QuickReturnHeader(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    offset = 0;
    setNestedScrollingEnabled(true);
  }

  @Override
  public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }


}
