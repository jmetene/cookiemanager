package com.metene.service.utiles;

import com.metene.common.Constantes;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticsUtils {
    private StatisticsUtils() {
        throw new IllegalStateException("Utility class");
    }

    static String clean(String[] entryValue) {
        return Arrays.toString(entryValue).replace("[", "").replace("]", "");
    }

    public static String builtKeyForQuery(String estado, String pais, String plataforma, String fechaInicio, String fechaFin) {
        return Stream
                .of(fechaInicio != null ? Constantes.START_DATE : null, fechaFin != null ? Constantes.END_DATE : null,
                        estado != null ? Constantes.STATUS : null, pais != null ? Constantes.COUNTRY : null,
                        plataforma != null ? Constantes.PLATFORM : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("_"));
    }

    public static Integer getTotalQueryParams(String estado, String pais, String plataforma, String fechaInicio, String fechaFin) {
        return Stream
                .of(fechaInicio != null ? Constantes.START_DATE : null, fechaFin != null ? Constantes.END_DATE : null,
                        estado != null ? Constantes.STATUS : null, pais != null ? Constantes.COUNTRY : null,
                        plataforma != null ? Constantes.PLATFORM : null)
                .filter(Objects::nonNull)
                .toList().size();
    }

    public static Map<String, String> extractQueryParams(List<Map.Entry<String, String[]>> parametros) {
        Map<String, String> mapOfqueryParamsValue = new HashMap<>();
        parametros.forEach(stringEntry -> mapOfqueryParamsValue.put(stringEntry.getKey(), clean(stringEntry.getValue())));

        return mapOfqueryParamsValue;
    }
}
