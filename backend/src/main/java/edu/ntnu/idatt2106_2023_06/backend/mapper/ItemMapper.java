package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.Store;

public class ItemMapper {

    public static Item toItem(ItemDTO itemDTO, Store store){
        return Item.builder()
                .productName(itemDTO.name())
                .briefDesc(itemDTO.description())
                .store(store)
                .expirationDate(itemDTO.expirationDate())
                .purchaseDate(itemDTO.purchaseDate())
                .price(itemDTO.price())
                .pictureLink(itemDTO.image())
                .build();
    }

    public static ItemDTO toItemDTO(Item item){
        return new ItemDTO(item.getProductName(), item.getBriefDesc(),
                item.getStore().getStoreName(), item.getPrice(),
                item.getPurchaseDate(), item.getExpirationDate(),
                item.getPictureLink());
    }
}
