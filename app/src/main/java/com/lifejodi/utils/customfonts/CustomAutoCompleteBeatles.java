package com.lifejodi.utils.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

/**
 * Created by Ajay on 03-11-2017.
 */

public class CustomAutoCompleteBeatles extends AppCompatAutoCompleteTextView {
    public CustomAutoCompleteBeatles(Context context) {
        super(context);
        init();
    }

    public CustomAutoCompleteBeatles(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomAutoCompleteBeatles(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/beatles.ttf");
        setTypeface(typeface);
    }
}