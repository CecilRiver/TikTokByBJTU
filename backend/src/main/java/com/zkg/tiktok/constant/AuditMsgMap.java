package com.zkg.tiktok.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 消息映射审核
 * @Version: 1.0
 */



public class AuditMsgMap {

    static Map<String,String> msgMap = new HashMap<>();


    static {
        msgMap.put("violence", "暴力内容（包括血腥、暴恐、警民冲突等）");
        msgMap.put("sensitive_politics", "涉政内容（包括国内外政要、涉政人物）");
        msgMap.put("pornography", "色情内容（包括性感图片、色情图片等）");
        msgMap.put("public_security", "公共安全威胁（包括枪支、刀具、恐怖分子等）");
        msgMap.put("negative_public_figures", "负面公众人物（包括落马官员、劣迹艺人等）");
        msgMap.put("spam_and_abuse", "垃圾信息与辱骂（包括广告、灌水、无意义内容等）");

    }

    public static String getInfo(String key){
        return msgMap.get(key);
    }
}

