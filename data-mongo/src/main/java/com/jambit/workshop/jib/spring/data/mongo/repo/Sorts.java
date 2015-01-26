package com.jambit.workshop.jib.spring.data.mongo.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public enum Sorts {
  NAME_DEFAULT(new Sort(new Order(Sort.DEFAULT_DIRECTION, "name", Sort.NullHandling.NULLS_LAST))),
  NAME_ASC(new Sort(new Order(Direction.ASC, "name", Sort.NullHandling.NULLS_LAST))),
  NAME_DESC(new Sort(new Order(Direction.DESC, "name").with(Sort.NullHandling.NULLS_LAST)));
  
  
  private final Sort mSort;
  
  private Sorts(final Sort sort) {
    mSort = sort;
  }
  
  public Sort getSort() {
    return mSort;
  }
  
  private static boolean hasIdenticalDirectionForName(final Sort sort, final Direction direction) {
    final Order orderFor = sort.getOrderFor("name");
    return orderFor.getDirection().equals(direction);
  }
  
  public static Sorts byName(final String direction) {
    final Direction fromValue = Direction.fromStringOrNull(direction);
    for(Sorts sorts : values()) {
      if(hasIdenticalDirectionForName(sorts.mSort, fromValue)) {
        return sorts;
      }
    }
    return NAME_DEFAULT;
  }
  
}
