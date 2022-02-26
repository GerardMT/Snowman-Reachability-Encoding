#!/usr/bin/env python

# Input an experiment folder

import os
import sys
from os import listdir
from os.path import isfile, join

instances_sorted = ['adam', 'alex', 'alice', 'andy', 'ben_alan', 'chris', 'cynthia_michael', 'david', 'freya', 'helen', 'jack_jill', 'jessica_amelia', 'julian', 'kate', 'kevin', 'lauren', 'louise', 'lucy', 'lydia', 'mary', 'paul', 'priscilla', 'rebecca', 'rob_james_matthew', 'sally', 'sarah', 'tanya', 'william', 'willow', 'zoe_richard', 'a_b', 'claire', 'hard_harder_hardest', 'kate_garry_craig', 'one_two_three', 'unnamed', 'unused_1', 'unused_2', 'zoe_2_richard_2']

path = sys.argv[1]

results = {}

preprocessing_pattern = 'Done!'

for f in next(os.walk(path + '/results/'))[1]:
	instance_path = path + '/results/' + f

	results[f] = {}

	# Check for plan cost
	files = [f for f in listdir(instance_path) if isfile(join(instance_path, f))]
	files = list(filter(lambda x: not x.find('sas_plan'), files))
	files.sort()

	if files:
		lines = open(instance_path + '/' + files[-1]).readlines()
		l = lines[-1]
		results[f]['cost'] = int(l[9:][:-15])

	# Get times
	file = open(instance_path + '/out')
	lines = file.readlines()
	
	for l in lines:
		if l.find('Done!') != -1:
			preprocessing_time = l.split(',')[0]
			preprocessing_time = round(float(preprocessing_time[7:-5]), 2)
			results[f]['preprocessing_time'] = preprocessing_time
		
		elif l.find('Solution found!') != -1:
			solution_time = l.split(',')[0]
			solution_time = round(float(solution_time[3:-1]), 2)
			results[f]['time'] = solution_time + preprocessing_time
			break

	file.close()

csv = open(os.path.basename(os.path.normpath(path)) + '.csv', 'w')
csv.write('instance,cost,time,preprocessing_time\n')
for instance in instances_sorted:
	csv.write(instance + ',')

	if instance in results:
		dic = results[instance]
		
		if 'cost' in dic:
			csv.write(str(dic['cost']))

		csv.write(',')

		if 'time' in dic:
			csv.write(str(dic['time']))

		csv.write(',')

		if 'preprocessing_time' in dic:
			csv.write(str(dic['preprocessing_time']))

	csv.write('\n')