{\rtf1\ansi\ansicpg1252\cocoartf2822
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 #!/usr/bin/env bash\
\
# Assicurati che Java sia disponibile e imposta JAVA_HOME (Render di solito lo fa, ma non fa male essere espliciti)\
# Rileva la versione di Java installata da Render, se necessario.\
# Per semplicit\'e0, ci affidiamo al fatto che Render abbia gi\'e0 il JDK disponibile nel PATH.\
\
# Scarica e configura Apache Maven se non \'e8 gi\'e0 presente nel PATH di default\
# Questo \'e8 il passaggio chiave per risolvere "mvn: command not found"\
if ! command -v mvn &> /dev/null\
then\
    echo "Maven non trovato. Scaricando e configurando Maven..."\
    MAVEN_VERSION="3.9.6" # Puoi aggiornare questa versione se necessario\
    wget https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz\
    tar -xzf apache-maven-$MAVEN_VERSION-bin.tar.gz\
    export PATH=$(pwd)/apache-maven-$MAVEN_VERSION/bin:$PATH\
    echo "Maven configurato."\
fi\
\
# Esegui il tuo comando di build Maven\
echo "Esecuzione del build Maven..."\
mvn clean install -DskipTests\
echo "Build Maven completato."}