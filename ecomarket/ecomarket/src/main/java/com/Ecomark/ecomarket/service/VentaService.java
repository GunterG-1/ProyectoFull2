package com.Ecomark.ecomarket.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ecomark.ecomarket.model.Cupon;
import com.Ecomark.ecomarket.model.DetalleVenta;
import com.Ecomark.ecomarket.model.Venta;
import com.Ecomark.ecomarket.repository.CuponRepository;
import com.Ecomark.ecomarket.repository.VentaRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service
@Transactional
public class VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private JavaMailSender mailSender;

    public List<Venta> listarVentas(){
        return ventaRepository.findAll();
    }

    public Venta registrarVenta(Venta venta , String codigoCupon){
        venta.setFechaVenta(new Date());

        BigDecimal total =venta.getDetalle().stream()
        .map(d->d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
        
   Optional<Cupon> cuponOpt = Optional.ofNullable(codigoCupon)
        .map(String::trim)
        .flatMap(codigo -> cuponRepository.findById(codigo));

    if (cuponOpt.isPresent() && Boolean.TRUE.equals(cuponOpt.get().getActivo())) {
    BigDecimal descuento = cuponOpt.get().getDescuento();
    total = total.subtract(total.multiply(descuento));
    }

    venta.setTotal(total);
    venta.getDetalle().forEach(d ->d.setVenta(venta));
    Venta listarVentas =ventaRepository.save(venta);

    enviarFacturaPorCorreo(listarVentas);
    return listarVentas;

    }
    private void enviarFacturaPorCorreo(Venta venta){
        try{
        StringBuilder cuerpo =new StringBuilder();
        cuerpo.append("Factura electronica\n\n");
        cuerpo.append("Cliente:").append(venta.getNom_usuario()).append("\n");
        cuerpo.append("fecha:").append(venta.getFechaVenta()).append("\n");
        cuerpo.append("Total:$").append(venta.getTotal()).append("\n\n");
        cuerpo.append("Datalle:\n");
        for (DetalleVenta d: venta.getDetalle()){
            cuerpo.append(" - ").append(d.getNombreProducto())
            .append(" x ").append(d.getCantidad())
            .append(" = $").append(d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
            .append("\n");
        }cuerpo.append("</ul>");

            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
            helper.setTo(venta.getEmail());
            helper.setSubject("Factura electrónica - Venta " + venta.getId());
            helper.setText(cuerpo.toString(), true);

            mailSender.send(mensaje);

        } catch (MessagingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
    }


    
}
}