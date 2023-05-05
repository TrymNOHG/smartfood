package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.utils.UnitParser;

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
        Object[] parsedUnit = UnitParser.parse(itemDTO.name());

        return Item.builder()
                .productName(itemDTO.name())
                .desc(itemDTO.description())
                .store(store)
                .price(itemDTO.price())
                .pictureLink(itemDTO.image())
                .ean(itemDTO.EAN())
                .amount((Double) parsedUnit[0])
                .unit((String) parsedUnit[1])
                .expiresIn(14)
                .build();
    }

    /**
     * This method maps an {@link Item} and a quantity to an {@link ItemDTO}.
     *
     * @param item      The item to map
     * @param quantity  The quantity to map
     * @return          The mapped item DTO
     */
    public static ItemDTO toItemDTO(Item item, int quantity, Boolean suggestion){
        return ItemDTO
                .builder()
                .name(item.getProductName())
                .description(item.getDesc())
                .store(item.getStore().getStoreName())
                .price(item.getPrice())
                .image(item.getPictureLink())
                .quantity(quantity)
                .suggestion(suggestion)
                .EAN(item.getEan())
                .build();
    }
}
