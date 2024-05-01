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
        try {
            return jdbcTemplate.query(sql, new DispoAlquilerDiariaRowMapper());
        } catch (DataAccessException e) {
            logger.error("Error al obtener registros", e);
            return Collections.emptyList(); // Devuelve una lista vacía en caso de error
        }
    }

    public boolean jsonYaCargadoHoy(Date today) {
        String sql = "SELECT COUNT(*) FROM dispo_alquiler_diaria WHERE Fecha_dispo = :today";
        SqlParameterSource paramSource = new MapSqlParameterSource("today", new java.sql.Date(today.getTime()));
        try {
            Integer count = jdbcTemplate.queryForObject(sql, paramSource, Integer.class);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            logger.error("Error al verificar si el JSON ya se cargó", e);
            return false; // Retorna false en caso de error
        }
    }

    public void batchInsert(List<Dispo_alquiler_diaria> dataList) {
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

        // Crea una lista de MapSqlParameterSource
        List<MapSqlParameterSource> parameterSources = new ArrayList<>();

        for (Dispo_alquiler_diaria dispo : dataList) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("rubroNombre", dispo.getRubroNombre());
            params.addValue("estado", dispo.getEstado());
            params.addValue("bienEmpresaNombre", dispo.getBienEmpresaNombre());
            params.addValue("contratoEmpresaNombre", dispo.getContratoEmpresaNombre());
            params.addValue("propietario", dispo.getPropietario());
            params.addValue("contratoEstado", dispo.getContratoEstado());
            params.addValue("contratoNumeroComp", dispo.getContratoNumeroComp());
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

        // Ejecuta el batchUpdate usando NamedParameterJdbcTemplate
        jdbcTemplate.batchUpdate(sql, parameterSources.toArray(new MapSqlParameterSource[0]));
    }

    public Set<String> obtenerNombresCampos() {
        Set<String> nombresCampos = new HashSet<>();
        String sql = "SHOW COLUMNS FROM dispo_alquiler_diaria"; // Consulta para obtener nombres de columnas

        try {
            // Ejecutar la consulta y procesar el ResultSet
            jdbcTemplate.query(sql, (rs) -> {
                while (rs.next()) {
                    // Extraer el nombre del campo
                    String fieldName = rs.getString("Field");
                    nombresCampos.add(fieldName);
                }
            });
        } catch (DataAccessException e) {
            // Manejo de errores en caso de que falle la consulta
            System.err.println("Error al obtener nombres de campos: " + e.getMessage());
        }

        return nombresCampos; // Devolver el conjunto de nombres de campos
    }


    private static class DispoAlquilerDiariaRowMapper implements RowMapper<Dispo_alquiler_diaria> {
        @Override
        public Dispo_alquiler_diaria mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dispo_alquiler_diaria dispo = new Dispo_alquiler_diaria();
            dispo.setId(rs.getInt("id"));
            dispo.setRubroNombre(rs.getString("rubro_nombre"));
            dispo.setEstado(rs.getString("estado"));
            dispo.setBienEmpresaNombre(rs.getString("bienEmpresa_nombre"));
            dispo.setContratoEmpresaNombre(rs.getString("contratoEmpresa_nombre"));
            dispo.setPropietario(rs.getString("propietario"));
            dispo.setContratoEstado(rs.getString("contrato_estado"));
            dispo.setContratoNumeroComp(rs.getString("contrato_numeroComp"));
            dispo.setContratoFechaFin(rs.getDate("contrato_fechaFin"));
            dispo.setContratoFecha(rs.getDate("contrato_fecha"));
            dispo.setContratoClienteNombre(rs.getString("contrato_cliente_nombre"));
            dispo.setContratoNumero(rs.getString("contrato_numero"));
            dispo.setContratoFechaInicio(rs.getDate("contrato_fechaInicio"));
            dispo.setLineaNombre(rs.getString("linea_nombre"));
            dispo.setPropio(rs.getString("propio"));
            dispo.setEnTransito(rs.getString("enTransito"));
            dispo.setOrdenDeTrabajoDescripcion(rs.getString("ordenDeTrabajo_descripcion"));
            dispo.setOrdenDeTrabajoEstado(rs.getString("ordenDeTrabajo_estado"));
            dispo.setOrdenDeTrabajoNumeroComp(rs.getString("ordenDeTrabajo_numeroComp"));
            dispo.setOrdenDeTrabajoFechaEntrega(rs.getDate("ordenDeTrabajo_fechaEntrega"));
            dispo.setOrdenDeTrabajoFechaInicio(rs.getDate("ordenDeTrabajo_fechaInicio"));
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
            dispo.setFechaDispo(rs.getDate("Fecha_dispo"));
            return dispo;
        }
    }
    }

