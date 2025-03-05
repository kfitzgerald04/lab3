/**
 * Kendra Fitzgerald
 * This class processes and analyzes the data.
 * Methods are for data exploration and statistics
 */

package org.climatechange.agriculture;

import java.util.List;
import java.util.stream.Collectors;

public class ClimateAgricultureProcessor {
    private List<ClimateAgricultureEntry> agricultureData;


     // initializes the processor with a list of agriculture entries
    public ClimateAgricultureProcessor(List<ClimateAgricultureEntry> agricultureData) {
        this.agricultureData = agricultureData;
    }

    /**
     * prints details of the first data entry
     */
    public void printFirstItemDetails() {
        if (!agricultureData.isEmpty()) {
            System.out.println("First Data Entry:");
            System.out.println(agricultureData.get(0));
        }
    }

    /**
     * prints details of the tenth data entry.
     */
    public void printTenthItemDetails() {
        if (agricultureData.size() >= 10) {
            System.out.println("Tenth Data Entry:");
            System.out.println(agricultureData.get(9));
        }
    }

    /**
     * prints total number of entries in the dataset.
     */
    public void printTotalEntries() {
        System.out.println("Total Number of Entries: " + agricultureData.size());
    }

    /**
     * calculates average crop yield across all entries
     */
    public double calculateAverageCropYield() {
        return agricultureData.stream()
                .mapToDouble(ClimateAgricultureEntry::getCropYield)
                .average()
                .orElse(0.0);
    }

    /**
     * finds the country with the highest crop yield
     */
    public String findHighestYieldCountry() {
        return agricultureData.stream()
                .max((a, b) -> Double.compare(a.getCropYield(), b.getCropYield()))
                .map(ClimateAgricultureEntry::getCountry)
                .orElse("No data");
    }

    /**
     * filters entries by a specific crop type
     */
    public List<ClimateAgricultureEntry> filterByCropType(String cropType) {
        return agricultureData.stream()
                .filter(entry -> entry.getCropType().equalsIgnoreCase(cropType))
                .collect(Collectors.toList());
    }
}
