package com.kxmall.market.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

//import org.omg.CORBA.SystemException;

/**
 *
 * Description:
 * User: admin
 * Date: 2019/12/27
 * Time: 20:15
 */
@Configuration
public class CertConfig {

    private static final Logger logger = LoggerFactory.getLogger(CertConfig.class);

    @Bean
    public void load() {

        String defalutFilePath ="/1491258202_20200312_cert/apiclient_cert.p12";

        String path = "/application-dev1.properties";

        FileOutputStream out = null;
        Properties properties = new Properties();
        ClassPathResource classPathResource = null;
        try {
            classPathResource = new ClassPathResource(defalutFilePath);
            properties.load(CertConfig.class.getResourceAsStream(path));
            properties.setProperty("com.kxmall.market.wx.key-path", classPathResource.getPath());
            out = new FileOutputStream(path);
            properties.store(out, "");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.close();
            } catch(IOException e) {
                logger.error(e.toString());
            }
        }
    }

}
