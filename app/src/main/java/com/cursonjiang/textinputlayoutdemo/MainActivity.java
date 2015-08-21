package com.cursonjiang.textinputlayoutdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

    private Pattern mPattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("用户名");
        passwordWrapper.setHint("密码");

        Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String username = usernameWrapper.getEditText().getText().toString().trim();
                String password = passwordWrapper.getEditText().getText().toString().trim();
                if (!validateEmail(username)) {
                    usernameWrapper.setError("邮箱地址不正确");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("密码不能小于6");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
                }
            }
        });
    }

    private void doLogin() {
        Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
    }

    /**
     * 隐藏键盘
     */
    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱
     * @return true 正确 or false 错误
     */
    public boolean validateEmail(String email) {
        Matcher matcher = mPattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证密码
     *
     * @param password 密码
     * @return true 正确 or false 错误
     */
    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

}
