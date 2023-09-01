package streamapi;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;

import java.util.stream.Collectors;

@Data
public class Student {
    @CsvBindByName(column = "ID", required = true)
    private int id;
    @CsvBindByName(column = "First Name", required = true)
    private String firstName;
    @CsvBindByName(column = "Last Name", required = true)
    private String lastName;
    @CsvBindByName(column = "Age", required = true)
    private int age;
    @CsvBindByName(column = "Gender", required = true)
    private Sex sex;
    @CsvBindAndJoinByName(column = "math|programming", elementType = Integer.class)
    private MultiValuedMap<String, Integer> marks;

    public enum Sex {
        MALE,
        FEMALE;
    }

    public double getAverageMark() {
        return getMarks().values().stream().collect(Collectors.averagingDouble(Integer::doubleValue));
    }
}
