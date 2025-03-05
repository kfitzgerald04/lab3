/**
 * Kendra Fitzgerald
 * The console reads and processes the data
 */

package org.climatechange.agriculture;

import java.util.List;


public class ClimateAgricultureConsole {
    public static void main(String[] args) {
        // Make sure to replace with the actual path to your CSV file
        String filePath = "\"C:\\Users\\kendr\\Downloads\\climate_change_impact_on_agriculture_2024.csv\"";

        // read data from file
        List<ClimateAgricultureEntry> agricultureData =
                ClimateAgricultureReader.readDataFromFile(filePath);

        // create processor for data analysis
        ClimateAgricultureProcessor processor =
                new ClimateAgricultureProcessor(agricultureData);

        // Part One Requirements
        // A. Console Test Application
        System.out.println("Climate Change Agriculture Data Analysis");
        System.out.println("----------------------------------------");

        // 1. Print attributes of the first data item
        processor.printFirstItemDetails();

        // 2. Print attributes of the 10th item
        processor.printTenthItemDetails();

        // 3. Display total number of entries
        processor.printTotalEntries();

        // Additional analysis
        System.out.println("\nAdditional Insights:");
        System.out.printf("Average Crop Yield: %.2f tons/hectare%n",
                processor.calculateAverageCropYield());
        System.out.println("Country with Highest Crop Yield: " +
                processor.findHighestYieldCountry());
    }
}