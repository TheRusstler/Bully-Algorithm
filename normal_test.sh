# Normal test
rm Node_*.txt
java -jar bully.jar 6 5005 Normal 3000 config1.txt &
java -jar bully.jar 5 5004 Normal 3000 config1.txt &
java -jar bully.jar 4 5003 Normal 3000 config1.txt &
java -jar bully.jar 3 5002 Normal 3000 config1.txt Initiator &
java -jar bully.jar 2 5001 Normal 3000 config1.txt &
java -jar bully.jar 1 5000 Normal 3000 config1.txt &
sleep 5
cat Node_*.txt >> normal_results.txt
