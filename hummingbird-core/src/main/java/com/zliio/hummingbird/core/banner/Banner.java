package com.zliio.hummingbird.core.banner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 系统控制台-Banner
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class Banner {
    public static final String CUSTOM_BANNER_NAME = "banner.txt";
    public static final String DEFAULT_BANNER_WORDING = "\n" +
            "    __  __                          _             __    _          __\n" +
            "   / / / /_  ______ ___  ____ ___  (_)___  ____ _/ /_  (_)________/ /\n" +
            "  / /_/ / / / / __ `__ \\/ __ `__ \\/ / __ \\/ __ `/ __ \\/ / ___/ __  / \n" +
            " / __  / /_/ / / / / / / / / / / / / / / / /_/ / /_/ / / /  / /_/ /  \n" +
            "/_/ /_/\\__,_/_/ /_/ /_/_/ /_/ /_/_/_/ /_/\\__, /_.___/_/_/   \\__,_/   \n" +
            "                                        /____/                       \n" +
            "                                   Hummingbird 1.0-SNAPSHOT Power by ZliIO.com";

    public static void print() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(CUSTOM_BANNER_NAME);
        if (url != null) {
            try {
                Path path = Paths.get(url.toURI());
                Files.lines(path).forEach(System.out::println);
            } catch (URISyntaxException | IOException ignore) {
            }
        } else {
            System.out.println(DEFAULT_BANNER_WORDING);
        }
    }
}
