#!/usr/bin/env bash

root=$(dirname "${BASH_SOURCE}")/..

# Download https://secure.central.sonatype.com/maven2/com/codacy/codacy-coverage-reporter/1.0.13/codacy-coverage-reporter-1.0.13-assembly.jar
# curl https://secure.central.sonatype.com/maven2/com/codacy/codacy-coverage-reporter/1.0.13/codacy-coverage-reporter-1.0.13-assembly.jar > codacy-coverage-reporter-assembly.jar
cd ${root}
# Set the token first: 
# export CODACY_PROJECT_TOKEN=%Project_Token%
COMMIT=$(git log -n 1 --pretty=format:"%H")
echo ${COMMIT}
java -cp ./scripts/codacy-coverage-reporter-assembly.jar com.codacy.CodacyCoverageReporter -l Java --commitUUID ${COMMIT} -r ./test-output/site/jacoco/report.xml
cd - > /dev/null
