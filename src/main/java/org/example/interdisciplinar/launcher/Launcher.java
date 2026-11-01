package org.example.interdisciplinar.launcher;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null && !portEnv.isBlank()) ? Integer.parseInt(portEnv) : 8080;
        tomcat.setPort(port);

        File warFile = extractWarFromJar();
        tomcat.addWebapp("", warFile.getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }

    private static File extractWarFromJar() throws IOException {
        String warResource = "/WEB-INF/web.xml";
        try (InputStream is = Launcher.class.getResourceAsStream(warResource)) {
            if (is == null) {
                throw new IllegalStateException("WEB-INF/web.xml não encontrado no classpath");
            }
        }

        File tempDir = Files.createTempDirectory("discloud-war-").toFile();
        tempDir.deleteOnExit();

        try (InputStream is = Launcher.class.getResourceAsStream("/app.war")) {
            if (is == null) {
                throw new IllegalStateException("app.war não encontrado dentro do JAR");
            }
            File extractedWar = new File(tempDir, "app.war");
            Files.copy(is, extractedWar.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return extractedWar;
        }
    }
}
