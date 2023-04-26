package test.project.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
//@JsonPropertyOrder({"parentCategoryId", "categoryId", "nameCategory"})
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
    @JoinColumn(name = "parent_category_id")
    private Category parentCategoryId;

    @JsonIgnore
    @OneToMany(mappedBy = "parentCategoryId")
    private Collection<Category> parentCategories;

    public Category() {
    }

    public Category(Integer categoryId, String nameCategory, Category parentCategoryId) {
        this.categoryId = categoryId;
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
