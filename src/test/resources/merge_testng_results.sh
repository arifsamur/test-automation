#!/bin/bash

# Create a new XML file with the root element
echo -e '<?xml version="1.0" encoding="UTF-8"?>\n<testng-results>\n<suite>' > merged-results.xml

# Loop through all testng-results_*.xml files in the current directory
for file in testng-results_*.xml; do
  # Check if the file exists and is an XML file
  if [[ -f "$file" && "$file" == *.xml ]]; then
    # Extract the <test> elements from each file and append them to the merged-results.xml file
    xmlstarlet sel -t -m '//test' -c . -n "$file" >> merged-results.xml
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

  fi
done

# Read the suite's attributes from the first xml file
first_file=$(find . -name "testng-results_*.xml" | head -n 1)
suite_started_at=$(xmlstarlet sel -t -v "//suite/@started-at" "$first_file")
suite_name=$(xmlstarlet sel -t -v "//suite/@name" "$first_file")
suite_finished_at=$(xmlstarlet sel -t -v "//suite/@finished-at" "$first_file")
suite_duration_ms=$(xmlstarlet sel -t -v "//suite/@duration-ms" "$first_file")

# Close the root element
echo -e '</suite>\n</testng-results>' >> merged-results.xml

xmlstarlet fo -s 2 merged-results.xml > testng-results.xml

# Insert the testng-results element with the extracted attributes in the merged-results.xml file
xmlstarlet ed -L \
  -i '//testng-results' -t attr -n 'ignored' -v "$total_ignored" \
  -i '//testng-results' -t attr -n 'total' -v "$total_total" \
  -i '//testng-results' -t attr -n 'passed' -v "$total_passed" \
  -i '//testng-results' -t attr -n 'failed' -v "$total_failed" \
  -i '//testng-results' -t attr -n 'skipped' -v "$total_skipped" testng-results.xml

#rm merged-results.xml

# Insert the suite element with the extracted attributes in the merged-results.xml file
xmlstarlet ed -L \
  -i '//suite' -t attr -n 'started-at' -v "$suite_started_at" \
  -i '//suite' -t attr -n 'name' -v "$suite_name" \
  -i '//suite' -t attr -n 'finished-at' -v "$suite_finished_at" \
  -i '//suite' -t attr -n 'duration-ms' -v "$suite_duration_ms" \
  testng-results.xml

{
echo "TOTAL_IGNORED=$total_ignored"
echo "TOTAL_TOTAL=$total_total"
echo "TOTAL_PASSED=$total_passed"
echo "TOTAL_FAILED=$total_failed"
echo "TOTAL_SKIPPED=$total_skipped"
} >> results.env
