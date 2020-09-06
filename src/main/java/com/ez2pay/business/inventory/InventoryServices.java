package com.ez2pay.business.inventory;

import com.ez2pay.exception.ResourceNotFoundException;
import com.ez2pay.util.DozerConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServices {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServices.class);
    private final InventoryRepository repository;

    public InventoryDTO findItemById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find item with id: " + id));
        return DozerConverter.parseObject(entity, InventoryDTO.class);
    }

    public Page<InventoryDTO> findAllItem(Pageable pageable) {

        var page = repository.findAll(pageable);

        //Use method reference
        return page.map(this::convertToInventoryDTO);

        //Use Lambda expresssion
        //return page.map(entity -> DozerConverter.parseObject(entity, InventoryDTO.class));
    }

    private InventoryDTO convertToInventoryDTO (Inventory entity){
        return DozerConverter.parseObject(entity, InventoryDTO.class);
    }

    public InventoryDTO createItem(InventoryDTO item) {
        var entity = DozerConverter.parseObject(item, Inventory.class);
        entity = repository.save(entity);
        return DozerConverter.parseObject(entity, InventoryDTO.class);
    }

    public InventoryDTO updateItem(InventoryDTO item) {
        var entity = repository.findById(item.getId()).orElseThrow(() -> new ResourceNotFoundException("Could not find item with id: " + item.getId()));

        entity = DozerConverter.parseObject(item, Inventory.class);
        entity = repository.save(entity);

        return DozerConverter.parseObject(entity, InventoryDTO.class);
    }

    public void deleteItem(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this item id: " + id));
        repository.delete(entity);
    }

}
