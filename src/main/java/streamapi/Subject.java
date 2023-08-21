package streamapi;

import lombok.Data;
import lombok.NonNull;

@Data
public class Subject {
    @NonNull
    private int id;
    @NonNull
    private String title;
}
