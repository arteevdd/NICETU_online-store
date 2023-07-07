package test.project.onlineshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CategoryDto {

    private Integer parentCategoryId;

    private Integer categoryId;

    private String nameCategory;

    public CategoryDto(Integer parentCategoryId, Integer categoryId, String nameCategory) {
        this.parentCategoryId = parentCategoryId;
        this.categoryId = categoryId;
        this.nameCategory = nameCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(parentCategoryId, that.parentCategoryId) && Objects.equals(categoryId, that.categoryId) && Objects.equals(nameCategory, that.nameCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentCategoryId, categoryId, nameCategory);
    }
}
