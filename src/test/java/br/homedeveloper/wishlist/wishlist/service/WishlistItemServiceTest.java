package br.homedeveloper.wishlist.wishlist.service;

import br.homedeveloper.wishlist.wishlist.model.WishlistItem;
import br.homedeveloper.wishlist.wishlist.repository.WishlistItemRepository;
import br.homedeveloper.wishlist.wishlist.service.impl.WishlistItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WishlistItemServiceTest {
    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @InjectMocks
    private WishlistItemServiceImpl wishlistItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllItems_ShouldReturnAllItems() {
        List<WishlistItem> items = new ArrayList<>();
        items.add(new WishlistItem(1L, "user1", null, "item1", "image1", "Item 1"));
        items.add(new WishlistItem(2L, "user1", null, "item2", "image2", "Item 2"));
        when(wishlistItemRepository.findAll()).thenReturn(items);

        List<WishlistItem> result = wishlistItemService.getAllItems();

        assertEquals(2, result.size());
        assertEquals(items, result);
        verify(wishlistItemRepository, times(1)).findAll();
    }

    @Test
    void getItemById_ExistingItemId_ShouldReturnItem() {
        Long itemId = 123L;
        WishlistItem item = new WishlistItem(itemId, "user1", null, "item1", "image1", "Item 1");
        when(wishlistItemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Optional<WishlistItem> result = wishlistItemService.getItemById(itemId);

        assertTrue(result.isPresent());
        assertEquals(item, result.get());
        verify(wishlistItemRepository, times(1)).findById(itemId);
    }

    @Test
    void getItemById_NonExistingItemId_ShouldReturnEmptyOptional() {
        Long itemId = 123L;;
        when(wishlistItemRepository.findById(itemId)).thenReturn(Optional.empty());

        Optional<WishlistItem> result = wishlistItemService.getItemById(itemId);

        assertFalse(result.isPresent());
        verify(wishlistItemRepository, times(1)).findById(itemId);
    }

    @Test
    void addItem_ShouldReturnAddedItem() {
        WishlistItem item = new WishlistItem(null, "user1", null, "item1", "image1", "Item 1");
        when(wishlistItemRepository.save(item)).thenReturn(item);

        WishlistItem result = wishlistItemService.addItem(item);

        assertEquals(item, result);
        verify(wishlistItemRepository, times(1)).save(item);
    }

    @Test
    void updateItem_ExistingItem_ShouldReturnUpdatedItem() {
        Long itemId = 123L;;
        WishlistItem existingItem = new WishlistItem(itemId, "user1", null, "item1", "image1", "Item 1");
        WishlistItem updatedItem = new WishlistItem(itemId, "user1", null, "item1", "image1", "Updated Item 1");
        when(wishlistItemRepository.save(updatedItem)).thenReturn(updatedItem);

        WishlistItem result = wishlistItemService.updateItem(updatedItem);

        assertEquals(updatedItem, result);
        verify(wishlistItemRepository, times(1)).save(updatedItem);
    }

    @Test
    void updateItem_NonExistingItem_ShouldReturnNull() {
        Long itemId = 123L;;
        WishlistItem updatedItem = new WishlistItem(itemId, "user1", null, "item1", "image1", "Updated Item 1");
        WishlistItem result = wishlistItemService.updateItem(updatedItem);

        assertNull(result);
        verify(wishlistItemRepository, times(1)).save(updatedItem);
    }

    @Test
    void deleteItem_ExistingItemId_ShouldDeleteItem() {
        Long itemId = 123L;;

        wishlistItemService.deleteItem(itemId);

        verify(wishlistItemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void deleteItem_NonExistingItemId_ShouldNotDeleteItem() {
        Long itemId = 123L;
        doNothing().when(wishlistItemRepository).deleteById(itemId);

        wishlistItemService.deleteItem(itemId);

        verify(wishlistItemRepository, times(1)).deleteById(itemId);
    }
}

