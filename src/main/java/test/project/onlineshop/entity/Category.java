package test.project.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId) && Objects.equals(nameCategory, category.nameCategory) && Objects.equals(parentCategoryId, category.parentCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, nameCategory, parentCategoryId);
    }
}
