package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDAO extends PagingAndSortingRepository<Tag, Long> {

    /**
     * Find the most widely used of users with the highest cost of all orders optional.
     *
     * @return the optional
     */
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

    /**
     * Exists by name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(@NonNull String name);

    /**
     * Gets by name in.
     *
     * @param names the names
     * @return the by name in
     */
    @NonNull
    List<Tag> getByNameIn(@NonNull List<String> names);

    /**
     * Gets by gift certificates id.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the by gift certificates id
     */
    @NonNull
    Page<Tag> getByGiftCertificates_Id(@NonNull Long id, @NonNull Pageable pageable);
}
