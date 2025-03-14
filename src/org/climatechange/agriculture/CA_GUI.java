/**
 * Kendra Fitzgerald
 * This class provides a GUI interface for viewing climate agriculture data
 */

package org.climatechange.agriculture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableRowSorter;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CA_GUI extends JFrame {
    private List<CAEntry> agricultureData;
    private JTable dataTable;
    private List<CAEntry> filteredData;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextArea statsTextArea;
    //private ChartPanel chartPanel;

    // filter components
    private JComboBox<String> cropType;
    private JComboBox<String> country;
    private JComboBox<String> year;


    private void initializeFrame(){
        setTitle("Climate Change Impact on Agriculture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
    }

    private JSplitPane createPanelLayout() {
        // create central panel with tabel
        JPanel tabelPanel = createTablePanel();

        // create stats pannel for the right side
        JPanel statsPanel = createStatsPanel();

        // details panel for the bottom
        JPanel detailsPanel = createDetailsPanel();

        // chart panel for the left side
       // chartPanel = new ChartPanel(filteredData);

        // split panel main should be chartPanel, but i have yet to create it
        JSplitPane horizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, statsPanel, tabelPanel);
        horizontal.setResizeWeight(0.3);

        // split panel stats and tabel
        JSplitPane right = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, horizontal, statsPanel);
        right.setResizeWeight(0.7);

        // split panel
        JSplitPane vertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, right, detailsPanel);
        vertical.setResizeWeight(0.7);

        return vertical;
    }

    private JPanel createFilterPanel(){
        JPanel panel = new JPanel();
        return panel;
    }

    private JPanel createStatsPanel(){
        JPanel panel = new JPanel();
        return panel;
    }

    private JPanel createDetailsPanel(){
        JPanel panel = new JPanel();
        return panel;
    }


    private JPanel createMainPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());

        // creating a filter panel at the top
        mainPanel.add(createFilterPanel(), BorderLayout.NORTH);

        // splitplane to help with layout
        JSplitPane verticalSplit = createPanelLayout();
        mainPanel.add(verticalSplit, BorderLayout.CENTER);

        return mainPanel;
    }

    public CA_GUI(List<CAEntry> agricultureData) {
        this.agricultureData = agricultureData;
        this.filteredData = new ArrayList<>(agricultureData);

        initializeFrame();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);

        //updateStats();
       // chartPanel.updateChart(filteredData);
        setLocationRelativeTo(null);
    }



    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Agriculture Data Table"));

        // table model from dataset
        String[] columnNames = {
                "Year", "Country", "Region", "Crop Type",
                "Avg. Temp (°C)", "Precipitation (mm)", "CO2 Emissions",
                "Crop Yield (tons/ha)", "Extreme Weather Events", "Irrigation"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // adding data to the model
        for (CAEntry entry : agricultureData) {
            model.addRow(new Object[] {
                    entry.getYear(),
                    entry.getCountry(),
                    entry.getRegion(),
                    entry.getCropType(),
                    entry.getAverageTemperature(),
                    entry.getTotalPrecipitation(),
                    entry.getCo2Emissions(),
                    entry.getCropYield(),
                    entry.getExtremeWeatherEvents(),
                    entry.getIrrigation()
            });
        }

        // creating table with the model
        dataTable = new JTable(model);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        dataTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(dataTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args){
        String filePath = "./csv_file/climate_change_impact_on_agriculture_2024.csv";

        List<CAEntry> agricultureData =
                CA_FileReader.readDataFromFile(filePath);

        SwingUtilities.invokeLater(() -> {
            CA_GUI gui = new CA_GUI(agricultureData);
            gui.setVisible(true);
        });
    }
}