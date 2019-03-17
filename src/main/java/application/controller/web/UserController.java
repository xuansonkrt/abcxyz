package application.controller.web;

import application.data.model.*;
import application.data.service.UserService;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.order.OrderVM;
import application.model.viewmodel.user.ChangePasswordVM;
import application.model.viewmodel.user.UserDetailVM;
import application.model.viewmodel.user.UserVM;
import javassist.bytecode.ExceptionsAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController extends  BaseController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;



    @GetMapping("/detail")
    public String userDetail(Model model,
                             @Valid @ModelAttribute("productname") ProductVM productName,
                             final Principal principal){

        UserDetailVM vm = new UserDetailVM();
        UserVM userVM = new UserVM();
        if(principal!= null) {
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            User userEntity = userService.findUserByUsername(username);
            if(userEntity!= null) {
                userVM.setName(userEntity.getName());
                userVM.setEmail(userEntity.getEmail());
                userVM.setGender(userEntity.getGender());
                userVM.setAvatar(userEntity.getAvatar());
                userVM.setAddress(userEntity.getAddress());
                userVM.setPhone_number(userEntity.getPhoneNumber());

            }

            vm.setLayoutHeaderVM(this.getLayoutHeaderVM());

            model.addAttribute("userVM",userVM);
            model.addAttribute("vm",vm);

        }
       // return "redirect:/403"; return "/user-detail";
        return "/user-detail";
    }

    @PostMapping("/update")
    public String checkout(@Valid @ModelAttribute("userVM") User user) {

        try{
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            User userEntity = userService.findUserByUsername(username);
            userEntity.setName(user.getName());
            userEntity.setEmail(user.getEmail());
            userEntity.setGender(user.getGender());
            userEntity.setAvatar(user.getAvatar());
            userEntity.setAddress(user.getAddress());
            userEntity.setPhoneNumber(user.getPhoneNumber());
            userService.updateUser(userEntity);
        } catch (Exception ex){

        }


        return "redirect:/user/detail?success";
    }
    @GetMapping("/change-password")
    public String changePassword(Model model,
                             @Valid @ModelAttribute("productname") ProductVM productName,
                             final Principal principal) {

        ChangePasswordVM vm = new ChangePasswordVM();
        vm.setLayoutHeaderVM(this.getLayoutHeaderVM());
        model.addAttribute("vm",vm);
        return "/change-password";
    }


}
