package Lab5.Project_mini_final_without_Security.controller;

import Lab5.Project_mini_final_without_Security.model.Category;
import Lab5.Project_mini_final_without_Security.model.User;
import Lab5.Project_mini_final_without_Security.repository.CategoryRepository;
import Lab5.Project_mini_final_without_Security.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Controller
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @PostMapping("/create")
//    public Category create (@RequestBody Category c){
//        return categoryService.create(c);
//    }

    @GetMapping("/categories")
    public String showCategories(Model model) {
        try {
            List<Category> categories = categoryService.show();
            model.addAttribute("categories", categories);
            return "category-list";
        } catch (Exception e) {
            return "error";
        }
    }



//
//    @PutMapping("/edit")
//    public Category edit (@RequestBody Category c){
//        return categoryService.edit(c);
//    }
//
//    @GetMapping("/show")
//    public List<Category> show (){
//        return categoryService.show();
//    }
//
//    @DeleteMapping("/delete")
//    public void delete(@RequestParam BigInteger category_id){
//        categoryService.delete(category_id);
//    }
}
