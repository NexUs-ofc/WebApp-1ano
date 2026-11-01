package org.example.interdisciplinar.launcher;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null && !portEnv.isBlank()) ? Integer.parseInt(portEnv) : 8080;
        tomcat.setPort(port);

        File webappDir = extractJarToTempDir();
        tomcat.addWebapp("", webappDir.getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }

    private static File extractJarToTempDir() throws IOException {
        File tempDir = Files.createTempDirectory("discloud-webapp-").toFile();
        tempDir.deleteOnExit();

        String jarPath = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File jarFile = new File(java.net.URLDecoder.decode(jarPath, "UTF-8"));

        try (JarFile jar = new JarFile(jarFile)) {
            java.util.Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                File entryFile = new File(tempDir, entry.getName());
                entryFile.getParentFile().mkdirs();
                try (InputStream is = jar.getInputStream(entry)) {
                    Files.copy(is, entryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }

        return tempDir;
    }
}
