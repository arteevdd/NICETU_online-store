package test.project.onlineshop.entity;

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

    @OneToMany(mappedBy = "categoryId")
    private Collection<Category> categories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategoryId;

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
