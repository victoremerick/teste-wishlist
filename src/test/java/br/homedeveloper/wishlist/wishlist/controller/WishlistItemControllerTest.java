package br.homedeveloper.wishlist.wishlist.controller;

import br.homedeveloper.wishlist.wishlist.model.WishlistItem;
import br.homedeveloper.wishlist.wishlist.service.WishlistItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class WishlistItemControllerTest {
    @Mock
    private WishlistItemService wishlistItemService;

    private WishlistItemController wishlistItemController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        wishlistItemController = new WishlistItemController(wishlistItemService);
    }

    @Test
    void createItem_ValidItem_ShouldReturnCreated() {
        WishlistItem item = new WishlistItem(null, "user1", null, "item1", "image1", "Item 1");
        when(wishlistItemService.addItem(item)).thenReturn(item);

        ResponseEntity<WishlistItem> response = wishlistItemController.addItem(item);

        assertSame(HttpStatus.CREATED, response.getStatusCode());
        assertSame(item, response.getBody());
        verify(wishlistItemService, times(1)).addItem(item);
    }

    @Test
    void getItemById_ExistingItemId_ShouldReturnItem() {
        Long itemId = 123L;
        WishlistItem item = new WishlistItem(itemId, "user1", null, "item1", "image1", "Item 1");
        when(wishlistItemService.getItemById(itemId)).thenReturn(Optional.of(item));

        ResponseEntity<WishlistItem> response = wishlistItemController.getItemById(itemId.toString());

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(item, response.getBody());
        verify(wishlistItemService, times(1)).getItemById(itemId);
    }

    @Test
    void getItemById_NonExistingItemId_ShouldReturnNotFound() {
        Long itemId = 123L;
        when(wishlistItemService.getItemById(itemId)).thenReturn(Optional.empty());

        ResponseEntity<WishlistItem> response = wishlistItemController.getItemById(itemId.toString());

        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(wishlistItemService, times(1)).getItemById(itemId);
    }

    @Test
    void updateItem_ExistingItemIdAndValidItem_ShouldReturnUpdatedItem() {
        Long itemId = 123L;
        WishlistItem existingItem = new WishlistItem(itemId, "user1", null, "item1", "image1", "Item 1");
        WishlistItem updatedItem = new WishlistItem(itemId, "user1", null, "item1", "image1", "Updated Item");
        when(wishlistItemService.getItemById(itemId)).thenReturn(Optional.of(existingItem));
        when(wishlistItemService.updateItem(updatedItem)).thenReturn(updatedItem);

        ResponseEntity<WishlistItem> response = wishlistItemController.updateItem(itemId.toString(), updatedItem);

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(updatedItem, response.getBody());
        verify(wishlistItemService, times(1)).getItemById(itemId);
        verify(wishlistItemService, times(1)).updateItem(updatedItem);
    }

    @Test
    void deleteItem_ExistingItemId_ShouldReturnNoContent() {
        Long itemId = 123L;
        WishlistItem item = new WishlistItem(itemId, "user1", null, "item1", "image1", "Item 1");
        when(wishlistItemService.getItemById(itemId)).thenReturn(Optional.of(item));

        ResponseEntity<Void> response = wishlistItemController.deleteItem(itemId.toString());

        assertSame(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(wishlistItemService, times(1)).getItemById(itemId);
        verify(wishlistItemService, times(1)).deleteItem(itemId);
    }

    @Test
    void deleteItem_NonExistingItemId_ShouldReturnNotFound() {
        Long itemId = 123L;
        when(wishlistItemService.getItemById(itemId)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = wishlistItemController.deleteItem(itemId.toString());

        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(wishlistItemService, times(1)).getItemById(itemId);
        verify(wishlistItemService, never()).deleteItem(anyLong());
    }

    @Test
    void getAllItems_ShouldReturnAllItems() {
        List<WishlistItem> items = new ArrayList<>();
        items.add(new WishlistItem(1L, "user1", null, "item1", "image1", "Item 1"));
        items.add(new WishlistItem(2L, "user1", null, "item2", "image2", "Item 2"));
        when(wishlistItemService.getAllItems()).thenReturn(items);

        ResponseEntity<List<WishlistItem>> response = wishlistItemController.getAllItems();

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(items, response.getBody());
        verify(wishlistItemService, times(1)).getAllItems();
    }
}


