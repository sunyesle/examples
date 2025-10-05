package com.sunys.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

//@WebListener
public class ExampleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ExampleListener contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ExampleListener contextDestroyed");
    }
}
