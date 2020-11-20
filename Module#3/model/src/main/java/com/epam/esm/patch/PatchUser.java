package com.epam.esm.patch;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PatchUser implements PatchOperation<User> {
    @Size(min = 3, max = 20)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false)
    private String name;

    private List<Order> orders;

    @Override
    public void mergeToEntity(User existing) {
        if (name != null) {
            existing.setName(name);
        }
        if (orders != null) {
            existing.setOrders(orders);
        }
    }
}
