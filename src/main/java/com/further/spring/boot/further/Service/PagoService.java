package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.PagoDTO;
import com.further.spring.boot.further.Entity.MetodoPago;
import com.further.spring.boot.further.Entity.Pago;
import com.further.spring.boot.further.Entity.Usuarios;
import com.further.spring.boot.further.Entity.Venta;
import com.further.spring.boot.further.Mapper.PagoMapper;
import com.further.spring.boot.further.Repository.MetodoPagoRepository;
import com.further.spring.boot.further.Repository.PagoRepository;
import com.further.spring.boot.further.Repository.UsuarioRepository;
import com.further.spring.boot.further.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PagoMapper pagoMapper;

    public List<PagoDTO> obtenerTodosPagos() {

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

        boolean esAdmin =
                usuario.getRoles()
                        .stream()
                        .anyMatch(r ->
                                "ADMIN".equals(r.getNombre()));

        List<Pago> pagos;

        if (esAdmin) {

            pagos = pagoRepository.findAll();

        } else {

            pagos = pagoRepository.findByUsuarioUsuariosId(
                    usuario.getUsuariosId()
            );
        }

        return pagos.stream()
                .map(pagoMapper::ToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PagoDTO> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id)
                .map(pagoMapper::ToDTO);
    }

    @Transactional
    public PagoDTO crearPago(PagoDTO pagoDTO) {

        Venta venta = ventaRepository.findById(
                        pagoDTO.getVentaId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Venta no encontrada"
                        ));

        // NO PAGAR VENTA ANULADA
        if ("ANULADA".equals(venta.getEstado())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede registrar un pago para una venta anulada"
            );
        }
        Long cantidadPagos =
                pagoRepository.contarPagosActivosPorVenta(
                        pagoDTO.getVentaId()
                );

        if (cantidadPagos > 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La venta ya tiene un pago PAGADO registrado"
            );
        }

        MetodoPago metodoPago =
                metodoPagoRepository.findById(
                                pagoDTO.getMetodoPagoId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Método de pago no encontrado"
                                ));

        Pago pago = new Pago();

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

        pago.setUsuario(usuario);

        pago.setEstado("PAGADO");

        pago.setVenta(venta);

        pago.setMetodoPago(metodoPago);

        // MONTO AUTOMÁTICO
        pago.setMonto(
                venta.getTotal()
        );

        pago.setFecha_Pago(
                new Date()
        );

        Pago pagoGuardado =
                pagoRepository.save(pago);

        // ACTUALIZAR VENTA
        venta.setEstado("EMITIDA");
        venta.setEstadoPago("PAGADA");

        System.out.println(
                "CREAR PAGO -> ESTADO = "
                        + venta.getEstado()
        );

        System.out.println(
                "CREAR PAGO -> ESTADO_PAGO = "
                        + venta.getEstadoPago()
        );


        ventaRepository.save(venta);

        return pagoMapper.ToDTO(
                pagoGuardado
        );
    }

    @Transactional
    public void eliminarPago(Long id) {

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pago no encontrado"
                        ));

        // EVITAR ANULAR DOS VECES
        if ("ANULADO".equals(pago.getEstado())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El pago ya fue anulado"
            );
        }

        pago.setEstado("ANULADO");
        pagoRepository.save(pago);
        Venta venta = pago.getVenta();
        venta.setEstado("BORRADOR");
        venta.setEstadoPago("PENDIENTE");

        ventaRepository.save(venta);
    }
}