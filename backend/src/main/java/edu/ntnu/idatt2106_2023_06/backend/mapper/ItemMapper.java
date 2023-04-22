package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.Store;

/**
 * This class is a mapper that maps between the Item model and the Item DTOs.
 *
 * @author Leon Egeberg Hesthaug, Trym Hamer Gudvangen
 */
public class ItemMapper {

    /**
     * This method maps an {@link ItemDTO} and a {@link Store} to an {@link Item}.
     *
     * @param itemDTO The item DTO to map
     * @param store   The store to map
     * @return        The mapped item
     */
    public static Item toItem(ItemDTO itemDTO, Store store){
        return Item.builder()
                .productName(itemDTO.name())
                .desc(itemDTO.description())
                .store(store)
                .expirationDate(itemDTO.expirationDate())
                .purchaseDate(itemDTO.purchaseDate())
                .price(itemDTO.price())
                .pictureLink(itemDTO.image())
                .build();
    }

    /**
     * This method maps an {@link Item} and a quantity to an {@link ItemDTO}.
     *
     * @param item      The item to map
     * @param quantity  The quantity to map
     * @return          The mapped item DTO
     */
    public static ItemDTO toItemDTO(Item item, int quantity){
        return new ItemDTO(item.getProductName(), item.getDesc(),
                item.getStore().getStoreName(), item.getPrice(),
                item.getPurchaseDate(), item.getExpirationDate(),
                item.getPictureLink(), quantity);
    }
}
