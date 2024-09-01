package com.example.salary_calculation.reporting;

import com.example.salary_calculation.model.PessoaSalarioConsolidado;
import com.example.salary_calculation.service.PessoaSalarioConsolidadoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    @Autowired
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    public byte[] generateReport() throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/salarios_consolidado.jrxml");

        List<PessoaSalarioConsolidado> data = pessoaSalarioConsolidadoService.findAll();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }
}