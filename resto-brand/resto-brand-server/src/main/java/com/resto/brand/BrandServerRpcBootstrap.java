package com.resto.brand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class BrandServerRpcBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServerRpcBootstrap.class);

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext.xml" });
        context.start();
        System.in.read();
    }
}
