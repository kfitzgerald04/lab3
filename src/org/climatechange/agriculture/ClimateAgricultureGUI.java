/**
 * Kendra Fitzgerald
 * This class provides a GUI interface for viewing climate agriculture data
 */

package org.climatechange.agriculture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClimateAgricultureGUI extends JFrame {
    private List<ClimateAgricultureEntry> agricultureData;
    private JTable dataTable;

    public ClimateAgricultureGUI(List<ClimateAgricultureEntry> agricultureData) {
        this.agricultureData = agricultureData;

        // application title
        setTitle("Climate Change Impact on Agriculture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // panel to frame
        add(mainPanel);

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
        for (ClimateAgricultureEntry entry : agricultureData) {
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

        List<ClimateAgricultureEntry> agricultureData =
                ClimateAgricultureReader.readDataFromFile(filePath);

        SwingUtilities.invokeLater(() -> {
            ClimateAgricultureGUI gui = new ClimateAgricultureGUI(agricultureData);
            gui.setVisible(true);
        });
    }
}