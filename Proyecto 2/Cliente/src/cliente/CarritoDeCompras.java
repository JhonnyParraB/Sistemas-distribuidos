/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import clasesrmi.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO PC
 */
public class CarritoDeCompras {
    List<ProductoCarrito> productosCarrito;

    public CarritoDeCompras() {
        productosCarrito = new ArrayList<>();
    }
    
    public void agregarProducto (Producto producto, int cantidad){
        ProductoCarrito productoCarrito = new ProductoCarrito();
        productoCarrito.setProducto(producto);
        productoCarrito.setCantidad(cantidad);
        this.productosCarrito.add(productoCarrito);
    }
    
    public long total(){
        long total = 0;
        for (ProductoCarrito productoCarrito: productosCarrito){
            total+= productoCarrito.subtotal();
        }
        return total;
    }

    public List<ProductoCarrito> getProductosCarrito() {
        return productosCarrito;
    }

    public void setProductosCarrito(List<ProductoCarrito> productosCarrito) {
        this.productosCarrito = productosCarrito;
    }
    
    
    public void modificarCantidad(int numeroProductoCarrito, int cantidadNueva){
        this.productosCarrito.get(numeroProductoCarrito).setCantidad(cantidadNueva);
    }
    
    public void eliminarProducto (int numeroProducto){
        this.productosCarrito.remove(numeroProducto);
    }
    
    public boolean productoAgregado(Producto producto){
        for (ProductoCarrito productoCarrito: productosCarrito){
            if (producto.getNombre().equals(productoCarrito.getProducto().getNombre()))
                return true;
        }
        return false;
    }
    
    
}
