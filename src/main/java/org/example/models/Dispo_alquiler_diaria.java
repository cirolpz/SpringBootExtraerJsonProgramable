package org.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name="dispo_alquiler_diaria")
public class Dispo_alquiler_diaria {
    // ID de la tabla con auto-incremento
    @Id
    @Column(name="id")
    private int id;
    @Column(name="rubro_nombre")
    // Nombre del rubro
    private String rubroNombre;
    @Column(name="estado")

    // Estado
    private String estado;
    @Column(name="bienEmpresa_nombre")

    // Nombre del bien de la empresa
    private String bienEmpresaNombre;
    @Column(name="contratoEmpresa_nombre")

    // Nombre del contrato de la empresa
    private String contratoEmpresaNombre;
    @Column(name="propietario")

    // Nombre del propietario
    private String propietario;
    @Column(name="contrato_estado")

    // Estado del contrato
    private String contratoEstado;
    @Column(name="contrato_numeroComp")

    // Número de comprobante del contrato
    private String contratoNumeroComp;
    @Column(name="contrato_fechaFin")

    // Fecha de fin del contrato
    private Date contratoFechaFin;
    @Column(name="contrato_fecha")

    // Fecha del contrato
    private Date contratoFecha;
    @Column(name="contrato_cliente_nombre")

    // Nombre del cliente en el contrato
    private String contratoClienteNombre;
    @Column(name="contrato_numero")

    // Número del contrato
    private String contratoNumero;
    @Column(name="contrato_fechaInicio")

    // Fecha de inicio del contrato
    private Date contratoFechaInicio;
    @Column(name="linea_nombre")

    // Nombre de la línea
    private String lineaNombre;
    @Column(name="propio")

    // Indica si es propio
    private String propio;
    @Column(name="enTransito")

    // Indica si está en tránsito
    private String enTransito;
    @Column(name="ordenDeTrabajo_descripcion")

    // Descripción de la orden de trabajo
    private String ordenDeTrabajoDescripcion;
    @Column(name="ordenDeTrabajo_estado")

    // Estado de la orden de trabajo
    private String ordenDeTrabajoEstado;
    @Column(name="ordenDeTrabajo_numeroComp")

    // Número de comprobante de la orden de trabajo
    private String ordenDeTrabajoNumeroComp;
    @Column(name="ordenDeTrabajo_fechaEntrega")

    // Fecha de entrega de la orden de trabajo
    private Date ordenDeTrabajoFechaEntrega;
    @Column(name="ordenDeTrabajo_fechaInicio")

    // Fecha de inicio de la orden de trabajo
    private Date ordenDeTrabajoFechaInicio;
    @Column(name="sucursal_nombre")

    // Nombre de la sucursal
    private String sucursalNombre;
    @Column(name="entregado")

    // Indica si fue entregado
    private String entregado;
    @Column(name="proveedor_nombre")

    // Nombre del proveedor
    private String proveedorNombre;
    @Column(name="articulo_codigo")

    // Código del artículo
    private String articuloCodigo;
    @Column(name="bien_descripcion")

    // Descripción del bien
    private String bienDescripcion;
    @Column(name="bien_estado")

    // Estado del bien
    private String bienEstado;
    @Column(name="bien_aFabricacion")

    // Año de fabricación del bien
    private String bienAFabricacion;
    @Column(name="bien_depositoAlmacen_nombre")

    // Nombre del depósito o almacén
    private String bienDepositoAlmacenNombre;
    @Column(name="bien_identificacion")

    // Identificación del bien
    private String bienIdentificacion;
    @Column(name="bien_modelo")

    // Modelo del bien
    private String bienModelo;
    @Column(name="bien_serie")

    // Serie del bien
    private String bienSerie;
    @Column(name="Fecha_dispo")

