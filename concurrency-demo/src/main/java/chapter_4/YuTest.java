package chapter_4;

/**
 * Created by xiang.wei on 2020/1/31
 *
 * @author xiang.wei
 */
public class YuTest {
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    public static void main(String[] args) {
        System.out.println(spread(14)+"&16="+(spread(14)&16));
        System.out.println(spread(14)+"&32="+(spread(14)&32));
        System.out.println(spread(31)+"&16="+(spread(31)&16));
        System.out.println(spread(31)+"&32="+(spread(31)&32));
    }

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

}
