package streamapi;

import lombok.Data;

import java.util.Map;

@Data
public class Student {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Sex sex;
    private Map<Subject, Integer> marks;

    public enum Sex {
        MALE,
        FEMALE;
    }
}
