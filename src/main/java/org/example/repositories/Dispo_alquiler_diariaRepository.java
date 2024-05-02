package org.example.repositories;

import org.example.models.Dispo_alquiler_diaria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class Dispo_alquiler_diariaRepository {
    private static final Logger logger = LoggerFactory.getLogger(Dispo_alquiler_diariaRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public Dispo_alquiler_diariaRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Dispo_alquiler_diaria> getAllDispo_alquiler_diaria() {
        String sql = "SELECT * FROM dispo_alquiler_diaria";
        List<Dispo_alquiler_diaria> resultados = Collections.emptyList();
        try {
            resultados = jdbcTemplate.query(sql, new DispoAlquilerDiariaRowMapper());

            if (resultados == null) {
                logger.warn("La consulta de todos los registros devolvió un resultado nulo.");
                resultados = Collections.emptyList(); // Asegura que no haya un valor nulo
            } else if (resultados.isEmpty()) {
                logger.info("La consulta de todos los registros no devolvió ningún resultado."); // Información para depuración
            }
        } catch (DataAccessException e) {
            logger.error("Error al obtener registros de dispo_alquiler_diaria", e); // Manejo de excepciones
        } catch (Exception ex) {
            logger.error("Error inesperado al obtener registros de dispo_alquiler_diaria", ex); // Manejo de errores inesperados
        }
        return resultados; // Retorna la lista, que será vacía en caso de error o sin resultados
    }

    public boolean jsonYaCargadoHoy(Date today) {
        if (today == null) {
            logger.warn("La fecha proporcionada para verificar si el JSON se cargó es nula.");
            return false; // Retorna false si la fecha es nula
        }
        String sql = "SELECT COUNT(*) FROM dispo_alquiler_diaria WHERE Fecha_dispo = :today";
        SqlParameterSource paramSource = new MapSqlParameterSource("today", new java.sql.Date(today.getTime()));

        try {
            Integer count = jdbcTemplate.queryForObject(sql, paramSource, Integer.class);

            if (count == null) {
                logger.warn("El resultado de la consulta para verificar si el JSON se cargó fue nulo.");
                return false; // Retorna false si el resultado es nulo
            }

            return count > 0; // Si la cuenta es mayor que 0, significa que ya se cargó
        } catch (DataAccessException e) {
            logger.error("Error al verificar si el JSON ya se cargó hoy", e); // Manejo de errores en la consulta
            return false; // Retorna false en caso de error
        } catch (Exception ex) {
            logger.error("Error inesperado al verificar si el JSON ya se cargó hoy", ex); // Manejo de otros errores
            return false;
        }
    }

    public void batchInsert(List<Dispo_alquiler_diaria> dataList) {
        // Asegurarse de que dataList no sea nula ni vacía
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("La lista de datos para insertar no puede ser nula ni vacía");
        }

        String sql = "INSERT INTO dispo_alquiler_diaria (id, rubro_nombre, estado, bienEmpresa_nombre, "
                + "contratoEmpresa_nombre, propietario, contrato_estado, contrato_numeroComp, contrato_fechaFin, "
                + "contrato_fecha, contrato_cliente_nombre, contrato_numero, contrato_fechaInicio, "
                + "linea_nombre, propio, enTransito, ordenDeTrabajo_descripcion, ordenDeTrabajo_estado, "
                + "ordenDeTrabajo_numeroComp, ordenDeTrabajo_fechaEntrega, ordenDeTrabajo_fechaInicio, "
                + "sucursal_nombre, entregado, proveedor_nombre, articulo_codigo, bien_descripcion, bien_estado, "
                + "bien_aFabricacion, bien_depositoAlmacen_nombre, bien_identificacion, bien_modelo, bien_serie, Fecha_dispo) "
                + "VALUES (NULL, :rubroNombre, :estado, :bienEmpresaNombre, :contratoEmpresaNombre, :propietario, "
                + ":contratoEstado, :contratoNumeroComp, :contratoFechaFin, :contratoFecha, :contratoClienteNombre, "
                + ":contratoNumero, :contratoFechaInicio, :lineaNombre, :propio, :enTransito, :ordenDeTrabajoDescripcion, "
                + ":ordenDeTrabajoEstado, :ordenDeTrabajoNumeroComp, :ordenDeTrabajoFechaEntrega, :ordenDeTrabajoFechaInicio, "
                + ":sucursalNombre, :entregado, :proveedorNombre, :articuloCodigo, :bienDescripcion, :bienEstado, "
                + ":bienAFabricacion, :bienDepositoAlmacenNombre, :bienIdentificacion, :bienModelo, :bienSerie, :FechaDispo)";

        // Crear una lista de MapSqlParameterSource
        List<MapSqlParameterSource> parameterSources = new ArrayList<>();

        for (Dispo_alquiler_diaria dispo : dataList) {
            MapSqlParameterSource params = new MapSqlParameterSource();

            // Validar campos clave antes de agregarlos al MapSqlParameterSource
            if (dispo.getRubroNombre() == null || dispo.getRubroNombre().isEmpty()) {
                throw new IllegalArgumentException("El campo 'rubro_nombre' no puede ser nulo o vacío");
            }

            params.addValue("rubroNombre", dispo.getRubroNombre());
            params.addValue("estado", dispo.getEstado());
            params.addValue("bienEmpresaNombre", dispo.getBienEmpresaNombre());
            params.addValue("contratoEmpresaNombre", dispo.getContratoEmpresaNombre());
            params.addValue("propietario", dispo.getPropietario());
            params.addValue("contratoEstado", dispo.getContratoEstado());
            params.addValue("contratoNumeroComp", dispo.getContratoNumeroComp());

            // Manejo de campos Date con control de nulos
            params.addValue("contratoFechaFin", dispo.getContratoFechaFin() != null ? new java.sql.Date(dispo.getContratoFechaFin().getTime()) : null);
            params.addValue("contratoFecha", dispo.getContratoFecha() != null ? new java.sql.Date(dispo.getContratoFecha().getTime()) : null);

            params.addValue("contratoClienteNombre", dispo.getContratoClienteNombre());
            params.addValue("contratoNumero", dispo.getContratoNumero());
            params.addValue("contratoFechaInicio", dispo.getContratoFechaInicio() != null ? new java.sql.Date(dispo.getContratoFechaInicio().getTime()) : null);
            params.addValue("lineaNombre", dispo.getLineaNombre());
            params.addValue("propio", dispo.getPropio());
            params.addValue("enTransito", dispo.getEnTransito());
            params.addValue("ordenDeTrabajoDescripcion", dispo.getOrdenDeTrabajoDescripcion());
            params.addValue("ordenDeTrabajoEstado", dispo.getOrdenDeTrabajoEstado());
            params.addValue("ordenDeTrabajoNumeroComp", dispo.getOrdenDeTrabajoNumeroComp());
            params.addValue("ordenDeTrabajoFechaEntrega", dispo.getOrdenDeTrabajoFechaEntrega() != null ? new java.sql.Date(dispo.getOrdenDeTrabajoFechaEntrega().getTime()) : null);
            params.addValue("ordenDeTrabajoFechaInicio", dispo.getOrdenDeTrabajoFechaInicio() != null ? new java.sql.Date(dispo.getOrdenDeTrabajoFechaInicio().getTime()) : null);
            params.addValue("sucursalNombre", dispo.getSucursalNombre());
            params.addValue("entregado", dispo.getEntregado());
            params.addValue("proveedorNombre", dispo.getProveedorNombre());
            params.addValue("articuloCodigo", dispo.getArticuloCodigo());
            params.addValue("bienDescripcion", dispo.getBienDescripcion());
            params.addValue("bienEstado", dispo.getBienEstado());
            params.addValue("bienAFabricacion", dispo.getBienAFabricacion());
            params.addValue("bienDepositoAlmacenNombre", dispo.getBienDepositoAlmacenNombre());
            params.addValue("bienIdentificacion", dispo.getBienIdentificacion());
            params.addValue("bienModelo", dispo.getBienModelo());
            params.addValue("bienSerie", dispo.getBienSerie());
            params.addValue("FechaDispo", dispo.getFechaDispo() != null ? new java.sql.Date(dispo.getFechaDispo().getTime()) : null);
            parameterSources.add(params);
        }

        try {
            // Ejecuta el batchUpdate usando NamedParameterJdbcTemplate
            jdbcTemplate.batchUpdate(sql, parameterSources.toArray(new MapSqlParameterSource[0]));
            System.out.println("Batch insert completado con éxito.");
        } catch (DataAccessException e) {
            logger.error("Error al realizar batch insert", e);
            throw new RuntimeException("Error al insertar datos", e);
        }
    }

    public Set<String> obtenerNombresCampos() {
        Set<String> nombresCampos = new HashSet<>();
        String sql = "SHOW COLUMNS FROM dispo_alquiler_diaria"; // Consulta para obtener nombres de columnas

        try {
            jdbcTemplate.query(sql, (rs) -> {
                while (rs.next()) {
                    // Valida que el campo "Field" no sea nulo antes de agregarlo
                    String fieldName = rs.getString("Field");
                    if (fieldName != null && !fieldName.trim().isEmpty()) {
                        nombresCampos.add(fieldName);
                    } else {
                        logger.warn("Se encontró un campo sin nombre o vacío en la tabla dispo_alquiler_diaria");
                    }
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error al obtener nombres de campos para dispo_alquiler_diaria", e); // Manejo de errores
        } catch (Exception ex) {
            logger.error("Error inesperado durante la obtención de nombres de campos", ex); // Controla otros errores
        }

        if (nombresCampos.isEmpty()) {
            logger.warn("No se encontraron nombres de campos en la tabla dispo_alquiler_diaria");
        }

        return nombresCampos; // Devuelve el conjunto de nombres de campos
    }
    private static class DispoAlquilerDiariaRowMapper implements RowMapper<Dispo_alquiler_diaria> {
        @Override
        public Dispo_alquiler_diaria mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dispo_alquiler_diaria dispo = new Dispo_alquiler_diaria();
            try {
                dispo.setId(rs.getInt("id"));
                dispo.setRubroNombre(rs.getString("rubro_nombre"));
                dispo.setEstado(rs.getString("estado"));
                dispo.setBienEmpresaNombre(rs.getString("bienEmpresa_nombre"));
                dispo.setContratoEmpresaNombre(rs.getString("contratoEmpresa_nombre"));
                dispo.setPropietario(rs.getString("propietario"));
                dispo.setContratoEstado(rs.getString("contrato_estado"));
                dispo.setContratoNumeroComp(rs.getString("contrato_numeroComp"));
                dispo.setContratoFechaFin(obtenerFecha(rs, "contrato_fechaFin"));
                dispo.setContratoFecha(obtenerFecha(rs, "contrato_fecha"));
                dispo.setContratoFechaInicio(obtenerFecha(rs, "contrato_fechaInicio"));
                dispo.setContratoClienteNombre(rs.getString("contrato_cliente_nombre"));
                dispo.setContratoNumero(rs.getString("contrato_numero"));
                dispo.setLineaNombre(rs.getString("linea_nombre"));
                dispo.setPropio(rs.getString("propio"));
                dispo.setEnTransito(rs.getString("enTransito"));
                dispo.setOrdenDeTrabajoDescripcion(rs.getString("ordenDeTrabajo_descripcion"));
                dispo.setOrdenDeTrabajoEstado(rs.getString("ordenDeTrabajo_estado"));
                dispo.setOrdenDeTrabajoNumeroComp(rs.getString("ordenDeTrabajo_numeroComp"));
                dispo.setOrdenDeTrabajoFechaEntrega(obtenerFecha(rs, "ordenDeTrabajo_fechaEntrega"));
                dispo.setOrdenDeTrabajoFechaInicio(obtenerFecha(rs, "ordenDeTrabajo_fechaInicio"));
                dispo.setSucursalNombre(rs.getString("sucursal_nombre"));
                dispo.setEntregado(rs.getString("entregado"));
                dispo.setProveedorNombre(rs.getString("proveedor_nombre"));
                dispo.setArticuloCodigo(rs.getString("articulo_codigo"));
                dispo.setBienDescripcion(rs.getString("bien_descripcion"));
                dispo.setBienEstado(rs.getString("bien_estado"));
                dispo.setBienAFabricacion(rs.getString("bien_aFabricacion"));
                dispo.setBienDepositoAlmacenNombre(rs.getString("bien_depositoAlmacen_nombre"));
                dispo.setBienIdentificacion(rs.getString("bien_identificacion"));
                dispo.setBienModelo(rs.getString("bien_modelo"));
                dispo.setBienSerie(rs.getString("bien_serie"));
                dispo.setFechaDispo(obtenerFecha(rs, "Fecha_dispo"));
            } catch (SQLException e) {
                throw new SQLException("Error al mapear el resultado del ResultSet a Dispo_alquiler_diaria", e);
            }
            return dispo;
        }
        private java.sql.Date obtenerFecha(ResultSet rs, String campo) throws SQLException {
            java.sql.Date fecha = rs.getDate(campo);
            return fecha != null ? fecha : null; // Devuelve null si la fecha es nula
        }
    }
    }

