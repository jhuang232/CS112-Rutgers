package kindergarten;

import javax.lang.model.element.Element;

/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student
 * in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given
 * seat), and
 * - a Student array parallel to seatingAvailability to show students filed into
 * seats
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in
 * studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine; // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs; // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability; // represents the classroom seats that are available to students
    private Student[][] studentsSitting; // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * 
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom(SNode l, SNode m, boolean[][] a, Student[][] s) {
        studentsInLine = l;
        musicalChairs = m;
        seatingAvailability = a;
        studentsSitting = s;
    }

    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in
     * line.
     * 
     * Reads students from input file and inserts these students in alphabetical
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the
     * file, say x
     * 2) x lines containing one student per line. Each line has the following
     * student
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom(String filename) {
        // WRITE YOUR CODE HERE
        StdIn.setFile(filename);
        // read int
        int num = StdIn.readInt();
        // for(SNode start = studentsInLine; start != null; start = start.next)
        for (int i = 0; i < num; i++) {
            Student s = new Student(StdIn.readString(), StdIn.readString(), StdIn.readInt());
            if (i == 0)
                studentsInLine = new SNode(s, null);
            else {
                SNode ptr = studentsInLine;
                while (ptr != null) {
                    // if n > 0 means parameter student's name comes before this student
                    if (ptr.getStudent().compareNameTo(s) > 0) {
                        SNode prev = new SNode(s, ptr);
                        studentsInLine = prev;
                        break;
                    }
                    // if n < 0 means parameter student's name comes after this student
                    else {
                        SNode next = new SNode(s, null);
                        if (ptr.getNext() != null) {
                            while (ptr != null && ptr.getNext() != null
                                    && ptr.getNext().getStudent().compareNameTo(s) < 0) {
                                ptr = ptr.getNext();
                            }
                            next.setNext(ptr.getNext());
                            ptr.setNext(next);

                        } else {
                            ptr.setNext(next);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of
     * available seats inside the classroom. Imagine that unavailable seats are
     * broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an
     * available seat)
     * 
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(seatingChart);
        int row = StdIn.readInt();
        int col = StdIn.readInt();
        seatingAvailability = new boolean[row][col];
        studentsSitting = new Student[row][col];
        while (!StdIn.isEmpty()) {
            for (int i = 0; i < seatingAvailability.length; i++) {
                for (int j = 0; j < seatingAvailability[0].length; j++) {
                    seatingAvailability[i][j] = StdIn.readBoolean();
                }
            }
        }
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the
     * front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into
     * studentsSitting according to
     * seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents() {
        // WRITE YOUR CODE HERE
        for (int i = 0; i < studentsSitting.length; i++) {
            for (int j = 0; j < studentsSitting[0].length; j++) {
                if (studentsSitting[i][j] == null && seatingAvailability[i][j]) {
                    if (musicalChairs != null) {
                        SNode first = musicalChairs.getNext();
                        // case for only one element
                        if (musicalChairs.equals(first)) {
                            studentsSitting[i][j] = musicalChairs.getStudent();
                            musicalChairs = null;
                        }
                        // case for more than one
                        else {
                            studentsSitting[i][j] = musicalChairs.getNext().getStudent();
                            musicalChairs.getNext().setNext(musicalChairs.getNext().getNext());
                        }
                    } else if (studentsInLine != null) {
                        studentsSitting[i][j] = studentsInLine.getStudent();
                        studentsInLine = studentsInLine.getNext();
                    }
                }
            }
        }

    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then
     * moves
     * into second row.
     */
    public void insertMusicalChairs() {
        // WRITE YOUR CODE HERE
        // set musical chairs equal to first student
        // add students to end
        // set end equal to musical chairs
        for (int i = 0; i < studentsSitting.length; i++) {
            for (int j = 0; j < studentsSitting[0].length; j++) {
                if (studentsSitting[i][j] != null) {
                    Student addStu = studentsSitting[i][j];
                    studentsSitting[i][j] = null;
                    SNode addNode = new SNode(addStu, null);
                    if (musicalChairs == null) {
                        musicalChairs = addNode;
                        musicalChairs.setNext(musicalChairs);
                    } else {
                        addNode.setNext(musicalChairs.getNext());
                        musicalChairs.setNext(addNode);
                        musicalChairs = addNode;
                    }
                }
            }
        }

    }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is
     * only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using
     * StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first
     * student in the
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in
     * studentsInLine
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students
     * can be seated.
     */
    public void playMusicalChairs() {
        // needs fix
        // WRITE YOUR CODE HERE
        // count num of students
        int count = 1;
        for (SNode ptrCLL = musicalChairs.getNext(); !ptrCLL.equals(musicalChairs); ptrCLL = ptrCLL.getNext()) {
            count++;
        }
        int num = count;
        for (int i = 1; i < num; i++) {
            int elimIndex = StdRandom.uniform(count);
            SNode curr = musicalChairs.getNext();
            count--;
            // find where elim spot is
            SNode prev = musicalChairs;
            for (int j = 0; j < elimIndex; j++) {
                curr = curr.getNext();
                prev = prev.getNext();
            }
            // if musicalChairs pointer is deleted preset the musical chairs node to prev
            if (curr.equals(musicalChairs)) {
                musicalChairs = prev;
                musicalChairs.setNext(curr.getNext());
            }
            // elim the node
            // set prev ponter to curr.next
            prev.setNext(curr.getNext());

            // resets curr pointer
            curr.setNext(null);
            // sorts elim node in studentsInLine
            if (i == 1) {
                studentsInLine = curr;
            } else {
                SNode ptrLL = studentsInLine;
                while (ptrLL != null) {
                    // if ptrLL is taller then currStu place currStu before
                    if (ptrLL.getStudent().getHeight() >= curr.getStudent().getHeight()) {
                        curr.setNext(ptrLL);
                        studentsInLine = curr;
                        break;
                    }
                    // if ptrLL is shorter then currStu place currStu before ptrLL is taller
                    else {
                        curr.setNext(null);
                        if (ptrLL.getNext() != null) {
                            while (ptrLL != null && ptrLL.getNext() != null
                                    && ptrLL.getNext().getStudent().getHeight() <= curr.getStudent().getHeight()) {
                                ptrLL = ptrLL.getNext();
                            }
                            curr.setNext(ptrLL.getNext());
                            ptrLL.setNext(curr);

                        } else {
                            ptrLL.setNext(curr);
                        }
                        break;
                    }
                }
            }
        }
        seatStudents();
    }

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is
     * not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * 
     * @param firstName the first name
     * @param lastName  the last name
     * @param height    the height of the student
     */
    public void addLateStudent(String firstName, String lastName, int height) {

        // WRITE YOUR CODE HERE
        Student newStu = new Student(firstName, lastName, height);
        SNode newNode = new SNode(newStu, null);
        // if musical charis is not null add to muscial chairs
        if (musicalChairs != null) {
            newNode.setNext(musicalChairs.getNext());
            musicalChairs.setNext(newNode);
            musicalChairs = newNode;

        }
        // if standing in line is not null add to standing in line
        else if (studentsInLine != null) {
            SNode ptr = studentsInLine;
            while (ptr.getNext() != null) {
                ptr = ptr.getNext();
            }
            ptr.setNext(newNode);
        }
        // if seated is not null then add to seated
        // fix multiple seatings
        else {
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[0].length; j++) {
                    if (studentsSitting[i][j] == null && seatingAvailability[i][j]) {
                        studentsSitting[i][j] = newStu;
                        return;
                    }
                }
            }
        }
    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName  the student's last name
     */
    public void deleteLeavingStudent(String firstName, String lastName) {

        // WRITE YOUR CODE HERE
        // if musical charis is not null remove from muscial chairs
        if (musicalChairs != null) {
            // case for only one element
            if (musicalChairs.equals(musicalChairs.getNext())
                    && musicalChairs.getStudent().getFirstName().equalsIgnoreCase(firstName)
                    && musicalChairs.getStudent().getLastName().equalsIgnoreCase(lastName)) {
                musicalChairs = null;
            }
            // case for last element
            else if (musicalChairs.getStudent().getFirstName().equalsIgnoreCase(firstName)
                    && musicalChairs.getStudent().getLastName().equalsIgnoreCase(lastName)) {

                for (SNode ptr = musicalChairs.getNext().getNext(); !ptr.equals(musicalChairs.getNext()); ptr = ptr
                        .getNext()) {
                    if (ptr.getNext().getStudent().getFirstName().equalsIgnoreCase(firstName)
                            && ptr.getNext().getStudent().getLastName().equalsIgnoreCase(lastName)) {
                        ptr.setNext(ptr.getNext().getNext());
                        musicalChairs = ptr;
                        break;
                    }
                }
            }
            // case for more than one
            else {
                for (SNode ptr = musicalChairs.getNext(); !ptr.equals(musicalChairs); ptr = ptr.getNext()) {
                    if (ptr.getNext().getStudent().getFirstName().equalsIgnoreCase(firstName)
                            && ptr.getNext().getStudent().getLastName().equalsIgnoreCase(lastName)) {
                        ptr.setNext(ptr.getNext().getNext());
                        break;
                    }
                }
            }
        }
        // if standing in line is not null remove from muscial chairs
        else if (studentsInLine != null) {
            // head delete can have next and next can be null
            if (studentsInLine.getNext() != null
                    && studentsInLine.getStudent().getFirstName().equalsIgnoreCase(firstName)
                    && studentsInLine.getStudent().getLastName().equalsIgnoreCase(lastName)) {
                studentsInLine = studentsInLine.getNext();
            } else if (studentsInLine.getNext() == null
                    && studentsInLine.getStudent().getFirstName().equalsIgnoreCase(firstName)
                    && studentsInLine.getStudent().getLastName().equalsIgnoreCase(lastName)) {
                studentsInLine = null;
            }
            // middle has next && end delete remove pointer of prev
            // must compare to nextNode
            else {
                for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
                    if (ptr.getNext().getNext() != null
                            && ptr.getNext().getStudent().getLastName().equalsIgnoreCase(lastName)
                            && ptr.getNext().getStudent().getFirstName().equalsIgnoreCase(firstName)) {
                        ptr.setNext(ptr.getNext().getNext());
                        break;
                    } else if (ptr.getNext().getNext() == null
                            && ptr.getNext().getStudent().getLastName().equalsIgnoreCase(lastName)
                            && ptr.getNext().getStudent().getFirstName().equalsIgnoreCase(firstName)) {
                        ptr.setNext(null);
                        break;
                    }
                }
            }
        }
        // if seated is not null then remove from muscial chairs
        else {
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[0].length; j++) {
                    if (studentsSitting[i][j] != null
                            && studentsSitting[i][j].getFirstName().equalsIgnoreCase(firstName)
                            && studentsSitting[i][j].getLastName().equalsIgnoreCase(lastName)) {
                        studentsSitting[i][j] = null;
                        return;
                    }
                }
            }
        }

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine() {

        // Print studentsInLine
        StdOut.println("Students in Line:");
        if (studentsInLine == null) {
            StdOut.println("EMPTY");
        }

        for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print());
            if (ptr.getNext() != null) {
                StdOut.print(" -> ");
            }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents() {

        StdOut.println("Sitting Students:");

        if (studentsSitting != null) {

            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[i].length; j++) {

                    String stringToPrint = "";
                    if (studentsSitting[i][j] == null) {

                        if (seatingAvailability[i][j] == false) {
                            stringToPrint = "X";
                        } else {
                            stringToPrint = "EMPTY";
                        }

                    } else {
                        stringToPrint = studentsSitting[i][j].print();
                    }

                    StdOut.print(stringToPrint);

                    for (int o = 0; o < (10 - stringToPrint.length()); o++) {
                        StdOut.print(" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs() {
        StdOut.println("Students in Musical Chairs:");

        if (musicalChairs == null) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for (ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if (ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() {
        return studentsInLine;
    }

    public void setStudentsInLine(SNode l) {
        studentsInLine = l;
    }

    public SNode getMusicalChairs() {
        return musicalChairs;
    }

    public void setMusicalChairs(SNode m) {
        musicalChairs = m;
    }

    public boolean[][] getSeatingAvailability() {
        return seatingAvailability;
    }

    public void setSeatingAvailability(boolean[][] a) {
        seatingAvailability = a;
    }

    public Student[][] getStudentsSitting() {
        return studentsSitting;
    }

    public void setStudentsSitting(Student[][] s) {
        studentsSitting = s;
    }

}
