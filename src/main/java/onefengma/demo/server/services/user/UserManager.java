package onefengma.demo.server.services.user;

import java.util.HashMap;
import java.util.Map;

import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.ValidateCode;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.UploadDemo;
import onefengma.demo.server.model.User;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.MsgCodeHelper;
import onefengma.demo.server.core.ValidateHelper;
import onefengma.demo.server.core.request.AuthHelper;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.SellerRequest;
import onefengma.demo.server.model.apibeans.login.ChangePassword;
import onefengma.demo.server.model.apibeans.login.Login;
import onefengma.demo.server.model.apibeans.login.Register;
import onefengma.demo.server.model.metaData.City;
import onefengma.demo.server.services.funcs.CityDataHelper;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class UserManager extends BaseManager {

    private UserDataHelper userDataHelper;
    private SellerDataHelper sellerDataHelper;

    @Override
    public void init() {
        initPages();
        /* 注册 */
        post("register", Register.class, (req, rep, register) -> {
            // 输入验证
            if (!ValidateHelper.isPasswordConfirmed(register.password, register.passwordConfirm)) {
                return error("俩次密码输入不一致");
            }
            if (!ValidateHelper.isPasswordRight(register.password)) {
                return error("密码长度为 6~16");
            }
            if (!VerifyUtils.isMobile(register.mobile)) {
                return error("手机号码输入不正确");
            }
            if(!ValidateHelper.isCodeValid(register.validateCode, req.session())) {
                return error("验证码不正确");
            }
            if (!MsgCodeHelper.isMsgCodeRight(req, register.msgCode, register.mobile)) {
                return error("短信验证码不正确");
            }

            // 是否是重复用户
            User user = getUserDataHelper().findUserByMobile(register.mobile);
            if (user != null) {
                return error("用户名已注册!");
            }

            getUserDataHelper().insertUser(register.generateUser());
            return success();
        });

        // 登陆
        post("login", Login.class, (request, response, loginBean) -> {
            User user = getUserDataHelper().findUserByMobile(loginBean.mobile);
            if (user == null) {
                return error("用户名不存在！");
            }
            if (StringUtils.equals(user.getPassword(), IdUtils.md5(loginBean.password))) {
                AuthHelper.setLoginSession(request, response, user);
                return success();
            } else {
                return error("密码错误！");
            }
        });

        // 用户列表
        get("userList", AuthSession.class, (request, response, requestBean) -> success(getUserDataHelper().getUserList()));

        // 商家信息
        post("fillSellerInfo", SellerRequest.class, ((request, response, requestBean) -> {
            User user = getUserDataHelper().findUserByUserId(requestBean.getUserId());
            Seller s = getSellerDataHelper().getSellerByUserId(requestBean.getUserId());
            if (user == null) {
                return errorAndClear(requestBean, "找不到该用户");
            }
            if (user.isSeller() || s != null) {
                return errorAndClear(requestBean, "用户已填写商家信息");
            }

            if (!VerifyUtils.isMobile(requestBean.cantactTel)) {
                return errorAndClear(requestBean, "手机号码输入不正确");
            }

            if (!(requestBean.regMoney > 0)) {
                return errorAndClear(requestBean, "注册资本填写有误");
            }

            City city = CityDataHelper.instance().getCityById(requestBean.cityId);
            if (city == null) {
                return errorAndClear(requestBean, "城市选择有误");
            }

            // 相关证件
            if (requestBean.isThreeInOne) {
                if(requestBean.allCer == null) {
                    return errorAndClear(requestBean, "需要填写三证合一");
                }
                requestBean.businessLic = null;
                requestBean.financeLic = null;
                requestBean.codeLic = null;
            } else {
                if (requestBean.businessLic == null) {
                    return errorAndClear(requestBean, "需要填写营业执照");
                }
                if (requestBean.codeLic == null) {
                    return errorAndClear(requestBean, "需要填写组织机构代码");
                }
                if (requestBean.financeLic == null) {
                    return errorAndClear(requestBean, "需要填写财务执照");
                }
                requestBean.allCer = null;
            }
            Seller seller = requestBean.generateSeller();
            getSellerDataHelper().insertSeller(seller);
            getUserDataHelper().setSeller(seller.userId, true);
            return success(seller);
        }));

        post("changePassword", ChangePassword.class, ((request, response, requestBean) -> {
            if (!ValidateHelper.isPasswordConfirmed(requestBean.newPassword, requestBean.newPasswordConfirm)) {
                return error("俩次密码输入不一致");
            }
            if (!ValidateHelper.isPasswordRight(requestBean.newPassword)) {
                return error("密码长度为 6~16");
            }
            User user = getUserDataHelper().findUserByUserId(requestBean.getUserId());
            // 输入验证
            if (!StringUtils.equals(user.getPassword(), IdUtils.md5(requestBean.oldPassword))) {
                return error("旧密码不正确");
            }
            getUserDataHelper().changeUserPassword(requestBean.getUserId(), IdUtils.md5(requestBean.newPassword));
            return success();
        }));

        // just for test
        multiPost("upload", UploadDemo.class, (request, response, requestBean) -> {
            return success(requestBean);
        });

    }


    private void initPages() {
        getPage("login", BaseBean.class, "login.html", (request, response, requestBean) -> {
            ValidateCode validateCode = ValidateCode.getDefaultValidateCode(request.session());
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", "Fengma");
            params.put("password", "123456");
            return params;
        });

        getPage("upload", BaseBean.class, "upload.html", (request, response, requestBean) -> {
            return null;
        });
    }


    private UserDataHelper getUserDataHelper() {
        if (userDataHelper == null) {
            userDataHelper = new UserDataHelper();
        }
        return userDataHelper;
    }

    private SellerDataHelper getSellerDataHelper() {
        if (sellerDataHelper == null) {
            sellerDataHelper = new SellerDataHelper();
        }
        return sellerDataHelper;
    }

    @Override
    public String getParentRoutePath() {
        return "member";
    }

}
