package application.data.repository;

import application.data.model.Order;
import application.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM dbo_order o " +
            "WHERE (:guid IS NULL OR (o.guid = :guid))" +
            "AND (:userName IS NULL OR (o.userName = :userName))")
    List<Order> findOrderByGuidOrUserName(@Param("guid") String guid, @Param("userName") String userName);

    @Query("SELECT o FROM dbo_order o ")
    List<Order> GetAll();

    @Query("SELECT o FROM dbo_order o "+
            "WHERE (:customerName IS NULL OR UPPER(o.customerName) LIKE CONCAT('%',UPPER(:customerName),'%'))")
    List<Order> GetByCustomerName(@Param("customerName") String customerName);

//    @Query("SELECT p " +
//            "FROM dbo_order_product o join dbo_product p on(o.product_id=p.product_id)"+
//            "WHERE (o.order_id=:orderId)")
//    @Query("SELECT p " +
//            "FROM dbo_product p" +
//            " where p.product_id in(" +
//            " select product_id" +
//            "    from dbo_order_product" +
//            "    where order_id=:orderID" +
//            ")")
//    List<Product> GetOrderProduct(@Param("orderId") int orderId);

}
