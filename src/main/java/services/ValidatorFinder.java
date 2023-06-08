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

    private final String path;

    public ValidatorFinder(String path) {
        this.path = path;
    }

    public Set<Validator> findValidators() throws FileNotFoundException {
        Set<Validator> validators = new HashSet<>();
        File[] files = getFiles(path);
        for (File file : files) {
            if (file.getName().endsWith(".jar")) {
                log.info("File encontrado: " + file.getName());
                loadValidators(validators, file);
            }
        }
        log.info("Cantidad de clases instanciadas: " + validators.size());
        return validators;
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

    private File[] getFiles(String path) throws FileNotFoundException {
        if (path.isEmpty()) {
            throw new FileNotFoundException("Ubicacion inexistente");
        }
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            log.info("Cantidad de archivos listados: {}", files != null ? files.length : 0);
            return files != null ? files : new File[0];
        } else {
            throw new IllegalArgumentException("Ubicacion invalida");
        }
    }
}