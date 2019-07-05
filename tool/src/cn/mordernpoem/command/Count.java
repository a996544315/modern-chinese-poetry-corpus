package cn.mordernpoem.command;

/**
 * @author zhyyy 2019/7/5
 * @since 1.0.0
 **/
public class Count extends BaseCommand {
    @Override
    public void help() {
        System.out.println("count\n" +
                "-p|-c|-d                选择功能，必选其一\n"+
                "    -p                  统计字符频率\n"+
                "    -c                  统计字符连接频率\n"+
                "    -d                  删除数据");
    }

    @Override
    boolean assertAndSave(ArgReader reader) {
        return false;
    }

    @Override
    void deal() {
    }
}
