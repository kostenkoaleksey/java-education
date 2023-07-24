package streamapi;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private short age;
    private String sex;

    public Student(int id, String firstName, String lastName, short age, String sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public short getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
