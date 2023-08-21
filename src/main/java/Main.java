import streamapi.Faculty;
import streamapi.Group;
import streamapi.Student;
import streamapi.Subject;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Subject math = new Subject(1, "Math");
        Subject programming = new Subject(2, "Programming");

        // 1st group students
        Student student1 = new Student(1, "Sherri", "Edwards", 28, Student.Sex.FEMALE);
        student1.setMarks(Map.of(math, 4, programming, 3));
        Student student2 = new Student(2, "Windy", "Kepley", 21, Student.Sex.FEMALE);
        student2.setMarks(Map.of(math, 5, programming, 5));
        Student student3 = new Student(3, "Maureen", "Bush", 25, Student.Sex.FEMALE);
        student3.setMarks(Map.of(math, 3, programming, 5));
        Student student4 = new Student(4, "Gordon", "Crawford", 18, Student.Sex.MALE);
        student4.setMarks(Map.of(math, 4, programming, 4));
        Student student5 = new Student(5, "Marlon", "Wolfe", 22, Student.Sex.MALE);
        student5.setMarks(Map.of(math, 5, programming, 5));
        // 2nd group students
        Student student6 = new Student(6, "Jaclyn", "Lam", 23, Student.Sex.FEMALE);
        student6.setMarks(Map.of(math, 5, programming, 3));
        Student student7 = new Student(7, "Jon", "Meehan", 27, Student.Sex.MALE);
        student7.setMarks(Map.of(math, 4, programming, 4));
        Student student8 = new Student(8, "Charles", "Miracle", 19, Student.Sex.MALE);
        student8.setMarks(Map.of(math, 3, programming, 3));
        Student student9 = new Student(9, "Erich", "Mathis", 22, Student.Sex.MALE);
        student9.setMarks(Map.of(math, 4, programming, 5));
        Student student10 = new Student(10, "Virginia", "Pruett", 27, Student.Sex.FEMALE);
        student10.setMarks(Map.of(math, 5, programming, 5));
        // 3rd group students
        Student student11 = new Student(11, "Willie", "Allen", 23, Student.Sex.MALE);
        student11.setMarks(Map.of(math, 3, programming, 3));
        Student student12 = new Student(12, "Robert", "Phelps", 20, Student.Sex.MALE);
        student12.setMarks(Map.of(math, 4, programming, 4));
        Student student13 = new Student(13, "Hattie", "Waggoner", 24, Student.Sex.FEMALE);
        student13.setMarks(Map.of(math, 4, programming, 5));
        Student student14 = new Student(14, "Jasper", "Knowles", 29, Student.Sex.MALE);
        student14.setMarks(Map.of(math, 3, programming, 4));
        Student student15 = new Student(15, "Michael", "Migues", 26, Student.Sex.MALE);
        student15.setMarks(Map.of(math, 5, programming, 4));
        // 4th group students
        Student student16 = new Student(16, "Katie", "Hyde", 20, Student.Sex.FEMALE);
        student16.setMarks(Map.of(math, 4, programming, 4));
        Student student17 = new Student(17, "Jonathan", "Spears", 22, Student.Sex.MALE);
        student17.setMarks(Map.of(math, 3, programming, 5));
        Student student18 = new Student(18, "Larry", "Guffey", 21, Student.Sex.MALE);
        student18.setMarks(Map.of(math, 5, programming, 4));
        Student student19 = new Student(19, "John", "Hernandez", 30, Student.Sex.MALE);
        student19.setMarks(Map.of(math, 5, programming, 5));
        Student student20 = new Student(20, "Joseph", "Shipp", 18, Student.Sex.MALE);
        student20.setMarks(Map.of(math, 4, programming, 4));

        Group group1 = new Group(1, "Group1");
        Group group2 = new Group(2, "Group2");
        Group group3 = new Group(3, "Group3");
        Group group4 = new Group(4, "Group4");

        group1.setStudents(new HashSet<>(List.of(student1, student2, student3, student4, student5)));
        group2.setStudents(new HashSet<>(List.of(student6, student7, student8, student9, student10)));
        group3.setStudents(new HashSet<>(List.of(student11, student12, student13, student14, student15)));
        group4.setStudents(new HashSet<>(List.of(student16, student17, student18, student19, student20)));

        Faculty faculty = new Faculty(1, "Faculty1");
        Faculty faculty2 = new Faculty(2, "Faculty2");

        faculty.setGroups(List.of(group1, group2));
        faculty2.setGroups(List.of(group3, group4));

        List<Faculty> university = List.of(faculty, faculty2);

        System.out.println("\nList of all students in " + faculty.getTitle());
        university.stream()
                .filter(f -> f.getTitle().equals(faculty.getTitle()))
                .flatMap(f -> f.getGroups()
                        .stream()
                        .flatMap(g -> g.getStudents().stream()))
                .sorted(Comparator.comparingInt(Student::getId))
                .map(s -> s.getId() + ": " + s.getFirstName() + " " + s.getLastName())
                .forEach(System.out::println);

        System.out.println("\nList of students of army draft age (age > 20)");
        university.stream()
                .flatMap(f -> f.getGroups()
                        .stream()
                        .flatMap(g -> g.getStudents().stream()))
                .filter(s -> s.getAge() >= 20)
                .sorted(Comparator.comparingInt(Student::getAge))
                .map(s -> s.getFirstName() + " " + s.getLastName() + " [" + s.getAge() + "]")
                .forEach(System.out::println);

        System.out.println("\nList of subjects");
        university.stream()
                .flatMap(f -> f.getGroups()
                        .stream()
                        .flatMap(g -> g.getStudents()
                                .stream()
                                .flatMap(s -> s.getMarks()
                                        .keySet()
                                        .stream()
                                        .map(o -> o.getTitle())
                                )
                        )
                )
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        System.out.println("\nAverage mark per subject among all students");
        university.stream()
                .flatMap(f -> f.getGroups()
                        .stream()
                        .flatMap(g -> g.getStudents()
                                .stream()
                                .flatMap(s -> s.getMarks()
                                        .entrySet()
                                        .stream()
                                        .collect(Collectors.toMap(k -> k.getKey().getTitle(), v -> v.getValue()))
                                        .entrySet()
                                        .stream()
                                )
                        )
                )
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)))
                .forEach((s, m) -> System.out.println(s + " " + m));

        System.out.println("\nGroups average mark");
        university.stream()
                .flatMap(f -> f.getGroups()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Group::getTitle,
                                        g -> g.getStudents()
                                                .stream()
                                                .flatMap(s -> s.getMarks()
                                                        .values()
                                                        .stream())
                                                .collect(Collectors.averagingDouble(v -> v.doubleValue()))
                                )
                        )
                        .entrySet()
                        .stream()
                )
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        System.out.println("\nGroups average mark by subjects");
        university.stream()
                .flatMap(f -> f.getGroups()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Group::getTitle,
                                        g -> g.getStudents()
                                                .stream()
                                                .flatMap(s -> s.getMarks()
                                                        .entrySet()
                                                        .stream())
                                                .collect(Collectors.groupingBy(
                                                        Map.Entry::getKey,
                                                        Collectors.averagingDouble(Map.Entry::getValue)
                                                ))
                                )
                        )
                        .entrySet()
                        .stream()
                )
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                o -> o.getValue()
                                        .entrySet()
                                        .stream()
                                        .map(v -> v.getKey().getTitle() + ": " + v.getValue().doubleValue())
                                        .collect(Collectors.toList())
                        )
                )
                .forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\nGroups where male students prevails over female segment");
        university.stream()
                .flatMap(f -> f.getGroups()
                        .stream()
                        .filter(g -> g.getStudents().stream().filter(s -> s.getSex() == Student.Sex.MALE).count() > g.getStudents().stream().filter(s -> s.getSex() == Student.Sex.FEMALE).count())

                )
                .forEach(g -> System.out.println(g.getTitle()));

        System.out.println("\nFind all excellent students");
        university.stream()
                .flatMap(f -> f.getGroups()
                        .stream()
                        .flatMap(g -> g.getStudents()
                                .stream()
                                .filter(s -> s.getMarks().entrySet().stream().allMatch(m -> m.getValue() == 5)))
                )
                .sorted(Comparator.comparingInt(Student::getId))
                .map(s -> s.getId() + ": " + s.getFirstName() + " " + s.getLastName() + " " + s.getMarks())
                .forEach(System.out::println);
    }
}