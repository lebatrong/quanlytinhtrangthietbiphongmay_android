package com.lbt.batro.tinhtrangthietbi.customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.lbt.batro.tinhtrangthietbi.R;

@SuppressLint("AppCompatCustomView")
public class EditTextCustom extends EditText {

    Drawable clear, noclear, drawable;
    boolean visible = false;


    public EditTextCustom(Context context) {
        super(context);
        khoitao();
    }

    public EditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao();
    }

    public EditTextCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditTextCustom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao();
    }

    public void khoitao(){
        clear = ContextCompat.getDrawable(getContext(), R.drawable.close32).mutate();
        //No clear trong
        noclear = ContextCompat.getDrawable(getContext(),android.R.drawable.screen_background_light_transparent).mutate();
        caidat();
    }

    private void caidat() {
        Drawable []drawables = getCompoundDrawables();
        drawable = visible ? clear : noclear;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= (getRight()-drawable.getBounds().width()))
            setText("");

        return super.onTouchEvent(event);

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        //CHECK VISIBLE X
        if(lengthAfter == 0 && start == 0)
        {
            visible = false;
            caidat();
        }else{
            visible = true;
            caidat();
        }

        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}
