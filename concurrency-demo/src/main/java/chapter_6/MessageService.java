package chapter_6;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiang.wei
 * @date 2020/6/17 9:34
 */
public class MessageService {

    private static int MESSAGE_LEAGE = 25;
    /**
     * 单条消息发送接口，模拟消息发送
     *
     * @param message
     */
    public String send(String message) {
        try {
            Thread.sleep(2);
            System.out.println(Thread.currentThread().getName() + message + "发送成功");
            return message + "发送成功";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量发送消息
     *
     * @param messageList
     */
    public String batchSend(List<String> messageList) throws InterruptedException {
        if (messageList == null) {
            return "消息不能为空";
        }
        if (messageList.size() > 100) {
            return "消息超过100条";
        }
        //多线程发送，每个任务发送20条，然后汇总
        int i = messageList.size() / MESSAGE_LEAGE;
        if (messageList.size() > i * MESSAGE_LEAGE) {
            i = i + 1;
        }
        CountDownLatch countDownLatch = new CountDownLatch(i);
        for (int j = 0; j < i; j++) {
            //new Thread(new SendTask(messageList, j * 20, ((j + 1) * 20))).start();
            int finalJ = j;
            new Thread(() -> {
                for (int m = (finalJ * MESSAGE_LEAGE); m < ((finalJ + 1) * MESSAGE_LEAGE); m++) {
                    if (m >= messageList.size()) {
                        continue;
                    }
                    send(messageList.get(m));
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        return "发送成功";
    }

    //定一个发送任务
    class SendTask implements Runnable {
        /**
         * 消息
         */
        List<String> messageList;
        int start;
        int end;

        public SendTask(List<String> messageList, int start, int end) {
            this.messageList = messageList;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                send(messageList.get(i));
            }
        }
    }

}
