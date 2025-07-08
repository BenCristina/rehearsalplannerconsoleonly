public class Person {
    private final int staffId; // Staff ID (will be auto-generated)
    private static int nextStaffId = 1; // Initializes StaffId to 1
    private String firstName; // Staff member first name
    private String lastName; // Staff member last name
    private String email; // Staff member email
    private String phone; // Staff member phone

    public Person(String firstName, String lastName, String email, String phone) {
        this.staffId = nextStaffId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public int getStaffId() {
        return staffId;
    }

    public static int getNextStaffId() {
        return nextStaffId;
    }

    public static void setNextStaffId(int nextStaffId) {
        Person.nextStaffId = nextStaffId;
    }

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

    @Override
    public String toString() {
        return String.format("ID: %d, %s %s, Email: %s, Phone: %s", staffId, firstName, lastName, email, phone);
    }
}
