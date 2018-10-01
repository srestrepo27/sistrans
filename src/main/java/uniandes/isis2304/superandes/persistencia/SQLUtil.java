package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


class SQLUtil
{
	
	private final static String SQL = PersistenciaSuperAndes.SQL;

	private PersistenciaSuperAndes ps;

	
	public SQLUtil (PersistenciaSuperAndes pp)
	{
		this.ps = pp;
	}
	
	
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ ps.darSeqSuperAndes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	
	public long [] limpiarSuperAndes (PersistenceManager pm)
	{
        Query qBodega = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCliente ());          
        Query qCliente = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCliente ());
        Query qCompra = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCompra ());
        Query qEmpresa = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaEmpresa ());
        Query qEstante = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaEstante ());
        Query qPedido = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPedido());
        Query qPersona = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPersona ());
        Query qProducto = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProducto());
        Query qPromPro = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPromocionProveedor());
        Query qPromSuc = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPromocionSucursal());
        Query qProvee = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProvee());
        Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProveedor());
        Query qSucursal = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaSucursal());
        Query qSuperMecado = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaSuperMercado());

        long bodegaEliminados = (long) qBodega.executeUnique ();
        long clienteEliminados = (long) qCliente.executeUnique ();
        long compraEliminadas = (long) qCompra.executeUnique ();
        long empresaEliminadas = (long) qEmpresa.executeUnique ();
        long estanteEliminados = (long) qEstante.executeUnique ();
        long pedidoEliminados = (long) qPedido.executeUnique ();
        long personaEliminados = (long) qPersona.executeUnique ();
        long productoEliminados = (long) qProducto.executeUnique ();
        long PromProEliminados = (long) qPromPro.executeUnique ();
        long PromSucEliminados = (long) qPromSuc.executeUnique ();
        long ProveeEliminados = (long) qProvee.executeUnique ();
        long ProveedorEliminados = (long) qProveedor.executeUnique ();
        long SucursalEliminados = (long) qSucursal.executeUnique ();
        long SuperMecadoEliminados = (long) qSuperMecado.executeUnique ();
        return new long[] {bodegaEliminados,clienteEliminados,compraEliminadas,empresaEliminadas,
        		estanteEliminados,pedidoEliminados,personaEliminados,
        		productoEliminados,PromProEliminados,PromSucEliminados,
        		ProveeEliminados,ProveedorEliminados,SucursalEliminados,SuperMecadoEliminados};
	}

}
