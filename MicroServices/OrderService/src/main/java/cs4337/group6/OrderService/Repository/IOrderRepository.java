package cs4337.group6.OrderService.Repository;

import cs4337.group6.OrderService.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IOrderRepository extends JpaRepository<Order, Integer>
{

}
