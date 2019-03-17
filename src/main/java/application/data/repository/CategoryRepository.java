package application.data.repository;

import application.data.model.Category;
import application.data.model.Product;
import org.hibernate.validator.constraints.EAN;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("select count(c.id) from dbo_category c")
    long getTotalCategories();


    @Query("SELECT p FROM dbo_category p " +
            "WHERE (:categoryName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:categoryName),'%'))")
    List<Category> getListCategoryByCategoryName(@Param("categoryName") String categoryName);
}
