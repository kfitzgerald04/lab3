/**
 * Kendra Fitzgerald
 * This class is for reading and parsing the data
 * from the CSV file
 */

package org.climatechange.agriculture;

import java.util.List;
import java.util.stream.Collectors;

public class CAParser {
    private List<CAEntry> agricultureData;

    // constructor initializes the parser with dataset
    public CAParser(List<CAEntry> agricultureData) {
        this.agricultureData = agricultureData;
    }

    // prints details of the first data entry
    public void printFirstItemDetails() {
        if (!agricultureData.isEmpty()) {
            System.out.println("First Data Entry:");
            System.out.println(agricultureData.get(0));
        }
    }

    // prints details of the tenth data entry
    public void printTenthItemDetails() {
        if (agricultureData.size() >= 10) {
            System.out.println("Tenth Data Entry:");
            System.out.println(agricultureData.get(9));
        }
    }

    // prints total number of entries in the dataset
    public void printTotalEntries() {
        System.out.println("Total Number of Entries: " + agricultureData.size());
    }

    // calculates average crop yield across all entries
    public double calculateAverageCropYield() {
        return agricultureData.stream()
                .mapToDouble(CAEntry::getCropYield)
                .average()
                .orElse(0.0);
    }

    // calculates average temperature across all entries
    public double calculateAverageTemperature() {
        return agricultureData.stream()
                .mapToDouble(CAEntry::getAverageTemperature)
                .average()
                .orElse(0.0);
    }

    // calculates average precipitation across all entries
    public double calculateAveragePrecipitation() {
        return agricultureData.stream()
                .mapToDouble(CAEntry::getTotalPrecipitation)
                .average()
                .orElse(0.0);
    }

    // calculates total extreme weather events across all entries
    public int calculateTotalExtremeWeatherEvents() {
        return agricultureData.stream()
                .mapToInt(CAEntry::getExtremeWeatherEvents)
                .sum();
    }

    // finds the country with the highest crop yield
    public String findHighestYieldCountry() {
        return agricultureData.stream()
                .max((a, b) -> Double.compare(a.getCropYield(), b.getCropYield()))
                .map(CAEntry::getCountry)
                .orElse("No data");
    }

    // filters entries by a specific crop type
    public List<CAEntry> filterByCropType(String cropType) {
        return agricultureData.stream()
                .filter(entry -> entry.getCropType().equalsIgnoreCase(cropType))
                .collect(Collectors.toList());
    }
}
