package com.metene.statistics;

import com.metene.statistics.dto.Statistic;
import com.metene.statistics.dto.StatisticRequest;
import com.metene.statistics.dto.StatisticResponse;
import com.metene.utiles.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatisticsCookieController {

    private final StatisticsCookieService statisticsCookieService;

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping(value = "/domains/{domainId}/estadisticas")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<StatisticResponse>> getStatisticsByDomain(WebRequest request, @PathVariable Long domainId,
                                                                         StatisticRequest statisticRequest) {

        if (!RequestUtils.getParametrosNoValidos(request).isEmpty())
            return ResponseEntity.badRequest().build();

        List<StatisticResponse> respone;
        try {
            respone = statisticsCookieService.fetchStatistics(domainId, statisticRequest.getEstado(),
                    statisticRequest.getFechaDesde(), statisticRequest.getFechaHasta(), statisticRequest.getPlataforma(),
                    statisticRequest.getPais());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(respone);
    }

    /**
     * Carga las estadísitcas de una cookie
     * @param statistics listado con las estadísiticas
     * @return {@return ResponseEntity}
     */
    @CrossOrigin
    @PostMapping(value = "/estadisticas")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> cargarEstadisticas( @RequestBody List<Statistic> statistics) {
        try {
            if (statistics.isEmpty()) return ResponseEntity.badRequest().build();
            statisticsCookieService.cargarEstadisticas(statistics);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
