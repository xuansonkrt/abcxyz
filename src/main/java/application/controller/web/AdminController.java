package application.controller.web;


import application.data.model.Category;
import application.data.model.Order;
import application.data.model.Product;
import application.data.model.User;
import application.data.service.CategoryService;
import application.data.service.OrderService;
import application.data.service.ProductService;
import application.model.viewmodel.admin.AdminCategoryVM;
import application.model.viewmodel.admin.AdminOrderVM;
import application.model.viewmodel.admin.AdminProductVM;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.common.CategoryVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.order.OrderVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


    @Autowired
    private OrderService orderService;


    @GetMapping("")
    public String admin(Model model){

        HomeAdminVM vm = new HomeAdminVM();
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());

        model.addAttribute("vm",vm);
        return "/admin/home";
    }


    @GetMapping("/product")
    public String product(Model model,
                          @Valid @ModelAttribute("productname") ProductVM productName,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "8") Integer size
                          ) {
        AdminProductVM vm = new AdminProductVM();


        /**
         * set list categoryVM
         */
        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }


        Pageable pageable = new PageRequest(page, size);

        Page<Product> productPage = null;

       if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
            vm.setKeyWord("Find with key: " + productName.getName());
       } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null);
       }


        List<ProductVM> productVMList = new ArrayList<>();

        for(Product product : productPage.getContent()) {
            ProductVM productVM = new ProductVM();
            if(product.getCategory() == null) {
                productVM.setCategoryName("Unknown");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }
            productVM.setId(product.getId());
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
            productVM.setCreatedDate(product.getCreatedDate());

            productVMList.add(productVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);
        vm.setProductVMList(productVMList);
        if(productVMList.size() == 0) {
            vm.setKeyWord("Not found any product");
        }


        model.addAttribute("vm",vm);
        model.addAttribute("page",productPage);

        return "/admin/product";
    }

    @GetMapping("/category")
    public String category(Model model,
                          @Valid @ModelAttribute("categoryName") CategoryVM categoryName
    ) {
        AdminCategoryVM vm = new AdminCategoryVM();

        /**
         * set list categoryVM
         */
        List<Category> categoryList=new ArrayList<>();// = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        if (categoryName.getName()!=null) {
            categoryList = categoryService.getListCategoryByCategoryName(categoryName.getName());
            vm.setKeyWord("Find with key: " + categoryName.getName());

        } else{
            categoryList = categoryService.getListAllCategories();

        }

        for(Category category : categoryList) {
            CategoryVM categoryVM2 = new CategoryVM();
            categoryVM2.setId(category.getId());
            categoryVM2.setName(category.getName());
            categoryVM2.setCreatedDate(category.getCreatedDate());
            categoryVM2.setShortDesc(category.getShortDesc());

            categoryVMList.add(categoryVM2);
        }






        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);
        if(categoryVMList.size() == 0) {
            vm.setKeyWord("Not found any category");
        }
        model.addAttribute("vm",vm);

        return "/admin/category";
    }

    @GetMapping("/order")
    public String order(Model model
                          ,@Valid @ModelAttribute("orderName") OrderVM orderName
    ) {
        AdminOrderVM vm = new AdminOrderVM();

        /**
         * set list categoryVM
         */
       List<Order> orderList= new ArrayList<>();
       List<OrderVM> orderVMList= new ArrayList<>();
     //  orderList= orderService.GetAll();
        orderList = orderService.GetByCustomerName(orderName.getCustomerName());
        if(orderName.getCustomerName()!=null){
            vm.setKeyWord("Find with key: " + orderName.getCustomerName());
            orderList = orderService.GetByCustomerName(orderName.getCustomerName());
        } else{
            orderList= orderService.GetAll();

        }
        for(Order order : orderList) {
            OrderVM orderVM = new OrderVM();
            orderVM.setAddress(order.getAddress());
            orderVM.setCreatedDate(order.getCreatedDate());
            orderVM.setCustomerName(order.getCustomerName());
            orderVM.setEmail(order.getEmail());
            orderVM.setPhoneNumber(order.getPhoneNumber());
            orderVM.setPrice(order.getPrice());
            orderVM.setId(order.getId());
            orderVMList.add(orderVM);
        }
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setOrderVMList(orderVMList);
        if(orderVMList.size() == 0) {
            vm.setKeyWord("Not found any order");
        }
        model.addAttribute("vm",vm);

        return "/admin/order";
    }
}
