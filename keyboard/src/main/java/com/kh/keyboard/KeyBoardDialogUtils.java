package com.kh.keyboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class KeyBoardDialogUtils implements View.OnClickListener {
    protected View view;
    protected Dialog popWindow;
    protected Activity mContext;
    private EditText contentView;
    private List<String> contentList;
    private KhKeyboardView keyboardUtil;

    public KeyBoardDialogUtils(Activity mContext) {
        try {
            this.mContext = mContext;
            if (contentList == null) {
                contentList = new ArrayList<>();
            }

            if (popWindow == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.keyboard_key_board_popu, null);
//				view.getBackground().setAlpha(68);
                popWindow = new Dialog(mContext, R.style.keyboard_popupAnimation);
                view.findViewById(R.id.keyboard_finish).setOnClickListener(this);
                view.findViewById(R.id.keyboard_back_hide).setOnClickListener(this);
            }
            popWindow.setContentView(view);
            popWindow.setCanceledOnTouchOutside(true);
            Window mWindow = popWindow.getWindow();
            mWindow.setWindowAnimations(R.style.keyboard_popupAnimation);
            mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            mWindow.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
            mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            popWindow.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (contentView != null && contentView.isFocused()) {
                        contentView.clearFocus();
                    }
                }
            });
            initView();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        try {
            if (keyboardUtil == null)
                keyboardUtil = new KhKeyboardView(mContext, view);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public void hideSystemSofeKeyboard(EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) mContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);



    }



    public void show(final EditText editText) {
//        		popWindow.show(showParentView, Gravity.CENTER, 0, 0);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        hideSystemSofeKeyboard(editText);
        popWindow.show();
        keyboardUtil.showKeyboard(editText);
    }

    public void dismiss() {
        if (popWindow != null && popWindow.isShowing()) {
            popWindow.dismiss();

        }

    }

    @Override
    public void onClick(View v) {
        try {
            int i = v.getId();
            if (i == R.id.keyboard_finish) {
                keyboardUtil.hideKeyboard();
                dismiss();

            } else if (i == R.id.keyboard_back_hide) {
                keyboardUtil.hideKeyboard();
                dismiss();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}









