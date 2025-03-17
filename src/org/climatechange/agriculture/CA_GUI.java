
/**
 * Kendra Fitzgerald
 * This class provides a GUI interface for viewing climate agriculture data
 */

package org.climatechange.agriculture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.stream.Collectors;

public class CA_GUI extends JFrame {
    private List<CAEntry> data, filteredData;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextArea detailsText, statsText;
    private ChartPanel chartPanel;
    private JComboBox<String> cropFilter, countryFilter, yearFilter;

    // constructor initializes the gui with the given dataset
    public CA_GUI(List<CAEntry> data) {
        this.data = data;
        this.filteredData = List.copyOf(data);
        setTitle("Climate Change & Agriculture");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createFilterPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        updateUI();
        setLocationRelativeTo(null);
    }

    // creates the filter panel with dropdowns and a reset button
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        cropFilter = createFilter(panel, "Crop Type", "All", CAEntry::getCropType);
        countryFilter = createFilter(panel, "Country", "All", CAEntry::getCountry);
        yearFilter = createFilter(panel, "Year", "All", e -> String.valueOf(e.getYear()));
        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> resetFilters());
        panel.add(reset);
        return panel;
    }

    // create dropdown filters dynamically
    private JComboBox<String> createFilter(JPanel panel, String label, String defaultItem, java.util.function.Function<CAEntry, String> extractor) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(defaultItem);
        data.stream().map(extractor).distinct().sorted().forEach(comboBox::addItem);
        comboBox.addItemListener(e -> { if (e.getStateChange() == ItemEvent.SELECTED) applyFilters(); });
        panel.add(new JLabel(label));
        panel.add(comboBox);
        return comboBox;
    }

    // creates the main panel containing the table, chart, and stats
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        chartPanel = new ChartPanel(filteredData);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanel, createTablePanel());
        split.setResizeWeight(0.3);
        panel.add(split, BorderLayout.CENTER);
        panel.add(createStatsPanel(), BorderLayout.EAST);
        panel.add(createDetailsPanel(), BorderLayout.SOUTH);
        return panel;
    }

    // sets up the table panel to display the dataset
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Year", "Country", "Region", "Crop", "Temp", "Precip", "CO2", "Yield", "Events", "Irrigation"};
        tableModel = new DefaultTableModel(columns, 0) { public boolean isCellEditable(int row, int col) { return false; } };
        dataTable = new JTable(tableModel);
        dataTable.setRowSorter(new TableRowSorter<>(tableModel));
        dataTable.getSelectionModel().addListSelectionListener(e -> updateDetails());
        panel.add(new JScrollPane(dataTable));
        return panel;
    }

    // creates the statistics panel
    private JPanel createStatsPanel() {
        statsText = new JTextArea(10, 20);
        statsText.setEditable(false);
        return wrapPanel(statsText, "Statistics");
    }

    // creates the details panel
    private JPanel createDetailsPanel() {
        detailsText = new JTextArea(5, 40);
        detailsText.setEditable(false);
        return wrapPanel(detailsText, "Details");
    }

    // wraps text area inside a titled panel
    private JPanel wrapPanel(JTextArea textArea, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(textArea));
        return panel;
    }

    // filters the dataset based on the selected criteria
    private void applyFilters() {
        filteredData = data.stream()
                .filter(e -> filterMatch(e.getCropType(), cropFilter))
                .filter(e -> filterMatch(e.getCountry(), countryFilter))
                .filter(e -> filterMatch(String.valueOf(e.getYear()), yearFilter))
                .collect(Collectors.toList());
        updateUI();
    }

    // checks if an entry matches the selected filter
    private boolean filterMatch(String value, JComboBox<String> filter) {
        String selected = (String) filter.getSelectedItem();
        return selected.equals("All") || selected.equals(value);
    }

    // updates all components after filtering
    private void updateUI() {
        updateTable();
        updateStats();
        chartPanel.updateChart(filteredData);
        detailsText.setText("No selection");
    }

    // updates the table with filtered data
    private void updateTable() {
        tableModel.setRowCount(0);
        filteredData.forEach(e -> tableModel.addRow(new Object[]{e.getYear(), e.getCountry(), e.getRegion(), e.getCropType(), e.getAverageTemperature(), e.getTotalPrecipitation(), e.getCo2Emissions(), e.getCropYield(), e.getExtremeWeatherEvents(), e.getIrrigation()}));
    }

    // updates the details panel when a row is selected
    private void updateDetails() {
        int row = dataTable.getSelectedRow();
        if (row >= 0) detailsText.setText(filteredData.get(dataTable.convertRowIndexToModel(row)).toString());
    }

    // calculates and updates statistics based on filtered data
    private void updateStats() {
        CAParser parser = new CAParser(filteredData);
        statsText.setText(String.format("Entries: %d\nAvg Yield: %.2f\nAvg Temp: %.2f°C\nAvg Precip: %.2fmm\nExtreme Events: %d",
                filteredData.size(), parser.calculateAverageCropYield(), parser.calculateAverageTemperature(), parser.calculateAveragePrecipitation(), parser.calculateTotalExtremeWeatherEvents()));
    }

    // resets all filters to default
    private void resetFilters() {
        cropFilter.setSelectedItem("All");
        countryFilter.setSelectedItem("All");
        yearFilter.setSelectedItem("All");
    }

    // main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CA_GUI(CA_FileReader.readDataFromFile("./csv_file/climate_change_impact_on_agriculture_2024.csv")).setVisible(true));
    }
}
