package cn.mordernpoem.command;

import cn.mordernpoem.bean.Poem;
import cn.mordernpoem.bean.Poet;
import cn.mordernpoem.util.FileHelper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author zhyyy 2019/6/19
 * @since 0.0.1
 **/
public class Clean extends BaseCommand {
    private static final Map<String, String> VALID_STRING_MAP = new HashMap<>();
    private static final Node ROOT = new Node((char) 0);
    private boolean all = false;
    private boolean simple = false;
    /**
     * emptyLine,changed
     */
    private final boolean[] state = {false, false};
    private String poet;
    private List<String> words = new LinkedList<>();

    static {
        VALID_STRING_MAP.put("--", "——");
        VALID_STRING_MAP.put("?", "？");
        VALID_STRING_MAP.put("......", "……");
        VALID_STRING_MAP.put(".....", "……");
        VALID_STRING_MAP.put("....", "……");
        VALID_STRING_MAP.put("...", "…");
        VALID_STRING_MAP.put(";", "；");
        VALID_STRING_MAP.put("０", "0");
        VALID_STRING_MAP.put("１", "1");
        VALID_STRING_MAP.put("２", "2");
        VALID_STRING_MAP.put("３", "3");
        VALID_STRING_MAP.put("４", "4");
        VALID_STRING_MAP.put("５", "5");
        VALID_STRING_MAP.put("６", "6");
        VALID_STRING_MAP.put("７", "7");
        VALID_STRING_MAP.put("８", "8");
        VALID_STRING_MAP.put("９", "9");
        VALID_STRING_MAP.keySet().forEach(i -> ROOT.add(i.toCharArray(), 0));
    }


    @Override
    public void help() {
        System.out.println("clean\n" +
                "[-a]                    清理所有诗歌文件，此开关开启以下所有开关均无效，默认关闭。如果没有输入任何参数则打开\n" +
                "[-s]                    结果只显示简略信息，默认关闭\n" +
                "[-p POET_NAME]          指定诗人目录，不指定则全局搜索\n" +
                "[WORD1 WORD2 ...]       清理标题中包含该词的诗歌，不指定则全局清理\n" +
                "" +
                "例子                    clean -p 海子 九月");
    }

    @Override
    boolean assertAndSave(ArgReader reader) {
        String s;
        while (reader.hasNext()) {
            s = reader.read();
            if (isArg(s)) {
                s = getArg(s);
                if ("a".equals(s)) {
                    all = true;
                } else if ("p".equals(s)) {
                    if (!reader.hasNext()) {
                        return err("缺少诗人参数");
                    } else {
                        poet = reader.read();
                    }
                } else if ("s".equals(s)) {
                    simple = true;
                } else {
                    return err("未知参数" + s);
                }
            } else {
                words.add(s);
            }
            if (words.isEmpty() && poet == null) {
                return all = true;
            }
        }
        return true;
    }

    @Override
    void deal() {
        Map<String, List<String>> result = new HashMap<>(32);
        final FileHelper fileHelper = new FileHelper();
        final Consumer<Poem> poemConsumer = p -> {
            state[0] = false;
            state[1] = false;
            List<String> before = p.getLines();
            List<String> after = new LinkedList<>();
            before.stream().map(this::deal).forEach(i -> {
                if (i.length() == 0) {
                    state[0] = true;
                } else {
                    if (state[0]) {
                        after.add("");
                    }
                    after.add(i);
                    state[0] = false;
                }
            });
            if (state[1]) {
                String poetName = p.getPoet().getName();
                List<String> list = result.getOrDefault(poetName, new LinkedList<>());
                list.add('[' + p.getTitle() + ']');
                result.put(poetName, list);
            }
            p.setLines(after);
            fileHelper.write(p);
        };

        if (!simple) {
            final Predicate<Poem> poemPredicate = words.isEmpty() ? null : p -> words.stream().anyMatch(w -> p.getTitle().contains(w));
            final Predicate<Poet> poetPredicate = poet == null ? null : p -> p.getName().equals(poet);
            iterate(null, poetPredicate, poemConsumer, poemPredicate);
            AtomicInteger i = new AtomicInteger();
            result.forEach((k, v) -> {
                split();
                System.out.println(k);
                System.out.println(String.join(" ", v));
                i.addAndGet(v.size());
            });
            split();
        }
        int pNum = result.size();
        int fNum = result.values().stream().map(List::size).reduce(Integer::sum).orElse(0);
        System.out.println("共计处理" + pNum + "位诗人的" + fNum + "个诗歌文件");
    }

    private String deal(String src) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        boolean begin = false;
        boolean lastBlank = false;
        Node now = ROOT, last;
        for (char c : src.toCharArray()) {
            boolean nowBlank = isBlank(c);
            if (nowBlank) {
                if (begin && now != ROOT) {
                    if (now.endChar) {
                        sb.append(VALID_STRING_MAP.get(temp.toString()));
                    } else {
                        sb.append(temp.toString());
                    }
                    temp.setLength(0);
                    now = ROOT;
                }
                lastBlank = true;
                continue;
            }
            if (lastBlank && sb.length() > 0) {
                sb.append(' ');
            }
            lastBlank = false;
            begin = true;
            last = now;
            now = now.get(c);
            if (now == null) {
                if (temp.length() > 0) {
                    if (last.endChar) {
                        sb.append(VALID_STRING_MAP.get(temp.toString()));
                    } else {
                        sb.append(temp.toString());
                    }
                    temp.setLength(0);
                }
                now = ROOT.get(c);
                if (now == null) {
                    sb.append(c);
                    now = ROOT;
                } else {
                    temp.append(c);
                }
            } else {
                temp.append(c);
            }
        }
        if (now.endChar) {
            sb.append(VALID_STRING_MAP.get(temp.toString()));
        } else {
            sb.append(temp.toString());
        }
        String res = sb.toString();
        if (!res.equals(src)) {
            state[1] = true;
        }
        return res;
    }

    private boolean isBlank(char c) {
        return c == 0 || c == ' ' || c == '\r' || c == '\t';
    }

}

class Node {
    Node(char c) {
        this.c = c;
    }

    private char c;
    boolean endChar = false;
    private Map<Character, Node> nexts = new HashMap<>();

    void add(char[] chars, int index) {
        char c = chars[index];
        Node n = nexts.getOrDefault(c, new Node(chars[index++]));
        if (index < chars.length) {
            n.add(chars, index);
        } else {
            n.endChar = true;
        }
        nexts.put(c, n);
    }

    Node get(char c) {
        return nexts.get(c);
    }

    @Override
    public String toString() {
        return endChar ? "T" : "F" + c;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && ((Node) obj).c == c;
    }
}
