package com.ez2pay.business.order;

import com.ez2pay.business.customer.CustomerServices;
import com.ez2pay.business.inventory.InventoryDTO;
import com.ez2pay.business.inventory.InventoryServices;
import com.ez2pay.business.order_detail.OrderDetailDTO;
import com.ez2pay.exception.ResourceConflictException;
import com.ez2pay.exception.ResourceNotFoundException;
import com.ez2pay.util.DozerConverter;
import com.ez2pay.util.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServices {

    private static final String PROCESSING_ORDER = "PROCESSING";
    private static final String PROCESSED_ORDER = "PROCESSED";
    private static final String CANCELLED_ORDER = "CANCELLED";

    private static final Logger logger = LoggerFactory.getLogger(OrderServices.class);
    private final OrderRepository repository;
    private final CustomerServices customerServices;
    private final InventoryServices inventoryServices;

    public OrderDTO findOrderById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find order with id: " + id));
        return DozerConverter.parseObject(entity, OrderDTO.class);
    }

    public List<OrderDTO> findAllOrders() {
        return DozerConverter.parseObjectList(repository.findAll(), OrderDTO.class);
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(new Date());
        orderDTO.setOrderStatus(PROCESSING_ORDER);
        orderDTO.setCustomer(customerServices.findCustomerById(createOrderDTO.getCustomerId()));

        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        OrderDTO tempOrderDTO = orderDTO;
        createOrderDTO.getOrderDetails().forEach(item -> {

            InventoryDTO inventoryDTO = inventoryServices.findItemById(item.getItemId());
            int leftQuantityAvailable = inventoryDTO.getQuantityAvailable() - item.getQuantity();

            if (leftQuantityAvailable < 0){
                throw new ResourceConflictException("Item " + inventoryDTO.getName() + " has not enough available quantity for the purchase");
            }

            inventoryDTO.setQuantityAvailable(leftQuantityAvailable);
            inventoryServices.updateItem(inventoryDTO);

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setItem(inventoryDTO);
            orderDetailDTO.setPrice(inventoryDTO.getPrice());
            orderDetailDTO.setQuantity(item.getQuantity());

            orderDetailDTO.setOrder(tempOrderDTO);

            orderDetailDTOList.add(orderDetailDTO);
        });

        orderDTO.setOrderDetails(orderDetailDTOList);

        logger.debug("Order DTO:" + Utils.parseObjectToJson(orderDTO));

        var entity = DozerConverter.parseObject(orderDTO, Order.class);

        logger.debug("Order Entity: " + Utils.parseObjectToJson(entity));

        entity = repository.save(entity);

        return DozerConverter.parseObject(entity, OrderDTO.class);
    }

    public OrderDTO updateOrder(OrderDTO order) {
        var entity = repository.findById(order.getId()).orElseThrow(() -> new ResourceNotFoundException("Could not find order with id: " + order.getId()));

        entity = DozerConverter.parseObject(order, Order.class);
        entity = repository.save(entity);

        return DozerConverter.parseObject(entity, OrderDTO.class);
    }

    @Transactional
    public OrderDTO deleteOrder(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this order id: " + id));

        if (entity.getOrderStatus().equals(PROCESSED_ORDER)){
            throw new ResourceConflictException("Order has been processed. You cannot cancel");
        }else if(entity.getOrderStatus().equals(CANCELLED_ORDER)){
            throw new ResourceConflictException("Order has already been cancelled");
        }

        entity.getOrderDetails().forEach(item -> {
            InventoryDTO inventoryDTO = inventoryServices.findItemById(item.getItem().getId());
            int newQuantityAvailable = inventoryDTO.getQuantityAvailable() + item.getQuantity();
            inventoryDTO.setQuantityAvailable(newQuantityAvailable);
            inventoryServices.updateItem(inventoryDTO);
        });
        entity.setOrderStatus(CANCELLED_ORDER);
        entity = repository.save(entity);

        return DozerConverter.parseObject(entity, OrderDTO.class);
    }

}
