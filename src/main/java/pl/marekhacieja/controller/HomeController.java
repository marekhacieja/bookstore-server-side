package pl.marekhacieja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pl.marekhacieja.service.BookService;
import pl.marekhacieja.service.OrderService;
import pl.marekhacieja.service.UserService;

@Controller
public class HomeController {

    private BookService bookService;
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public HomeController(BookService bookService, OrderService orderService, UserService userService) {
        this.bookService = bookService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String referToHome(Model model) {
        bookService.addAttributeBooks(model);
        return "home";
    }

    @GetMapping("/basket")
    public String referToBooks(Model model) {
        orderService.addAttributeOrder(model);
        orderService.addAttributeSum(model);
        return "basket";
    }

    @GetMapping("/users/{id}")
    public String referToMyAccount(@PathVariable Long id, Model model){
        userService.addAttributeUser(getLoggedUserName(), model);
        return "account";
    }

    @PostMapping("/books/{id}")
    public String addBookToOrder(@PathVariable Long id, Model model) {
        orderService.addBookToOrder(id, model);
        return "message";
    }

     private String getLoggedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }
}