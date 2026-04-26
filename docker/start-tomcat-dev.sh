#!/usr/bin/env bash
set -e

if [ -f /workspace/pom.xml ]; then
  echo "Compilando classes Java em /workspace..."
  mvn -f /workspace/pom.xml compile
  rm -rf /usr/local/tomcat/webapps/ROOT/WEB-INF/classes
  cp -R /workspace/target/classes /usr/local/tomcat/webapps/ROOT/WEB-INF/classes
fi

exec catalina.sh run
