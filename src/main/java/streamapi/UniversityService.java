package streamapi;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class UniversityService {
    private List<Faculty> university;

    public UniversityService() {
        this.loadData();
    }

    /**
     * Returns the list of student filtered by faculty title.
     *
     * @param facultyTitle The faculty title.
     * @return List of students.
     */
    public List<Student> getStudentsByFaculty(String facultyTitle) {
        return university.stream()
                .filter(f -> f.getTitle().equals(facultyTitle))
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .map(Group::getStudents)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingInt(Student::getId))
                .toList();
    }

    /**
     * Returns the list students of army draft age.
     *<p>
     * The draft army age is greater than or equals 20.
     *
     * @return List of students.
     */
    public List<Student> getStudentsOfArmyDraftAge() {
        return university.stream()
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .map(Group::getStudents)
                .flatMap(Collection::stream)
                .filter(s -> s.getAge() >= 20)
                .sorted(Comparator.comparingInt(Student::getAge))
                .toList();
    }

    /**
     * Returns the set of all subjects in university.
     *
     * @return The set of subjects.
     */
    public Set<String> getAllSubjects() {
        return university.stream()
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .map(Group::getStudents)
                .flatMap(Collection::stream)
                .flatMap(s -> s.getMarks().keySet().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the map of subjects with average marks.
     *
     * @return The map of average marks per subject.
     */
    public Map<String, Double> getSubjectsAverageMarks() {
        return university.stream()
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .map(Group::getStudents)
                .flatMap(Collection::stream)
                .flatMap(s -> s.getMarks()
                        .entries()
                        .stream()
                )
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)));
    }

    /**
     * Returns the map of average marks per group.
     * <p>
     * The key equals group's name and value - average mark per all subjects.
     *
     * @return The map of groups with average marks
     */
    public Map<String, Double> getGroupsAverageMarks() {
        return university.stream()
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .collect(
                        Collectors.toMap(
                                Group::getTitle,
                                g -> g.getStudents()
                                        .stream()
                                        .flatMap(s -> s.getMarks().values().stream())
                                        .collect(Collectors.averagingDouble(Integer::doubleValue))
                        )
                );
    }

    /**
     * Returns the map of average marks per groups' subjects.
     * <p>
     * The key equals group's name and value - map of subject and average mark per for it.
     *
     * @return The map of groups with average marks per subjects
     */
    public Map<String, Map<String, Double>> getGroupsAverageMarksPerSubject() {
        return university.stream()
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .collect(
                        Collectors.toMap(
                                Group::getTitle,
                                g -> g.getStudents()
                                        .stream()
                                        .flatMap(s -> s.getMarks().entries().stream())
                                        .collect(Collectors.groupingBy(
                                                Map.Entry::getKey,
                                                Collectors.averagingDouble(Map.Entry::getValue)
                                        ))
                        )
                );
    }

    /**
     * Returns the list of groups where male students prevails over female segment.
     *
     * @return The list of groups.
     */
    public List<Group> getGroupsWhereMaleStudentsPrevailsOverFemale() {
        return university.stream()
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .filter(UniversityService::checkMaleStudentsPrevailOverFemale)
                .toList();
    }

    /**
     * Returns the list of all excellent students in the university.
     * <p>
     * Excellent student is a student whose average mark is greater than or equals 90.
     *
     * @return The list of excellent students
     */
    public List<Student> getAllExcellentStudents() {
        return university.stream()
                .map(Faculty::getGroups)
                .flatMap(Collection::stream)
                .map(Group::getStudents)
                .flatMap(Collection::stream)
                .filter(s -> s.getAverageMark() >= 90)
                .sorted(Comparator.comparingInt(Student::getId))
                .toList();
    }

    /**
     * Loading students' data from the CSV file and build up the university data structure.
     */
    private void loadData() {
        try {
            List<Student> students = new CsvToBeanBuilder<Student>(new FileReader("src/main/resources/data.csv"))
                    .withSeparator(';')
                    .withType(Student.class)
                    .build()
                    .parse();

            Group group1 = new Group(1, "Group1");
            Group group2 = new Group(2, "Group2");
            Group group3 = new Group(3, "Group3");
            Group group4 = new Group(4, "Group4");

            group1.setStudents(new HashSet<>(students.subList(0, 50)));
            group2.setStudents(new HashSet<>(students.subList(50, 100)));
            group3.setStudents(new HashSet<>(students.subList(100, 150)));
            group4.setStudents(new HashSet<>(students.subList(150, 200)));

            Faculty faculty = new Faculty(1, "Faculty1");
            Faculty faculty2 = new Faculty(2, "Faculty2");

            faculty.setGroups(List.of(group1, group2));
            faculty2.setGroups(List.of(group3, group4));

            university = List.of(faculty, faculty2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean checkMaleStudentsPrevailOverFemale(Group g) {
        return g.getStudents()
                .stream()
                .filter(s -> s.getSex() == Student.Sex.MALE)
                .count() > g.getStudents().stream().filter(s -> s.getSex() == Student.Sex.FEMALE).count();
    }
}
