package streamapi;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class Faculty {
    @NonNull
    private int id;
    @NonNull
    private String title;
    private List<Group> groups;
}
