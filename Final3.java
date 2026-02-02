import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class Final3 {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter the number of students of class: ");
        int classCapacity = input.nextInt();

        // setting the number of student of the class

        String[][] record = new String[classCapacity][3];

        // the array for class record, which contains the no.of students, their names, their roll numbers and their attendance

        double[][] marksRecord = new double[classCapacity][7];

        /*
         the array for marks of students, which contains the number of students, 0-5th index for storing the marks of the subjects,
         and the 7th index for calculating and storing the GPA
         */

        String[] subjects = { "PF", "ITM", "IS", "PS", "CS", "DLD" };    // the subjects of the students
        int[] subjectCredits = { 4, 3, 3, 3, 3, 4 };                     // the credit hours of each subject
        int choice;
        try {
            do {
                System.out.println("""
                        ---------------------Welcome to your class record management system!----------------------
                        Press 1 for adding students the details (name, roll number and attendance) of the student!
                        Press 2 for entering the marks of the student!
                        Press 3 for GPA calculation!
                        Press 4 to display the result in a file!
                        Press 5 to display the result of the class!
                        Press 6 to see the students who got probe!
                        Press 0 to exit!
                        """);
                System.out.print("Make your choice: ");
                choice = input.nextInt();
                System.out.println();

                switch (choice) {
                    case 1: {
                        record = detailsOfStudent(classCapacity);        // allows to enter the credentials of students
                        fileOfRecord(record);                            // storing the record in a file
                        break;
                    }

                    case 2: {
                        marksRecord = marksOfStudent(record, marksRecord, subjects);

                        // updating the marksRecord array and calling the marksOfStudent function in it

                        fileOfMarks(marksRecord, record, subjects);

                        // making a file for marks of students
                        break;
                    }

                    case 3: {
                        marksRecord = calculateGPA(marksRecord, subjectCredits);

                        // updating the marksRecord and calling the GPA function
                        break;
                    }

                    case 4: {
                        resultFile(record, marksRecord, subjects);

                        // creating a result file
                        break;
                    }

                    case 5: {
                        readFile("result.txt");

                        // reading from the result file and displaying it on console
                        break;
                    }

                    case 6: {
                        probe(marksRecord,record);
                        break;
                    }

                    case 0: {
                        System.out.println("Exiting...");
                        break;
                    }

                    default: {
                        System.out.println("Invalid Input!");
                        break;
                    }
                }

            } while (choice != 0);
        } catch (InputMismatchException ex) {
            System.out.println("Invalid Input!");
        }
    }

    public static String[][] detailsOfStudent(int classCapacity) {
        String[][] record = new String[classCapacity][3];
        try {
            for (int i = 0; i < record.length; i++) {
                input.nextLine();

                // to create and extra line, so that the compiler does not miss any input

                System.out.print("Enter the name of the " + (i + 1) + " student: ");
                record[i][0] = input.nextLine();        // storing the name at 1st index
                System.out.print("Enter the roll number of the " + (i + 1) + " student: ");
                record[i][1] = String.valueOf(input.nextInt());

                /*
                 storing the roll number at the 2nd index after converting it to string as the array in which data is being
                  stored is of String type
                 */

                System.out.print("Enter the total attendance of the " + (i + 1) + " student: ");
                record[i][2] = String.valueOf(input.nextDouble());     // storing the attendance as string
                input.nextLine();
                System.out.println();
            }
        } catch (InputMismatchException ex) {
            System.out.println("Invalid Input!");
        }
        return record;
    }

    public static void fileOfRecord(String[][] record) {
        try {
            PrintWriter writer = new PrintWriter("record.txt");

            // creating a file to store the credentials

            for (int i = 0; i < record.length; i++) {
                writer.println("Name is: " + record[i][0]);
                writer.println("Roll number is: " + record[i][1]);
                writer.println("Attendance is: " + record[i][2]);
            }
            writer.close();

            System.out.println();
            System.out.println("The record file was successfully written!");
            System.out.println();

        } catch (IOException ex) {
            System.out.println("There was an error writing the file!");
        }
    }

    public static double[][] marksOfStudent(String[][] record, double[][] marksRecord, String[] subjects) {
        try {
            for (int i = 0; i < subjects.length; i++) {

                /*
                we iterate over the subject array, and use the record array in the subject array with number of rows as name of students
                and number of columns for the name of subjects
                 */

                System.out.println("---------- " + subjects[i] + " Marks ----------");
                for (int j = 0; j < record.length; j++) {
                    System.out.print(record[j][0] + " - " + record[j][1] + " | Marks ( / 100 ): ");
                    marksRecord[j][i] = input.nextDouble();
                }
            }

        } catch (InputMismatchException ex) {
            System.out.println("Invalid Input!");
        }
        return marksRecord;
    }

    public static void fileOfMarks(double[][] marksRecord, String[][] record, String[] subjects) {
        try {
            PrintWriter writer = new PrintWriter("marks.txt");

            // creating file for marks of student

            for (int i = 0; i < subjects.length; i++) {
                writer.println("------- " + subjects[i] + " Marks -------");
                for (int j = 0; j < marksRecord.length; j++) {
                    writer.print(record[j][0] + " - " + record[j][1] + " | Marks: ");
                    writer.println(marksRecord[j][i]);
                }
            }
            writer.close();

            System.out.println();
            System.out.println("The marks file was successfully written!");
            System.out.println();

        } catch (IOException ex) {
            System.out.println("There was an error writing the file!");
        }
    }
    public static char calcGrade(double marks) {

        /*
        the marks entered for each subject are given a separate grade, which pass on from the marksOfStudent method
         */

        if (marks >= 85)
            return 'A';
        else if (marks >= 75)
            return 'B';
        else if (marks >= 60)
            return 'C';
        else if (marks >= 55)
            return 'D';
        else if (marks >= 50)
            return 'E';
        else
            return 'F';

    }
    public static double calcGradePoints(char grade) {

        /*
        the grades are given points according to which the GPA will be calculated
         */

        if (grade == 'A')
            return 4.00;
        else if (grade == 'B')
            return 3.5;
        else if (grade == 'C')
            return 3;
        else if (grade == 'D')
            return 2;
        else if (grade == 'E')
            return 1.5;
        else
            return 1;

    }
    public static double[][] calculateGPA(double[][] marksRecord, int[] subjectCredits) {
        int totalCredits = 0;
        int totalPoints;

        for (int i = 0; i < subjectCredits.length; i++) {

            /*
            the credit hours of each subject are then summed up and stored in a variable total credit hours
             */

            totalCredits += subjectCredits[i];
        }
        for (int i = 0; i < marksRecord.length; i++) {
            totalPoints = 0;
            for (int j = 0; j < marksRecord[i].length - 1; j++) {

                /*
                 we use marksRecord[i].length - 1 because we want to store the student points till the 2nd last index because at the last
                 index i.e. index 7, we are storing the GPA of the student
                 */

                totalPoints += calcGradePoints(calcGrade(marksRecord[i][j])) * subjectCredits[j];

                // we call the calcGrade function inside the calcGradePoints function
                // multiplying the marks of the students with their credit hours and adding them to the total points
            }
            marksRecord[i][6] = (double)totalPoints / totalCredits;    // calculating and storing the GPA
        }
        return marksRecord;
    }

    public static void probe(double [][] marksRecord, String [][] record) {
        int count = 0;
        for (int i = 0; i < marksRecord.length; i++) {
            for (int j =0; j < marksRecord[0].length; j++) {
                if (marksRecord[i][6] < 2.0) {
                    count++;
                }
            }
        }
        String [][] students = new String[count][2];
        for (int i = 0; i < marksRecord.length; i++) {
            for (int j = 0; j < marksRecord[0].length; j++) {
                if (marksRecord[i][6] < 2.0) {
                    students[i][0] = String.valueOf(record[i][0]) ;
                    students[i][1] = String.valueOf(marksRecord[i][6]);
                }
            }
            System.out.println("The student with probe is: " + students[i][0]);
            System.out.println("The GPA of the student is: " + students[i][1]);
        }
    }
    public static void resultFile(String[][] record, double[][] marksRecord, String[] subjects) {
        try {
            PrintWriter writer = new PrintWriter("result.txt");

            /*
            writing the result of the student in a file. the result will display the name of the student, roll number, attendance
            marks of all the subjects, and the GPA
             */

            for (int i = 0; i < record.length; i++) {
                writer.println("-------------------------------------------------------------");
                writer.println();
                writer.println("The name of " + (i + 1) + " student is: " + record[i][0]);
                writer.println("The roll number of " + (i + 1) + " student is: " + record[i][1]);
                writer.println("The attendance of " + (i + 1) + " student is: " + record[i][2]);
                writer.println("Marks of " + subjects[0] + " is: " + marksRecord[i][0]);
                writer.println("Marks of " + subjects[1] + " is: " + marksRecord[i][1]);
                writer.println("Marks of " + subjects[2] + " is: " + marksRecord[i][2]);
                writer.println("Marks of " + subjects[3] + " is: " + marksRecord[i][3]);
                writer.println("Marks of " + subjects[4] + " is: " + marksRecord[i][4]);
                writer.println("Marks of " + subjects[5] + " is: " + marksRecord[i][5]);
                writer.println("The GPA of " + (i + 1) + " student is: " + marksRecord[i][6]);
                writer.println();
                writer.println("-------------------------------------------------------------");
            }
            writer.close();

            System.out.println("Result has been written to the file successfully.");
            System.out.println();

        } catch (IOException e) {
            System.out.println("An error occurred while writing the result to the file.");
        }
    }

    public static void readFile(String filePath) {
        try {

            /*
            this will read the result from the file and display it on the console
             */

            File file = new File("result.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
            reader.close();

        } catch (IOException ex) {
            System.out.println("An error occurred while reading the file!");
        }
    }
}
