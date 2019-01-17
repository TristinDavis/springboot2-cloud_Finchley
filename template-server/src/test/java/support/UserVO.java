package support;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Author: luweihong
 * @Date: 2018/8/7
 */
public class UserVO implements Serializable {

    private Long userId;

    private String name;

    private Double amount;

    private Integer age;

    public UserVO() {
        this.userId = RandomUtils.nextLong();
        this.name = String.valueOf(this.userId);
        this.amount = RandomUtils.nextDouble();
        this.age = RandomUtils.nextInt();
    }

    public UserVO(Long userId, String name, Double amount, Integer age) {
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.age = age;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void test() {
        System.out.println("这是 userVO 的 test 方法");
    }

    public String test(String msg) {
        System.out.println("这是 userVO 的 test msg 方法");
        return "这是 userVO 的 test msg 方法";
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
