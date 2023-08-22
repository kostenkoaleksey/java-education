package streamapi;

import lombok.Data;

import java.util.List;

@Data
public class Faculty {
    private final int id;
    private final String title;
    private List<Group> groups;
}
