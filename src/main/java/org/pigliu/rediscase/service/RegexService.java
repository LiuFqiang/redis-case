package org.pigliu.rediscase.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.pigliu.rediscase.dto.R;

import java.util.List;
import java.util.regex.Pattern;

public class RegexService {



    /**
     * 匹配风格 #风格#
     */
    private static final String STYLE_REGEX = "#\\S+(#\\s|#)";
    /**
     * 匹配字符串首 "@用户名 "
     */
    private static final String AT_REGEX = "^(@|\\s+@)"  ;

    private static final Pattern AT_PATTERN = Pattern.compile(AT_REGEX);

    private static final Pattern STYLE_PATTERN = Pattern.compile(STYLE_REGEX);

    public static void main (String[] args) {
//        String content = "@不可能画画  #古风# 我饿了";
//        String styleContent = ReUtil.delFirst(AT_PATTERN + "不可能画画", content);
//        List<String> matchStyle = ReUtil.findAllGroup0(STYLE_PATTERN, styleContent);
//        if (CollUtil.isNotEmpty(matchStyle)) {
//            String style = matchStyle.get(0);
//            style = ReUtil.replaceAll(style, "(#)|(\\s)", StrUtil.EMPTY);
//            System.out.println(style);
//        }
//        String prompt = ReUtil.replaceFirst(STYLE_PATTERN, styleContent, "");
//        prompt = StrUtil.trimStart(prompt);
//        System.out.println(prompt);
//        System.out.println(ReUtil.isMatch( AT_PATTERN + "不可能画画" + ".+" , content));
//        System.out.println(prompt);

        String regex = "<book id=\\d{9} name=\\S*></book>";
        String content = "asdas<book id=100014531 name=医品宗师（按章）></book>接口和可敬可嘉<book id=100014531 name=医品宗师（按章）></book>";
        boolean match = ReUtil.isMatch(regex, content);

        String con = ReUtil.replaceAll(content, regex, parameter -> parameter.group(0)
                .replaceAll("<book id=\\d{9} name=|></book>", ""));
        System.out.println(con);
    }
}
