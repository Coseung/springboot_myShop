package com.myShop.myShop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

    @Query(value = "SELECT * FROM shop.item WHERE MATCH(title) AGAINST(?1)", nativeQuery =true)
    List<Item> fullTextSearch(String text);
}
