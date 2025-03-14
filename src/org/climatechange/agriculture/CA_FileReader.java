/**
 * Kendra Fitzgerald
 * This class will handle reading and parsing the data
 * from the CSV file
 */

package org.climatechange.agriculture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CA_FileReader {
    
    public static List<CAEntry> readDataFromFile(String filePath) {
        List<CAEntry> agricultureData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // read and print header line for verification
            String headerLine = br.readLine();
            System.out.println("Dataset Columns: " + headerLine);

            // read and parse data lines
            agricultureData = br.lines()
                    .map(CA_FileReader::parseDataLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return agricultureData;
    }

    
     //parse a string to integer, returning a default value if parsing fails.
    private static int parseInt(String value, int defaultValue) {
        try {
            return value == null || value.isEmpty() ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    
     //parse a string to double, returning a default value if parsing fails.
    private static double parseDouble(String value, double defaultValue) {
        try {
            return value == null || value.isEmpty() ? defaultValue : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    
     // parses a single line of CSV data into a ClimateAgricultureEntry object
    private static CAEntry parseDataLine(String line) {
        String[] parts = line.split(",");
        return new CAEntry(
                parseInt(parts[0], 0),         // Year
                parts[1],                                  // Country
                parts[2],                                 // Region
                parts[3],                                // Crop Type
                parseDouble(parts[4], 0.0),// Average Temperature
                parseDouble(parts[5], 0.0),// Total Precipitation
                parseDouble(parts[6], 0.0),// CO2 Emissions
                parseDouble(parts[7], 0.0),// Crop Yield
                parseInt(parts[8], 0),     // Extreme Weather Events
                parseDouble(parts[9], 0.0) // Irrigation
        );
    }
}