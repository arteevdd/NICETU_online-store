package test.project.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "producer")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producer_id")
    private Integer producerId;

    @Column(name = "name_producer", length = 50, unique = true, nullable = false)
    private String nameProducer;

    @JsonIgnore
    @OneToMany(mappedBy = "producerId")
    private Collection<Product> products;

    public Producer() {
    }

    public Producer(String nameProducer) {
        this.nameProducer = nameProducer;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "producerId=" + producerId +
                ", nameProducer='" + nameProducer + '\'' +
                '}';
    }
}
