// Kendra Fitzgerald

package org.climatechange.agriculture;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ChartPanel extends JPanel {
    private List<CAEntry> data;

    // constructor initializes the chart panel with dataset
    public ChartPanel(List<CAEntry> data) {
        this.data = data;
        setPreferredSize(new Dimension(400, 300));
        setBorder(BorderFactory.createTitledBorder("Crop Yield vs. Temperature"));
    }

    // updates the chart when data is filtered
    public void updateChart(List<CAEntry> newData) {
        this.data = newData;
        repaint();
    }

    // draws the chart
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (data == null || data.isEmpty()) {
            g2.drawString("No data available", getWidth() / 2 - 40, getHeight() / 2);
            return;
        }

        // set up scaling factors for plotting
        double minTemp = data.stream().mapToDouble(CAEntry::getAverageTemperature).min().orElse(0);
        double maxTemp = data.stream().mapToDouble(CAEntry::getAverageTemperature).max().orElse(40);
        double minYield = data.stream().mapToDouble(CAEntry::getCropYield).min().orElse(0);
        double maxYield = data.stream().mapToDouble(CAEntry::getCropYield).max().orElse(10);

        double xScale = (getWidth() - 60) / (maxTemp - minTemp);
        double yScale = (getHeight() - 60) / (maxYield - minYield);

        // draw axes
        g2.drawLine(50, getHeight() - 30, 50, 30);
        g2.drawLine(50, getHeight() - 30, getWidth() - 30, getHeight() - 30);
        g2.drawString("Temperature (°C)", getWidth() / 2 - 30, getHeight() - 10);
        g2.drawString("Yield (tons/ha)", 5, getHeight() / 2);

        // plot data points
        g2.setColor(Color.BLUE);
        for (CAEntry entry : data) {
            int x = (int) ((entry.getAverageTemperature() - minTemp) * xScale) + 50;
            int y = (int) ((maxYield - entry.getCropYield()) * yScale) + 30;
            g2.fillOval(x - 3, y - 3, 6, 6);
        }
    }
}
