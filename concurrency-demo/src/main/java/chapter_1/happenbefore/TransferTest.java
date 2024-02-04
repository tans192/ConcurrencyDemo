package chapter_1.happenbefore;

/**
 * @author xiang.wei
 * @date 2019/10/29 13:31
 */
public class TransferTest {
    int a = 10;
    int b = 1;
    int c = 0;

    public void transfer() {
        c = a + b;
        a = 100;
        c = a - b;
    }

    public static void main(String[] args) {

    }
}
