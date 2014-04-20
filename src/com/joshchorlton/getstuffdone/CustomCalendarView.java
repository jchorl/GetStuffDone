package com.joshchorlton.getstuffdone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.CalendarView;

public class CustomCalendarView extends CalendarView{

	public CustomCalendarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public CustomCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CustomCalendarView(Context context) {
		super(context);
	}
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            ViewParent p = getParent();
            if (p != null)
                p.requestDisallowInterceptTouchEvent(true);
        }

        return false;
    }
	

}
