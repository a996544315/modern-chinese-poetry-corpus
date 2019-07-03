package cn.mordernpoem.util;

import cn.mordernpoem.Main;
import cn.mordernpoem.bean.Poem;
import cn.mordernpoem.bean.Poet;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhyyy 2019/6/19
 * @since 0.0.1
 **/
public class FileHelper {

    private static final String ROOT_DIR_PATH;

    static {
        String fs = File.separator;
        String s = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        s = new File(s).getAbsolutePath();
        s = s.substring(0, s.lastIndexOf(fs));
        ROOT_DIR_PATH = s.substring(0, s.lastIndexOf(fs)) + fs + "data" + fs + "origin" + fs;
//        ROOT_DIR_PATH = "C:\\workspace\\modern-chinese-poetry-corpus\\data\\origin\\";
    }

    public List<Poet> getAll() {
        List<Poet> poetList = new LinkedList<>();
        File file = new File(ROOT_DIR_PATH);
        String[] childFile = file.list();
        if (childFile != null) {
            for (String s : childFile) {
                File f = new File(ROOT_DIR_PATH + s);
                if (f.isDirectory()) {
                    Poet poet = new Poet();
                    poet.setName(s.substring(0, s.indexOf('_')));
                    poet.setDirName(s);
                    poetList.add(poet);
                }
            }
        }
        return poetList;
    }

    public List<Poem> findByPoet(Poet poet) {
        List<Poem> poems = new ArrayList<>();
        File file = new File(ROOT_DIR_PATH + "/" + poet.getDirName());
        String[] childFile = file.list();
        if (childFile != null) {
            for (String s : childFile) {
                if (!s.endsWith("pt")) continue;
                File f = new File(ROOT_DIR_PATH + "/" + poet.getDirName() + "/" + s);
                try {
                    BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(f));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    Poem poem = new Poem();
                    poem.setPoet(poet);
                    int infoFinish = 2;
                    boolean contentStart = false;
                    String ss;
                    while ((ss = reader.readLine()) != null) {
                        if (ss.startsWith("title")) {
                            poem.setTitle(ss.substring(6));
                            infoFinish--;
                        } else if (ss.startsWith("date")) {
                            poem.setDate(ss.substring(5));
                            infoFinish--;
                        } else if (infoFinish == 0) {
                            if (contentStart) {
                                poem.lineAppend(ss);
                            } else if (!ss.trim().isEmpty()) {
                                poem.lineAppend(ss);
                                contentStart = true;
                            }
                        }
                    }
                    if (poem.getTitle() != null)
                        poems.add(poem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return poems;
    }

    private String getPath(Poem p) {
        return ROOT_DIR_PATH + p.getPoet().getDirName() + File.separator + p.getTitle() + ".pt";
    }

    public void write(Poem p) {
        File file = new File(getPath(p));
        File temp = new File(getPath(p) + "temp");
        if (file.exists() && file.isFile()) {
            copyFile(file, temp);
        }
        write(getPath(p), p);
        temp.delete();
    }

    private void copyFile(File sourceFile, File targetFile) {
        try (FileInputStream input = new FileInputStream(sourceFile);
             BufferedInputStream inBuff = new BufferedInputStream(input);
             FileOutputStream output = new FileOutputStream(targetFile);
             BufferedOutputStream outBuff = new BufferedOutputStream(output)) {
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } catch (IOException ee) {
            throw new RuntimeException(ee.getMessage());
        }
    }

    private void write(String path, Poem p) {
        File file = new File(path);
        try (FileOutputStream fo = new FileOutputStream(file);
             OutputStreamWriter ow = new OutputStreamWriter(fo, StandardCharsets.UTF_8);
             Writer writer = new BufferedWriter(ow)) {
            writer.write("title:" + p.getTitle() + "\n");
            writer.write("date:" + p.getDate() + "\n\n");

            List<String> lines = p.getLines();
            int i = lines.size();
            for (String s : lines) {
                if (--i > 0) {
                    s += "\n";
                }
                writer.append(s);
            }
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
