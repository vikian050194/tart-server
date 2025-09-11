package tart.domain.file;

import java.util.ArrayList;
import java.util.List;

public class DateFilter {

    public final List<Integer> years;
    public final List<Integer> months;
    public final List<Integer> days;

    public DateFilter() {
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();
    }
//
//    public boolean add(String mask) {
//
//        if (items.contains(mask)) {
//            return false;
//        }
//
//        items.add(mask);
//
//        return true;
//    }
//
//    public boolean remove(String mask) {
//        return items.remove(mask);
//    }
//
//    public List<String> get() {
//        if (isEmpty()) {
//            return List.of(defaultMask);
//        }
//
//        return items;
//    }
//
//    public boolean contains(String v) {
//        return items.contains(v);
//    }
//
//    public boolean isEmpty() {
//        return items.isEmpty();
//    }
//
//    public void clear() {
//        items.clear();
//    }
}
