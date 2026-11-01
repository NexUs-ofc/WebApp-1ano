package org.example.interdisciplinar.launcher;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null && !portEnv.isBlank()) ? Integer.parseInt(portEnv) : 8080;
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(".");
        File warFile = new File("app.war");
        if (!warFile.exists()) {
            throw new IllegalStateException("Arquivo app.war não encontrado no diretório de execução");
        }
        tomcat.addWebapp("", warFile.getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }
}
