package org.example;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) throws Exception {
        MyClass myClass = new MyClass();
        Class<?> clazz = myClass.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Repeat.class)) {
                Repeat repeat = method.getAnnotation(Repeat.class);
                for (int i = 0; i < repeat.value(); i++) {
                    method.setAccessible(true);
                    method.invoke(myClass, "Параметр");
                }
            }
        }

        String surname = "Янов";
        String name = "Илья";

        Path surnameDir = Paths.get(surname);
        Path nameFile = surnameDir.resolve(name);

        // Создание директории <surname>
        if (!Files.exists(surnameDir)) {
            Files.createDirectory(surnameDir);
        }

        // Создание файла <name> внутри директории <surname>
        if (!Files.exists(nameFile)) {
            Files.createFile(nameFile);
        }

        // Создание вложенных директорий dir1/, dir2/, dir3/
        Path dir1 = surnameDir.resolve("dir1");
        Path dir2 = surnameDir.resolve("dir2");
        Path dir3 = surnameDir.resolve("dir3");
        if (!Files.exists(dir1)) {
            Files.createDirectory(dir1);
        }
        if (!Files.exists(dir2)) {
            Files.createDirectory(dir2);
        }
        if (!Files.exists(dir3)) {
            Files.createDirectory(dir3);
        }

        // Копирование файла <name> во вложенные директории
        Path nameFileInDir1 = dir1.resolve(name);
        Path nameFileInDir2 = dir2.resolve(name);
        Path nameFileInDir3 = dir3.resolve(name);
        Files.copy(nameFile, nameFileInDir1, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(nameFile, nameFileInDir2, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(nameFile, nameFileInDir3, StandardCopyOption.REPLACE_EXISTING);

        // Создание файла file1 внутри директории dir1
        Path file1 = dir1.resolve("file1");
        if (!Files.exists(file1)) {
            Files.createFile(file1);
        }

        // Создание файла file2 внутри директории dir2
        Path file2 = dir2.resolve("file2");
        if (!Files.exists(file2)) {
            Files.createFile(file2);
        }

        // Рекурсивный обход директории <surname> и вывод названий всех файлов и директорий
        Files.walkFileTree(surnameDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.println("F: " + file.getFileName());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                System.out.println("D: " + dir.getFileName());
                return FileVisitResult.CONTINUE;
            }
        });

        // Удаление директории dir1 со всем ее содержимым
        Files.walkFileTree(dir1, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}