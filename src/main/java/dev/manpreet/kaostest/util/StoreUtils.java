package dev.manpreet.kaostest.util;

import dev.manpreet.kaostest.stores.Store;

import java.util.Collection;
import java.util.List;

public class StoreUtils {

    public static List<String> getAllClasses() {
        Store store = Store.getInstance();
        return store.getPackagesData().values().stream()
                .map(eachPkg -> eachPkg.getTestClassesData().keySet())
                .flatMap(Collection::stream)
                .toList();
    }
}
