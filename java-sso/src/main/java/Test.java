/**
 * TODO
 *
 * @author zc.ding
 * @create 2018/9/26
 */
public class Test {
    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("sh /share/service_switch.sh payment start 5103 cxj_master");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
