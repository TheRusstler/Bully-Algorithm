# Crash test
rm Node_*.txt
java -jar bully.jar 3 5002 Normal 3000 config1.txt &
java -jar bully.jar 2 5001 Crash 3000 config1.txt &
java -jar bully.jar 1 5000 Normal 3000 config1.txt Initiator &
