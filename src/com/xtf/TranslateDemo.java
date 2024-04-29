package com.xtf;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xtf.utils.AuthV3Util;
import com.xtf.utils.HttpUtil;


import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易有道智云翻译服务api调用demo
 * api接口: https://openapi.youdao.com/api
 */
public class TranslateDemo {
    //1. 需要去有道网上去找自己应用对应的id和密钥
    private static final String APP_KEY = "136a304828588172";     // 您的应用ID
    private static final String APP_SECRET = "pdxBTvdAuYtAUTgA0flskF2h60VDbMZz";  // 您的应用密钥

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 添加请求参数
        Map<String, String[]> params = createRequestParams();
        // 添加鉴权相关参数:确认你当前确实是有道的用户
        AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            //json字符串,目标:把json字符串中 键为 translation的值拿出来(翻译的结果)
            String jsonStr = new String(result, StandardCharsets.UTF_8);
            //json的解析??????  fastjson:alibaba提供的一个解析json的工具
            JSONObject jsonObject = (JSONObject) JSON.parse(jsonStr);
            //translation对应的是一个数组
            JSONArray jsonArray = (JSONArray) jsonObject.get("translation");
            String translateResult = jsonArray.get(0).toString();
            System.out.println(translateResult);
            //System.out.println(new String(result, StandardCharsets.UTF_8));
        }
        System.exit(1);
    }
    //用来准备请求参数
    private static Map<String, String[]> createRequestParams() {
        /*
         * note: 将下列变量替换为需要请求的参数
         * 取值参考文档: https://ai.youdao.com/DOCSIRMA/html/%E8%87%AA%E7%84%B6%E8%AF%AD%E8%A8%80%E7%BF%BB%E8%AF%91/API%E6%96%87%E6%A1%A3/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1-API%E6%96%87%E6%A1%A3.html
         */
        //2. 代翻译的文本,源语种和目标语种
        String q = "this is my first translate plugin";//待翻译文本
        String from = "en";//源语言语种
        String to = "zh-CHS";//目标语言语种
        //String vocabId = "您的用户词表ID";

        return new HashMap<String, String[]>() {{
            put("q", new String[]{q});
            put("from", new String[]{from});
            put("to", new String[]{to});
            //put("vocabId", new String[]{vocabId});
        }};
    }
}
