# Timing test
rm Node_*.txt
java -jar bully.jar 3 5002 Normal 100 config1.txt &
java -jar bully.jar 2 5001 Timing 100 config1.txt &
java -jar bully.jar 1 5000 Normal 100 config1.txt Initiator &
sleep 10
cat Node_*.txt >> timing_results.txt
