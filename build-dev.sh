#!/bin/bash
echo "➥ INICIANDO BUILD: jstatus-cli"
echo "--------------------------------------------------"
./gradlew clean build shadowJar

echo "➥ removendo jar incompleto"
echo "--------------------------------------------------"
rm build/libs/jstatus-cli-1.0.0.jar

if [ $? -eq 0 ]; then
    echo "--------------------------------------------------"
    echo "[ SUCCESS ] Build finalizado com sucesso!"
    echo "[ FILE    ] build/libs/jstatus-cli.jar"
    echo "--------------------------------------------------"
else
    echo "🅧 Erro no build. Verifique os logs acima."
    exit 1
fi
