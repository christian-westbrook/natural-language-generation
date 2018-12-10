#!/bin/bash

set -e

javacc NLG.jj
javac BlogTokenizer.java
java BlogTokenizer

cat tokens/*.out > output.txt
tr -sc 'A-Za-z0-9' '\n' < output.txt | tr A-Z a-z | sort | uniq -c | sort -n -r > final.txt

rm *.class
rm *.java
