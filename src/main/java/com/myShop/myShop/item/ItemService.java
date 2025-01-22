package com.myShop.myShop.item;

import com.myShop.myShop.Sales.Sales;
import com.myShop.myShop.Sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final SalesRepository salesRepository;
    public void saveItem(String title, Integer price, String imageUrl){

        var Item = new Item();
        Item.setTitle(title);
        Item.setPrice(price);
        Item.setImage(imageUrl);
        itemRepository.save(Item);
    }
    public List<Item> findItem(){
        var result = itemRepository.findAll();
        return result;
    }
    public Optional<Item> findbyid(Long id){
        var result = itemRepository.findById(id);
        System.out.println(result.get().getTitle());
        return result;
    }
    public void updateItem(Long id, String title, Integer price){
        Optional<Item>  optionalItem =itemRepository.findById(id);
        if(optionalItem.isPresent()){
            Item item = optionalItem.get();
            item.setTitle(title);
            item.setPrice(price);
            itemRepository.save(item);

        }
    }
    public void deleteItem(Long id){
        Optional<Item>  optionalItem = itemRepository.findById(id);
        if(optionalItem.isPresent()){
            itemRepository.deleteById(id);
        }

    }
    public void orderItem(Long userid,String title,Integer price,Integer count){
        var Order = new Sales();
        Order.setItemName(title);
        Order.setPrice(price);
        Order.setMemberId(userid);
        Order.setCount(count);
        salesRepository.save(Order);
    }
}