    // Fecha de disponibilidad
    private Date fechaDispo;
    public Dispo_alquiler_diaria(){}
    public Dispo_alquiler_diaria(int id, String rubroNombre, String estado, String bienEmpresaNombre, String contratoEmpresaNombre, String propietario, String contratoEstado, String contratoNumeroComp, Date contratoFechaFin, Date contratoFecha, String contratoClienteNombre, String contratoNumero, Date contratoFechaInicio, String lineaNombre, String propio, String enTransito, String ordenDeTrabajoDescripcion, String ordenDeTrabajoEstado, String ordenDeTrabajoNumeroComp, Date ordenDeTrabajoFechaEntrega, Date ordenDeTrabajoFechaInicio, String sucursalNombre, String entregado, String proveedorNombre, String articuloCodigo, String bienDescripcion, String bienEstado, String bienAFabricacion, String bienDepositoAlmacenNombre, String bienIdentificacion, String bienModelo, String bienSerie, Date fechaDispo) {
        this.id = id;
        this.rubroNombre = rubroNombre;
        this.estado = estado;
        this.bienEmpresaNombre = bienEmpresaNombre;
        this.contratoEmpresaNombre = contratoEmpresaNombre;
        this.propietario = propietario;
        this.contratoEstado = contratoEstado;
        this.contratoNumeroComp = contratoNumeroComp;
        this.contratoFechaFin = contratoFechaFin;
        this.contratoFecha = contratoFecha;
        this.contratoClienteNombre = contratoClienteNombre;
        this.contratoNumero = contratoNumero;
        this.contratoFechaInicio = contratoFechaInicio;
        this.lineaNombre = lineaNombre;
        this.propio = propio;
        this.enTransito = enTransito;
        this.ordenDeTrabajoDescripcion = ordenDeTrabajoDescripcion;
        this.ordenDeTrabajoEstado = ordenDeTrabajoEstado;
        this.ordenDeTrabajoNumeroComp = ordenDeTrabajoNumeroComp;
        this.ordenDeTrabajoFechaEntrega = ordenDeTrabajoFechaEntrega;
        this.ordenDeTrabajoFechaInicio = ordenDeTrabajoFechaInicio;
        this.sucursalNombre = sucursalNombre;
        this.entregado = entregado;
        this.proveedorNombre = proveedorNombre;
        this.articuloCodigo = articuloCodigo;
        this.bienDescripcion = bienDescripcion;
        this.bienEstado = bienEstado;
        this.bienAFabricacion = bienAFabricacion;
        this.bienDepositoAlmacenNombre = bienDepositoAlmacenNombre;
        this.bienIdentificacion = bienIdentificacion;
        this.bienModelo = bienModelo;
        this.bienSerie = bienSerie;
        this.fechaDispo = fechaDispo;
    }

    public int getId() {
        return id;
    }

    public String getRubroNombre() {
        return rubroNombre;
    }

    public String getEstado() {
        return estado;
    }

    public String getBienEmpresaNombre() {
        return bienEmpresaNombre;
    }

    public String getContratoEmpresaNombre() {
        return contratoEmpresaNombre;
    }

    public String getPropietario() {
        return propietario;
    }

    public String getContratoEstado() {
        return contratoEstado;
    }

    public String getContratoNumeroComp() {
        return contratoNumeroComp;
    }

    public Date getContratoFechaFin() {
        return contratoFechaFin;
    }

    public Date getContratoFecha() {
        return contratoFecha;
    }

    public String getContratoClienteNombre() {
        return contratoClienteNombre;
    }

    public String getContratoNumero() {
        return contratoNumero;
    }

    public Date getContratoFechaInicio() {
        return contratoFechaInicio;
    }

    public String getLineaNombre() {
        return lineaNombre;
    }

    public String getPropio() {
        return propio;
    }

    public String getEnTransito() {
        return enTransito;
    }

    public String getOrdenDeTrabajoDescripcion() {
        return ordenDeTrabajoDescripcion;
    }

    public String getOrdenDeTrabajoEstado() {
        return ordenDeTrabajoEstado;
    }

    public String getOrdenDeTrabajoNumeroComp() {
        return ordenDeTrabajoNumeroComp;
    }

    public Date getOrdenDeTrabajoFechaEntrega() {
        return ordenDeTrabajoFechaEntrega;
    }

    public Date getOrdenDeTrabajoFechaInicio() {
        return ordenDeTrabajoFechaInicio;
    }

    public String getSucursalNombre() {
        return sucursalNombre;
    }

