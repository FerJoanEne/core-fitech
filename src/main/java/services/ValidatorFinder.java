package services;

import interfaces.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ValidatorFinder {

    private final Logger log = LogManager.getLogger("ValidatorFinder");

    public ValidatorFinder() {
    }

    public Set<Validator> findValidators(String path) throws FileNotFoundException {
        checkPath(path);
        Set<Validator> result = new HashSet<>();
        File[] files = getFiles(path);
        if (files != null) {
            for (File f : files) {
                if (f.getName().endsWith(".jar")) {
                    log.info("file encontrado: " + f.getName());
                    loadValidators(result, f);
                }
            }
        } else {
            log.error("ubicacion invalida");
            throw new IllegalArgumentException("ubicacion invalida");
        }
        log.info("Cantidad de clases instanciadas: " + result.size());
        return result;
    }

    private void loadValidators(Set<Validator> result, File jarFile) {
        try {
            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> entries = jar.entries();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
            List<Class<?>> classes = new ArrayList<>();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                    continue;
                }
                String className = entry.getName().substring(0, entry.getName().length() - 6);
                className = className.replace('/', '.');
                classes.add(classLoader.loadClass(className));
            }
            log.info("Lista de Clases Encontradas: size {} - clases {} ", classes.size(), classes);

            classes.forEach(possibleValidator -> {
                if (!Validator.class.isAssignableFrom(possibleValidator) || possibleValidator.isInterface()) {
                    log.warn("{} No asignable", possibleValidator.getName());
                } else {
                    log.info("{} Asignable", possibleValidator.getName());
                    try {
                        result.add((Validator) possibleValidator.newInstance());
                    } catch (InstantiationException e) {
                        log.error("InstantiationException: {}", e.getMessage());
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        log.error("IllegalAccessException: {}", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            log.error("Excepcion al instanciar las clases: {}", e.getMessage());
        }
    }

    private File[] getFiles(String path) {
        File[] files = new File[0];
        try {
            log.info("path del file: " + path);
            File file = new File(path);
            if (file.exists()) {
                files = file.listFiles();
                log.info("cantidad de archivos listados: {}", files != null ? files.length : 0);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("no se pudo leer lista de archivos");
        }
        return files;
    }

    private void checkPath(String path) throws FileNotFoundException {
        if (path == null || path.equals("")) {
            log.error("el path es invalido");
            throw new FileNotFoundException("el path es invalido");
        }
    }
}
