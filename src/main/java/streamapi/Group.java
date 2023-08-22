package streamapi;

import lombok.Data;

import java.util.Set;

@Data
public class Group {
    private final int id;
    private final String title;
    private Set<Student> students;
}
