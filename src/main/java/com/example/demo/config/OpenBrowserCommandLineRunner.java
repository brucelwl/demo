package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@ConditionalOnWebApplication
@Component
public class OpenBrowserCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyConfig.class);

    @Autowired
    private ServerProperties serverProperties;

    @Override
    public void run(String... args) throws Exception {
        //匹配任意 path= 开头,但是后面不为空的字符串
        String path = Arrays.stream(args)
                .filter(arg -> arg.matches("^path=.*[^\\s]"))
                .findFirst().map(pathArg -> pathArg.split("=")[1])
                .orElse("/hello");

        Integer port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();

        port = port == null ? 8080 : port;
        contextPath = StringUtils.hasText(contextPath) ? contextPath : "";

        String openUrl = "http://localhost:" + port + contextPath + path;
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + openUrl);

        logger.info("启动浏览器Url是:" + openUrl);
    }
}
