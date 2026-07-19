package com.codexdemo.orderplatform.order;

import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<SalesOrder, Long> {

  Optional<SalesOrder> findByIdempotencyKey(String idempotencyKey);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query(
      "select salesOrder from SalesOrder salesOrder where salesOrder.status = ?1 "
          + "and salesOrder.createdAt < ?2 order by salesOrder.createdAt asc")
  List<SalesOrder> findExpiryBatch(OrderStatus status, Instant cutoff, Pageable page);
}
