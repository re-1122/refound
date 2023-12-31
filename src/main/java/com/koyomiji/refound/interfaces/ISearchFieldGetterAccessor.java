package com.koyomiji.refound.interfaces;

import java.util.function.Supplier;

public interface ISearchFieldGetterAccessor {
  void refound$setSearchFieldGetter(Supplier<String> searchFieldGetter);
}
