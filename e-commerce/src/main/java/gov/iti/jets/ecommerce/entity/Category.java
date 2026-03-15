package gov.iti.jets.ecommerce.entity;



public class Category {

    private Integer categoryId;
    private String name;
    private String description;

    // Getters and Setters
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}