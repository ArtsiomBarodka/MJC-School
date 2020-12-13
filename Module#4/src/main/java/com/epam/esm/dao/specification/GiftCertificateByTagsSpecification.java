package com.epam.esm.dao.specification;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GiftCertificateByTagsSpecification implements Specification<GiftCertificate> {
    private Iterable<Tag> list;

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (Tag tag : list){
            predicates.add(criteriaBuilder.isMember(tag, root.get("tags")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
