package com.example.temitour;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/**
 * This class creates a custom EditText that tells the soft keyboard to have no ENTER action.
 * This allows a multiline text input to actually be closed (provides the checkmark to close
 * the keyboard rather than the enter button)
 *
 * @author Gavin Vogt
 */
// TODO: delete the CustomEditText class now that we are using Google Forms for the tour survey
public class CustomEditText extends com.google.android.material.textfield.TextInputEditText {
    public CustomEditText(@NonNull @NotNull Context context) {
        super(context);
    }

    public CustomEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        // yoinked from https://stackoverflow.com/questions/5014219/multiline-edittext-with-done-softinput-action-label-on-2-3
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }
}
