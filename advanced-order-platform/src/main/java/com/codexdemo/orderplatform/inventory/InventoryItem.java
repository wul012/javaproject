package com.codexdemo.orderplatform.inventory;

import com.codexdemo.orderplatform.common.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private Long productId;

  @Column(nullable = false)
  private int available;

  @Column(nullable = false)
  private int reserved;

  @Version private long version;

  protected InventoryItem() {}

  private InventoryItem(Long productId, int available) {
    this.productId = productId;
    this.available = available;
  }

  public static InventoryItem create(Long productId, int available) {
    return new InventoryItem(productId, available);
  }

  public void reserve(int quantity) {
    requirePositive(quantity);
    if (available < quantity) {
      throw BusinessException.conflict(
          "INSUFFICIENT_STOCK",
          "Product " + productId + " has only " + available + " units available");
    }
    available -= quantity;
    reserved += quantity;
  }

  public void commitReserved(int quantity) {
    requireReserved(quantity, "commit");
    reserved -= quantity;
  }

  public void returnCommitted(int quantity) {
    requirePositive(quantity);
    available += quantity;
  }

  public void releaseReserved(int quantity) {
    requirePositive(quantity);
    requireReserved(quantity, "release");
    reserved -= quantity;
    available += quantity;
  }

  private void requirePositive(int quantity) {
    if (quantity <= 0) {
      throw BusinessException.invalidInput(
          "INVALID_QUANTITY", "Quantity must be greater than zero");
    }
  }

  private void requireReserved(int quantity, String action) {
    if (reserved < quantity) {
      throw BusinessException.conflict(
          "RESERVATION_MISMATCH",
          "Product " + productId + " reservation is lower than requested " + action + " quantity");
    }
  }

  public Long getId() {
    return id;
  }

  public Long getProductId() {
    return productId;
  }

  public int getAvailable() {
    return available;
  }

  public int getReserved() {
    return reserved;
  }

  public long getVersion() {
    return version;
  }
}
