package org.pigliu.rediscase.dto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 查询参数
 *
 * @author liufuqiang
 * @since 2023/8/3
 */
@Data
public class QueryParams {
	private String date;
	private String tableName;
	private String orderId;
	private String username;
	private String bookId;
	private String bookName;
	private String chapterId;
	private String chapterName;
	private String origin;
	private String type;

	private String startTime;
	private String endTime;

	private Integer pageNum;
	private Integer pageSize;

	public static void main(String[] args) {
		List<String> list = Lists.newArrayList();
		String str = "{\"device\":\"2E6349AD-B74E-4519-AF73-65B0AA33BDA3\",\"key\":\"bkn-ios-jlsp-task-test\"," +
				"\"token\":\"a0214f53d8172683946b90dbbdd16700\",\"username\":\"C141728\"," +
				"\"timestamp\":\"1691575721644\"}";
		String signStr = "SzYaZRti1m2195k/NE7QdeNL6RqajVVK8x/+nDRV3ZBgQUHg+wk+BNCtWtYFFqztO99VDfe1ntMT5P3PQ" +
				"/xSFCaM7eJxz5Z107PtLBnbbU88wq2SISy00HIMxd34KDx0nTwELRwKEV42XFgBYyyAW1P+8t1978BTIVs87iN+2UI=";
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALli2crFpcf2" +
				"/XMxmwU5NbvMMHtECpYOvIozfV8iPbOa7J0NyAD2HCszxd3LcWQqtNZLhieudF8/4KV1e5x9BditTM+L8oN6QI/wJaJh+Qg6Z8NnInPoTL6Cjgh9lzGsykjQRdGNNsl2uvNbh9ETMdKhGMKb4/6KorlntxzIiv7NAgMBAAECgYAVfwYYilsLCROB+jhKvoPByy8699yPYlWP4+EPeTG05Au4OVsd62vAQbBZo8e58O7Ho3nCm6rASbCjlvAWqPOWpewyYBYMocLv7kjhoq35vVAat25xVthugYMI08QJLBqisqsVGcyBxdz8B7fMWw3Wg5Wz35r4icNsMWChiDGJ2QJBAOz9/2mEFGhdBU01O65CeQDY2gB6NZ1INn4Uc9c/VPyA/H8Z0AvQnEwvjeLm+Waiilxx38f+vw++aenv4tzfv5UCQQDIQUK2jtnMYXWGFWa1V93VWfzUPu1Oki8Z4/IwoHGmcpa/dJfsn3fUn8w6a+8IbXDdP8dApOQ6vmE8Cv3lMtRZAkBnI9vGpi7jhKhMA8DnhBJ5ZebqkkVHIChnEqufn/ZytggtoKREoBOFrKSJfZBJNLAWr5B+z8WDKpGZuEb5bX9JAkEAqMtheVHVwdGQZ1HYvhhvMuvkEd9s1eKgcxlRRq2mvl8uM/kKKfiM4BtF4SSjWh4tmUCuKg4TFnTsrtixBCWhGQJAQnOTVm601NQf5AwrDPYr6h20Tgzd/FSoL4tLTBvkE5WfiowtJElYLVWUqFn3Q3LnbxX3nF/NXRjFQRyYtw5MfA==";
		String publicKey =
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5YtnKxaXH9v1zMZsFOTW7zDB7RAqWDryKM31fIj2zmuydDcgA9hwrM8Xdy3FkKrTWS4YnrnRfP+CldXucfQXYrUzPi/KDekCP8CWiYfkIOmfDZyJz6Ey+go4IfZcxrMpI0EXRjTbJdrrzW4fREzHSoRjCm+P+iqK5Z7ccyIr+zQIDAQAB";
		Map<String, String> map = JSONUtil.toBean(str, new TypeReference<Map<String, String>>() {
		}, false);
		map.entrySet().stream().sorted(Map.Entry.comparingByKey())
					.forEachOrdered(d -> list.add(d.getKey() + "=" + d.getValue()));
		String paramsStr = String.join("&", list);
		Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA, privateKey, publicKey);
		boolean result = sign.verify(paramsStr.getBytes(StandardCharsets.UTF_8), Base64.decode(signStr));
		System.out.println(result);

		byte[] sign1 = sign.sign(paramsStr, Charset.forName("UTF-8"));
		System.out.println(Base64.encode(sign1));

		boolean result2 = sign.verify(paramsStr.getBytes(StandardCharsets.UTF_8), sign1);
		System.out.println(result2);
	}
}
