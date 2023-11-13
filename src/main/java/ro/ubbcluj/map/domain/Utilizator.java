package ro.ubbcluj.map.domain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long> {
    private String firstName;
    private String lastName;
    private List<Utilizator> friends;

    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends=new ArrayList<>();
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

    public List<Utilizator> getFriends() {
        return friends;
    }

    public void setFriends(List<Utilizator> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        StringBuilder prieteni= new StringBuilder();
        friends.forEach(u->prieteni.append(u.firstName).append(' ').append(u.lastName).append(','));
        if(!prieteni.isEmpty())
            prieteni.delete(prieteni.length()-1,prieteni.length());
        return "Utilizator{" +
                "id="+this.getId()+'\''+
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" + prieteni +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator that)) return false;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }
}