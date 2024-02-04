package chapter_4.queue_14;

/**
 * @author xiang.wei
 * @date 2020/2/14 4:09 PM
 */
public class ClqTest {
    public static void main(String[] args) {
        String tail = "";
        String t = (tail = "oldTail");
        System.out.println("t的值:" + t);
        tail = "newTail";
        boolean isEqual = t != (t = tail);
        System.out.println("isEqual:" + isEqual);
    }
}
