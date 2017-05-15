#!/usr/bin/env bash

source "$(dirname "${BASH_SOURCE}")/../scripts/utils/generator-util.sh"

# Paths
# Those paths are not needed when building runner.jar,
# but will be used in the future to package the mode.
modes="/root/sketchbook/modes"
executable="/mock-user/Processing"
# Those paths are important to build runner.jar.
core="/code/processing/core/library/core.jar"
pde="/code/processing/lib/pde.jar"

# Call functions in utils/generator-util.sh
generate-build-config ${modes} ${executable} ${core} ${pde}
