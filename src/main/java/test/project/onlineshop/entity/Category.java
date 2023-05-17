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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")

    private Integer categoryId;

    @Column(name = "name_category")
    private String nameCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategoryId;

    @JsonIgnore
    @OneToMany(mappedBy = "categoryId")
    private Collection<Category> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "parentCategoryId")
    private Collection<Category> parentCategories;

    public Category() {
    }

    public Category(String nameCategory, Category parentCategoryId) {
        this.nameCategory = nameCategory;
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", nameCategory='" + nameCategory + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                '}';
    }
}
