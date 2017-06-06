#!/usr/bin/env python3

# Usage: Create `raw-data.txt` and copy all built-in objects and functions
#        In https://processing.org/reference/ and run the code below.

functions = set()

with open('./hack/raw-data.txt', 'r') as f:
    lines = f.readlines()
    for line in lines:
        # If the line is not ended with '()', it is not a function.
        if line[-3:-1] != '()':
            continue
        functions.add(line)

with open ('./hack/functions.txt', 'w+') as f:
    for function in sorted(functions):
        f.write(function[:-3] + '\n')
