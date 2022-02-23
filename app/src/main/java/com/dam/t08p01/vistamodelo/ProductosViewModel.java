package com.dam.t08p01.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dam.t08p01.modelo.Aula;
import com.dam.t08p01.modelo.Departamento;
import com.dam.t08p01.modelo.FiltroProductos;
import com.dam.t08p01.modelo.Producto;
import com.dam.t08p01.repositorio.ProductosRepository;

import java.util.List;

public class ProductosViewModel extends AndroidViewModel {

    //ViewModel Producots
    private final ProductosRepository mProductosRep;
    private LiveData<List<Producto>> mProductos;

    private Departamento mLogin;
    private Producto mProductoAEliminar;

    // (LiveData y crear observer) que recuerde el aula seleccionada, para que cuando gires el movil no se pierda el dato
    private MutableLiveData<Aula> aulaSeleccionada;

    private final MutableLiveData<String> mFechaDlg;
    private final FiltroProductos mProductoFiltro;

    public ProductosViewModel(@NonNull Application application) {
        super(application);
        mProductosRep = new ProductosRepository(application);
        mProductoFiltro = new FiltroProductos();
        aulaSeleccionada = new MutableLiveData<>();
//        mProductos = null;
        mProductos = mProductosRep.recuperarProductosFiltro(mProductoFiltro.getFecAlta(),mProductoFiltro.getIdAula());
        mLogin = null;
        mProductoAEliminar = null;
        mFechaDlg = new MutableLiveData<>();
    }

    //Metodos mantenimiento productos

    //TO DO borrar este filtro, solo hace falta uno, el de getProductosByFiltro
//    public LiveData<List<Producto>> getProductos() {
//
//////        mProductos =  mProductosRep.recuperarProductosFiltro(mProductoFiltro.getFecAltaFiltro(), mProductoFiltro.getIdAula());
//        return mProductos;
//
////        return mProductosRep.recuperarProductosFiltro(mProductoFiltro.getFecAltaFiltro(), mProductoFiltro.getIdAula());
//    }

    public LiveData<List<Producto>> getProductosByFiltro() {

        String fecAlta = mProductoFiltro.getFecAlta();
//        String idAula = mProductoFiltro.getIdAula();
        String idAula = (mProductoFiltro.getIdAula().equals(""))?"%":mProductoFiltro.getIdAula();
        mProductos = mProductosRep.recuperarProductosFiltro(fecAlta, idAula);
        //Con este return lo que hacemos es pasarselo como parametro al observer, no se pone
        // como una asignacino pq al usar un observer es unproceso asincrono
        return mProductos;
    }

    public LiveData<Boolean> altaProducto(Producto producto) {
        return mProductosRep.altaProducto(producto);
    }

    public LiveData<Boolean> editarProducto(Producto producto) {
        return mProductosRep.editarProducto(producto);
    }

    public LiveData<Boolean> bajaProducto(Producto producto) {
        return mProductosRep.bajaProducto(producto);
    }

    //getters & setters

    public Departamento getLogin() {
        return mLogin;
    }

    public void setLogin(Departamento login) {
        mLogin = login;
    }

    public Producto getProductoAEliminar() {
        return mProductoAEliminar;
    }

    public void setProductoAEliminar(Producto mProductoAEliminar) {
        this.mProductoAEliminar = mProductoAEliminar;
    }

    public FiltroProductos getmProductoFiltro() {
        return mProductoFiltro;
    }

    public LiveData<String> getFechaDlg() {
        return mFechaDlg;
    }

    public void setFechaDlg(String mFechaDlg) {
        this.mFechaDlg.setValue(mFechaDlg);
    }

    public void setmProductoFiltro(String idAula, String fecAlta) {
        this.mProductoFiltro.setFecAlta(fecAlta);
        this.mProductoFiltro.setIdAula(idAula);
    }

    public MutableLiveData<Aula> getAulaSeleccionada() {
        return aulaSeleccionada;
    }

    public void setAulaSeleccionada(Aula aulaSeleccionada) {
        this.aulaSeleccionada.setValue(aulaSeleccionada);
    }
}
