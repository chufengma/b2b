package onefengma.demo.server.model.apibeans;

/**
 * Created by chufengma on 16/7/16.
 */
public class ForgetPasswordRequest extends BaseBean {
    public String mobile;
    public String newPassword;
    public String newPasswordConfirm;
    public int msgCode;
}
