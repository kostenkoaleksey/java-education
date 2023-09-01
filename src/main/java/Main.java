import streamapi.Faculty;
import streamapi.Group;
import streamapi.Student;
import streamapi.UniversityService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        UniversityService universityService = new UniversityService();
        
        long startTime = System.currentTimeMillis();
        System.out.println("\nList of all students in Faculty1");
        universityService
                .getStudentsByFaculty("Faculty1")
                .forEach(s -> System.out.println(s.getId() + ": " + s.getFirstName() + " " + s.getLastName()));

        System.out.println("\nList of students of army draft age (age > 20)");
        universityService
                .getStudentsOfArmyDraftAge()
                .forEach(s -> System.out.println(s.getFirstName() + " " + s.getLastName() + " [" + s.getAge() + "]"));


        System.out.println("\nList of subjects");
        universityService
                .getAllSubjects()
                .forEach(System.out::println);

        System.out.println("\nAverage mark per subject among all students");
        universityService
                .getSubjectsAverageMarks()
                .forEach((s, m) -> System.out.println(s + ": " + m));


        System.out.println("\nGroups average mark");
        universityService
                .getGroupsAverageMarks()
                .forEach((e, v) -> System.out.println(e + ": " + v));

        System.out.println("\nGroups average mark by subjects");
        universityService
                .getGroupsAverageMarksPerSubject()
                .forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\nGroups where male students prevails over female segment");
        universityService
                .getGroupsWhereMaleStudentsPrevailsOverFemale()
                .forEach(g -> System.out.println(g.getTitle()));

        System.out.println("\nFind all excellent students");
        universityService
                .getAllExcellentStudents()
                .forEach(s -> System.out.println(s.getId() + ": " + s.getFirstName() + " " + s.getLastName() + " " + s.getMarks() + ": " + s.getAverageMark()));

        long endTime = System.currentTimeMillis();

        System.out.println("The time is: " + (endTime - startTime) + "ms");
        System.out.println("\n======================== Async Calculations =======================");

        try {
            startTime = System.currentTimeMillis();

            CompletableFuture<List<Student>> listStudentsOfFaculty1 = CompletableFuture.supplyAsync(
                    () -> universityService.getStudentsByFaculty("Faculty1")
            );
            CompletableFuture<List<Student>> listStudentsOfArmyDraftAge = CompletableFuture.supplyAsync(
                    () -> universityService.getStudentsOfArmyDraftAge()
            );
            CompletableFuture<Set<String>> listSubjects = CompletableFuture.supplyAsync(
                    () -> universityService.getAllSubjects()
            );
            CompletableFuture<Map<String, Double>> averageMarksPerSubject = CompletableFuture.supplyAsync(
                    () -> universityService.getSubjectsAverageMarks()
            );
            CompletableFuture<Map<String, Double>> groupsAverageMarks = CompletableFuture.supplyAsync(
                    () -> universityService.getGroupsAverageMarks()
            );
            CompletableFuture<Map<String, Map<String, Double>>> groupsAverageMarksPerSubject = CompletableFuture.supplyAsync(
                    () -> universityService.getGroupsAverageMarksPerSubject()
            );
            CompletableFuture<List<Group>> groupsWhereMaleStudentsPrevailOverFemale = CompletableFuture.supplyAsync(
                    () -> universityService.getGroupsWhereMaleStudentsPrevailsOverFemale()
            );
            CompletableFuture<List<Student>> listOfExcellentStudents = CompletableFuture.supplyAsync(
                    () -> universityService.getAllExcellentStudents()
            );

            System.out.println("\nList of all students in Faculty1");
            listStudentsOfFaculty1.get().forEach(s -> System.out.println(s.getId() + ": " + s.getFirstName() + " " + s.getLastName()));
            System.out.println("\nList of students of army draft age (age > 20)");
            listStudentsOfArmyDraftAge.get().forEach(s -> System.out.println(s.getFirstName() + " " + s.getLastName() + " [" + s.getAge() + "]"));
            System.out.println("\nList of subjects");
            listSubjects.get().forEach(System.out::println);
            System.out.println("\nAverage mark per subject among all students");
            averageMarksPerSubject.get().forEach((s, m) -> System.out.println(s + ": " + m));
            System.out.println("\nGroups average mark");
            groupsAverageMarks.get().forEach((e, v) -> System.out.println(e + ": " + v));
            System.out.println("\nGroups average mark by subjects");
            groupsAverageMarksPerSubject.get().forEach((k, v) -> System.out.println(k + ": " + v));
            System.out.println("\nGroups where male students prevails over female segment");
            groupsWhereMaleStudentsPrevailOverFemale.get().forEach(g -> System.out.println(g.getTitle()));
            System.out.println("\nFind all excellent students");
            listOfExcellentStudents.get().forEach(s -> System.out.println(s.getId() + ": " + s.getFirstName() + " " + s.getLastName() + " " + s.getMarks() + ": " + s.getAverageMark()));

            endTime = System.currentTimeMillis();
            System.out.println("The time is: " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}