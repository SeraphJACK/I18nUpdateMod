package org.cfpa.i18nupdatemod;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class I18nUtils {
    public I18nUtils() {
        throw new UnsupportedOperationException("no instance");
    }

    /**
     * 将语言换成中文
     */
    public static void setupLang() {
        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gameSettings = mc.gameSettings;
        // 强行修改为简体中文
        if (!gameSettings.language.equals("zh_cn")) {
            mc.getLanguageManager().currentLanguage = "zh_cn";
            gameSettings.language = "zh_cn";
        }
    }

    /**
     * 检测系统语言
     *
     * @return 是否为简体中文语言
     */
    public static boolean isChinese() {
        return System.getProperty("user.language").equals("zh");
    }

    /**
     * 依据等号切分字符串，将 list 处理成 hashMap
     *
     * @param listIn 想要处理的字符串 list
     * @return 处理好的 HashMap
     */
    public static HashMap<String, String> listToMap(List<String> listIn) {
        HashMap<String, String> mapOut = new HashMap<>();

        // 抄袭原版加载方式
        Splitter I18N_SPLITTER = Splitter.on('=').limit(2);

        // 遍历拆分
        for (String s : listIn) {
            if (!s.isEmpty() && s.charAt(0) != '#') {
                String[] splitString = Iterables.toArray(I18N_SPLITTER.split(s), String.class);

                if (splitString != null && splitString.length == 2) {
                    String s1 = splitString[0];
                    String s2 = splitString[1];
                    mapOut.put(s1, s2);
                }
            }
        }
        return mapOut;
    }

    /**
     * 从文件中获取 Token
     *
     * @return 得到的 Token
     */
    @Nullable
    public static String readToken() {
        File tokenFile = new File(Minecraft.getMinecraft().mcDataDir.toString() + File.separator + "config" + File.separator + "TOKEN.txt");
        try {
            List<String> token = FileUtils.readLines(tokenFile, "UTF-8");
            if (token.size() != 0) {
                return token.get(0);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}

