#!/bin/bash

input_directory="$1"
input_files="public/testng_result_*.xml"
output_file="public/testng-results.xml"

# Initialize the variables for the root data
ignored=0
total=0
passed=0
failed=0
skipped=0

# Create the merged output file with the XML declaration
echo '<?xml version="1.0" encoding="UTF-8"?>' > $output_file
echo '<testng-results>' >> $output_file

# Process each input file
for file in $input_files; do
    # Accumulate the root data values
    ignored=$((ignored + $(xmlstarlet sel -t -v '/testng-results/@ignored' $file)))
    total=$((total + $(xmlstarlet sel -t -v '/testng-results/@total' $file)))
    passed=$((passed + $(xmlstarlet sel -t -v '/testng-results/@passed' $file)))
    failed=$((failed + $(xmlstarlet sel -t -v '/testng-results/@failed' $file)))
    skipped=$((skipped + $(xmlstarlet sel -t -v '/testng-results/@skipped' $file)))

    # Extract the suite elements and append them to the merged output file
    xmlstarlet sel -t -c '/testng-results/suite' $file >> $output_file
done

# Add the accumulated root data values to the merged output file
xmlstarlet ed -L -u '/testng-results/@ignored' -v $ignored -u '/testng-results/@total' -v $total -u '/testng-results/@passed' -v $passed -u '/testng-results/@failed' -v $failed -u '/testng-results/@skipped' -v $skipped $output_file
