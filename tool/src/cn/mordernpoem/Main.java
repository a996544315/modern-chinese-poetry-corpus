package cn.mordernpoem;

import cn.mordernpoem.command.BaseCommand;
import cn.mordernpoem.command.Clean;
import cn.mordernpoem.command.Count;

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
        COMMAND_MAP.put("count", Count.class);
        COMMAND_MAP.put("clean", Clean.class);
    }

    private static final String HELP = "全部指令：\n" +
            "clean        清洗文本\n" +
            "count        统计数目\n" +
            "find         查找\n" +
            "help         获取帮助信息\n\n" +
            "使用help加具体指令获取指令的具体帮助信息";

    public static void main(String[] args) throws Exception {
        // 强制使用utf-8
        System.setProperty("file.encoding", "utf-8");

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
