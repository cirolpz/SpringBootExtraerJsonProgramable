package org.example.services;

import org.example.models.Dispo_alquiler_diaria;
import org.example.repositories.Dispo_alquiler_diariaRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileReader;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@Service
public class Dispo_alquiler_diariaService {
    private static final Logger logger = LoggerFactory.getLogger(Dispo_alquiler_diariaService.class);
    private final Dispo_alquiler_diariaRepository repository;
    private final AtomicInteger executionCount = new AtomicInteger(0);
    private static final int MAX_EXECUTIONS = 3;

    @Autowired
    public Dispo_alquiler_diariaService(Dispo_alquiler_diariaRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo");
        }
        this.repository = repository;
    }
    public List<Dispo_alquiler_diaria> getAllDispo_alquiler_diaria() {
        try {
            return repository.getAllDispo_alquiler_diaria();
        } catch (Exception e) {
            logger.error("Error al obtener registros de dispo_alquiler_diaria", e);
            return Collections.emptyList(); // Devuelve una lista vacía para evitar problemas
        }
    }
    @Scheduled(cron = "0 * * * * ?")  // Método programado para cargar el JSON cada un minuto
  //  @Scheduled(cron = "0 0 9,10,11 * * ?") // Programación para 9, 12 y 15 horas

    public void cargarJsonSiNoCargado() {
        int currentCount = executionCount.incrementAndGet(); // Incrementa el contador
        System.out.println("Verificando si el JSON se cargó hoy...");
        if (currentCount > MAX_EXECUTIONS) {
            logger.info("Se ha alcanzado el máximo número de ejecuciones.");
            return;
        }

        try {
            if (!jsonYaCargadoHoy()) {        // Verificación si el JSON ya fue cargado hoy
                System.out.println("Cargando JSON ahora...");
                cargarJson(); // Llama al método para cargar JSON
            } else {
                System.out.println("El JSON ya se cargó hoy.");
            }
        } catch (Exception e) {
            logger.error("Error al cargar JSON", e);
        }
    }
    private boolean jsonYaCargadoHoy() {
        Date today = new Date();
        try {
            return repository.jsonYaCargadoHoy(today);
        } catch (Exception e) {
            logger.error("Error al comprobar si el JSON ya se cargó hoy", e);
            return false;
        }
    }
