package com.lis.trainee.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

/**
 * ClassName NioPathFile
 *
 * @author Lis on 2021/12/4
 **/
public class NioPathFile {
    public static void main(String[] args) throws IOException {
        // 1. 创建一个path
        // 绝对路径
        Path absPath = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java.txt");
        // 相对路径
        Path relPath = Paths.get("data/bcl-java.txt");
        Path filePath = FileSystems.getDefault().getPath("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java.txt");

        // File与Path互换
        File file = new File("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java.txt");
        Path p1 = file.toPath();
        System.out.println("path to file: " + p1.toFile());
        System.out.println("path to URI: " + file.toURI());

        // 获取path相关的信息
        Path pathInfo = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java.txt");
        System.out.println("文件名：" + pathInfo.getFileName());
        System.out.println("名称元素的数量：" + pathInfo.getNameCount());
        System.out.println("父路径：" + pathInfo.getParent());
        System.out.println("根路径：" + pathInfo.getRoot());
        System.out.println("是否是绝对路径：" + pathInfo.isAbsolute());
        //startsWith()方法的参数既可以是字符串也可以是Path对象
        System.out.println("是否是以为给定的路径D:开始：" + pathInfo.startsWith("E:\\"));
        System.out.println("该路径的字符串形式：" + pathInfo.toString());

        // 移除冗余项
        Path currentDir = Paths.get(".");
        System.out.println(currentDir.toAbsolutePath());
        Path currentDir2 = Paths.get(".\\WebClient.java");
        System.out.println("原始路径格式：" + currentDir2.toAbsolutePath());
        System.out.println("执行normalize（）方法之后：" + currentDir2.toAbsolutePath().normalize());
//        System.out.println("执行toRealPath()方法之后：" + currentDir2.toRealPath());
        //..表示父目录或者说是上一级目录：
        Path currentDir3 = Paths.get("..");
        System.out.println("原始路径格式：" + currentDir3.toAbsolutePath());
        System.out.println("执行normalize（）方法之后：" + currentDir3.toAbsolutePath().normalize());
        System.out.println("执行toRealPath()方法之后：" + currentDir3.toRealPath());

        // 2.Files类
        // 检查给定的Path在文件系统中是否存在
        Path path = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java.txt");
        boolean pahtExists = Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        System.out.println(pahtExists);
        // 创建文件、文件夹
        Path target = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java1.txt");

        try {
            if (!Files.exists(target)) {
                Files.createFile(target);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建文件夹
        // 1.通过 Files.createDirectory() 只是创建目录，如果它的上级目录不存在就会报错
        // 2.通过 Files.createDirectories() 首先创建所有不存在的父目录来创建目录
        Path target1 = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java1.txt");
        try {
            Path newDir = Files.createDirectories(target1);
        } catch (FileAlreadyExistsException e) {
            // 文件已经存在
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除文件或目录
        Path target2 = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java1.txt");
        try {
            Files.delete(target2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 把文件从一个地址复制到另一个位置
        Path sourcePath = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java.txt");
        Path destinationPath = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java1.txt");
        try {
            Files.copy(sourcePath, destinationPath);
        } catch (FileAlreadyExistsException e) {
            // 文件已经存在
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取文件属性
        Path path2 = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data\\bcl-java.txt");
        System.out.println(Files.getLastModifiedTime(path2));
        System.out.println(Files.size(path2));
        System.out.println(Files.isSymbolicLink(path2));
        System.out.println(Files.isDirectory(path2));
        System.out.println(Files.readAttributes(path2, "*"));

        // 遍历文件夹
        Path path3 = Paths.get("E:\\Project\\java_trainee\\src\\main\\resources\\data");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path3)) {
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 遍历整个文件目录
        Path startingDir = Paths.get("E:\\Project\\java_trainee\\src\\main\\java\\com\\lis\\trainee\\nio");
        List<Path> result = new LinkedList<Path>();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println("result.size()=" + result.size());

    }

    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
        private List<Path> result;

        public FindJavaVisitor(List<Path> result) {
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.toString().endsWith(".java")) {
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
