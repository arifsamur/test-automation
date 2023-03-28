#!/bin/bash

# Create a new XML file with the root element
echo '<?xml version="1.0" encoding="UTF-8"?><suite>' > merged-results.xml

# Loop through all testng-results_*.xml files in the current directory
for file in testng-results_*.xml; do
  # Check if the file exists and is an XML file
  if [[ -f "$file" && "$file" == *.xml ]]; then
    # Extract the <test> elements from each file and append them to the merged-results.xml file
    xmlstarlet sel -t -m '//test' -c . -n "$file" >> merged-results.xml
  fi
done

# Close the root element
echo '</suite>' >> merged-results.xml
echo '</testng-results>' >> merged-results.xml
