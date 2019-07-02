package cn.mordernpoem.bean;

import java.util.List;
import java.util.Objects;

/**
 * @author zhyyy 2019/6/19
 * @since 0.0.1
 **/
public class Poet {
    private String name;
    private List<Poem> poemList;
    private String dirName;


    public String getName() {
        return name;
    }

    public Poet setName(String name) {
        this.name = name;
        return this;
    }

    public List<Poem> getPoemList() {
        return poemList;
    }

    public Poet setPoemList(List<Poem> poemList) {
        this.poemList = poemList;
        return this;
    }

    public String getDirName() {
        return dirName;
    }

    public Poet setDirName(String dirName) {
        this.dirName = dirName;
        return this;
    }

    @Override
    public int hashCode() {
        return dirName == null ? 0 : dirName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Poet)) return false;
        return Objects.equals(this.dirName, ((Poet) obj).dirName);
    }

    @Override
    public String toString() {
        return name;
    }
}
