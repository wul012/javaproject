package com.codexdemo.orderplatform.inventory;

import com.codexdemo.orderplatform.common.BusinessException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

  private final InventoryRepository inventoryRepository;
  private final InventoryMovementRepository inventoryMovementRepository;

  public InventoryService(
      InventoryRepository inventoryRepository,
      InventoryMovementRepository inventoryMovementRepository) {
    this.inventoryRepository = inventoryRepository;
    this.inventoryMovementRepository = inventoryMovementRepository;
  }

  public void reserve(Map<Long, Integer> productQuantities) {
    applyAll(productQuantities, InventoryMovementType.RESERVE, InventoryItem::reserve);
  }

  public void commitReserved(Map<Long, Integer> productQuantities) {
    applyAll(
        productQuantities, InventoryMovementType.COMMIT_RESERVED, InventoryItem::commitReserved);
  }

  public void returnCommitted(Map<Long, Integer> productQuantities) {
    applyAll(
        productQuantities, InventoryMovementType.RETURN_COMMITTED, InventoryItem::returnCommitted);
  }

  public void releaseReserved(Map<Long, Integer> productQuantities) {
    applyAll(
        productQuantities, InventoryMovementType.RELEASE_RESERVED, InventoryItem::releaseReserved);
  }

  private void applyAll(
      Map<Long, Integer> productQuantities,
      InventoryMovementType type,
      BiConsumer<InventoryItem, Integer> operation) {
    productQuantities.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(entry -> applyAndRecord(entry.getKey(), entry.getValue(), type, operation));
  }

  public List<InventoryMovementResponse> listProductMovements(Long productId) {
    findExisting(productId);
    return inventoryMovementRepository.findByProductIdOrderByCreatedAtAscIdAsc(productId).stream()
        .map(InventoryMovementResponse::from)
        .toList();
  }

  private void applyAndRecord(
      Long productId,
      int quantity,
      InventoryMovementType type,
      BiConsumer<InventoryItem, Integer> operation) {
    InventoryItem item = findLocked(productId);
    int availableBefore = item.getAvailable();
    int reservedBefore = item.getReserved();
    operation.accept(item, quantity);
    inventoryMovementRepository.save(
        InventoryMovement.record(item, type, quantity, availableBefore, reservedBefore));
  }

  private InventoryItem findLocked(Long productId) {
    return requireInventory(inventoryRepository.findByProductIdForUpdate(productId), productId);
  }

  private InventoryItem findExisting(Long productId) {
    return requireInventory(inventoryRepository.findByProductId(productId), productId);
  }

  private InventoryItem requireInventory(Optional<InventoryItem> inventory, Long productId) {
    return inventory.orElseThrow(
        () ->
            BusinessException.notFound(
                "INVENTORY_NOT_FOUND", "Inventory was not found for product " + productId));
  }
}
