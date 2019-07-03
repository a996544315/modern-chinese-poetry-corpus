package cn.mordernpoem.bean;

import cn.mordernpoem.util.FileHelper;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author zhyyy 2019/6/19
 * @since 0.0.1
 **/
public class Poem {
    private Poet poet;
    private String title;
    private String date;
    private List<String> lines = new LinkedList<>();

    public String getTitle() {
        return title;
    }

    public Poem setTitle(String title) {
        if (title.startsWith("\"")) {
            title = title.substring(1);
        }
        if (title.endsWith("\"")) {
            title = title.substring(0, title.length() - 1);
        }
        this.title = title;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Poem setDate(String date) {
        this.date = date;
        return this;
    }

    public List<String> getLines() {
        return lines;
    }

    public Poem setLines(List<String> lines) {
        this.lines = lines;
        return this;
    }

    public Poet getPoet() {
        return poet;
    }

    public Poem setPoet(Poet poet) {
        this.poet = poet;
        return this;
    }

    public String getTitleAndPoet() {
        return "[" + title + "] " + poet + "  " + date;
    }

    public void lineAppend(String line) {
        lines.add(line);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Poem)) {
            return false;
        }
        Poem o = (Poem) obj;
        return Objects.equals(title, o.title) && Objects.equals(poet, o.poet);
    }

    @Override
    public int hashCode() {
        return title.hashCode() ^ poet.hashCode();
    }

    @Override
    public String toString() {
        return getTitleAndPoet() + "\n\n" + String.join("\n", lines);
    }
}
