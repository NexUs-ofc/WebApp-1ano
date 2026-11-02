package org.example.interdisciplinar.launcher;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null && !portEnv.isBlank()) ? Integer.parseInt(portEnv) : 8080;
        tomcat.setPort(port);

        File webappDir = extractWarToTempDir();
        tomcat.addWebapp("", webappDir.getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }

    private static File extractWarToTempDir() throws IOException {
        File tempDir = Files.createTempDirectory("discloud-webapp-").toFile();
        tempDir.deleteOnExit();

        String jarPath = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File jarFile = new File(java.net.URLDecoder.decode(jarPath, "UTF-8"));

        File appWar = extractAppWarFromJar(jarFile);

        try (ZipFile war = new ZipFile(appWar)) {
            Enumeration<? extends ZipEntry> entries = war.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryFile = new File(tempDir, entry.getName());
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                    continue;
                }
                entryFile.getParentFile().mkdirs();
                try (InputStream is = war.getInputStream(entry)) {
                    Files.copy(is, entryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }

        return tempDir;
    }

    private static File extractAppWarFromJar(File jarFile) throws IOException {
        File extractDir = Files.createTempDirectory("discloud-jar-").toFile();
        extractDir.deleteOnExit();

        File appWar = null;
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                if (!"app.war".equals(entry.getName())) {
                    continue;
                }
                appWar = new File(extractDir, entry.getName());
                appWar.getParentFile().mkdirs();
                try (InputStream is = jar.getInputStream(entry)) {
                    Files.copy(is, appWar.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }

        if (appWar == null || !appWar.exists()) {
            throw new IllegalStateException("app.war not found in fat jar");
        }

        return appWar;
    }
}
