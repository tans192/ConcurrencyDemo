package chapter_2;

import lombok.Data;

/**
 * Created by xiang.wei on 2019/11/9
 *
 * @author xiang.wei
 */
@Data
public class User {

    /**
     * id
     */
    private int id;

    /**
     * 名称
     */
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

