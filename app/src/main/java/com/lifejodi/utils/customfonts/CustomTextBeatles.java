package com.lifejodi.utils.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Ajay on 22-04-2016.
 */
public class CustomTextBeatles extends AppCompatTextView {
    public CustomTextBeatles(Context context) {
        super(context);
        init();
    }

    public CustomTextBeatles(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextBeatles(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/beatles.ttf");
        setTypeface(typeface);
    }
}
