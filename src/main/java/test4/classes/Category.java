package test4.classes;

import java.util.Objects;

public class Category {
    private final String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
//        if (categoryName == null){
//            throw new NullPointerException();
//
//        } else if (categoryName.equals("")){
//            throw new IllegalAccessException("Category name can't be empty");
//        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryName.equals(category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }

    @Override
    public String toString() {
        return "Category: categoryName: " + categoryName;
    }
}
