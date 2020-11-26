package cn.mycookies.file;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件处理工具
 *
 * @author liqiang
 * @date 2020-11-23 8:44 下午
 **/
public class FileProcessUtils {

    public static List<String> readFile(String path) {
        List<String> result = Lists.newArrayList();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = fileReader.readLine()) != null) {
                result.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String path = "/Users/liqiang/code-repository/study/liqiang-learning/utils/aaa.txt";
        AtomicLong longAdder = new AtomicLong();
        replaceLine(path, (String a) -> a + longAdder.addAndGet(1));
    }

    public static void replaceLine(String filePath, Function<String, String> replaceFunction) {
        try {
            if (Objects.isNull(replaceFunction)) {
                return;
            }
            Path path = Paths.get(filePath);
            Stream<String> lines = Files.lines(path);
            List<String> replacedLines = lines.map(replaceFunction).collect(Collectors.toList());
            Files.write(path, replacedLines);
            lines.close();
            System.out.println("Find and Replace done!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
