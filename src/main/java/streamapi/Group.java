package streamapi;

import lombok.*;

import java.util.HashSet;

@Data
public class Group {
    @NonNull
    private int id;
    @NonNull
    private String title;
    private HashSet<Student> students;
}
