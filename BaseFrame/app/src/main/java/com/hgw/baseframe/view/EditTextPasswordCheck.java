package com.hgw.baseframe.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：密码输入 EditText（带规则检验）
 * @author hgw
 * */
public class EditTextPasswordCheck extends AppCompatEditText {

    public EditTextPasswordCheck(Context context) {
        super(context);  
        init();  
    }  
  
    public EditTextPasswordCheck(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);  
        init();  
    }  
  
    public EditTextPasswordCheck(Context context, AttributeSet attrs) {
        super(context, attrs);  
        init();  
    }  
      
    private void init() {  
        addTextChangedListener(textWatcher);  
    }  
      
    /**
     * TextWatcher：接口，继承它要实现其三个方法，分别为Text改变之前、改变的过程中、改变之后各自发生的动作
     */
    TextWatcher textWatcher = new TextWatcher() {

        private CharSequence charSequence;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            charSequence = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            // 让TextView一直跟随EditText输入的内容同步显示
//        	setSelection(getText().length());
        }

        @Override
        public void afterTextChanged(Editable edt) {
            /**长度限制6-20位字符*/
            String temp = edt.toString();
            if(temp.length()>20){
                edt.delete(temp.length()-1, temp.length());
                return;
            }

            /**校验输入规则：由数字与大小写字母，符号组成，不支持空格、中文*/
        	int length = edt.length();
            // 输入的时候，只有一个光标，那么这两个值应该是相等的。。。
            editStart = getSelectionStart();
            editEnd = getSelectionEnd();
            setSelection(length);
            if(editStart!=0){
                if (isPasswordString(charSequence.toString().substring(editStart - 1, editEnd))) {
                	//不符合删除
                    edt.delete(editStart - 1, editEnd);
                }
            }
        }
    };

    /**
     * 密码输入规则（由数字与大小写字母，符号组成，不支持空格、中文）
     * @param str
     * @return true包含特殊字符  false不包含特殊字符
     */
    public boolean isPasswordString(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|[A-Za-z0-9]$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return !m.find();
    }
  
}  