public class Person {
    // Unique identifier for each person
    private static int staffId;

    // Static counter for generating unique staff IDs, shared across all instances
    private static int nextStaffId = 1; // Initializes StaffId to 1

    // Basic personal information
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    /**
     * Creates a new Person with provided personal information.
     * Automatically assigns a unique staff ID.
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param email Contact email address
     * @param phone Contact phone number
     */
    public Person(String firstName, String lastName, String email, String phone) {
        // Assign and increment the staff ID counter
        staffId = nextStaffId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    /**
     * @return The unique staff ID assigned to this person
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * @return The next available staff ID that will be assigned
     */
    public static int getNextStaffId() {
        return nextStaffId;
    }

    /**
     * Sets the next staff ID to be assigned.
     * Useful when restoring state from saved data.
     *
     * @param nextStaffId The next ID to be used
     */
    public static void setNextStaffId(int nextStaffId) {
        Person.nextStaffId = nextStaffId;
    }

    // Getters and setters for personal information
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Provides a string representation of the person's information.
     * Format: "ID: X, FirstName LastName, Email: email@example.com, Phone: XXX"
     * @return Formatted string with person's details
     */
    @Override
    public String toString() {
        return String.format("ID: %d, %s %s, Email: %s, Phone: %s", staffId, firstName, lastName, email, phone);
    }
}
