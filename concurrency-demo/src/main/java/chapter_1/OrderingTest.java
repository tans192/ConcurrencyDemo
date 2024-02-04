package chapter_1;

/**
 * @author xiang.wei
 * @date 2019/11/18 9:40
 */
public class OrderingTest {
/***
 * 指令重排，对于同一个线程，他看到的指令执行顺序一定是一致的。
 * 指令重排可以保证串行语义的一致，但是不能保证多线程间的语义也一致。
 *
 *
 * 一条CPU指令的执行可以分为很多步骤，简单的说，可以分为以下几步：
 *  1.取值IF
 *  2.译码和取寄存器操作数ID
 *  3.执行或者有效地址计算EX
 *  4.存储器访问MEM
 *  5.写回WB
 *
 *  例如：A=B+C的这个操作，写在左边的指令就是汇编指令，LW表示load,
 *  其中LW R1,B,表示B的值加载到R1寄存器中，ADD指令就是加法，把R1,R2的值相加，并存放在R3中，
 *  SW表示store,存储，就是将R3寄存器的值保存到变量A中。
 *
 *  A=B+C 的处理
 *
 *     LW R1,B   IF ID EX MEM  WB
 *     LW R2,C     IF ID  EX  MEM  WB
 * ADD R3,R1,R2       IF  ID   X   EX  MEM  WB
 *     SW A, R3           IF  X    ID  EX   MEM  WB
 *
 *   A=B+C
 *   D=E-F
 *
 *   重排前
 *     LW Ra,B   IF ID EX MEM  WB
 *     LW Rc,C     IF ID  EX  MEM  WB
 * ADD Ra,Rb,Rc       IF  ID   X   EX  MEM  WB
 *     SW A, Ra           IF  X    ID  EX   MEM  WB
 *     LW Re E                     IF  ID   EX   MEM WB
 *     LW Rf F                         IF   ID   EX  MEM  WB
 * SUB Rd,Re,Rf                             IF   ID  X    EX  MEM  WB
 *     SW D,Rd                                   IF  X    ID  EX   MEM WB
 *
 *    重排后
 *     LW Rb,B   IF ID EX MEM  WB
 *     LW Rc,C     IF ID  EX  MEM  WB
 *     LW Re E        IF  ID   EX  MEM WB
 * ADD Ra,Rb,Rc           IF  ID   EX  MEM  WB
 *     LW Rf F                IF   ID   EX  MEM  WB
 *     SW A, Ra                    IF  ID  EX   MEM  WB
 * SUB Rd,Re,Rf                        IF   ID  EX  MEM  WB
 *     SW D,Rd                              IF  ID  EX   MEM WB
 *  重排是为了减少CPU流水线的中断处理时间。提高CPU的处理性能
 */

private  boolean flag = false;
    private  int a = 0;

    public void writer() {
        a = 43;
        System.out.println(""+Thread.currentThread().getName()+"写入到的a值是="+a);
        flag = true;
    }
    public void read() {
        if (flag) {
            System.out.println(""+Thread.currentThread().getName()+"读取到的a值是="+a);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final OrderingTest orderingTest = new OrderingTest();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                orderingTest.writer();
            }
        },"线程A");
        threadA.start();
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                orderingTest.read();
            }
        },"线程B");
        threadB.start();

    }
}
