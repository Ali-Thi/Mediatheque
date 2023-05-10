package mediatheque;

public record Member(int id, String firstName, String lastName) {

    @Override
    public boolean equals(Object o) {
        if(o instanceof Member m){
            return m.id() == id && m.firstName.equals(firstName) && m.lastName.equals(lastName);
        }
        return false;
    }
}
