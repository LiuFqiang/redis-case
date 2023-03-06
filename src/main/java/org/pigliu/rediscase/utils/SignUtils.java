package org.pigliu.rediscase.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bukeneng
 * @since 2022/2/4
 */
@Slf4j
public class SignUtils {
    public SignUtils () {
    }

    /**
     * 针对json字符串格式解析为map数据格式
     *
     * @param jsonString json字符串
     * @return
     */
    public static Map<String, String> signParseMap(String jsonString) {
        Map<String, String> parameterMap = new LinkedHashMap<>(16);
        JSONObject jsonObject = JSONUtil.parseObj(jsonString, false, true);
        Map<String, Object> map = JSONUtil.toBean(jsonObject, new TypeReference<LinkedHashMap<String, Object>>() {
        }.getType(), false);
        Map<String, String> finalParameterMap = parameterMap;
        map.forEach((key, value) -> {
            if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                finalParameterMap.put(key,
                        array.stream().map(String::valueOf).collect(Collectors.joining(StrUtil.COMMA)));
            } else {
                finalParameterMap.put(key, value + "");
            }
        });
        return finalParameterMap;
    }

    /**
     * 数据格式转换
     * <pre>k1=v1&k2=v2</pre>
     *
     * @param map
     * @return
     */
    public static String mapConvertString(Map<String, String> map) {
        if (CollUtil.isEmpty(map)) {
            return StrUtil.EMPTY;
        }
        // 排序字典并拼接，字典序 a=a&b=b
        List<String> paramsTest = new ArrayList<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> paramsTest.add(x.getKey() + "=" + x.getValue()));
        return String.join("&", paramsTest);
    }
}
