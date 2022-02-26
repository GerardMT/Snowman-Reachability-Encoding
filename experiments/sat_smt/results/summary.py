#!/usr/bin/env python

import os
import sys

path = sys.argv[1]

results = {}
instances_sorted = ['adam', 'alex', 'alice', 'andy', 'ben_alan', 'chris', 'cynthia_michael', 'david', 'freya', 'helen', 'jack_jill', 'jessica_amelia', 'julian', 'kate', 'kevin', 'lauren', 'louise', 'lucy', 'lydia', 'mary', 'paul', 'priscilla', 'rebecca', 'rob_james_matthew', 'sally', 'sarah', 'tanya', 'william', 'willow', 'zoe_richard', 'a_b', 'claire', 'hard_harder_hardest', 'kate_garry_craig', 'one_two_three', 'unnamed', 'unused_1', 'unused_2', 'zoe_2_richard_2']

for f in next(os.walk(path))[1]:
	results_directory_path = path + "/" + f + "/results"

	results_files = [x for x in os.listdir(results_directory_path) if os.path.isfile(os.path.join(results_directory_path, x))]
	results_files_out = list(filter(lambda x: x.endswith(".res"), results_files))

	results_experiment = {}

	for r in results_files_out:
		instance = r[:-4]

		results_file_res_path = results_directory_path + "/" + instance + ".res"
		
		results_instance = {}

		if os.path.exists(results_file_res_path):
			file = open(results_file_res_path)
			lines = file.readlines()
			
			for l in lines:
				sub_string = l.replace("\n", "").split("=")

				if f.find("sat") != -1 and f.find("r") != -1 and sub_string[0] == "nActions":
					results_instance["nBallActions"] = sub_string[1]
				else:
					results_instance[sub_string[0]] = sub_string[1]
			
			file.close()

		results_experiment[instance] = results_instance

	results[f] = results_experiment


lines_experiments = {}

results_keys_sorted = sorted(results)

for e in results_keys_sorted:
	i_dic = results[e]
	
	lines_experiment = []

	lines_experiment.append(e + ",,")
	lines_experiment.append("Time,Timesteps,")

	for i in instances_sorted:
		if not i in i_dic:
			lines_experiment.append("-,-,")
		else:
			k_dic = i_dic[i]
			if (not "nBallActions" in k_dic and not "nActions" in k_dic) or not "solvingTime" in k_dic:
				lines_experiment.append(",,")
			else:
				line = str(round(float(k_dic["solvingTime"]), 2)) + ","
				if not e.find("smt-l") == -1:
					line += k_dic["nActions"] + ","
				elif not e.find("smt-r") == -1:
					line += k_dic["nBallActions"] + ","
				else: 
					if "nBallActions" in k_dic:
						line += k_dic["nBallActions"] + ","
					else:
						line += k_dic["nActions"] + ","

				lines_experiment.append(line)

	lines_experiments[e] = lines_experiment

lines = [",", ","]
for i in instances_sorted:
	lines.append(i + ",")

for e, l in lines_experiments.items():
	for i in range(len(l)):
		lines[i] += l[i]

csv = open("./summary.csv", "w")

for l in lines:
	csv.write(l + "\n")

csv.close()