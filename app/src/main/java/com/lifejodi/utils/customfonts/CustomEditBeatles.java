package com.lifejodi.utils.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by parikshit on 23/10/17.
 */

public class CustomEditBeatles extends AppCompatEditText {
    public CustomEditBeatles(Context context) {
        super(context);
        init();
    }

    public CustomEditBeatles(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditBeatles(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/beatles.ttf");
        setTypeface(typeface);
    }
}
