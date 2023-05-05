package edu.ntnu.idatt2106_2023_06.backend.service.items;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.*;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemSearchDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.RecipeShoppingDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.*;
import edu.ntnu.idatt2106_2023_06.backend.mapper.FridgeItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ShoppingItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScore;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ShoppingItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.store.StoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.notification.NotificationService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.sortAndFilter.*;
import edu.ntnu.idatt2106_2023_06.backend.utils.UnitParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**

 ItemService is responsible for handling requests related to items, including adding items to the store,
 fridge and shopping list. It also has methods to retrieve items from the fridge and shopping list and delete
 them from the fridge.
 */
@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {

    private final ItemRecipeScoreService itemRecipeScoreService;
    private final NotificationService notificationService;
    private final ItemRepository itemRepository;
    private final FridgeItemsRepository fridgeItemsRepository;
    private final FridgeRepository fridgeRepository;
    private final StoreRepository storeRepository;
    private final ShoppingItemsRepository shoppingItemsRepository;
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final UserRepository userRepository;
    private final FridgeMemberRepository fridgeMemberRepository;
    private final JwtService jwtService;
    private final FridgeService fridgeService;

    //TODO: add
    //        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
    /**
     * Adds an item to the item database.
     *
     * @param itemDTO                   The ItemDTO containing the item to add.
     * @return                          The item created or found using the itemDTO.
     * @throws StoreNotFoundException   if the store cannot be found.
     * @throws ItemNotFoundException    if the item cannot be found.
     */
    @Override
    public Item addItem(ItemDTO itemDTO) {
        if (itemDTO.price() < 0) throw  new IllegalArgumentException("Cannot have negative price");
        Store store = storeRepository.findByStoreName(itemDTO.store())
                .orElseGet(() -> storeRepository.save(
                Store.builder()
                .storeName(itemDTO.store())
                .itemsInStore(new ArrayList<>())
                .build()));

        Item item = itemRepository.findByProductNameAndStore(itemDTO.name(), store).orElse(null);
        if (item != null) {
            item.setPrice(itemDTO.price());
            itemRepository.save(item);
            return item;
        }

        Item i = ItemMapper.toItem(itemDTO, store);
        itemRepository.save(i);

        item = itemRepository.findByProductNameAndStore(itemDTO.name(), store).orElseThrow(() -> new ItemNotFoundException(itemDTO.name()));

        itemRecipeScoreService.generateScoreForItem(item.getItemId());

        return item;
    }

    /**
     * Adds an item to the fridge.
     *
     * @param itemDTO The dto containing the item information
     * @param fridgeId The ID of the fridge to add the item to.
     * @throws ItemNotFoundException if the item cannot be found.
     * @throws FridgeNotFoundException if the fridge cannot be found.
     */
    @Override
    public void addToFridge(ItemDTO itemDTO, Long fridgeId) {
        logger.info("User wants to add a new item to fridge");
        Item item = addItem(itemDTO);

        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");

        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        FridgeItems fridgeItem = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseGet(() ->
                FridgeItems.builder()
                        .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                        .item(item)
                        .fridge(fridge)
                        .amount(0)
                        .purchaseDate(LocalDateTime.now())
                        .expirationDate(LocalDateTime.now().plusDays(item.getExpiresIn()))
                        .build());

        fridgeItem.setAmount(fridgeItem.getAmount() + itemDTO.quantity() * fridgeItem.getItem().getAmount());
        fridgeItemsRepository.save(fridgeItem);
    }

    /**
     * Adds an item to the shopping list for the specified fridge.
     *
     * @param itemDTO The itemDTO containing the essential information for an item.
     * @param fridgeId The ID of the fridge to add the item to the shopping list for.
     * @param suggestion A boolean indicating whether the item is a suggested item or not.
     * @throws ItemNotFoundException if the specified item ID does not exist.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     */
    @Override
    public void addToShoppingList(ItemDTO itemDTO, Long fridgeId, boolean suggestion) {
        logger.info("User wants to add a new item to shopping list");
        Item item = addItem(itemDTO);

        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");


        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, suggestion).orElse(null);
        if(shoppingItem == null){
            shoppingItem = ShoppingItems.builder()
                    .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                    .item(item)
                    .fridge(fridge)
                    .quantity(itemDTO.quantity())
                    .suggestion(suggestion)
                    .build();
        }
        else {
            shoppingItem.setQuantity(shoppingItem.getQuantity() + itemDTO.quantity());
        }
        shoppingItemsRepository.save(shoppingItem);
    }

    //TODO: add if
    @Transactional
    public void addIngredientsToShoppingList(RecipeShoppingDTO recipeShoppingDTO, String username) {
        boolean isSuperUser = fridgeMemberRepository.findFridgeMemberByFridge_FridgeIdAndUser_Username(recipeShoppingDTO.fridgeId(), username)
                .orElseThrow(() -> new UnauthorizedException(username)).isSuperUser();

        Fridge fridge = fridgeRepository.findByFridgeId(recipeShoppingDTO.fridgeId())
                .orElseThrow(() -> new FridgeNotFoundException(recipeShoppingDTO.fridgeId()));

        for(Long itemId : recipeShoppingDTO.itemIds()) {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException(itemId));
            ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, !isSuperUser).orElse(null);
            if(shoppingItem == null){
                shoppingItem = ShoppingItems.builder()
                        .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                        .item(item)
                        .fridge(fridge)
                        .quantity(1)
                        .suggestion(!isSuperUser)
                        .build();
            }
            else {
                shoppingItem.setQuantity(shoppingItem.getQuantity() + 1);
            }
            shoppingItemsRepository.save(shoppingItem);
        }
    }

    /**
     * This method updates a fridge item
     *
     * @param fridgeItemUpdateDTO   The DTO containing the information of the update fridge item.
     */
    @Transactional
    public void updateFridgeItem(FridgeItemUpdateDTO fridgeItemUpdateDTO){
        logger.info("Searching for the fridge item to change");
        FridgeItems fridgeItem = fridgeItemsRepository.findByItem_ItemIdAndFridge_FridgeId(fridgeItemUpdateDTO.itemId(), fridgeItemUpdateDTO.fridgeId())
                .orElseThrow(() -> new FridgeItemsNotFoundException(fridgeItemUpdateDTO.itemId()));
        logger.info("Fridge item was found");

        logger.info("Changing the fridge item");
        fridgeItem.setAmount(fridgeItemUpdateDTO.amount() != null && fridgeItemUpdateDTO.amount() > 0 ?
                fridgeItemUpdateDTO.amount() : fridgeItem.getAmount());

        fridgeItem.setPurchaseDate(fridgeItemUpdateDTO.purchaseDate() != null ?
                fridgeItemUpdateDTO.purchaseDate() : fridgeItem.getPurchaseDate());

        fridgeItem.setExpirationDate(fridgeItemUpdateDTO.expirationDate() != null ?
                fridgeItemUpdateDTO.expirationDate() : fridgeItem.getExpirationDate());

        fridgeItemsRepository.save(fridgeItem);
    }

    /**
     * This method updates a shopping list item. This may for example be changing a suggestion to an item that can
     * be bought.
     *
     * @param shoppingItemUpdateDTO   The DTO containing the information of the update shopping list item.
     */
    @Transactional
    public void updateShoppingItem(ShoppingItemUpdateDTO shoppingItemUpdateDTO, String username){
        logger.info("Searching for the shopping item to change");
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItem_ItemIdAndFridge_FridgeId(shoppingItemUpdateDTO.itemId(), shoppingItemUpdateDTO.fridgeId())
                .orElseThrow(() -> new FridgeItemsNotFoundException(shoppingItemUpdateDTO.itemId()));
        logger.info("Shopping item was found");

        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));

        logger.info("Changing the shopping item");
        shoppingItem.setQuantity(shoppingItemUpdateDTO.quantity() != null && shoppingItemUpdateDTO.quantity() >= 1 ?
                shoppingItemUpdateDTO.quantity() : shoppingItem.getQuantity());

        shoppingItem.setSuggestion(shoppingItemUpdateDTO.suggestion() != null ?
                shoppingItemUpdateDTO.suggestion() : shoppingItem.isSuggestion());

        shoppingItem.setUser(user);

        shoppingItemsRepository.save(shoppingItem);
    }

    /**
     * Removes the specified quantity of an item from the specified fridge.
     *
     * @param itemRemoveDTO A DTO object containing the details of the item to remove.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if the specified item does not exist in the specified fridge.
     */
    @Override
    public void removeItemFromFridge(ItemRemoveDTO itemRemoveDTO) {
        if (itemRemoveDTO.quantity() < 0) throw new IllegalArgumentException("Cannot have negative amount");
        Store store = storeRepository.findByStoreName(itemRemoveDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemRemoveDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemRemoveDTO.itemName(), store).orElseThrow(() -> new ItemNotFoundException(itemRemoveDTO.itemName()));
        Fridge fridge = fridgeRepository.findByFridgeId(itemRemoveDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(itemRemoveDTO.fridgeId()));
        FridgeItems fridgeItem = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseThrow(() -> new FridgeItemsNotFoundException(""));

        notificationService.deleteNotificationForEveryUserInFridge(itemRemoveDTO);

        if (itemRemoveDTO.quantity() == 0) {
            fridgeItemsRepository.delete(fridgeItem);
        } else {
            System.out.println(itemRemoveDTO.quantity());
            fridgeItem.setAmount(itemRemoveDTO.quantity());
            fridgeItemsRepository.save(fridgeItem);
        }
    }

    /**
     * Searches for items in the fridge. The items can be sorted and filtered by expirationDate or purchaseDate.
     *
     * @param fridgeItemSearchDTO  The DTO containing the information of the search.
     * @return A list of FridgeItemLoadDTOs containing the items that match the search.
     */
    @Override
    public Page<FridgeItemLoadDTO> searchFridgeItems(FridgeItemSearchDTO fridgeItemSearchDTO) {
        logger.info("Searching for fridge items");
        Long userId = jwtService.getAuthenticatedUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        logger.info("User was found");
        if(!fridgeService.userExistsInFridge(fridgeItemSearchDTO.fridgeId(), user.getUsername())) {
            throw new UnauthorizedException(jwtService.getAuthenticatedUserEmail());
        }
        if (!fridgeItemSearchDTO.sortField().equals("expirationDate") && !fridgeItemSearchDTO.sortField().equals("purchaseDate"))
            throw new IllegalArgumentException("Sort field must be either expirationDate or purchaseDate"); // TODO: global exception handler
        if (!fridgeItemSearchDTO.sortOrder().equalsIgnoreCase("ASC") && !fridgeItemSearchDTO.sortOrder().equalsIgnoreCase("DESC"))
            throw new IllegalArgumentException("Sort order must be either ASC or DESC");
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeItemSearchDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(fridgeItemSearchDTO.fridgeId())
        );
        List<FridgeItems> fridgeItems = fridgeItemsRepository.findByFridge(fridge).orElseThrow(
                () -> new FridgeItemsNotFoundException(fridgeItemSearchDTO.fridgeId())
        );
        logger.info("Fridge items were found");

        int pageNumber = fridgeItemSearchDTO.page();
        int pageSize = fridgeItemSearchDTO.pageSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Comparator<FridgeItems> comparator;
        if (fridgeItemSearchDTO.sortField().equals("expirationDate")) {
            comparator = Comparator.comparing(FridgeItems::getExpirationDate);
        } else {
            comparator = Comparator.comparing(FridgeItems::getPurchaseDate);
        }

        if (fridgeItemSearchDTO.sortOrder().equalsIgnoreCase("DESC")) {
            comparator = comparator.reversed();
        }

        Stream<FridgeItems> fridgeItemsStream = fridgeItems.stream().sorted(comparator);

        if (!fridgeItemSearchDTO.productName().isEmpty()) {
            String productName = fridgeItemSearchDTO.productName().toLowerCase();
            fridgeItemsStream = fridgeItemsStream.filter(fi -> fi.getItem().getProductName().toLowerCase().contains(productName));
        }
        logger.info("Fridge items were filtered");

        List<FridgeItems> fridgeItemsList = fridgeItemsStream.toList();
        List<FridgeItemLoadDTO> itemDTOList = new ArrayList<>();

        for (FridgeItems item : fridgeItemsList){
            itemDTOList.add(FridgeItemMapper.toFridgeItemLoadDTO(item));
        }
        long totalElements = itemDTOList.size();
        int startIndex = (int) pageRequest.getOffset();
        int endIndex = (int) Math.min(startIndex + pageRequest.getPageSize(), totalElements);
        List<FridgeItemLoadDTO> paginatedList = itemDTOList.subList(startIndex, endIndex);
        logger.info("Fridge items were paginated");
        return new PageImpl<>(paginatedList, pageRequest, totalElements);
    }


    /**
     * This method searches for items in the database based on the search request. DOES NOT WORK.
     *
     * @param request The search request containing the search parameters.
     * @param fridgeId The ID of the fridge to search in.
     * @return A page of fridge items matching the search request.
     */
    @Deprecated
    @Override
    public Page<FridgeItems> searchFridgeItems(SearchRequest request, Long fridgeId) {
        SearchSpecification<Item> specification1 = new SearchSpecification<>(request);
        logger.info("1");
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        logger.info("2");
        List<Item> items = itemRepository.findAll(specification1, pageable).getContent();
        logger.info("Number of items found: " + items.size());
        logger.info(items.stream().map(Item::getItemId).toList().toString());

        ArrayList<FilterRequest> filters = new ArrayList<>();
        filters.add(FilterRequest.builder()
                .key("item")
                .operator(Operator.IN)
                .fieldType(FieldType.LONG)
                .values(Collections.singletonList(items))
                .build());
        filters.add(FilterRequest.builder()
                .key("fridge")
                .operator(Operator.EQUAL)
                .fieldType(FieldType.LONG)
                .value(fridgeId)
                .build());
        logger.info("hello");
        SearchRequest request2 = SearchRequest.builder()
                .page(request.getPage())
                .filters(filters)
                .sorts(request.getSorts())
                .size(request.getSize())
                .build();
        logger.info("hello2");
        SearchSpecification<FridgeItems> specification2 = new SearchSpecification<>(request2);
        logger.info("hello3");
        Pageable pageable2 = SearchSpecification.getPageable(request2.getPage(), request2.getSize());
        return fridgeItemsRepository.findAll(specification2, pageable2);
    }

    /**
     * Removes the specified quantity of an item from the shopping list for the specified fridge.
     *
     * @param itemRemoveDTO A DTO object containing the details of the item to remove.
     * @param suggestion A boolean indicating whether the item is a suggested item or not.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws ShoppingItemsNotFoundException if the specified item does not exist in the shopping list for the specified fridge.
     */
    @Override
    public void removeItemFromShoppingList(ItemRemoveDTO itemRemoveDTO, boolean suggestion) {
        if (itemRemoveDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Store store = storeRepository.findByStoreName(itemRemoveDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemRemoveDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemRemoveDTO.itemName(), store).orElseThrow(() -> new ItemNotFoundException(itemRemoveDTO.itemName()));
        Fridge fridge = fridgeRepository.findByFridgeId(itemRemoveDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(itemRemoveDTO.fridgeId()));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, suggestion).orElseThrow(() -> new ShoppingItemsNotFoundException(""));
        if (shoppingItem.getQuantity() <= itemRemoveDTO.quantity()){
            shoppingItemsRepository.delete(shoppingItem);
        }
        else {
            shoppingItem.setQuantity(shoppingItem.getQuantity() - itemRemoveDTO.quantity());
            shoppingItemsRepository.save(shoppingItem);
        }
    }

    /**
     * Deletes the specified quantity of many items from the shopping list for the specified fridge.
     *
     * @param itemRemoveDTOList A DTO object containing the details of the items to remove as a list.
     */
    @Override
    public void deleteAllItemsFromShoppingList(List<ItemRemoveDTO> itemRemoveDTOList) {
        for(ItemRemoveDTO i: itemRemoveDTOList){
            removeItemFromShoppingList(i, false);
        }
    }

    /**
     * Buys the specified list of items from the shopping list.
     *
     * @param shoppingItemIds A list of DTO objects containing the details of the items to buy.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if the specified item does not exist in the specified fridge.
     * @throws ShoppingItemsNotFoundException if the specified item does not exist in the shopping list for the specified fridge.
     */
    @Transactional
    @Override
    public void buyItemsFromShoppingList(List<ItemMoveDTO> shoppingItemIds) {
        for(ItemMoveDTO itemMoveDTO: shoppingItemIds){
            ShoppingItems shoppingItem = shoppingItemsRepository.findByItem_ItemIdAndFridge_FridgeId(itemMoveDTO.itemId(), itemMoveDTO.fridgeId())
                    .orElseThrow(() -> new ShoppingItemsNotFoundException(itemMoveDTO.itemId()));

            if(shoppingItem.isSuggestion()) continue;

            logger.info("Removing shopping item from list");
            shoppingItemsRepository.delete(shoppingItem);

            FridgeItems fridgeItem = fridgeItemsRepository.findByItem_ItemIdAndFridge_FridgeId(itemMoveDTO.itemId(), itemMoveDTO.fridgeId())
                    .orElseGet(() -> FridgeItemMapper.toFridgeItems(shoppingItem));
            fridgeItem.setAmount(fridgeItem.getAmount() + shoppingItem.getQuantity() * fridgeItem.getItem().getAmount());
            fridgeItemsRepository.save(fridgeItem);
            logger.info("Item has been saved or added to the fridge's item list");
        }
    }

    /**
     * Retrieves a list of items from the specified fridge.
     *
     * @param fridgeId The ID of the fridge to retrieve items from.
     * @return A list of ItemDTO objects representing the items in the fridge.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if there are no items in the specified fridge.
     */
    @Override
    public List<FridgeItemLoadDTO> getFridgeItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<FridgeItems> fridgeItems = fridgeItemsRepository.findByFridge(fridge).orElseThrow(() -> new FridgeItemsNotFoundException(fridgeId));
        List<FridgeItemLoadDTO> itemDTOList = new ArrayList<>();
        for (FridgeItems item : fridgeItems){
            itemDTOList.add(FridgeItemMapper.toFridgeItemLoadDTO(item));
        }
        return itemDTOList;
    }

    /**
     * Retrieves a list of items from the shopping list for the specified fridge.
     *
     * @param fridgeId The ID of the fridge to retrieve shopping list items for.
     * @return A list of ItemDTO objects representing the items in the shopping list for the specified fridge.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws ShoppingItemsNotFoundException if there are no items in the shopping list for the specified fridge.
     */
    @Override
    public List<ShoppingListLoadDTO> getShoppingListItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<ShoppingItems> shoppingItems = shoppingItemsRepository.findByFridge(fridge).orElseThrow(() -> new ShoppingItemsNotFoundException(fridgeId));
        List<ShoppingListLoadDTO> itemDTOList = new ArrayList<>();
        for (ShoppingItems item : shoppingItems){
            itemDTOList.add(ShoppingItemMapper.toShoppingListLoadDTO(item.getItem(), item.getQuantity(), item.isSuggestion()));
        }
        return itemDTOList;
    }

    /**
     Accepts the suggested item by finding the store, item, fridge, and shopping item associated with the given itemDTO,
     and then sets the suggestion status of the shopping item to false.

     @param itemDTO an object containing the name of the store, item, and fridge, as well as the ID of the fridge and a boolean indicating whether the item was suggested for removal
     @throws StoreNotFoundException if the store with the given name cannot be found in the store repository
     @throws ItemNotFoundException if the item with the given name cannot be found in the item repository for the given store
     @throws FridgeNotFoundException if the fridge with the given ID cannot be found in the fridge repository
     @throws ShoppingItemsNotFoundException if the shopping item associated with the given item, fridge, and suggestion status cannot be found in the shopping items repository
     */
    @Override
    public void acceptSuggestion(ItemRemoveDTO itemDTO) {
        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Store store = storeRepository.findByStoreName(itemDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemDTO.itemName(), store).orElseThrow(() -> new ItemNotFoundException(itemDTO.itemName()));
        Fridge fridge = fridgeRepository.findByFridgeId(itemDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(itemDTO.fridgeId()));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, true).orElseThrow(() -> new ShoppingItemsNotFoundException(""));
        shoppingItem.setSuggestion(false);
        shoppingItemsRepository.save(shoppingItem);
    }

    /**
     * This method fills the database's existing items with their corresponding units. The unit and amount are
     * found through the {@link UnitParser#parse(String)}.
     */
    @Transactional
    public void addUnitToExistingItems(){
        List<Item> itemList = itemRepository.findAll();

        for(Item item : itemList) {
            Object[] units = UnitParser.parse(item.getProductName());
            item.setAmount((Double) units[0]);
            item.setUnit((String) units[1]);
            itemRepository.save(item);
        }
    }

}
