#!/bin/bash
rm -r out
mkdir out
cd out
./../summary.py ../../run/fdss/
./../summary.py ../../run/fdss-c/
./../summary_anytime.py ../../run/lama/
./../summary_anytime.py ../../run/lama-c/