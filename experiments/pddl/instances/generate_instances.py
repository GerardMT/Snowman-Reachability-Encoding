#!/usr/bin/env python

import os
import sys
from os import listdir
from os.path import isfile, join

if len(sys.argv) == 2 and (sys.argv[1] == '-h' or sys.argv[1] == '--help'):
	print('<snowman editor path> <snowman editor config path> <pddl encoding> <input levels path> <output path>')
	exit(0)

if len(sys.argv) != 6:
	print('Wrong number of parameters. Use -h or --help to print the help')
	exit(0)

snowman_editor_path = sys.argv[1]
snowman_editor_config_path = sys.argv[2]
pddl_encoding = sys.argv[3]
levels_path = sys.argv[4]
output_path =  sys.argv[5]

for f in next(os.walk(levels_path))[2]:
	level_name = os.path.splitext(f)[0]
	ouput_instance_path = output_path + '/' + level_name

	os.system('mkdir ' + ouput_instance_path)
	
	command = 'java -jar ' + snowman_editor_path + ' ' + snowman_editor_config_path + ' ' + pddl_encoding + ' ' + levels_path + '/' + f + ' ' + ouput_instance_path
	print(command)
	os.system(command)
