package br.homedeveloper.wishlist.wishlist.controller;

import br.homedeveloper.wishlist.wishlist.model.WishlistItem;
import br.homedeveloper.wishlist.wishlist.service.WishlistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist-items")
@Tag(name = "Wishlist Items", description = "API para gerenciar os itens da lista de desejos")
public class WishlistItemController {
    private WishlistItemService wishlistItemService;

    @Autowired
    public WishlistItemController(WishlistItemService wishlistItemService) {
        this.wishlistItemService = wishlistItemService;
    }

    @GetMapping
    @Operation(summary = "Obter todos os itens da lista de desejos")
    public ResponseEntity<List<WishlistItem>> getAllItems() {
        List<WishlistItem> items = wishlistItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter um item da lista de desejos pelo ID")
    @ApiResponse(responseCode = "200", description = "Item encontrado", content = @Content(schema = @Schema(implementation = WishlistItem.class)))
    @ApiResponse(responseCode = "404", description = "Item não encontrado")
    public ResponseEntity<WishlistItem> getItemById(@PathVariable("id") @Parameter(description = "ID do item") String id) {
        Optional<WishlistItem> item = wishlistItemService.getItemById(Long.parseLong(id));
        return item.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Adicionar um novo item à lista de desejos")
    @ApiResponse(responseCode = "201", description = "Item adicionado", content = @Content(schema = @Schema(implementation = WishlistItem.class)))
    public ResponseEntity<WishlistItem> addItem(@RequestBody WishlistItem item) {
        WishlistItem addedItem = wishlistItemService.addItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um item da lista de desejos")
    @ApiResponse(responseCode = "200", description = "Item atualizado", content = @Content(schema = @Schema(implementation = WishlistItem.class)))
    @ApiResponse(responseCode = "404", description = "Item não encontrado")
    public ResponseEntity<WishlistItem> updateItem(@PathVariable("id") @Parameter(description = "ID do item") String id, @RequestBody WishlistItem item) {
        Optional<WishlistItem> existingItem = wishlistItemService.getItemById(Long.parseLong(id));
        if (existingItem.isPresent()) {
            item.setId(Long.parseLong(id));
            WishlistItem updatedItem = wishlistItemService.updateItem(item);
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um item da lista de desejos")
    @ApiResponse(responseCode = "204", description = "Item excluído")
    @ApiResponse(responseCode = "404", description = "Item não encontrado")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") @Parameter(description = "ID do item") String id) {
        Optional<WishlistItem> existingItem = wishlistItemService.getItemById(Long.parseLong(id));
        if (existingItem.isPresent()) {
            wishlistItemService.deleteItem(Long.parseLong(id));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
