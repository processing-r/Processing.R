#!/usr/bin/env bash

source "$(dirname "${BASH_SOURCE}")/../scripts/utils/generator-util.sh"

# Download Processing.
curl -L http://download.processing.org/processing-3.3-linux64.tgz > $HOME/processing.tgz && \
    tar xvf $HOME/processing.tgz -C $HOME > /dev/null && \
    mv $HOME/processing-3.3 $HOME/processing && \
    rm -rf $HOME/processing.tgz

# Paths
# Those paths are not needed when building runner.jar,
# but will be used in the future to package the mode.
modes="/mock-user/modes"
executable="/mock-user/Processing"
# Those paths are important to build runner.jar.
processing="$HOME/processing"
core="$HOME/processing/core/library"
pde="$HOME/processing/lib/pde.jar"

# Call functions in utils/generator-util.sh
generate-build-config ${modes} ${executable} ${core} ${pde}

root=$(dirname "${BASH_SOURCE}")/..
mkdir -p /home/travis/.ant/lib/
cp ./lib/test/* /home/travis/.ant/lib/
cd - > /dev/null
