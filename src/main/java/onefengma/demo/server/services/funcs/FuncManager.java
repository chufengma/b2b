package onefengma.demo.server.services.funcs;

import java.io.File;
import java.io.FileInputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import onefengma.demo.common.FileHelper;
import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.rx.MetaDataFetcher;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.MsgCodeHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.core.ValidateHelper;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.BasePageBean;
import onefengma.demo.server.model.apibeans.WeixinShareBean;
import onefengma.demo.server.model.apibeans.codes.MsgCode;
import onefengma.demo.server.model.apibeans.codes.ValidateCodeBean;
import onefengma.demo.server.model.apibeans.meta.CityDescRequest;
import onefengma.demo.server.model.apibeans.others.ImageUploadRequest;
import onefengma.demo.server.model.apibeans.others.NewsDetailRequest;
import onefengma.demo.server.model.news.InnerNewsDetail;
import onefengma.demo.server.model.news.InnerNewsResponse;
import onefengma.demo.server.model.news.NewsDetail;
import onefengma.demo.server.services.funcs.RecruitDataManager.RecruitDetail;
import spark.utils.IOUtils;

/**
 * Created by chufengma on 16/5/26.
 */
public class FuncManager extends BaseManager {

    private static String lastTicket = "";
    private static long lastGetAccessTokenTime = 0l;

