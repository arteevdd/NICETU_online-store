package test.project.onlineshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
}
