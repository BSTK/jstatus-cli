echo "Construindo Jar ..."
./gradlew clean build shadowJar

echo "Removendo Jar incompleto"
rm build/libs/jstatus-cli-1.0.0.jar

echo "Executando Jar ..."
java -jar build/libs/jstatus-cli.jar