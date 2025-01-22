package com.myShop.myShop.item;

import com.myShop.myShop.comment.CommentRepository;
import com.myShop.myShop.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {


    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentService commentService;
    private final CommentRepository commentRepository;


    @GetMapping("/list")
    String list(Model model) {


        var result = itemService.findItem();
        model.addAttribute("items", result);
        return "list.html";
    }
    @GetMapping("/write")
    String write(Model model) {


        return "write.html";
    }
    @PostMapping("/add")
    String add(@RequestParam String title,@RequestParam Integer price, @RequestParam String imageUrl) {
        itemService.saveItem(title, price, imageUrl);

        return "redirect:/list/page/1";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
        try {
            var result = itemService.findbyid(id);
            var comment = commentRepository.findAllByParentId(id);



            if(result.isEmpty()) {
                return "redirect:/list/page/1";
            }
            else {

                model.addAttribute("title", result.get().getTitle());
                model.addAttribute("price", result.get().getPrice());
                model.addAttribute("id", id);

                model.addAttribute("comment", comment);

            }

            return "detail.html";
        }catch (Exception e) {
            return "redirect:/list/page/1";
        }

    }
    @GetMapping("/retext/{id}")
    String retext(@PathVariable Long id, Model model) {

        try {
            var result = itemService.findbyid(id);
            if(result.isEmpty()) {
                return "redirect:/list/page/1";
            }
            else {
                id = id-1;
                model.addAttribute("title", result.get().getTitle());
                model.addAttribute("price", result.get().getPrice());
                model.addAttribute("id", id);
            }

            return "retext.html";
        }catch (Exception e) {
            return "redirect:/list/page/1";
        }

    }

    @PostMapping("/modi")
    String modi(@RequestParam Long id,@RequestParam String title,@RequestParam Integer price) {
        itemService.updateItem(id+1, title, price);
        return "redirect:/list/page/1";
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }


    @GetMapping("/list/page/{id}")
    String pagelist(Model model, @PathVariable Integer id) {


        var result = itemRepository.findPageBy(PageRequest.of(id-1,5));
        var pagelist = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("pages", pagelist);
        return "list.html";
    }
    @GetMapping("/presigned-url")
    @ResponseBody
    String getUrl(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("test/"+filename);

        return result;
    }

    @PostMapping("/search")
    String search(@RequestParam String searchtext, Model model) {
        var result = itemRepository.fullTextSearch(searchtext);
        if(result.isEmpty()) {
            return "redirect:/list/page/1";
        }
        else {
            model.addAttribute("items", result);

        }
        return "searchlist.html";
    }
    @GetMapping("/orderpage/{id}")
    String orderpage(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            var result = itemService.findbyid(id);
            CustomUser user = (CustomUser) authentication.getPrincipal();
            if(result.isEmpty()) {
                return "redirect:/list/page/1";
            }
            else {

                model.addAttribute("title", result.get().getTitle());
                model.addAttribute("price", result.get().getPrice());
                model.addAttribute("id", id);
                model.addAttribute("user", user.getUsername());
                model.addAttribute("userid",user.id);

            }

            return "Sales.html";
        }catch (Exception e) {
            return "redirect:/list/page/1";
        }

    }
    @PostMapping("/orderme")
    String orderme(@RequestParam Long userid, @RequestParam String title, @RequestParam Integer price,@RequestParam Integer count) {
        itemService.orderItem(userid, title, price, count);

        return "redirect:/list/page/1";
    }
}
