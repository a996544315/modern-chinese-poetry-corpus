package cn.mordernpoem;

import cn.mordernpoem.command.BaseCommand;
import cn.mordernpoem.command.Clean;
import cn.mordernpoem.command.Find;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhy 20190618
 * @since 0.0.1
 */
public class Main {
    private static Map<String, Class<? extends BaseCommand>> COMMAND_MAP;

    static {
        COMMAND_MAP = new HashMap<>();
        COMMAND_MAP.put("find", Find.class);
        COMMAND_MAP.put("clean", Clean.class);
    }

    private static final String HELP = "全部指令：\n" +
            "clean        清洗文本\n" +
            "find         查找数目\n" +
            "count        统计与计算\n" +
            "help         获取帮助信息\n\n" +
            "使用help加具体指令获取指令的具体帮助信息";

    public static void main(String[] args) throws Exception {
        // 强制使用utf-8
        System.setProperty("file.encoding", "utf8");

        // 检查java版本
//        String version = System.getProperty("java.version");
//        int dotIndex = version.indexOf('.', 2);
//        String verNum = version.substring(2, dotIndex);
//        if (Integer.valueOf(verNum) < 8) {
//            System.out.println("Java版本过低，支持Java 8以上。当前" + version);
//            return;
//        }


        if (args.length == 0) {
            help();
            return;
        }

        Class<? extends BaseCommand> commandType;

        if (Objects.equals(args[0], "help")) {
            if (args.length > 1) {
                if ((commandType = COMMAND_MAP.get(args[1])) != null) {
                    newInstance(commandType).help();
                } else {
                    System.out.println("没有该指令：" + args[1]);
                }
            } else {
                help();
            }
            return;
        }

        commandType = COMMAND_MAP.get(args[0]);
        if (commandType == null) {
            help();
            return;
        }

        newInstance(commandType).deal(args);
    }

    private static BaseCommand newInstance(Class<? extends BaseCommand> clz) throws Exception {
        Constructor<? extends BaseCommand> constructor = clz.getConstructor();
        return constructor.newInstance();
    }

    private static void help() {
        System.out.println(HELP);
    }
}
