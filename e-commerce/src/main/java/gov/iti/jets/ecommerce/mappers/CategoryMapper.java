package gov.iti.jets.ecommerce.mappers;

import gov.iti.jets.ecommerce.DTO.CategoryDTO;
import gov.iti.jets.ecommerce.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();

        // ⚠️ handle null ID (important for insert vs update)
        if (dto.getCategoryId() != null) {
            category.setCategoryId(dto.getCategoryId());
        }

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return category;
    }

    public static List<CategoryDTO> toDTOList(List<Category> categories) {
        return categories
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Category> toEntityList(List<CategoryDTO> dtos) {
        return dtos
                .stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toList());
    }
}