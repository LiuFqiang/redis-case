package org.pigliu.rediscase.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.FontUtil;
import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.dtflys.forest.http.ForestResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
//import org.pigliu.rediscase.config.AIDrawConfig;
import org.pigliu.rediscase.config.AIDrawConfig;
import org.pigliu.rediscase.dto.R;
import org.pigliu.rediscase.dto.request.QueryResponse;
import org.pigliu.rediscase.dto.request.TaskResponse;
import org.pigliu.rediscase.service.client.AIDrawRequestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class DrawService {

    private final StringRedisTemplate opmRedisTemplate;

    private final AIDrawRequestClient drawRequestClient;

    private final AIDrawConfig drawConfig;

    private final ResourceLoader resourceLoader;


    @Value("${oss.bookDomain}")
    private String opmDomain;

    public String getToken() {
        String key = "ai_token";
        String token = opmRedisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(token)) {
            R<String> result = drawRequestClient.getAccessToken("client_credentials", drawConfig.getApiKey(),
                    drawConfig.getSecretKey());
            if (result.getCode() == 0) {
                opmRedisTemplate.opsForValue().set(key, result.getData(), 24, TimeUnit.HOURS);
            }
            return result.getData();
        }
        return token;
    }

    public TaskResponse createTask() {
        try {
            R<TaskResponse> response = drawRequestClient.createTask("\uD83D\uDE02\uD83D\uDE02", "古风", "1024*1024",
                    "sss");
            if (response.getCode() == 0) {
                return response.getData();
            }else {
                log.warn("txt2img response code {}, msg {}", response.getCode(), response.getMsg());
            }
        } catch (Exception e) {
            log.error("txt2img request error", e);
        }
        return null;
    }

    public void queryTask() {
        R<QueryResponse> queryResponseR = drawRequestClient.queryTask("10344142");
        System.out.println(queryResponseR);
    }

    @SneakyThrows
    public boolean paddingImg(String pressUrl, String prompt, String userNick) {
        long start = System.currentTimeMillis();
        // 先缩放原图
        BufferedImage image = ImgUtil.read(URLUtil.url(pressUrl));
        Image scaleImage = ImgUtil.scale(image, CanvasStyle.imgWidth, CanvasStyle.imgHeight);
        // 读取模版背景图
        if (template == null) {
            ClassPathResource resource = new ClassPathResource("static/template.jpg");
            template = ImgUtil.read(resource.getInputStream());
        }

        draw(template, scaleImage, prompt, userNick, "");
        long end = System.currentTimeMillis();
//        byte[] bytes = ImgUtil.toBytes(template, "jpg");
        File file = new File("/Users/liufuqiang/Downloads/baiduImg/" + DateUtil.current() + ".jpg");
        ImgUtil.write(template, file);
        System.out.println(end - start);
        return true;
    }

    private static Font PROMPT_FONT;


    private static Font USER_NICK_FONT;

    private static BufferedImage template = null;

    private static final Color PROMPT_COLOR = Color.BLACK;

    private static final Color USER_NICK_COLOR = new Color(134, 156, 148);

    private static final Color SHARE_COLOR = new Color(171, 174, 169);

    private static final String PRE_NICK = "画家：";

    static {
        ClassPathResource resource = new ClassPathResource("static/MiSans-Demibold.ttf");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, resource.getInputStream());
            PROMPT_FONT = font.deriveFont(Font.PLAIN, 51);
        } catch (Exception e) {
            PROMPT_FONT = new Font(null, Font.PLAIN, 51);
            log.error("font1 error", e);
        }

        ClassPathResource resource2 = new ClassPathResource("static/MiSans-Normal.ttf");
        try {
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, resource2.getInputStream());
            USER_NICK_FONT = font2.deriveFont(Font.PLAIN, 42);
        } catch (Exception e) {
            log.error("font2 error", e);
            USER_NICK_FONT = new Font(null, Font.PLAIN, 42);
        }
    }

    private static void draw(BufferedImage targetImage, Image image, String prompt, String userNick, String bookName) {
        int marginTop;
        final Graphics2D g = targetImage.createGraphics();
        // 透明度
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1L));
        // 文字抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 画图片
        marginTop = CanvasStyle.marginTop;
        g.drawImage(image, CanvasStyle.marginLeft, marginTop, CanvasStyle.imgWidth, CanvasStyle.imgHeight, null);

        // 画文案
        g.setFont(PROMPT_FONT);
        g.setColor(PROMPT_COLOR);
        marginTop += CanvasStyle.imgHeight + CanvasStyle.promptMarginTop;
        Dimension dimension = drawWrapString(g, PROMPT_FONT, prompt, CanvasStyle.promptWidth, marginTop,
                0, CanvasStyle.promptHeight, 2);

        marginTop += dimension.height + CanvasStyle.textMarginTop;
        // 获取分享文案长宽
        FontMetrics metrics = g.getFontMetrics(USER_NICK_FONT);
        int shareWidth = metrics.stringWidth(SHARE_TEXT);

        // 画作者昵称
        g.setFont(USER_NICK_FONT);
        g.setColor(SHARE_COLOR);
        Dimension preDimension = drawWrapString(g, USER_NICK_FONT, PRE_NICK, 150, marginTop, 0,
                CanvasStyle.textHeight, 1);
        g.setColor(USER_NICK_COLOR);
        Dimension nickDimension = drawWrapString(g, USER_NICK_FONT, userNick,
                CanvasStyle.promptWidth - shareWidth - CanvasStyle.shareMarginLeft,
                marginTop, preDimension.width, CanvasStyle.textHeight, 1);

        // 画分享文案
        g.setColor(SHARE_COLOR);
        drawWrapString(g, USER_NICK_FONT, SHARE_TEXT, CanvasStyle.promptWidth, marginTop,
                preDimension.width + nickDimension.width + CanvasStyle.shareMarginLeft,
                CanvasStyle.textHeight, 1);

        // 画书名
        g.setColor(PROMPT_COLOR);
        marginTop += CanvasStyle.bookMarginTop + nickDimension.height;
        if (StrUtil.isNotBlank(bookName)) {
            int symbolWidth = metrics.stringWidth("《》");
            int bookWidth = metrics.stringWidth(bookName);
            String subBookName = bookName;
            while (bookWidth + symbolWidth > CanvasStyle.promptWidth) {
                subBookName = StrUtil.subPre(subBookName, subBookName.length() - 1);
                bookWidth = metrics.stringWidth(subBookName);
            }
            if (!StrUtil.equals(subBookName, bookName)) {
                subBookName = String.format("《%s》", StrUtil.maxLength(subBookName, subBookName.length() - 1));
            } else {
                subBookName = String.format("《%s》", subBookName);
            }
            drawWrapString(g, USER_NICK_FONT, subBookName, CanvasStyle.promptWidth, marginTop, 0,
                    CanvasStyle.textHeight, 1);
        }

        // 收笔
        g.dispose();
    }

    private static final String SHARE_TEXT = "邀您一起成为AI画家";


    private static class CanvasStyle {
        private static int imgWidth = 984, imgHeight = 984;
        private static int marginLeft = 48, marginRight = 48;
        private static int marginTop = 54;
        private static int promptMarginTop = 78;
        private static int textMarginTop = 24;
        private static int shareMarginLeft = 15;
        private static int promptWidth = 51 * 18;
        private static int promptHeight = 69;
        private static int textHeight = 48;
        private static int bookMarginTop = 76;
    }


    /**
     * 添加文字
     * <pre>
     *     在某个固定宽度的画板上添加文字，文字超过宽度限制自动换行
     *     其余内容如果超过最大行数，...显示
     * </pre>
     * @param g       画板
     * @param font    文字字体
     * @param text    文字
     * @param width   宽度限制
     * @param marginTop  距离顶部高度
     * @param indentWidth  首行缩进距离
     * @param lineHeight 行高
     * @param maxRow  最大行数
     * @return 返回文字所占用的高度，用于后续操作画板
     */
    private static Dimension drawWrapString(Graphics g, Font font, String text, int width, int marginTop,
                                            int indentWidth, int lineHeight, int maxRow) {
        String prompt = StrUtil.maxLength(text, 40);
        FontMetrics metrics = g.getFontMetrics(font);
        int length = metrics.stringWidth(prompt);
        int height = metrics.getAscent() - metrics.getLeading() - metrics.getDescent();
        int row = 1;
        int top = (lineHeight - height) / 2;
        int paddingTop = marginTop;
        int firstRowWidth = width - indentWidth;
        if (length > firstRowWidth) {
            int rowLen = 0;
            int lastIndex = 0;
            for (int i = 0; i < prompt.length(); i++) {
                rowLen += metrics.charWidth(prompt.charAt(i));
                int fontMarginHeight = row == 1 ? height + top : lineHeight + top;
                int rowWidth = row == 1 ? firstRowWidth : width;
                int marginLeft = row == 1 ?  indentWidth + CanvasStyle.marginLeft : CanvasStyle.marginLeft;
                if (rowLen >= rowWidth - 15) {
                    if (row == maxRow) {
                        String lastRowTxt = i == prompt.length() - 1 ?
                                prompt.substring(lastIndex, i + 1) : prompt.substring(lastIndex, i) + "...";
                        g.drawString(lastRowTxt, marginLeft, paddingTop += fontMarginHeight);
                        break;
                    }
                    rowLen = 0;
                    g.drawString(prompt.substring(lastIndex, i + 1), marginLeft, paddingTop += fontMarginHeight);
                    row++;
                    lastIndex = i + 1;
                }
                if (i == prompt.length() - 1) {
                    g.drawString(prompt.substring(lastIndex), marginLeft, paddingTop += fontMarginHeight);
                }
            }
        } else {
            int marginLeft = row == 1 ?  indentWidth + CanvasStyle.marginLeft : CanvasStyle.marginLeft;
            g.drawString(prompt, marginLeft, paddingTop += top + height);
        }
        int actualWidth = maxRow == 1 ? firstRowWidth : width;
        return new Dimension(length > actualWidth ? actualWidth : length,paddingTop + top - marginTop);
    }

    @SneakyThrows
    public void drawTest() {
        System.out.println(opmDomain);
        ClassPathResource resource = new ClassPathResource("static/template.jpg");
        BufferedImage template = ImgUtil.read(resource.getInputStream());
        Graphics2D g = template.createGraphics();

        Font font = new Font(null, Font.PLAIN, 120);
        g.setFont(font);
        g.setColor(Color.CYAN);
        FontMetrics metrics = g.getFontMetrics(font);
        metrics.getAscent();
        metrics.getDescent();
        metrics.getLeading();
        g.drawString("Sphinx", 0, metrics.getAscent());
        g.setColor(new Color(255, 89, 100));
        g.drawLine(0, metrics.getAscent(), template.getWidth(), metrics.getAscent());
        g.setColor(new Color(40, 178, 9));
        g.drawLine(0, metrics.getHeight(), template.getWidth(), metrics.getHeight());
        g.setColor(new Color(255, 100, 230));
        g.drawLine(0, metrics.getDescent(), template.getWidth(), metrics.getDescent());
        g.setColor(new Color(255, 189, 100));
        g.drawLine(0, metrics.getAscent() - metrics.getDescent(), template.getWidth(), metrics.getAscent() - metrics.getDescent());
        g.dispose();
        File file = new File("/Users/liufuqiang/Downloads/baiduImg/" + DateUtil.current() + "test.jpg");
        ImgUtil.write(template, file);
    }


    public static void main (String[] args) {
        String a = "hello";
        System.out.println(StrUtil.subPre(a, a.length() - 1));
    }

}
