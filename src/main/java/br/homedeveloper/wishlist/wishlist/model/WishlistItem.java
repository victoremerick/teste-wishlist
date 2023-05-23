package br.homedeveloper.wishlist.wishlist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {
    private Long id;
    private String userId;
    private Date insertionDate;
    private String itemUrl;
    private String imageUrl;
    private String itemName;
}
