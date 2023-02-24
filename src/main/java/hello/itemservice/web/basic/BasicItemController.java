package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    // 상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 상품등록 Form
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    // addForm.html 에서 속성 name 에 담겨서 데이터가 넘어온다.
    // 상품등록
    /*@PostMapping("/add")
    public String save(@RequestParam String itemName, @RequestParam Integer price, @RequestParam Integer quantity, Model model){
        Item item = new Item(itemName, price, quantity);
        Item savedItem = itemRepository.save(item);
        model.addAttribute("item", savedItem);
        return "basic/item";
    }*/

    /*@PostMapping("/add")
    public String save(@ModelAttribute("item") Item item, Model model){
        itemRepository.save(item);
        //model.addAttribute("item", savedItem);     // 자동 추가, 생략 가능
        return "basic/item";
    }*/

    /*@PostMapping("/add")
    public String save(@ModelAttribute Item item, Model model){
        itemRepository.save(item);
        //model.addAttribute("item", savedItem);     // 자동 추가, 생략 가능
        return "basic/item";
    }*/

    //상품 등록
    /*@PostMapping("/add")
    public String save(Item item){
        itemRepository.save(item);
        //model.addAttribute("item", savedItem);     // 자동 추가, 생략 가능
        return "basic/item";
    }*/

   /* @PostMapping("/add")
    public String save(Item item){
        itemRepository.save(item);
        //model.addAttribute("item", savedItem);     // 자동 추가, 생략 가능
        return "redirect:/basic/items/" + item.getId();
    } */

    @PostMapping("/add")
    public String save(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        //model.addAttribute("item", savedItem);     // 자동 추가, 생략 가능
        return "redirect:/basic/items/{itemId}";
    }

    // 상품수정 Form
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/editForm";
    }

    // 상품 수정 저장
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }



    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
