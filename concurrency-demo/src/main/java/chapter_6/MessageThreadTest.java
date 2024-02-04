package chapter_6;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang.wei
 * @date 2020/6/17 9:33
 * <p>
 * 有一个消息发送接口MessageService.send(String message)，每次消息发送需要耗时2ms；
 * 基于以上接口，实现一个批量发送接口MessageService.batchSend(List messages);
 * 要求如下：
 * 一次批量发送消息最大数量为100条
 * 批量发送接口一次耗时不超过50ms
 */
public class MessageThreadTest {
    public static void main(String[] args) throws InterruptedException {
        List<String> messageList = new ArrayList<>();
        //初始化数据
        for (int i = 0; i < 100; i++) {
            messageList.add("消息" + i);
        }
        long startTime = System.currentTimeMillis();
        MessageService messageService = new MessageService();
        messageService.batchSend(messageList);
        System.out.println("********发送消息总耗时=" + (System.currentTimeMillis() - startTime) + "毫秒");
    }
}
