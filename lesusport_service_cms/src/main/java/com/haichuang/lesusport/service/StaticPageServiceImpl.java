package com.haichuang.lesusport.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;

public class StaticPageServiceImpl implements StaticPageService, ServletContextAware {
    private Configuration configuration;
    private ServletContext servletContext;

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.configuration = freeMarkerConfigurer.getConfiguration();
    }

    @Override
    public void index(Map<String, Object> root, Long id) {
        Writer out = null;
        try {
            String path = getRealPath("html/product/" + id + ".html");
            File file = new File(path);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            Template template = configuration.getTemplate("product.ftl");
            template.process(root, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getRealPath(String path) {
        return servletContext.getRealPath(path);
    }
}
