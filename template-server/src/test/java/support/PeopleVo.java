package support;

import java.util.Date;

/**
 * @Author: luweihong
 * @Date: 2018/12/14
 */
public class PeopleVo {

    private String name;

    private int age;

    private Date birthday;

    public PeopleVo(){
        this.name = "monkey";
        this.age = 18;
        this.birthday = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "PeopleVo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
