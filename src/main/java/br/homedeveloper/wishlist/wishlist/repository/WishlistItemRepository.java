package br.homedeveloper.wishlist.wishlist.repository;

import br.homedeveloper.wishlist.wishlist.model.WishlistItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishlistItemRepository extends MongoRepository<WishlistItem, Long> {
}
