package com.further.spring.boot.further.Service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class JasperService {

    public byte[] generarReporte(String nombreReporte,
                                 Map<String, Object> parametros,
                                 List<?> datos) throws Exception {

        InputStream reporte = new ClassPathResource(
                "reports/" + nombreReporte + ".jrxml")
                .getInputStream();

        JasperReport jasperReport =
                JasperCompileManager.compileReport(reporte);

        JRBeanCollectionDataSource datasource =
                new JRBeanCollectionDataSource(datos);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        datasource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

}