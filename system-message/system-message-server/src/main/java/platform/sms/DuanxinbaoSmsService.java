package platform.sms;

import cn.hutool.core.map.MapUtil;
import constant.Constant;
import exception.RenException;
import platform.enums.PlatformEnum;
import platform.exception.ModuleErrorCode;
import platform.service.SysSmsService;
import utils.SpringContextUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 短信宝短信服务
 */

public class DuanxinbaoSmsService extends AbstractSmsService
{
    //短信宝固定参数，无需修改
    private final String httpUrl="http://api.smsbao.com/sms";

    public DuanxinbaoSmsService(SmsConfig config){
        this.config = config;
    }

    @Override
    public void sendSms(String mobile, LinkedHashMap<String, String> params) {
        this.sendSms(mobile, params, config.getDuanxinbaoSignName(), config.getDuanxinbaoTemplate());
    }

    @Override
    public void sendSms(String mobile, LinkedHashMap<String, String> params, String signName, String template) {

        //最后生成的字符串
        String duanxin="";

        //短信参数
        ArrayList<String> paramsList = new ArrayList<>();
        if(MapUtil.isNotEmpty(params)){
            for(String value : params.values()){
                paramsList.add(value);
            }
        }
        //生成字符串
        duanxin=config.getDuanxinbaoSignName()+
                String.format(config.getDuanxinbaoTemplate(),
                        paramsList.get(0),paramsList.get(1),
                        paramsList.get(2),paramsList.get(3));
        //拼接URL
        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(config.getDuanxinbaoName()).append("&");
        httpArg.append("p=").append(md5(config.getDuanxinbaoPassword())).append("&");
        httpArg.append("m=").append(mobile).append("&");
        httpArg.append("c=").append(encodeUrlString( duanxin, "UTF-8"));
        String result = request(httpUrl, httpArg.toString());
        System.out.println(result);
        int status= Constant.SUCCESS;
        if(!"0".equalsIgnoreCase(result)){
            status = Constant.FAIL;
        }
        //保存短信记录
        SysSmsService sysSmsService = SpringContextUtils.getBean(SysSmsService.class);
        sysSmsService.save(PlatformEnum.DUANXINBAO.value(), mobile, params, status);

        if(status == Constant.FAIL){
            throw new RenException(ModuleErrorCode.SEND_SMS_ERROR, "短信宝发送失败");
        }
    }

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    /**
     *
     * @param str
     * @param charset
     * @return
     */
    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }
}
