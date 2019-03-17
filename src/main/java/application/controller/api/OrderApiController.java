package application.controller.api;

import application.data.model.Category;
import application.data.model.Order;
import application.data.model.OrderProduct;
import application.data.model.Product;
import application.data.service.OrderService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.CategoryDTO;
import application.model.dto.OrderDTO;
import application.model.dto.ProductDTO;
import application.model.viewmodel.order.OrderProductVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/order")
public class OrderApiController {
    @Autowired
    public OrderService orderService;

    @GetMapping("/detail/{orderId}")
    public DataApiResult getDetailCategory(@PathVariable int orderId){
        DataApiResult result= new DataApiResult();

        try{
            Order order = orderService.findOne(orderId);
            if(order==null){
                result.setSuccess(false);
                result.setMessage("Can't find this category");
            } else {
                OrderDTO dto= new OrderDTO();
                dto.setId(order.getId());
                dto.setCustomerName(order.getCustomerName());
                dto.setUserName(order.getUserName());
                dto.setEmail(order.getEmail());
                dto.setPhoneNumber(order.getPhoneNumber());
                dto.setPrice(order.getPrice());
                dto.setAddress(order.getAddress());
                result.setSuccess(true);
                result.setMessage("Get detail order successfully !");
                result.setData(dto);
            }
        } catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

//    @GetMapping("/detail-product/{orderId}")
//    public DataApiResult getDetaiOrderProduct(@PathVariable int orderId){
//        DataApiResult result= new DataApiResult();
//
//        try{
//            Order order = orderService.findOne(orderId);
//            if(order==null){
//                result.setSuccess(false);
//                result.setMessage("Can't find this order");
//            } else {
//                List<OrderProductVM> orderProductList = new ArrayList<>();
//                for (OrderProduct orderProduct: order.getListProductOrders()) {
//                    OrderProductVM dto= new OrderProductVM();
//                    dto.setAmount(orderProduct.getAmount());
//                    dto.setPrice(orderProduct.getPrice());
//                    dto.setName(orderProduct.getProduct().getName());
//                    dto.setMainImage(orderProduct.getProduct().getMainImage());
//                    dto.setProductId(orderProduct.getProduct().getId());
//
//                    orderProductList.add(dto);
//                }
//
//                result.setSuccess(true);
//                result.setMessage("Get detail order successfully !");
//                result.setData(orderProductList);
//            }
//        } catch (Exception e){
//            result.setSuccess(false);
//            result.setMessage(e.getMessage());
//        }
//
//        return result;
//    }



    @PostMapping("/update/{orderId}")
    public BaseApiResult updateCategory(@PathVariable int orderId, @RequestBody OrderDTO dto){
        BaseApiResult result = new BaseApiResult();


        try {
            Order order = orderService.findOne(orderId);
            order.setAddress(dto.getAddress());
            order.setCustomerName(dto.getCustomerName());
            order.setPhoneNumber(dto.getPhoneNumber());
            order.setEmail(dto.getEmail());

            order.setCreatedDate(new Date());
            orderService.addNewOrder(order);
            result.setSuccess(true);
            result.setMessage("Update order " + order.getId() + " successfully:");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }


        return result;
    }

}
