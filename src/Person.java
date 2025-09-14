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

    // Getters for personal information
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * Provides a string representation of the person's information.
     * Format: "ID: X, FirstName LastName, Email: email@example.com, Phone: XXX"
     * @return Formatted string with person's details
     */
    @Override
    public String toString() {
        return String.format("%s %s, Email: %s, Phone: %s", firstName, lastName, email, phone);
    }
}
