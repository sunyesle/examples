package com.sunys.initializer;

import com.sunys.filter.ExampleFilter;
import com.sunys.listener.ExampleListener;
import com.sunys.servlet.ExampleServlet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.HandlesTypes;

import java.util.Set;

@HandlesTypes({ExampleWebInitializer.class})
public class ExampleServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("ExampleServletContainerInitializer onStartup");

        for (Class<?> handleClass : set) {
            System.out.println(handleClass.getName());
        }

        servletContext.addListener(ExampleListener.class);

        ServletRegistration.Dynamic servlet = servletContext.addServlet("exampleServlet", ExampleServlet.class);
        servlet.addMapping("/");

        FilterRegistration.Dynamic filter = servletContext.addFilter("exampleFilter", ExampleFilter.class);
        filter.addMappingForUrlPatterns(null, true, "/*");
    }
}
