package vista.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class PanelGraficos {

    public int cTenientes;
    public int cCoroneles;
    public int cCapitanes;
    public int cSoldados;

    public int tRegañados;
    public int tNoRegañados;

    public int mExitosas;
    public int mFallidas;

    public int dCoronel;
    public int dCapitan;
    public int dTeniente;
    public int dSoldado;

    private JPanel panelLateral = new JPanel(); 

    public PanelGraficos() {
        this.mostrarPanelLateral();
        this.mostrarGraficoRango();
        this.mostrarGraficoReganados();
        this.mostrarGraficoMisiones();
        this.mostrarGraficoDescensos();
    }

    private void mostrarPanelLateral() {
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBounds(0, 0, 350, 680); 
        panelLateral.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void mostrarGraficoRango() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Coronel", cCoroneles);
        dataset.setValue("Capitan", cCapitanes);
        dataset.setValue("Teniente", cTenientes);
        dataset.setValue("Soldado", cSoldados);

        // Crear el gráfico de pastel
        JFreeChart chart = ChartFactory.createPieChart(
                "Soldados por Rango",  
                dataset,               
                true,                  
                true,                   
                false                   
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300)); 

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chartPanel);
        panelLateral.add(panel);
    }

    private void mostrarGraficoReganados() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(tRegañados, "Regañados", "Hoy");
        dataset.addValue(tNoRegañados, "No Regañados", "Hoy");

        JFreeChart chart = ChartFactory.createBarChart(
                "Regaños",
                "Estado",
                "",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300)); 

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chartPanel);
        panelLateral.add(panel);
    }

    private void mostrarGraficoMisiones() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Misiones Exitosas", mExitosas);
        dataset.setValue("Misiones Fallidas", mFallidas);
    
        JFreeChart chart = ChartFactory.createRingChart(
                "Estado de misiones", 
                dataset,                   
                true,                      
                true,                       
                false                       
        );
    
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300)); 
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chartPanel);
        panelLateral.add(panel);
    }
    

    private void mostrarGraficoDescensos() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(dCoronel, "Coronel", "Descensos");
        dataset.addValue(dCapitan, "Capitan", "Descensos");
        dataset.addValue(dTeniente, "Teniente", "Descensos");
        dataset.addValue(dSoldado, "Soldados", "Descensos");
    
        JFreeChart chart = ChartFactory.createBarChart(
                "Descensos por Rango", 
                "Rango",              
                "",           
                dataset               
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300)); 

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chartPanel);
        panelLateral.add(panel);
    }
    
    public void actualizarGraficos() {
        panelLateral.removeAll();
    
        mostrarGraficoRango();
        mostrarGraficoReganados();
        mostrarGraficoMisiones();
        mostrarGraficoDescensos();
    
        panelLateral.revalidate();
        panelLateral.repaint();
    }

    public JPanel getPanelLateral() {
        return this.panelLateral;
    }
}