    @Override
    public void init() {
        get("weixin_access_token", WeixinShareBean.class, ((request, response, requestBean1) -> {
            String ticket;
            if (System.currentTimeMillis() - lastGetAccessTokenTime <= 1000 * 5 && !StringUtils.isEmpty(lastTicket)) {
                ticket = lastTicket;
            } else {
                lastGetAccessTokenTime = System.currentTimeMillis();

                OkHttpClient client = new OkHttpClient();
                Request weiRequest = new Request.Builder()
                        .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx6ed459563788605b&secret=7c139bcd819c30a8cd65269dfffebd02")
                        .build();
                Response weiResponse = client.newCall(weiRequest).execute();
                String lastAccessToken = weiResponse.body().string();
                JSONObject tokenJsonObject = JSON.parseObject(lastAccessToken);
                String token = tokenJsonObject.getString("access_token");


                Request tokenRequest = new Request.Builder()
                        .url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi")
                        .build();
                Response tokenResponse = client.newCall(tokenRequest).execute();
                JSONObject ticketJsonObject = JSON.parseObject(tokenResponse.body().string());
                ticket = ticketJsonObject.getString("ticket");
                lastTicket = ticket;
            }

            String noncestr = "D33WZgTPz0wzccna";
            String timestamp = System.currentTimeMillis() + "";
            String signatureBig = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + requestBean1.url;
            String signature = IdUtils.getSha1(signatureBig);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("nonceStr", noncestr);
            jsonObject.put("signature", signature);

            return success(jsonObject);
        }));

        //  获取验证码
        get("validateCode.png", BaseBean.class, ((request, response, requestBean1) -> {
            ValidateHelper.generateValidateCode(request, response);
            return "";
        }));

        // 验证验证码
        post("validateCode", ValidateCodeBean.class, ((request, response, requestBean) -> {
            if (ValidateHelper.isValidateCodeOutOfDate(request.session())) {
                return error("验证码过期");
            }
            if (ValidateHelper.isCodeValid(requestBean.code, request.session())) {
                return success("验证成功");
            } else {
                return error("验证失败");
            }
        }));

        get("quotations", BaseBean.class, ((request, response, requestBean) -> {
            return success(MetaDataFetcher.getQuotations());
        }));

        //  获取手机号码
        get("msgCode", MsgCode.MsgCodeGetRequest.class, ((request, response, requestBean) -> {
            if (!VerifyUtils.isMobile(requestBean.mobile)) {
                return error("请输入正确的手机号");
            }
            if (MsgCodeHelper.hasSendLateMinitue(request)) {
                return error("请求验证码太频繁，请稍后再试");
            }
            try {
                MsgCodeHelper.generateMsgCode(request, requestBean.mobile);
            } catch (Exception e) {
                return error("请求失败，请稍后再试");
            }
            return success();
        }));

        //  验证手机号码
        post("msgCode", MsgCode.MsgCodePostRequest.class, ((request1, response1, requestBean1) -> {
            boolean success = MsgCodeHelper.isMsgCodeRight(request1, requestBean1.code, requestBean1.mobile);
            if (success) {
                return success();
            } else {
                return error("验证失败");
            }
        }));

        // 上传的文件
        get(Config.getDownLoadFileRequestPath() + "*", BaseBean.class, ((request, response, requestBean) -> {
            if (request.splat().length == 0) {
                return null;
            }
            String fileName = request.splat()[0];
            File file = new File(Config.getDownLoadFilePath() + fileName);
            if (file.exists()) {
                response.type(FileHelper.getContentType(fileName));
                IOUtils.copy(new FileInputStream(file.getAbsoluteFile()), response.raw().getOutputStream());
            } else {
                return null;
            }
            return "";
        }));

        // 下载apk
        get(Config.getDownLoadFileRequestPathAll() + "*", BaseBean.class, ((request, response, requestBean) -> {
            if (request.splat().length == 0) {
                return null;
            }
            String fileName = request.splat()[0];
            File file = new File(Config.getDownLoadFileRequestPathAllFile() + fileName);
            if (file.exists()) {
                response.type(FileHelper.getContentType(fileName));
                IOUtils.copy(new FileInputStream(file.getAbsoluteFile()), response.raw().getOutputStream());
                DownloadDataHelper.instance().addAppDownloadTimes(fileName);
            } else {
                return null;
            }
            return "";
        }));

        // 获取城市列表
        get("cities", BaseBean.class, ((request, response, requestBean) -> {
            return success(Config.getCities());
        }));

        get("cityDesc", CityDescRequest.class, ((request, response, requestBean) -> {
            return success(CityDataHelper.instance().getCityDescById(requestBean.cityId));
        }));

        // 行业快报
        get("indexInnerNews", BaseBean.class, ((request, response, requestBean) -> {
            return success(NewsDataHelper.instance().getIndexInnerNews());
        }));

        // 行业新闻
        get("indexNews", BaseBean.class, ((request, response, requestBean) -> {
            return success(NewsDataHelper.instance().getIndexNews());
        }));

        // 首页招聘信息
        get("indexRecruit", BaseBean.class, ((request, response, requestBean) -> {
            return success(RecruitDataManager.instance().getIndexRecruits());
        }));

        // 首页他们想对不锈钢说
        get("theySay", BaseBean.class, ((request, response, requestBean) -> {
            return success(NewsDataHelper.instance().getTheySay());
        }));


        get("innerNews", BasePageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            InnerNewsResponse innerNewsResponse = NewsDataHelper.instance().getInnerNews(pageBuilder);
            return success(innerNewsResponse);
        }));

        get("innerNewsDetail", NewsDetailRequest.class, ((request, response, requestBean) -> {
            InnerNewsDetail innerNewsDetail = NewsDataHelper.instance().getInnerNewsDetail(requestBean.id);
            if (innerNewsDetail == null) {
                return error("没有找到该条新闻");
            }
            return success(innerNewsDetail);
        }));

        get("news", BasePageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            return success(NewsDataHelper.instance().getNews(pageBuilder));
        }));

        get("newsDetail", NewsDetailRequest.class, ((request, response, requestBean) -> {
            NewsDetail NewsDetail = NewsDataHelper.instance().getNewsDetail(requestBean.id);
            if (NewsDetail == null) {
                return error("没有找到该条新闻");
            }
            return success(NewsDetail);
        }));

        // 首页招聘信息
        get("recruits", BasePageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            return success(RecruitDataManager.instance().getRecruitBriefs(pageBuilder));
        }));

        get("recruitDetail", NewsDetailRequest.class, ((request, response, requestBean) -> {
            RecruitDetail recruitDetail = RecruitDataManager.instance().getRecruitDetail(requestBean.id);
            if (recruitDetail == null) {
                return error("没有找到该条新闻");
            }
            return success(recruitDetail);
        }));

        multiPost("imageUpload", ImageUploadRequest.class, ((request, response, requestBean) -> {
            return success(requestBean.image.getPath().replace('\\', '/').replace("./res/", "/"));
        }));

        get("test", NewsDetailRequest.class, ((request, response, requestBean) -> {
            return success("You Are Right");
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "";
    }
}
