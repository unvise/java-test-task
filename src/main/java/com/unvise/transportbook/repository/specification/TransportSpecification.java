package com.unvise.transportbook.repository.specification;

import com.unvise.transportbook.entity.Transport;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TransportSpecification implements Specification<Transport> {

    private final List<SearchCriteria> list;

    public TransportSpecification() {
        list = new ArrayList<>();
    }

    public void add(SearchCriteria searchCriteria) {
        list.add(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<Transport> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : list) {

            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue() ));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    predicates.add(builder.like(
                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                } else
                    predicates.add(builder.equal(
                            root.get(criteria.getKey()), criteria.getValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
