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
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Dispo_alquiler_diariaService {
    private static final Logger logger = LoggerFactory.getLogger(Dispo_alquiler_diariaService.class);
    private final Dispo_alquiler_diariaRepository repository;
    private final AtomicInteger executionCount = new AtomicInteger(0); //borrar para el otro metodo
    private static final int MAX_EXECUTIONS = 3;
    @Autowired
    public Dispo_alquiler_diariaService(Dispo_alquiler_diariaRepository repository) {
        this.repository = repository;
    }

    public List<Dispo_alquiler_diaria> getAllDispo_alquiler_diaria() {
        return repository.getAllDispo_alquiler_diaria();
    }

    // Método programado para cargar el JSON tres veces al día
    @Scheduled(cron = "0 * * * * ?")
  //  @Scheduled(cron = "0 0 9,10,11 * * ?") // Programación para 9, 12 y 15 horas

    public void cargarJsonSiNoCargado() {
        int currentCount = executionCount.incrementAndGet(); // Incrementa el contador

        if (currentCount <= MAX_EXECUTIONS) {
        System.out.println("Verificando si el JSON se cargó hoy...");
        if (!jsonYaCargadoHoy()) {
            System.out.println("Cargando JSON ahora...");
            cargarJson();
        } else {
            System.out.println("El JSON ya se cargó hoy.");
        }   }
        else {
            logger.info("Se ha alcanzado el máximo número de ejecuciones.");
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

    private void cargarJson() {
        JSONParser parser = new JSONParser();
        try {
            String jsonUrl = "http://example.com/datos.json";
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Leer la respuesta de la URL
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONArray jsonArray = (JSONArray) parser.parse(reader);


            // Creo lista de objetos Dispo_alquiler_diaria desde JSON
            List<Dispo_alquiler_diaria> dataList = new ArrayList<>();

            for (Object item : jsonArray) {
                JSONObject jsonObject = (JSONObject) item;
                Dispo_alquiler_diaria dispo = new Dispo_alquiler_diaria();
                dispo.setRubroNombre((String) jsonObject.get("rubro_nombre"));
                dispo.setEstado((String) jsonObject.get("estado"));
                dispo.setBienEmpresaNombre((String) jsonObject.get("bienEmpresa_nombre"));
                dispo.setContratoEmpresaNombre((String) jsonObject.get("contratoEmpresa_nombre"));
                dispo.setPropietario((String) jsonObject.get("propietario"));
                dispo.setContratoEstado((String) jsonObject.get("contrato_estado"));
                dispo.setContratoNumeroComp((String) jsonObject.get("contrato_numeroComp"));
                dispo.setContratoFechaFin(jsonObject.containsKey("contrato_fechaFin")
                        ? new java.sql.Date(((Date) jsonObject.get("contrato_fechaFin")).getTime())
                        : null);
                dispo.setContratoFecha(jsonObject.containsKey("contrato_fecha")
                        ? new java.sql.Date(((Date) jsonObject.get("contrato_fecha")).getTime())
                        : null);
                dispo.setContratoClienteNombre((String) jsonObject.get("contrato_cliente_nombre"));
                dispo.setContratoNumero((String) jsonObject.get("contrato_numero"));
                dispo.setContratoFechaInicio(jsonObject.containsKey("contrato_fechaInicio")
                        ? new java.sql.Date(((Date) jsonObject.get("contrato_fechaInicio")).getTime())
                        : null);
                dispo.setLineaNombre((String) jsonObject.get("linea_nombre"));
                dispo.setPropio((String) jsonObject.get("propio"));
                dispo.setEnTransito((String) jsonObject.get("enTransito"));
                dispo.setOrdenDeTrabajoDescripcion((String) jsonObject.get("ordenDeTrabajo_descripcion"));
                dispo.setOrdenDeTrabajoEstado((String) jsonObject.get("ordenDeTrabajo_estado"));
                dispo.setOrdenDeTrabajoNumeroComp((String) jsonObject.get("ordenDeTrabajo_numeroComp"));
                dispo.setOrdenDeTrabajoFechaEntrega(jsonObject.containsKey("ordenDeTrabajo_fechaEntrega")
                        ? new java.sql.Date(((Date) jsonObject.get("ordenDeTrabajo_fechaEntrega")).getTime())
                        : null);
                dispo.setOrdenDeTrabajoFechaInicio(jsonObject.containsKey("ordenDeTrabajo_fechaInicio")
                        ? new java.sql.Date(((Date) jsonObject.get("ordenDeTrabajo_fechaInicio")).getTime())
                        : null);
                dispo.setSucursalNombre((String) jsonObject.get("sucursal_nombre"));
                dispo.setEntregado((String) jsonObject.get("entregado"));
                dispo.setProveedorNombre((String) jsonObject.get("proveedor_nombre"));
                dispo.setArticuloCodigo((String) jsonObject.get("articulo_codigo"));
                dispo.setBienDescripcion((String) jsonObject.get("bien_descripcion"));
                dispo.setBienEstado((String) jsonObject.get("bien_estado"));
                dispo.setBienAFabricacion((String) jsonObject.get("bien_aFabricacion"));
                dispo.setBienDepositoAlmacenNombre((String) jsonObject.get("bien_depositoAlmacen_nombre"));
                dispo.setBienIdentificacion((String) jsonObject.get("bien_identificacion"));
                dispo.setBienModelo((String) jsonObject.get("bien_modelo"));
                dispo.setBienSerie((String) jsonObject.get("bien_serie"));
                dispo.setFechaDispo(jsonObject.containsKey("Fecha_dispo")
                        ? new java.sql.Date(((Date) jsonObject.get("Fecha_dispo")).getTime())
                        : null);
                dataList.add(dispo);
            }

            // Insertar datos en el repositorio
            repository.batchInsert(dataList);
            logger.info("JSON cargado correctamente.");
        } catch (Exception e) {
            logger.error("Error al cargar JSON", e);
        }
    }
}