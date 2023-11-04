package ro.ubbcluj.map.domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;

    public Prietenie(Long userId1, Long userId2, LocalDateTime date) {
        this.setId(new Tuple<>(userId1, userId2));
        this.date = date;
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "id=" + getId() +
                ", date=" + date +
                '}';
    }
}
