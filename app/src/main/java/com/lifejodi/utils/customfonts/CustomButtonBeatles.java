package com.lifejodi.utils.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by Ajay on 04-12-2017.
 */

public class CustomButtonBeatles extends AppCompatButton {
    public CustomButtonBeatles(Context context) {
        super(context);
        init();
    }

    public CustomButtonBeatles(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonBeatles(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/beatles.ttf");
        setTypeface(typeface);
    }
}
