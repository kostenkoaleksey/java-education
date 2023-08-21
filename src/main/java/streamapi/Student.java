package streamapi;

import lombok.*;

import java.util.Map;

@Data
public class Student {
    @NonNull
    private int id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private int age;
    @NonNull
    private Sex sex;
    private Map<Subject, Integer> marks;
    public enum Sex {
        MALE,
        FEMALE;
    }
}
