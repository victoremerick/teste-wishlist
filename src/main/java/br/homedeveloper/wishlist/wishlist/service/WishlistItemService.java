package br.homedeveloper.wishlist.wishlist.service;

import br.homedeveloper.wishlist.wishlist.model.WishlistItem;

import java.util.List;
import java.util.Optional;

public interface WishlistItemService {
    List<WishlistItem> getAllItems();

    Optional<WishlistItem> getItemById(Long id);

    WishlistItem addItem(WishlistItem item);

    WishlistItem updateItem(WishlistItem item);

    void deleteItem(Long id);
}
