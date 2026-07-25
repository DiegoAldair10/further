package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.DashboardDTO;
import com.further.spring.boot.further.Dto.VentasMesDTO;
import com.further.spring.boot.further.Entity.Usuarios;
import com.further.spring.boot.further.Repository.ClienteRepository;
import com.further.spring.boot.further.Repository.ProductoRepository;
import com.further.spring.boot.further.Repository.UsuarioRepository;
import com.further.spring.boot.further.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DashboardDTO obtenerDashboard() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        Usuarios usuario =
                usuarioRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuario no encontrado"
                                ));

        DashboardDTO dto =
                new DashboardDTO();

        boolean esAdmin =
                usuario.getRoles()
                        .stream()
                        .anyMatch(r ->
                                "ADMIN".equals(
                                        r.getNombre()
                                ));

        if (esAdmin) {

            dto.setTotalProductos(
                    productoRepository.count()
            );

            dto.setTotalClientes(
                    clienteRepository.count()
            );

            dto.setTotalVentas(
                    ventaRepository.count()
            );

            dto.setMontoVentas(
                    ventaRepository.obtenerMontoTotalVentas()
            );

            dto.setTotalUsuarios(
                    usuarioRepository.count()
            );

        } else {

            dto.setTotalProductos(
                    0L
            );

            dto.setTotalClientes(
                    0L
            );

            dto.setTotalUsuarios(
                    0L
            );

            dto.setTotalVentas(
                    ventaRepository.countByUsuarioUsuariosId(
                            usuario.getUsuariosId()
                    )
            );

            dto.setMontoVentas(
                    ventaRepository.obtenerMontoTotalVentasPorUsuario(
                            usuario.getUsuariosId()
                    )
            );

        }

        return dto;

    }

    public List<VentasMesDTO> obtenerVentasMes() {

        List<Object[]> resultado =
                ventaRepository.obtenerVentasPorMes();

        List<VentasMesDTO> lista =
                new ArrayList<>();

        for (Object[] fila : resultado) {

            VentasMesDTO dto =
                    new VentasMesDTO();

            dto.setMes(
                    fila[0].toString()
            );

            dto.setTotal(
                    ((Number) fila[1]).doubleValue()
            );

            lista.add(dto);

        }

        return lista;

    }
}