/*
private void cargarJson() {
    JSONParser parser = new JSONParser();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
        String jsonUrl = "http://example.com/datos.json";
        URL url = new URL(jsonUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONArray jsonArray = (JSONArray) parser.parse(reader);

        List<Dispo_alquiler_diaria> dataList = new ArrayList<>();

        for (Object item : jsonArray) {
            JSONObject jsonObject = (JSONObject) item;
            Dispo_alquiler_diaria dispo = new Dispo_alquiler_diaria();

            // Validación de datos clave
            if (!jsonObject.containsKey("rubro_nombre") || !jsonObject.containsKey("estado")) {
                logger.warn("Datos clave ausentes: " + jsonObject);
                continue; // Si faltan datos clave, saltar a la siguiente iteración
            }

            dispo.setRubroNombre((String) jsonObject.get("rubro_nombre"));
            dispo.setEstado((String) jsonObject.get("estado"));

            // Validación y asignación de datos con manejo de errores
            if (jsonObject.containsKey("bienEmpresa_nombre")) {
                dispo.setBienEmpresaNombre((String) jsonObject.get("bienEmpresa_nombre"));
            }

            if (jsonObject.containsKey("contratoEmpresa_nombre")) {
                dispo.setContratoEmpresaNombre((String) jsonObject.get("contratoEmpresa_nombre"));
            }

            if (jsonObject.containsKey("propietario")) {
                dispo.setPropietario((String) jsonObject.get("propietario"));
            }

            if (jsonObject.containsKey("contrato_estado")) {
                dispo.setContratoEstado((String) jsonObject.get("contrato_estado"));
            }

            if (jsonObject.containsKey("contrato_numeroComp")) {
                dispo.setContratoNumeroComp((String) jsonObject.get("contrato_numeroComp"));
            }

            // Validación y manejo de fechas con control de errores
            if (jsonObject.containsKey("contrato_fechaFin")) {
                String dateStr = (String) jsonObject.get("contrato_fechaFin");
                try {
                    dispo.setContratoFechaFin(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir contrato_fechaFin: " + dateStr, e);
                    dispo.setContratoFechaFin(null); // Asignar null para evitar errores posteriores
                }
            }

            if (jsonObject.containsKey("contrato_fecha")) {
                String dateStr = (String) jsonObject.get("contrato_fecha");
                try {
                    dispo.setContratoFecha(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir contrato_fecha: " + dateStr, e);
                    dispo.setContratoFecha(null);
                }
            }

            dispo.setContratoClienteNombre((String) jsonObject.get("contrato_cliente_nombre"));
            dispo.setContratoNumero((String) jsonObject.get("contrato_numero"));

            if (jsonObject.containsKey("contrato_fechaInicio")) {
                String dateStr = (String) jsonObject.get("contrato_fechaInicio");
                try {
                    dispo.setContratoFechaInicio(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir contrato_fechaInicio", e);
                    dispo.setContratoFechaInicio(null);
                }
            }

            dispo.setLineaNombre((String) jsonObject.get("linea_nombre"));
            dispo.setPropio((String) jsonObject.get("propio"));
            dispo.setEnTransito((String) jsonObject.get("enTransito"));

            // Validación adicional de fechas para orden de trabajo
            if (jsonObject.containsKey("ordenDeTrabajo_fechaEntrega")) {
                String dateStr = (String) jsonObject.get("ordenDeTrabajo_fechaEntrega");
                try {
                    dispo.setOrdenDeTrabajoFechaEntrega(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir ordenDeTrabajo_fechaEntrega", e);
                    dispo.setOrdenDeTrabajoFechaEntrega(null);
                }
            }

            if (jsonObject.containsKey("ordenDeTrabajo_fechaInicio")) {
                String dateStr = (String) jsonObject.get("ordenDeTrabajo_fechaInicio");
                try {
                    dispo.setOrdenDeTrabajoFechaInicio(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir ordenDeTrabajo_fechaInicio", e);
                    dispo.setOrdenDeTrabajoFechaInicio(null);
                }
            }

            dispo.setSucursalNombre((String) jsonObject.get("sucursal_nombre"));
            dispo.setEntregado((String) jsonObject.get("entregado"));
            dispo.setProveedorNombre((String) jsonObject.get("proveedor_nombre"));

            dispo.setArticuloCodigo((String) jsonObject.get("articulo_codigo"));
            dispo.setBienDescripcion((String) jsonObject.get("bien_descripción"));
            dispo.setBienEstado((String) jsonObject.get("bien_estado"));

            if (jsonObject.containsKey("Fecha_dispo")) {
                String dateStr = (String) jsonObject.get("Fecha_dispo");
                try {
                    dispo.setFechaDispo(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir Fecha_dispo: " + dateStr, e);
                    dispo.setFechaDispo(null);
                }
            }

            dataList.add(dispo);
        }

        // Insertar datos validados en el repositorio
        repository.batchInsert(dataList);
        logger.info("JSON cargado correctamente.");
    } catch (Exception e) {
        logger.error("Error al cargar JSON", e);
    }
}
*/
private void cargarJson() {
    JSONParser parser = new JSONParser();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
        FileReader reader = new FileReader("datos.JSON");

        JSONArray jsonArray = (JSONArray) parser.parse(reader);
        List<Dispo_alquiler_diaria> dataList = new ArrayList<>();        // Crear lista de objetos Dispo_alquiler_diaria desde JSON
        for (Object item : jsonArray) {
            JSONObject jsonObject = (JSONObject) item;
            Dispo_alquiler_diaria dispo = new Dispo_alquiler_diaria();
            if (!jsonObject.containsKey("rubro_nombre") || !jsonObject.containsKey("estado")) {            // Validar campos clave
                logger.warn("Datos incompletos para el objeto: " + jsonObject);
                continue; // Saltar a la siguiente iteración si faltan datos clave
            }
            dispo.setRubroNombre((String) jsonObject.get("rubro_nombre"));
            dispo.setEstado((String) jsonObject.get("estado"));
            if (jsonObject.containsKey("bienEmpresa_nombre")) {            // Validar y asignar valores, manejando excepciones
                dispo.setBienEmpresaNombre((String) jsonObject.get("bienEmpresa_nombre"));
            }
            if (jsonObject.containsKey("contratoEmpresa_nombre")) {
                dispo.setContratoEmpresaNombre((String) jsonObject.get("contratoEmpresa_nombre"));
            }
            if (jsonObject.containsKey("propietario")) {
                dispo.setPropietario((String) jsonObject.get("propietario"));
            }

            if (jsonObject.containsKey("contrato_estado")) {
                dispo.setContratoEstado((String) jsonObject.get("contrato_estado"));
            }

            if (jsonObject.containsKey("contrato_numeroComp")) {
                dispo.setContratoNumeroComp((String) jsonObject.get("contrato_numeroComp"));
            }
            // Validación y conversión de fechas con control de errores
            if (jsonObject.containsKey("contrato_fechaFin")) {
                String dateStr = (String) jsonObject.get("contrato_fechaFin");
                try {
                    dispo.setContratoFechaFin(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir contrato_fechaFin: " + dateStr, e);
                    dispo.setContratoFechaFin(null); // Asignar null para evitar errores posteriores
                }
            }

            if (jsonObject.containsKey("contrato_fecha")) {
                String dateStr = (String) jsonObject.get("contrato_fecha");
                try {
                    dispo.setContratoFecha(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir contrato_fecha: " + dateStr, e);
                    dispo.setContratoFecha(null);
                }
            }

            if (jsonObject.containsKey("contrato_cliente_nombre")) {
                dispo.setContratoClienteNombre((String) jsonObject.get("contrato_cliente_nombre"));
            }

            if (jsonObject.containsKey("contrato_numero")) {
                dispo.setContratoNumero((String) jsonObject.get("contrato_numero"));
            }

            if (jsonObject.containsKey("contrato_fechaInicio")) {
                String dateStr = (String) jsonObject.get("contrato_fechaInicio");
                try {
                    dispo.setContratoFechaInicio(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir contrato_fechaInicio: " + dateStr, e);
                    dispo.setContratoFechaInicio(null);
                }
            }

            dispo.setLineaNombre((String) jsonObject.get("linea_nombre"));
            dispo.setPropio((String) jsonObject.get("propio"));
            dispo.setEnTransito((String) jsonObject.get("enTransito"));
            dispo.setOrdenDeTrabajoDescripcion((String) jsonObject.get("ordenDeTrabajo_descripción"));
            dispo.setOrdenDeTrabajoEstado((String) jsonObject.get("ordenDeTrabajo_estado"));

            if (jsonObject.containsKey("ordenDeTrabajo_fechaEntrega")) {
                String dateStr = (String) jsonObject.get("ordenDeTrabajo_fechaEntrega");
                try {
                    dispo.setOrdenDeTrabajoFechaEntrega(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir ordenDeTrabajo_fechaEntrega: " + dateStr, e);
                    dispo.setOrdenDeTrabajoFechaEntrega(null);
                }
            }

            dispo.setSucursalNombre((String) jsonObject.get("sucursal_nombre"));
            dispo.setEntregado((String) jsonObject.get("entregado"));
            dispo.setProveedorNombre((String) jsonObject.get("proveedor_nombre"));
            dispo.setArticuloCodigo((String) jsonObject.get("articulo_codigo"));

            // Validar y asignar la fecha de disponibilidad
            if (jsonObject.containsKey("Fecha_dispo")) {
                String dateStr = (String) jsonObject.get("Fecha_dispo");
                try {
                    dispo.setFechaDispo(new java.sql.Date(dateFormat.parse(dateStr).getTime()));
                } catch (java.text.ParseException e) {
                    logger.error("Error al convertir Fecha_dispo: " + dateStr, e);
                    dispo.setFechaDispo(null);
                }
            }
            dataList.add(dispo);
        }
        repository.batchInsert(dataList);
        logger.info("JSON cargado correctamente.");
    } catch (Exception e) {
        logger.error("Error al cargar JSON", e);
    }
}
}