    public String getEntregado() {
        return entregado;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public String getArticuloCodigo() {
        return articuloCodigo;
    }

    public String getBienDescripcion() {
        return bienDescripcion;
    }

    public String getBienEstado() {
        return bienEstado;
    }

    public String getBienAFabricacion() {
        return bienAFabricacion;
    }

    public String getBienDepositoAlmacenNombre() {
        return bienDepositoAlmacenNombre;
    }

    public String getBienIdentificacion() {
        return bienIdentificacion;
    }

    public String getBienModelo() {
        return bienModelo;
    }

    public String getBienSerie() {
        return bienSerie;
    }

    public Date getFechaDispo() {
        return fechaDispo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRubroNombre(String rubroNombre) {
        this.rubroNombre = rubroNombre;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setBienEmpresaNombre(String bienEmpresaNombre) {
        this.bienEmpresaNombre = bienEmpresaNombre;
    }

    public void setContratoEmpresaNombre(String contratoEmpresaNombre) {
        this.contratoEmpresaNombre = contratoEmpresaNombre;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public void setContratoEstado(String contratoEstado) {
        this.contratoEstado = contratoEstado;
    }

    public void setContratoNumeroComp(String contratoNumeroComp) {
        this.contratoNumeroComp = contratoNumeroComp;
    }

    public void setContratoFechaFin(Date contratoFechaFin) {
        this.contratoFechaFin = contratoFechaFin;
    }

    public void setContratoFecha(Date contratoFecha) {
        this.contratoFecha = contratoFecha;
    }

    public void setContratoClienteNombre(String contratoClienteNombre) {
        this.contratoClienteNombre = contratoClienteNombre;
    }

    public void setContratoNumero(String contratoNumero) {
        this.contratoNumero = contratoNumero;
    }

    public void setContratoFechaInicio(Date contratoFechaInicio) {
        this.contratoFechaInicio = contratoFechaInicio;
    }

    public void setLineaNombre(String lineaNombre) {
        this.lineaNombre = lineaNombre;
    }

    public void setPropio(String propio) {
        this.propio = propio;
    }

    public void setEnTransito(String enTransito) {
        this.enTransito = enTransito;
    }

    public void setOrdenDeTrabajoDescripcion(String ordenDeTrabajoDescripcion) {
        this.ordenDeTrabajoDescripcion = ordenDeTrabajoDescripcion;
    }

    public void setOrdenDeTrabajoEstado(String ordenDeTrabajoEstado) {
        this.ordenDeTrabajoEstado = ordenDeTrabajoEstado;
    }

    public void setOrdenDeTrabajoNumeroComp(String ordenDeTrabajoNumeroComp) {
        this.ordenDeTrabajoNumeroComp = ordenDeTrabajoNumeroComp;
    }

    public void setOrdenDeTrabajoFechaEntrega(Date ordenDeTrabajoFechaEntrega) {
        this.ordenDeTrabajoFechaEntrega = ordenDeTrabajoFechaEntrega;
    }

    public void setOrdenDeTrabajoFechaInicio(Date ordenDeTrabajoFechaInicio) {
        this.ordenDeTrabajoFechaInicio = ordenDeTrabajoFechaInicio;
    }

    public void setSucursalNombre(String sucursalNombre) {
        this.sucursalNombre = sucursalNombre;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public void setArticuloCodigo(String articuloCodigo) {
        this.articuloCodigo = articuloCodigo;
    }

    public void setBienDescripcion(String bienDescripcion) {
        this.bienDescripcion = bienDescripcion;
    }

    public void setBienEstado(String bienEstado) {
        this.bienEstado = bienEstado;
    }

    public void setBienAFabricacion(String bienAFabricacion) {
        this.bienAFabricacion = bienAFabricacion;
    }

    public void setBienDepositoAlmacenNombre(String bienDepositoAlmacenNombre) {
        this.bienDepositoAlmacenNombre = bienDepositoAlmacenNombre;
    }

    public void setBienIdentificacion(String bienIdentificacion) {
        this.bienIdentificacion = bienIdentificacion;
    }

    public void setBienModelo(String bienModelo) {
        this.bienModelo = bienModelo;
    }

    public void setBienSerie(String bienSerie) {
        this.bienSerie = bienSerie;
    }

    public void setFechaDispo(Date fechaDispo) {
        this.fechaDispo = fechaDispo;
    }
}
