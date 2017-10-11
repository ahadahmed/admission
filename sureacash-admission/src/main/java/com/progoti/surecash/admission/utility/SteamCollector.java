package com.progoti.surecash.admission.utility;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SteamCollector {

    public static <T> Collector<T, ?, T> firstOrDefault() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list == null || list.size() == 0) {
                        return null;
                    }
                    return list.get(0);
                }
        );
    }
}
