package com.polaris.pay.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.polaris.pay.R;

/**
 * @Date 2021/2/25 12:51
 * @Author toPolaris
 * @Description 自定义键盘类
 */
public class KeyboardUtils {

    /**
     * 确定回调接口
     * */
    public interface OnEnsureListener {
        /**
         * onEnsure方法用于处理确定按键事件
         */
        void onEnsure();
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    private final KeyboardView keyboardView;
    private final EditText editText;
    private OnEnsureListener onEnsureListener;

    public KeyboardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        // 取消弹出系统键盘
        this.editText.setInputType(InputType.TYPE_NULL);
        Keyboard keyboard = new Keyboard(this.editText.getContext(), R.xml.key);
        this.keyboardView.setKeyboard(keyboard);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(true);
        this.keyboardView.setOnKeyboardActionListener(listener);
    }

    final KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable text = editText.getText();
            int start = editText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (text != null && text.length() > 0) {
                        if (start > 0) {
                            text.delete(start - 1, start);
                        } else if (start == 0) {
                            // 清除所有内容，避免处于修改状态时，start为0无法继续删除的情况
                            text.clear();
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    text.clear();
                    break;
                case Keyboard.KEYCODE_DONE:
                    onEnsureListener.onEnsure();
                    break;
                default:
                    if (start == 0) {
                        // 清除所有内容，避免处于修改状态时，start为0无法继续删除的情况
                        text.clear();
                    }
                    text.insert(start, String.valueOf((char) primaryCode));
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    /**
     * 显示软键盘
     */
    public void showKeyboard() {
        int visible = keyboardView.getVisibility();
        if (visible == View.INVISIBLE || visible == View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        int visible = keyboardView.getVisibility();
        if (visible == View.INVISIBLE || visible == View.VISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }
}
