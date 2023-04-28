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

    @JsonIgnore
    @OneToMany(mappedBy = "categoryId")
    private Collection<Category> categories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_category_id")
    private Category childCategoryId;

    @JsonIgnore
    @OneToMany(mappedBy = "childCategoryId")
    private Collection<Category> childCategories;

    public Category() {
    }

    public Category(String nameCategory, Category childCategoryId) {
        this.nameCategory = nameCategory;
        this.childCategoryId = childCategoryId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", nameCategory='" + nameCategory + '\'' +
                ", childCategoryId=" + childCategoryId +
                '}';
    }
}
