package org.pigliu.rediscase.service.client;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.PostRequest;
import com.dtflys.forest.annotation.Retry;
import com.dtflys.forest.http.ForestResponse;
import org.pigliu.rediscase.dto.R;
import org.pigliu.rediscase.dto.request.QueryResponse;
import org.pigliu.rediscase.dto.request.TaskResponse;
import org.pigliu.rediscase.interceptor.DrawRequestInterceptor;
import org.pigliu.rediscase.service.DrawService;

/**
 * 百度AI画图api
 * <a href="https://wenxin.baidu.com/wenxin/docs#Pl6llwf92">api</a>
 * @author liufuqiang
 */
@BaseRequest(baseURL = "${drawUrl}", interceptor = DrawRequestInterceptor.class)
@Retry(maxRetryInterval = "10", maxRetryCount = "2")
public interface AIDrawRequestClient {

    /**
     * 获取token
     * @param grantType 固定参数
     * @param clientId  ak
     * @param secret    sk
     * @return
     */
    @PostRequest(value = "/oauth/token")
    R<String> getAccessToken(@Body("grant_type") String grantType, @Body("client_id") String clientId, @Body("client_secret") String secret);

    /**
     * 创建生成图片任务
     * @param text        输入内容 "睡莲"
     * @param style       图片风格 "古风"
     * @param resolution  图片尺寸 "1024*1024"
     * @return
     */
    @PostRequest(value = "/rest/1.0/ernievilg/v1/txt2img")
    R<TaskResponse> createTask(@Body("text") String text, @Body("style") String style, @Body(
            "resolution") String resolution, String drawId);

    /**
     * 查询生成图片任务
     * @param taskId  任务ID
     * @return
     */
    @PostRequest(value = "/rest/1.0/ernievilg/v1/getImg")
    R<QueryResponse> queryTask(@Body("taskId") String taskId);
}
