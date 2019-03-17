package application.model.viewmodel.user;

import application.model.viewmodel.common.LayoutHeaderVM;

public class ChangePasswordVM {
    private LayoutHeaderVM layoutHeaderVM;
    private  String OldPassword;
    private String newPassWord;
    private String comfirmPassWord;

    public LayoutHeaderVM getLayoutHeaderVM() {
        return layoutHeaderVM;
    }

    public void setLayoutHeaderVM(LayoutHeaderVM layoutHeaderVM) {
        this.layoutHeaderVM = layoutHeaderVM;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    public String getComfirmPassWord() {
        return comfirmPassWord;
    }

    public void setComfirmPassWord(String comfirmPassWord) {
        this.comfirmPassWord = comfirmPassWord;
    }
}
