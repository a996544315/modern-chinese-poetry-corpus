package cn.mordernpoem.command;

/**
 * @author zhyyy 2019/6/18
 * @since 0.0.1
 **/
public class ArgReader {
    private String[] args;
    private int pos, len;

    public ArgReader(String[] args) {
        this.args = args;
        this.pos = 1;
        this.len = args.length;
    }

    public int getPos() {
        return pos;
    }

    public boolean hasNext() {
        return pos < len;
    }

    public String read() {
        return args[pos++];
    }

    public int read(String[] c) {
        int i = 0;
        while (i < c.length && hasNext()) {
            c[i++] = read();
        }
        return i;
    }
}
