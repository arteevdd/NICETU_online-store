package test.project.onlineshop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id", nullable = false)
    private Integer catalogId;

    @Column(name = "name_catalog")
    private String nameCatalog;

    public Catalog() {
    }

    public Catalog(String nameCatalog) {
        this.nameCatalog = nameCatalog;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "catalogId=" + catalogId +
                ", nameCatalog='" + nameCatalog + '\'' +
                '}';
    }
}
