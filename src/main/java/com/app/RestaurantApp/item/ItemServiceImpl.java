package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.item.dto.ItemPriceDTO;
import com.app.RestaurantApp.price.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findAllWithIds(List<Long> ids) {
        return itemRepository.findAllWithIds(ids);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createUpdatePriceOnItem(ItemPriceDTO itemPriceDTO) throws ItemException {
        Item i = itemRepository.findByIdItemWithPrices(itemPriceDTO.getId());
        if (i == null) throw new ItemException("Item does not exist!");

        if (itemPriceDTO.getNewPrice() <= 0) throw new ItemException("Price must be above 0");

        Price p = new Price();
        p.setAmount(itemPriceDTO.getNewPrice());
        p.setDateFrom(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))
                , DateTimeFormatter.ofPattern("dd.MM.yyyy."))
                .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
        i.getPrices().add(p);
        p.setItem(i);

        boolean indicator = false;
        if (i.getCurrentPrice() == 0)
            indicator = true;

        i.setCurrentPrice(itemPriceDTO.getNewPrice());
        itemRepository.save(i);

        return indicator;
    }

    @Override
    public List<ItemPriceDTO> getPricesOfItem(String id) throws ItemException {
        Item item = itemRepository.findByIdItemWithPrices(Long.valueOf(id));
        if (item == null) throw new ItemException("Item does not exist!");

        List<ItemPriceDTO> items = new ArrayList<>();
        Iterator<Price> it = item.getPrices().stream().sorted(Comparator.comparingLong(Price::getDateFrom)).iterator();
        int i = 0;
        while (it.hasNext()) {
            items.add(new ItemPriceDTO(it.next(), item.getId()));
            if (i != 0)
                items.get(i - 1).setDateTo(items.get(i).getDateFrom());
            ++i;
        }
        return items;
    }

}
