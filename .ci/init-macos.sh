#!/usr/bin/env bash

brew install ant

source "$(dirname "${BASH_SOURCE}")/../scripts/utils/generator-util.sh"

# Download Processing.
cd ${HOME}
curl -L http://download.processing.org/processing-3.3.5-macosx.zip > ${HOME}/processing.zip && \
    unzip ${HOME}/processing.zip > /dev/null && \
    rm -rf ${HOME}/processing.zip
cd - > /dev/null

# Paths
# Those paths are not needed when building runner.jar,
# but will be used in the future to package the mode.
modes="/mock-user/modes"
executable="/mock-user/Processing"
# Those paths are important to build runner.jar.
processing="${HOME}/Processing.app"
core="${HOME}/Processing.app/Contents/Java/core/library"
pde="${HOME}/Processing.app/Contents/Java/pde.jar"
version="travis-ci"
pretty="pretty-travis-ci"

# Call functions in utils/generator-util.sh
generate-build-config ${modes} ${executable} ${core} ${pde} ${version} ${pretty}
