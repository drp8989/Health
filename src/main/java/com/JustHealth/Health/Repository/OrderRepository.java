package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.OnlineOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OnlineOrder,Long> {
}
