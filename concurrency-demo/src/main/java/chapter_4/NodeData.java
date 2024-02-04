package chapter_4;

import lombok.Data;

/**
 * 用于存放结点的数据
 * @author xiang.wei
 * @date 2019/12/20 16:06
 */
@Data
public class NodeData {
    private int id;
    private String data;

    public NodeData(int id, String data) {
        this.id = id;
        this.data = data;
    }

}
