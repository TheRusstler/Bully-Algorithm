# Omission test
rm Node_*.txt
java -jar bully.jar 3 5002 Normal 3000 config1.txt &
java -jar bully.jar 2 5001 Omission 3000 config1.txt &
java -jar bully.jar 1 5000 Normal 3000 config1.txt Initiator &
sleep 10
cat Node_*.txt >> omission_results.txt
