package br.com.codigof.iguana.jsf.controller;

import br.com.codigof.iguana.beans.EstatusFacade;
import br.com.codigof.iguana.jpa.entities.Solicitacoes;
import br.com.codigof.iguana.jsf.controller.util.JsfUtil;
import br.com.codigof.iguana.jsf.controller.util.PaginationHelper;
import br.com.codigof.iguana.beans.SolicitacoesFacade;
import br.com.codigof.iguana.jpa.entities.Estatus;
import br.com.codigof.iguana.util.Protocolo;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("solicitacoesController")
@SessionScoped
public class SolicitacoesController implements Serializable {

    private Solicitacoes current;
    private DataModel items = null;
    @EJB
    private br.com.codigof.iguana.beans.SolicitacoesFacade ejbFacade;
    @EJB
    private br.com.codigof.iguana.beans.EstatusFacade ejbEstatusFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Protocolo protocolo;

    public SolicitacoesController() {
    }

    public Solicitacoes getSelected() {
        if (current == null) {
            current = new Solicitacoes();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SolicitacoesFacade getFacade() {
        return ejbFacade;
    }

    public EstatusFacade getEjbEstatusFacade() {
        return ejbEstatusFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String findAllEstatusPendente() {
        recreateModel();
        recreatePagination();
        Estatus est = new Estatus();
        List<Solicitacoes> solic = null;
        est = getEjbEstatusFacade().findByNome("PENDENTE");
        solic = ejbFacade.findByEstatus(est);
        items = new ListDataModel(solic);
        pagination =  new PaginationHelper(10) {
            @Override
            public int getItemsCount() {
                return getFacade().count();
            }
            
            @Override
            public DataModel createPageDataModel() {
                return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
            }
        };
        return "/admin/solicitacoes/List";
    }
    public String findAllCurrentDate() {
        recreateModel();
        recreatePagination();
        List<Solicitacoes> solic = null;
        solic = ejbFacade.findAllCurrentDate();
        items = new ListDataModel(solic);
        pagination =  new PaginationHelper(10) {
            @Override
            public int getItemsCount() {
                return getFacade().count();
            }
            
            @Override
            public DataModel createPageDataModel() {
                return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
            }
        };
        return "/admin/solicitacoes/List";
    }

    public String prepareView() {
        current = (Solicitacoes) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
//        return "View";
        return null;
    }

    public String prepareCreate() {
        current = new Solicitacoes();
        selectedItemIndex = -1;
        return "Create";
    }

    public String linkCreate() {
        current = new Solicitacoes();
        selectedItemIndex = -1;
        return "/admin/solicitacoes/Create";
    }

    public String create() {
        try {
            protocolo = new Protocolo();
            current.setProtocolo(protocolo.createProtocolo());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitacoesCreated"));
            JsfUtil.addWarnMessage("Protocolo gerado: " + current.getProtocolo());
            return prepareCreate();
        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            System.out.println("Erro: " + e.getMessage());
            System.out.println("Protocolo " + current.getProtocolo());
            return null;
        }
    }

    public String prepareEdit() {
        current = (Solicitacoes) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
//        return "Edit";
        return null;
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitacoesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Solicitacoes) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitacoesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Solicitacoes getSolicitacoes(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Solicitacoes.class)
    public static class SolicitacoesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SolicitacoesController controller = (SolicitacoesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "solicitacoesController");
            return controller.getSolicitacoes(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Solicitacoes) {
                Solicitacoes o = (Solicitacoes) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Solicitacoes.class.getName());
            }
        }

    }

}
