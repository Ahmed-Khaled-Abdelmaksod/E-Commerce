package gov.iti.jets.ecommerce.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link gov.iti.jets.ecommerce.entity.Category}
 */
public class CategoryDTO implements Serializable {
    private Integer categoryId;
    private String name;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO entity = (CategoryDTO) o;
        return Objects.equals(this.categoryId, entity.categoryId) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "categoryId = " + categoryId + ", " +
                "name = " + name + ", " +
                "description = " + description + ")";
    }
}