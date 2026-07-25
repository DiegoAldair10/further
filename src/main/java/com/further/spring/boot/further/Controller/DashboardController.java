package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.DashboardDTO;
import com.further.spring.boot.further.Dto.VentasMesDTO;
import com.further.spring.boot.further.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "http://localhost:63842")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardDTO dashboard() {
        return dashboardService.obtenerDashboard();
    }

    @GetMapping("/ventas-mes")
    public List<VentasMesDTO> ventasMes() {

        return dashboardService.obtenerVentasMes();

    }
}