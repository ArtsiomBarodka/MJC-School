package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends PagingAndSortingRepository<Tag, Long> {

    @Query(value = "select tag.* from tag " +
            "inner join gift_certificate_tag on gift_certificate_tag.tag_id=tag.id " +
            "inner join certificate on certificate.id=gift_certificate_tag.gift_certificate_id " +
            "inner join certificate_order on certificate_order.certificate_id=certificate.id " +
            "inner join user_order on user_order.id = certificate_order.order_id " +
            "inner join users on users.id = user_order.fk_user_id " +
            "group by tag.id, user_order.id " +
            "order by user_order.price desc ,count(tag.id) desc " +
            "limit 1", nativeQuery = true)
    @NonNull Optional<Tag> findTheMostWidelyUsedOfUsersWithTheHighestCostOfAllOrders();

    boolean existsByName(@NonNull String name);

    @NonNull
    List<Tag> getByNameIn(@NonNull List<String> names);

    @NonNull
    Page<Tag> getByGiftCertificates_Id(@NonNull Long id, @NonNull Pageable pageable);
}
