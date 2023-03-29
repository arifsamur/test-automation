#!/bin/bash

# Create a new XML file with the root element
# echo '<?xml version="1.0" encoding="UTF-8"?><testng-results><suite>' > merged-results.xml

# Loop through all testng-results_*.xml files in the current directory
for file in testng-results_*.xml; do
 # Extract the attributes from each file and update the attribute variables
    ignored=$(xmlstarlet sel -t -v '/testng-results/@ignored' "$file")
    total=$(xmlstarlet sel -t -v '/testng-results/@total' "$file")
    passed=$(xmlstarlet sel -t -v '/testng-results/@passed' "$file")
    failed=$(xmlstarlet sel -t -v '/testng-results/@failed' "$file")
    skipped=$(xmlstarlet sel -t -v '/testng-results/@skipped' "$file")

    total_ignored=$((total_ignored + ignored))
    total_total=$((total_total + total))
    total_passed=$((total_passed + passed))
    total_failed=$((total_failed + failed))
    total_skipped=$((total_skipped + skipped))
done

first_file=$(find . -name "testng-results_*.xml" | head -n 1)

# If a first file is found, copy it to merged-results.xml
if [ -n "$first_file" ]; then
  cp "$first_file" merged-results.xml
fi

# Loop through all testng-results_*.xml files except the first one
for file in $(find . -name "testng-results_*.xml" | tail -n +2); do
  # Check if the file exists and is an XML file
  if [[ -f "$file" && "$file" == *.xml ]]; then
    # Extract the <test> elements from each file and append them to the merged-results.xml file
    xmlstarlet sel -t -m '//test' -c . -n "$file" >> merged-results.xml
  fi
done

# Close the root element
echo '</suite></testng-results>' >> merged-results.xml

echo "TOTAL_IGNORED=$total_ignored" > results.env
echo "TOTAL_TOTAL=$total_total" >> results.env
echo "TOTAL_PASSED=$total_passed" >> results.env
echo "TOTAL_FAILED=$total_failed" >> results.env
echo "TOTAL_SKIPPED=$total_skipped" >> results.env

