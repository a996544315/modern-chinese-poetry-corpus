package cn.mordernpoem.command;

import cn.mordernpoem.bean.Poem;
import cn.mordernpoem.bean.Poet;
import cn.mordernpoem.util.FileHelper;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author zhyyy 2019/6/18
 * @since 0.0.1
 **/
public abstract class BaseCommand {

    private String err = "";

    /**
     * 展示帮助信息
     */
    public abstract void help();

    /**
     * 验证参数并进行保存
     *
     * @param reader 参数序列
     * @return true 参数有效，false 参数无效
     */
    abstract boolean assertAndSave(ArgReader reader);

    /**
     * 处理指令
     */
    abstract void deal();

    public void deal(String[] args) {
        ArgReader reader = new ArgReader(args);
        if (!assertAndSave(reader)) {
            System.out.println("未知指令" + args[reader.getPos() - 1] + ",err:" + err);
            help();
        } else {
            deal();
        }
    }

    boolean isArg(String s) {
        return s != null && s.startsWith("-");
    }

    String getArg(String s) {
        return s.substring(1);
    }

    private boolean err(String err) {
        this.err = err;
        return false;
    }

    boolean err(char c) {
        return err("非法字符" + c);
    }

    boolean err(String... c1) {
        return err(String.join("|", c1) + "只能出现一次");
    }

    /**
     * 遍历操作
     *
     * @param poetConsumer 诗人遍历
     * @param poemConsumer 诗歌遍历
     */
    void iterate(Consumer<Poet> poetConsumer, Consumer<Poem> poemConsumer) {
        iterate(poetConsumer, null, poemConsumer, null);
    }

    void iterate(Consumer<Poet> poetConsumer, Predicate<Poet> poetPredicate, Consumer<Poem> poemConsumer, Predicate<Poem> poemPredicate) {
        final Predicate<Poem> finalPredicate = (poemPredicate == null) ? a -> true : poemPredicate;
        if (poetPredicate == null) {
            poetPredicate = a -> true;
        }
        FileHelper fileHelper = new FileHelper();
        List<Poet> poets = fileHelper.getAll();
        poets.stream().filter(poetPredicate).forEach(p -> {
            List<Poem> poems = fileHelper.findByPoet(p);
            if (poetConsumer != null) {
                poetConsumer.accept(p);
            }
            if (poemConsumer != null) {
                poems.stream().filter(finalPredicate).forEach(poemConsumer);
            }
        });
    }

    void split() {
        System.out.println("-------------------------------------");
    }
}
