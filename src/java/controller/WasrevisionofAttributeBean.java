/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import dao.WasrevisionofAttributeDAO;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import model.WasrevisionofAttribute;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author Humberto
 */
@ManagedBean(name = "wasrevisionofAttributeBean")
@ViewScoped
public class WasrevisionofAttributeBean {

    WasrevisionofAttribute wasrevisionofAttribute = new WasrevisionofAttribute();

    List wasrevisionofAttributes = new ArrayList();

    //construtor
    public WasrevisionofAttributeBean() {
        wasrevisionofAttributes = new WasrevisionofAttributeDAO().buscarTodas();
        wasrevisionofAttribute = new WasrevisionofAttribute();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        new WasrevisionofAttributeDAO().persistir(wasrevisionofAttribute);
        wasrevisionofAttributes = new WasrevisionofAttributeDAO().buscarTodas();
        wasrevisionofAttribute = new WasrevisionofAttribute();
    }

    public void exclude(ActionEvent actionEvent) {
        new WasrevisionofAttributeDAO().remover(wasrevisionofAttribute);
        wasrevisionofAttributes = new WasrevisionofAttributeDAO().buscarTodas();
        wasrevisionofAttribute = new WasrevisionofAttribute();
    }

    //getters and setters
    public WasrevisionofAttribute getWasrevisionofAttribute() {
        return wasrevisionofAttribute;
    }

    public void setWasrevisionofAttribute(WasrevisionofAttribute wasrevisionofAttribute) {
        this.wasrevisionofAttribute = wasrevisionofAttribute;
    }

    public List getWasrevisionofAttributes() {
        return wasrevisionofAttributes;
    }

    public void setWasrevisionofAttributes(List wasrevisionofAttributes) {
        this.wasrevisionofAttributes = wasrevisionofAttributes;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";

        // pdf.add(Image.getInstance(logo));
    }
}
