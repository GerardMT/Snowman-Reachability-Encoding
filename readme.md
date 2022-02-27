## Contents
    - detailed_results.pdf      - A table specifying each instance run time.
    - experiments               - All source code to run the experiments and their raw results.
        - pddl                  - PDDL experiments (lama, lama-c, fdss, and fdss-c).
            - instances         - Script to generate PDDL models from instances in batch.
            - results           - Summary of the experiments.
            - run               - Experiments source code and raw results.  
        - sat_smt               - SAT experiments (sat-l, sat-r, sat-r+) and SMT experiments (smt-l, smt-r, smt-r+).
            - results           - Summary of the experiments.
            - run               - Experiments source code and raw results.
    - instances                 - All game levels and some toy instances.
    - pddl                      - PDDL models (Problems and Instances files) for each provided instance.
        - basic-adl             - Normal model instances.
            - domains           - Domains for each number of snowmen.
            - instances         - The actual instances (domain.pddl and problem.pddl for each instance).
        - cheating-adl          - Cheating model instances.


## Notes about SAT/SMT experiments
All SAT/SMT experiments require to have their respective SAT/SMT solver located inside its experiment folder. Inside each experiment folder you will find some empty files which specifies the required solvers. Replace these empty files (*kissat*, *limboole* and *yices-smt2*) with the actual solver executable (or a soft link).

SAT experiments contain a folder named *original*. Inside this folder you will find a similar program which executes a single time step. With SMT, you can also execute a single time step with the Snowman Editor. 


## Notes about the PDDL experiments
To run the PDDL experiments, it is require to have the Fast Downward installed and added to the path.


## Other notes
To execute an experiment, execute the *run.sh* script located inside the experiment folder.

To generate the summary tables, run the *run.sh* script. It will parse all experiments inside the upper run folder at once. Note that PDDL and SAT/SMT are independent from each other.


## Snowman Editor
The Snowman Editor is a tool that eases the development and testing of different encodings for A Good Snowman is Hard to build instances.

For example, PDDL models are generated using tool. Also, SMT experiments use this tool as the main program.

Different versions of this tool are provided as snowman_editor.jar files. Also, the source code an more information about this tool can be found in its [repository](https://github.com/GerardMT/Snowman-Editor).