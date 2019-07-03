package cn.mordernpoem.command;

import cn.mordernpoem.bean.Poem;
import cn.mordernpoem.bean.Poet;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zhyyy 2019/6/18
 * @since 0.0.1
 **/
public class Count extends BaseCommand {
    private boolean poemC = true;
    private boolean all = false;
    private boolean title = false;
    private boolean content = false;
    private boolean detail = false;
    private String query;

    @Override
    public void help() {
        System.out.println("count\n" +
                "[-a]                    统计总数，此开关开启以下所有开关均无效，默认关闭\n" +
                "[-d]                    显示详细信息，默认关闭\n" +
                "[-s]                    显示简略信息，默认关闭。[s][d]同时开启，只有[s]有效\n" +
                "[[-[t][c]] WORD]        统计查询条件及开关：\n" +
                "    [t][c]              查询范围：[t]诗歌标题，[c]诗歌内容。如果不指定任何范围，则默认开启t。\n" +
                "    WORD                查询内容\n" +
                "" +
                "例子                    count -d -tc 月亮");
    }

    @Override
    boolean assertAndSave(ArgReader reader) {
        String s;
        while (reader.hasNext()) {
            s = reader.read();
            if (isArg(s)) {
                s = getArg(s);
                if ("s".equals(s)) {
                    poemC = false;
                } else if ("a".equals(s)) {
                    all = true;
                } else {
                    for (char c : s.toCharArray()) {
                        switch (c) {
                            case 't':
                                title = true;
                                break;
                            case 'c':
                                content = true;
                                break;
                            case 'd':
                                detail = true;
                                break;
                            default:
                                return err(c);
                        }
                    }
                }
            } else {
                query = s;
            }
        }
        if (query == null) {
            all = true;
        }
        if (!(title || content)) {
            title = true;
        }
        return true;
    }

    @Override
    void deal() {
        List<Poem> titleContains = new ArrayList<>();
        Map<Poem, List<String>> appear = new HashMap<>(16);

        if (!all) {
            Consumer<Poem> poemConsumer = p -> {
                if (title && p.getTitle().contains(query)) {
                    titleContains.add(p);
                }
                List<String> appearLines = new ArrayList<>();

                for (String s : p.getLines()) {
                    if (s.contains(query)) {
                        appearLines.add(s);
                    }
                }
                if (!appearLines.isEmpty()) {
                    appear.put(p, appearLines);
                }
            };

            iterate(null, poemConsumer);

            //统计诗歌
            if (poemC) {
                if (title) {
                    titleContains.stream().map(p -> p.getTitle() + "  by  " + p.getPoet()).forEach(System.out::println);
                    System.out.println("[" + query + "]" + "在标题中出现" + titleContains.size() + "次");
                }
                if (content) {
                    split();
                    if (detail) {
                        appear.keySet().stream().map(p -> "\n------\n" + p).forEach(System.out::println);
                    } else {
                        appear.entrySet().stream().map(e -> {
                            Poem p = e.getKey();
                            String lines = String.join("\n", e.getValue());
                            return "------\n" + p.getTitleAndPoet() + "\n" + lines;
                        }).forEach(System.out::println);
                    }
                    split();
                    System.out.println("[" + query + "]" +
                            "在" + appear.size() + "首诗文中" +
                            "出现" + appear.values().stream().map(List::size).reduce((a, b) -> a + b).orElse(0) + "次");
                }
            } else {
                // 统计诗人
                Map<Poet, List<String>> map = new HashMap<>(appear.size());
                Consumer<Poem> poem2Poet = p -> {
                    List<String> list = map.getOrDefault(p.getPoet(), new LinkedList<>());
                    list.add(p.getTitle());
                    map.put(p.getPoet(), list);
                };
                BiConsumer<Poet, List<String>> poetShow = (k, v) -> {
                    System.out.println(k.getName());
                    System.out.println(v.stream().map(p -> '[' + p + ']').collect(Collectors.joining(" ")));
                    split();
                };
                if (title) {
                    titleContains.forEach(poem2Poet);
                    split();
                    map.forEach(poetShow);
                    System.out.println("[" + query + "]在" + map.size() + "位诗人的标题中出现过");
                    split();
                }
                if (content) {
                    map.clear();
                    appear.keySet().forEach(poem2Poet);
                    split();
                    map.forEach(poetShow);
                    System.out.println("[" + query + "]在" + map.size() + "位诗人的诗文中出现过");
                }
            }
        } else {
            AtomicInteger mc = new AtomicInteger();
            AtomicInteger pc = new AtomicInteger();
            Consumer<Poem> poemConsumer = p -> mc.getAndIncrement();
            Consumer<Poet> poetConsumer = p -> pc.getAndIncrement();
            iterate(poetConsumer, poemConsumer);
            System.out.println("共收录" + pc.get() + "位诗人的" + mc.get() + "首诗歌");
        }
    }

    public static void main(String[] a) {
        new Count().help();
    }
}
