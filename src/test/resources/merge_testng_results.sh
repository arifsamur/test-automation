#!/bin/bash

# Initialize variables for attribute totals
total_ignored=0
total_total=0
total_passed=0
total_failed=0
total_skipped=0

# Get the list of testng-results_*.xml files
files_list=$(find . -name "testng-results_*.xml")

# Get the first file from the list
first_file=$(echo "$files_list" | head -n 1)

# If a first file is found, copy it to merged-results.xml
if [ -n "$first_file" ]; then
  cp "$first_file" merged-results.xml

  # Extract the attribute values from the first file and add them to the totals
  total_ignored=$((total_ignored + $(xmlstarlet sel -t -v "//testng-results/@ignored" "$first_file")))
  total_total=$((total_total + $(xmlstarlet sel -t -v "//testng-results/@total" "$first_file")))
  total_passed=$((total_passed + $(xmlstarlet sel -t -v "//testng-results/@passed" "$first_file")))
  total_failed=$((total_failed + $(xmlstarlet sel -t -v "//testng-results/@failed" "$first_file")))
  total_skipped=$((total_skipped + $(xmlstarlet sel -t -v "//testng-results/@skipped" "$first_file")))
fi

echo "Loop through all testng-results_*.xml files except the first one"
for file in $(echo "$files_list" | tail -n +2); do
  # Check if the file exists and is an XML file
  if [[ -f "$file" && "$file" == *.xml ]]; then
    # Extract the <test> elements from each file
    tests_to_insert=$(xmlstarlet sel -t -m '//test' -c . -n "$file")
    echo $tests_to_insert
    # Insert the <test> elements after the last <test> element in the merged-results.xml file
    xmlstarlet ed -L -a "//suite" -t xml -v "$tests_to_insert" merged-results.xml

    # Extract the attribute values from each file and add them to the totals
    total_ignored=$((total_ignored + $(xmlstarlet sel -t -v "//testng-results/@ignored" "$file")))
    total_total=$((total_total + $(xmlstarlet sel -t -v "//testng-results/@total" "$file")))
    total_passed=$((total_passed + $(xmlstarlet sel -t -v "//testng-results/@passed" "$file")))
    total_failed=$((total_failed + $(xmlstarlet sel -t -v "//testng-results/@failed" "$file")))
    total_skipped=$((total_skipped + $(xmlstarlet sel -t -v "//testng-results/@skipped" "$file")))
  fi
done

# Close the root element
# echo '</suite></testng-results>' >> merged-results.xml

echo "TOTAL_IGNORED=$total_ignored" > results.env
echo "TOTAL_TOTAL=$total_total" >> results.env
echo "TOTAL_PASSED=$total_passed" >> results.env
echo "TOTAL_FAILED=$total_failed" >> results.env
echo "TOTAL_SKIPPED=$total_skipped" >> results.env
