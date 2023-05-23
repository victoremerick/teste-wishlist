package br.homedeveloper.wishlist.wishlist.service.impl;

import br.homedeveloper.wishlist.wishlist.model.WishlistItem;
import br.homedeveloper.wishlist.wishlist.repository.WishlistItemRepository;
import br.homedeveloper.wishlist.wishlist.service.WishlistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemServiceImpl implements WishlistItemService {
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    public WishlistItemServiceImpl(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    public List<WishlistItem> getAllItems() {
        return wishlistItemRepository.findAll();
    }

    @Override
    public Optional<WishlistItem> getItemById(Long id) {
        return wishlistItemRepository.findById(id);
    }

    @Override
    public WishlistItem addItem(WishlistItem item) {
        return wishlistItemRepository.save(item);
    }

    @Override
    public WishlistItem updateItem(WishlistItem item) {
        return wishlistItemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        wishlistItemRepository.deleteById(id);
    }
}

