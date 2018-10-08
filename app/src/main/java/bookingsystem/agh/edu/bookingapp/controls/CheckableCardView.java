package bookingsystem.agh.edu.bookingapp.controls;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.Checkable;

public class CheckableCardView extends CardView implements Checkable {
    // Constructors and view initialization
    private boolean isChecked = false;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked,
    };

    public CheckableCardView(@NonNull Context context) {
        super(context);
    }

    public CheckableCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }
    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }
    @Override
    public boolean isChecked() {
        return isChecked;
    }
    @Override
    public void toggle() {
        setChecked(!this.isChecked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState =
                super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}