package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.UserInfo;

import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.TextField;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;

/**
 * 注册登陆页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class RegisterAbilitySlice extends AbilitySlice {
    private static final String TAG = RegisterAbilitySlice.class.getSimpleName();
    private UserInfo userInfo = new UserInfo();;
    private Image imageRegisterUserPortrait = null; // 头像
    private TextField textFieldUsername = null; // 用户名
    private TextField textFieldPassword = null; // 密码
    private Button buttonRegister = null; // 注册

    @Override
    public void onStart(Intent intent) {
        LogUtil.info(TAG, "onStart");
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_register);

        // 初始化UI组件
        initUi();

        // 初始化事件监听
        initListener();
    }

    private void initUi() {
        imageRegisterUserPortrait = (Image)findComponentById(ResourceTable.Id_image_register_user_portrait);
        textFieldUsername = (TextField)findComponentById(ResourceTable.Id_textfield_username);
        textFieldPassword = (TextField)findComponentById(ResourceTable.Id_textfield_password);
        buttonRegister = (Button)findComponentById(ResourceTable.Id_button_login);
    }

    private void initListener() {
        imageRegisterUserPortrait.setClickedListener(component -> {
            presentForResult(new ImageSelectionAbilitySlice(), new Intent(), 0);

        });

        buttonRegister.setClickedListener(component -> {
            String username = textFieldUsername.getText();
            String password = textFieldPassword.getText();

            userInfo.setUsername(username);
            userInfo.setPassword(password);

            // 检查
            checkLogin(userInfo);
        });

    }

    private void checkLogin(UserInfo userInfo) {
        LogUtil.info(TAG, "checkLogin");
        if (userInfo.getPortraitPath() == null || userInfo.getPortraitPath().isEmpty()){
            new ToastDialog(getContext()).setText("请选择要发布的视频！").setAlignment(LayoutAlignment.CENTER).show();
        }

        if (userInfo.getUsername() != null && !userInfo.getUsername().isEmpty() && userInfo.getPassword() != null
            && !userInfo.getPassword().isEmpty()) {

            // 回到主页
            Intent intent = new Intent();
            intent.setParam(Constants.LOGIN_USERNAME, userInfo.getUsername());
            intent.setParam(Constants.IMAGE_SELECTION, userInfo.getPortraitPath());
            setResult(intent);

            // 销毁当前页面
            terminate();
        } else {
            new ToastDialog(getContext()).setText("账号、密码不能为空！").setAlignment(LayoutAlignment.CENTER).show();
        }

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        LogUtil.info(TAG, "onResult requestCode:" + requestCode + "; resultIntent:" + resultIntent);
        if (requestCode == 0) {
            String portrait = resultIntent.getStringParam(Constants.IMAGE_SELECTION);
            imageRegisterUserPortrait.setPixelMap(CommonUtil.getImageSource(this.getContext(), portrait));
            userInfo.setPortraitPath(portrait);
        } else {
            terminate();
        }
    }
}
