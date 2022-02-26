/**
 * @class snowman2sat_infix
 * @author Miquel Bofill
 * @version 1.0
 * @date 2021-04-23
 * @brief Translation of "A good snowman is hard to build" instance to SAT formula; using infix notation with &, |, !, ->, <-> operators in order to feed the formula to a tseitin converter (Limboole)
   @attention East and west directions are swapped
 * @see http://smtlib.cs.uiowa.edu/language.shtml
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;

public class snowman2sat_infix {

		static float totalSolvingTime = 0f;

    /// @pre Program has been called with argument <tt> n </tt> where n >= 0 is the
    ///      number of time steps of the desired plan
    ///
    ///      A problem instance is available in the standard input with the format:
    ///
    ///      xx#######
    ///      ##..1...#
    ///      #.##.##.#
    ///      #...'2..#
    ///      #..#.#..#
    ///      #...1...#
    ///      ##..q..##
    ///      ######### 
    ///
    ///      where
    ///
    ///       x : out of grid
    ///       # : wall
    ///       p : character with snow on the floor
    ///       q : character
    ///       1 : small ball
    ///       2 : medium ball
    ///       3 : small ball on top of a medium ball
    ///       4 : large ball
    ///       5 : small ball on top of a large ball
    ///       6 : medium ball on top of a large ball
    ///       7 : small ball on top of a medium ball on top of a large ball
    ///       ' : grass
    ///       . : snow
    ///
    ///       The grid is assumed to be rectangular and closed
    
    /// @post Outputs an smt2 translation of the problem instance described by the input
	public static void main(String[] args) throws Exception {
		String levelName = args[0].replaceAll(".*/", "").replace(".lvl", "");

		String[] commandParser = { "./limboole", "-s", "-d"};
		String[] commandSolver = { "/usr/bin/time", "-p", "./kissat", "-q", "-n"};

		String level = new String (Files.readAllBytes(Paths.get(args[0])));

		ProcessBuilder builder = new ProcessBuilder();
		builder.redirectErrorStream(true);

		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		long plannerStartTime = threadMXBean.getThreadCpuTime(Thread.currentThread().getId());

		boolean sat;
		int nSteps = 1;
		do {
			System.out.println("Timesteps: " + nSteps);

			InputStream levelIn = new ByteArrayInputStream(level.getBytes(StandardCharsets.UTF_8));

			builder.command(commandParser);
			Process processParser = builder.start();
			BufferedReader parserOut = new BufferedReader(new InputStreamReader(processParser.getInputStream()));
			PrintStream parserInt = new PrintStream(processParser.getOutputStream());


			//printPreamble(parserInt);
			translate(levelIn, parserInt, nSteps);
			parserInt.flush();
			parserInt.close();

			builder.command(commandSolver);
			Process processSolver = builder.start();
			BufferedReader solverOut = new BufferedReader(new InputStreamReader(processSolver.getInputStream()));
			PrintStream solverIn = new PrintStream(processSolver.getOutputStream());

			String line;
			while ((line = parserOut.readLine()) != null) {
				if(!line.equals("unknown")) {
					solverIn.println(line);
				}
			}

			solverIn.close();

			sat = checkSat(solverOut);

			processParser.destroy();
			processSolver.destroy();

			++nSteps;
		} while (!sat && nSteps <= 100);

		long plannerTime = threadMXBean.getThreadCpuTime(Thread.currentThread().getId()) - plannerStartTime;

		float time = plannerTime / 1e9f + totalSolvingTime;

		FileWriter resultsWriter = new FileWriter("./results/" + levelName + ".res");
		resultsWriter.write("solved=" + sat + "\n");
		resultsWriter.write("solvingTime=" + new BigDecimal(time).toPlainString() + "\n");
		resultsWriter.write("nActions=" + (nSteps - 1));
		resultsWriter.close();
	}

	private static boolean checkSat(BufferedReader solverOut) throws Exception {
		short sat = -1;

		String line;
		while ((line = solverOut.readLine()) != null) {
			System.out.println(line);

			if (line.equals("s SATISFIABLE")) {
				sat = 1;
			} else if (line.equals("s UNSATISFIABLE")) {
				sat = 0;
			} else if (line.substring(0, 4).equals("user")) {
				totalSolvingTime += Float.parseFloat(line.substring(5));
			} else if (line.substring(0, 3).equals("sys")) {
				totalSolvingTime += Float.parseFloat(line.substring(4));
			}
		}

		switch (sat) {
			case 0:
				return false;
			case 1:
				return true;
		}

		throw new Exception("Solver unknown result");
	}

    private static void printPreamble(PrintStream out) {
	out.println("(set-option :produce-models true)");
	out.println("(set-logic QF_UF)");
	out.println("(set-info :smt-lib-version 2.6)");
	out.println("(set-info :category \"crafted\")");
    }

    /// @post Reads the description of the initial state and returns it
    private static char[][] readGrid(InputStream in) throws Exception {
	BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
	LinkedList<String> l = new LinkedList<String>();
	String line = buffer.readLine();
	int nColumns = line.length();
	//	try {  // old : before knowing existence of x!
	    //	    assert line.matches("#+?");
	    l.add(line);
	    line = buffer.readLine();
	    while (line != null && line.length() != 0) {
		//		assert line.length() == nColumns;
		//		assert line.matches("#[#pq1234567'.]+?#");
		l.add(line);
		line = buffer.readLine();
	    }
	    //	    assert l.getLast().matches("#+?");
	    //	}
	    //	catch (AssertionError e) {
	    //	    throw new Exception("Grid is not rectangular or not closed or contains invalid characters. Check whitespaces at the end of lines!");
	    //	}
	char[][] grid = new char[l.size()][];
	int i = 0;
	for (String s : l)
	    grid[i++] = s.toCharArray();
	return grid;
    }

    private static void printVariables(PrintStream out, int nSteps, int nBall, int nSnowman, Set<Integer> validLocations) {
	out.println("\n;; Variables");
	for (int i = 0; i < nSteps; ++i) {
	    out.println("(declare-const n_" + i + " Bool)"); // action in north direction 
	    out.println("(declare-const s_" + i + " Bool)"); // action in south direction 
	    out.println("(declare-const e_" + i + " Bool)"); // action in east direction 
	    out.println("(declare-const w_" + i + " Bool)"); // action in west direction
	}
	
	for (int i = 0; i <= nSteps; ++i) {
	    out.println();
	    for (Integer loc : validLocations) {
		out.println("(declare-const c_" + loc + "_" + i + " Bool)"); // Character
		out.println("(declare-const s_" + loc + "_" + i + " Bool)"); // Snow
		out.println("(declare-const bs_" + loc + "_" + i + " Bool)"); // Small ball
		out.println("(declare-const bm_" + loc + "_" + i + " Bool)"); // Medium ball
		out.println("(declare-const bl_" + loc + "_" + i + " Bool)"); // Large ball
	    }
	}

    }

    // Returns <number of snowmans, assertions for the initial state>
    // Replaces 'x' by '#' in the grid
    private static Pair<Integer,String> initialState(char[][] grid) throws Exception {
	int nRows = grid.length;
	int nCols = grid[0].length;
	int nChar = 0; // Number of characters (players)
	int nBall = 0; // Number of balls
	int nSmall = 0; // Number of small balls
	int nLarge = 0; // Number of large balls	
	String s = ""; // = "\n;; Initial state\n";
	for (int i = 0; i < nRows; ++i) {
	    for (int j = 0; j < nCols; ++j) {
		int loc = i * nCols + j + 1;
		switch (grid[i][j]) {
		case 'x':
		    grid[i][j] = '#';
		    break;
		case '#':
		    break;
		case 'p':
		    nChar++;
		    s += "c_" + loc + "_0 & ";
		    s += "s_" + loc + "_0 & ";
		    s += "(!bs_" + loc + "_0) & ";
		    s += "(!bm_" + loc + "_0) & ";
		    s += "(!bl_" + loc + "_0) & ";
		    break;
		case 'q':
		    nChar++;
		    s += "c_" + loc + "_0 & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "(!bs_" + loc + "_0) & ";
		    s += "(!bm_" + loc + "_0) & ";
		    s += "(!bl_" + loc + "_0) & ";
		    break;
		case '1':
		    nBall++; nSmall++;
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "bs_" + loc + "_0 & ";
		    s += "(!bm_" + loc + "_0) & ";
		    s += "(!bl_" + loc + "_0) & ";
		    break;
		case '2':
		    nBall++;
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "(!bs_" + loc + "_0) & ";
		    s += "bm_" + loc + "_0 & ";
		    s += "(!bl_" + loc + "_0) & ";
		    break;
		case '3':
		    nBall += 2; nSmall++;
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "bs_" + loc + "_0 & ";
		    s += "bm_" + loc + "_0 & ";
		    s += "(!bl_" + loc + "_0) & ";
		    break;
		case '4':
		    nBall++; nLarge++;
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "(!bs_" + loc + "_0) & ";
		    s += "(!bm_" + loc + "_0) & ";
		    s += "bl_" + loc + "_0 & ";
		    break;
		case '5':
		    nBall += 2; nSmall++; nLarge++;
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "bs_" + loc + "_0 & ";
		    s += "(!bm_" + loc + "_0) & ";
		    s += "bl_" + loc + "_0 & ";
		    break;
		case '6':
		    nBall += 2; nLarge++;
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "(!bs_" + loc + "_0) & ";
		    s += "bm_" + loc + "_0 & ";
		    s += "bl_" + loc + "_0 & ";
		    break;
		case '7':
		    nBall += 3; nSmall++; nLarge++;
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "bs_" + loc + "_0 & ";
		    s += "bm_" + loc + "_0 & ";
		    s += "bl_" + loc + "_0 & ";
		    break;
		case '\'':
		    s += "(!c_" + loc + "_0) & ";
		    s += "(!s_" + loc + "_0) & ";
		    s += "(!bs_" + loc + "_0) & ";
		    s += "(!bm_" + loc + "_0) & ";
		    s += "(!bl_" + loc + "_0) & ";
		    break;
		case '.':
		    s += "(!c_" + loc + "_0) & ";
		    s += "s_" + loc + "_0 & ";
		    s += "(!bs_" + loc + "_0) & ";
		    s += "(!bm_" + loc + "_0) & ";
		    s += "(!bl_" + loc + "_0) & ";
		    break;
		default:
		    throw new Exception("Symbol '" + grid[i][j] + "' invalid in grid");
		}
	    }
	}

	if (nChar != 1)
	    throw new Exception("There must be one and only one character");

	if (nBall % 3 != 0)
	    throw new Exception("Found " + nBall + " balls (should be a multiple of three)");

	int nSnowman = nBall / 3;
	
	if (nSmall < nSnowman)
	    throw new Exception("Trivially unsatisfiable (" + nSmall + " are too few small balls)");
	
	if (nLarge > nSnowman)
	    throw new Exception("Trivially unsatisfiable (" + nLarge + " are too many large balls)");
	
	s += "bs" + nSmall + "_0 & ";
	s += "bl" + nLarge + "_0 & ";

	return new Pair<Integer,String>(nSnowman, s); 
    }

    // Key = d + l where  d  is a direction ('n', 's', 'e', 'w') and  l  is the number of the location; Value is the number of the location next to  l  in the direction  d
    private static Map<String,Integer> computeNextRelation(int nRows, int nCols) {
	TreeMap<String,Integer> m = new TreeMap<>();
	int loc = 1;
	for (int i = 0; i < nRows; ++i) {
	    for (int j = 1; j <= nCols; ++j, ++loc) {
		if (loc > nCols) // Not first row
		    m.put("n" + loc, loc - nCols);
		if (loc <= nCols * (nRows - 1)) // Not last row
		    m.put("s" + loc, loc + nCols);
		if (loc % nCols != 1) // Not first column
		    m.put("e" + loc, loc - 1);
		if (loc % nCols != 0) // Not last column
		    m.put("w" + loc, loc + 1);
	    }
	}
	return m;
    }

    // Next to the next (in the same direction)
    private static Map<String,Integer> computeNext2Relation(Map<String,Integer> next) {
	TreeMap<String,Integer> m = new TreeMap<>();
	for (Map.Entry<String,Integer> e : next.entrySet()) {
	    String k = e.getKey();
	    Integer j = next.get(k.substring(0,1) + e.getValue());
	    if (j != null)
		m.put(k, j);
	}
	return m;
    }

    private static void	computeSetsNext2Wall(Set<Integer> l, Set<Integer> ln, Set<Integer> lnn, String d, Map<String,Integer> next, Map<String,Integer> next2, char[][] grid) {
	int nCols = grid[0].length;
	for(Integer i : l)
	    if (grid[(i - 1) / nCols][(i - 1) % nCols] != '#') {
		Integer j = next.get(d + i);
		Integer k = next2.get(d + i);
		if (j != null && grid[(j - 1) / nCols][(j - 1) % nCols] == '#')
		    ln.add(i);
		if (k != null && grid[(k - 1) / nCols][(k - 1) % nCols] == '#')
		    lnn.add(i);   
	    }
    }

    private static void printExactlyOneAction(PrintStream out, int nSteps) {
	//	out.println("\n;; Exactly one action per time step");
	for (int i = 0; i < nSteps; ++i) {
	    out.print("(n_" + i + " | s_" + i + " | e_" + i + " | w_" + i + ") & ");
	    out.print("((!n_" + i + ") | (!s_" + i + ")) & ");
	    out.print("((!n_" + i + ") | (!e_" + i + ")) & ");
	    out.print("((!n_" + i + ") | (!w_" + i + ")) & ");
	    out.print("((!s_" + i + ") | (!e_" + i + ")) & ");
	    out.print("((!s_" + i + ") | (!w_" + i + ")) & ");
	    out.print("((!e_" + i + ") | (!w_" + i + ")) & ");
	}
    }
    
    private static void printActions(PrintStream out, int nSteps, int nSnowman, Set<Integer> l, Set<Integer> lwall, Set<Integer> lwall2, String d, Map<String,Integer> next, Map<String,Integer> next2) {
	for (int i = 0; i < nSteps; ++i) {
	    String t = "_" + i; String t1 = "_" + (i + 1);

	    // no action allowed
	    for (Integer j : lwall)
		out.print("(c_" + j + t + " -> !" + d + t + ") & ");
	    
	    // only move allowed
	    for (Integer j : lwall2)
		if (!lwall.contains(j)) {
		    String ln = "_" + next.get(d + j);
		    out.print("((c_" + j + t + " & " + d + t + ") -> (!c_" + j + t1 + " & c" + ln + t1 + " & !bs" + ln + t + " & !bm" + ln + t + " & !bl" + ln + t + ")) & ");
		}

	    for (Integer j : l)
		if (!lwall.contains(j) && !lwall2.contains(j)) {
		    String ln = "_" + next.get(d + j);
		    String lnn = "_" + next2.get(d + j);
		    out.print("((c_" + j + t + " & " + d + t + ") -> (");
		    out.print("(!c_" + j + t1 + " & c" + ln + t1 + " & !bs" + ln + t + " & !bm" + ln + t + " & !bl" + ln + t + ") |"); // move
		    out.print("(!c_" + j + t1 + " & c" + ln + t1 + " & ((bs" + ln + t + " & !bm" + ln + t + " & !bl" + ln + t + " & !bs" + lnn + t + " & (bm" + lnn + t + " |  bl" + lnn + t + ") & !bs" + ln + t1 + " & bs" + lnn + t1 + ") | ");
		    out.print("(!bs" + ln + t + " & bm" + ln + t + " & !bl" + ln + t + " & !bs" + lnn + t + " & !bm" + lnn + t + " & bl" + lnn + t + " & !bm" + ln + t1 + " & bm" + lnn + t1 + "))) | "); // push
		    out.print("(!c_" + j + t1 + " & c" + ln + t1 + " & !bs" + lnn + t + " & !bm" + lnn + t + " & !bl" + lnn + t + " & !s" + lnn + t1 + " & (");
		    out.print("(bs" + ln + t + " & !bm" + ln + t + " & !bl" + ln + t + " & !bs" + ln + t1 + " & ((!s" + lnn + t + " & bs" + lnn + t1 + ") | (s" + lnn + t + " & bm" + lnn + t1 + "))) |"); 
		    out.print("(!bs" + ln + t + " & bm" + ln + t + " & !bl" + ln + t + " & !bm" + ln + t1 + " & ((!s" + lnn + t + " & bm" + lnn + t1 + ") | (s" + lnn + t + " & bl" + lnn + t1 + "))) | "); 
		    out.print("(!bs" + ln + t + " & !bm" + ln + t + " & bl" + ln + t + " & !bl" + ln + t1 + " & bl" + lnn + t1 + "))) | "); // roll
		    out.print("(c_" + j + t1 + " & !bs" + lnn + t + " & !bm" + lnn + t + " & !bl" + lnn + t + " & !s" + lnn + t1 + " & (");
		    out.print("(bs" + ln + t + " & (bm" + ln + t + " | bl" + ln + t + ") & !bs" + ln + t1 + " & ((!s" + lnn + t + " & bs" + lnn + t1 + ") | (s" + lnn + t + " & bm" + lnn + t1 + "))) | ");
		    out.print("(!bs" + ln + t + " & bm" + ln + t + " & bl" + ln + t + " & !bm" + ln + t1 + " & ((!s" + lnn + t + " & bm" + lnn + t1 + ") | (s" + lnn + t + " & bl" + lnn + t1 + ")))))"); // pop
		    out.print(")) & ");
		}
	    
	}
    }

    private static void printFrameAxioms(PrintStream out, int nSteps, Set<Integer> l) {
	
	for (int i = 0; i < nSteps; ++i) {
	    String t = "_" + i; String t1 = "_" + (i + 1);
	    
	    for (Integer j : l) {
		out.print("(!s_" + j + t + " -> !s_" + j + t1 + ") & ");
		out.print("((s_" + j + t + " & !s_" + j + t1 + ") -> (bs_" + j + t1 + " | bm_" + j + t1 + " | bl_" + j + t1 + ")) & ");
	    }
	}

    }

    // returns k such that next.get(d + k) is j
    private static Integer inext(char d, Integer j, Map<String,Integer> next) {
	Integer k = null;
	for (Map.Entry<String,Integer> e : next.entrySet()) {
	    if (e.getValue() == j) {
		String key = e.getKey();
		if (key.charAt(0) == d)
		    return Integer.parseInt(key.substring(1));
	    }
	}
	return k;
    }

    private static void printFrameAxioms(PrintStream out, int nSteps, Set<Integer> l, String d, Map<String,Integer> next, Map<String,Integer> next2) {
	for (int i = 0; i < nSteps; ++i) {
	    String t = "_" + i; String t1 = "_" + (i + 1);
	    
	    for (Integer j : l) {
		Integer ln = next.get(d + j);
		Integer ls = inext(d.charAt(0), j, next);
		Integer lss = inext(d.charAt(0), j, next2);
		if (ln != null && l.contains(ln))
		    out.print("((c_" + j + t + " & !c_" + j + t1 + " & " + d + t + ") -> c_" + ln + t1 + ") & ");
		else
		    out.print("!(c_" + j + t + " & !c_" + j + t1 + " & " + d + t + ") & ");
		if (ls != null && l.contains(ls)) {
		    out.print("((!c_" + j + t + " & c_" + j + t1 + " & " + d + t + ") -> c_" + ls + t + ") & ");
		    out.print("((bs_" + j + t + " & !bs_" + j + t1 + " & " + d + t + ") -> c_" + ls + t + ") & ");
		    out.print("((bm_" + j + t + " & !bm_" + j + t1 + " & " + d + t + ") -> (c_" + ls + t + " & !bs_" + j + t + ")) & ");
		    out.print("((bl_" + j + t + " & !bl_" + j + t1 + " & " + d + t + ") -> (c_" + ls + t + " & !bs_" + j + t + " & !bm_" + j + t + ")) & ");
		    if (lss != null && l.contains(lss)) {			
			out.print("((!bs_" + j + t + " & bs_" + j + t1 + " & " + d + t + ") -> (c_" + lss + t + " & bs_" + ls + t + ")) & ");
			out.print("((!bm_" + j + t + " & bm_" + j + t1 + " & " + d + t + ") -> (c_" + lss + t + " & ((!s_" + j + t + " & !bs_" + ls + t + " & bm_" + ls + t + ") | (s_" + j + t + " & bs_" + ls + t + ")))) & ");
			out.print("((!bl_" + j + t + " & bl_" + j + t1 + " & " + d + t + ") -> (c_" + lss + t + " & ((!bs_" + ls + t + " & !bm_" + ls + t + " & bl_" + ls + t + ") | (s_" + j + t + " & !bs_" + ls + t + " & bm_" + ls + t + ")))) & ");
		    }
		    else {
			out.print("(bs_" + j + t + " | !bs_" + j + t1 + " | !" + d + t + ") & ");
			out.print("(bm_" + j + t + " | !bm_" + j + t1 + " | !" + d + t + ") & ");
			out.print("(bl_" + j + t + " | !bl_" + j + t1 + " | !" + d + t + ") & ");
		    }
		}
		else {
		    out.print("(!(!c_" + j + t + " & c_" + j + t1 + " & " + d + t + ")) & ");
		    out.print("(!bs_" + j + t + " | bs_" + j + t1 + " | !" + d + t + ") & ");
		    out.print("(!bm_" + j + t + " | bm_" + j + t1 + " | !" + d + t + ") & ");
		    out.print("(!bl_" + j + t + " | bl_" + j + t1 + " | !" + d + t + ") & ");
		    out.print("(bs_" + j + t + " | !bs_" + j + t1 + " | !" + d + t + ") & ");
		    out.print("(bm_" + j + t + " | !bm_" + j + t1 + " | !" + d + t + ") & ");
		    out.print("(bl_" + j + t + " | !bl_" + j + t1 + " | !" + d + t + ") & ");
		}
	    }
	}
    }

    private static void printGoal(PrintStream out, int nSteps, Set<Integer> validLocations) {
	String s = "";
	for (Integer loc : validLocations)
	    s += "(bs_" + loc + "_" + nSteps + " <-> bm_" + loc + "_" + nSteps + ") & (bm_" + loc + "_" + nSteps + " <-> bl_" + loc + "_" + nSteps + ") & ";
	out.println(s.substring(0, s.length() - 3));
    }
    
    private static void translate(InputStream in, PrintStream out, int nSteps) throws Exception {
	char[][] grid = readGrid(in);
	int nRows = grid.length;
	int nCols = grid[0].length;
	int nLocs = nRows * nCols;
	Set<Integer> l = new TreeSet<>(); // Set of valid locations
	//	out.println("\n;; Problem instance");
	int loc = 1;
	for (int i = 0; i < nRows; i++) {
	    //	    out.print(";; ");
	    for (int j = 0; j < nCols; j++, ++loc) {
		//	 	out.print(grid[i][j]);
		if (grid[i][j] != '#' && grid[i][j] != 'x')
		    l.add(loc);
	    }
	    //	    out.println();
	}
	Map<String,Integer> next = computeNextRelation(nRows, nCols); // Key = d + l where  d  is a direction ('n', 's', 'e', 'w') and  l  is the number of the location; Value is the number of the location next to  l  in the direction  d
	Map<String,Integer> next2 = computeNext2Relation(next); // Next to the next (in the same direction)
	Set<Integer> ln = new TreeSet<>(); // Set of valid positions with a wall in the north
	Set<Integer> lnn = new TreeSet<>(); // Set of valid positions with a wall two steps ahead in the north
	Set<Integer> ls = new TreeSet<>(); // Set of valid positions with a wall in the south
	Set<Integer> lss = new TreeSet<>(); // Set of valid positions with a wall two steps ahead in the south
	Set<Integer> le = new TreeSet<>(); // Set of valid positions with a wall in the east
	Set<Integer> lee = new TreeSet<>(); // Set of valid positions with a wall two steps ahead in the east
	Set<Integer> lw = new TreeSet<>(); // Set of valid positions with a wall in the west
	Set<Integer> lww = new TreeSet<>(); // Set of valid positions with a wall two steps ahead in the west
	computeSetsNext2Wall(l, ln, lnn, "n", next, next2, grid);
	computeSetsNext2Wall(l, ls, lss, "s", next, next2, grid);
	computeSetsNext2Wall(l, le, lee, "e", next, next2, grid);
	computeSetsNext2Wall(l, lw, lww, "w", next, next2, grid);
	Pair<Integer,String> p  = initialState(grid);
	int nSnowman = p.first;
	int nBall = nSnowman * 3;
	//	printVariables(out, nSteps, nBall, nSnowman, l);
	out.print(p.second); // print initial state
	printExactlyOneAction(out, nSteps);
	//	out.print("\n;; Actions preconditions and effects");
	printActions(out, nSteps, nSnowman, l, ln, lnn, "n", next, next2);
	printActions(out, nSteps, nSnowman, l, ls, lss, "s", next, next2);
	printActions(out, nSteps, nSnowman, l, le, lee, "e", next, next2);
	printActions(out, nSteps, nSnowman, l, lw, lww, "w", next, next2);
	//	out.print("\n;; Frame axioms");
	printFrameAxioms(out, nSteps, l);
	printFrameAxioms(out, nSteps, l, "n", next, next2);
	printFrameAxioms(out, nSteps, l, "s", next, next2);
	printFrameAxioms(out, nSteps, l, "e", next, next2);
	printFrameAxioms(out, nSteps, l, "w", next, next2);
	//	out.println("\n;; Goal");
	printGoal(out, nSteps, l);
	//	out.println("\n(check-sat)");
	//	out.println("(get-model)");
	//	out.println("(exit)");
	}
/** @file Pair.java
 @brief Un parell genèric
 */

	/** @class Pair
	 @brief Parell genèric
	 */
	public static class Pair<S,T> {
		public S first;
		public T second;

		public Pair(S first, T second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public boolean equals(Object o) {
			boolean r = false;
			if (o != null && o instanceof Pair) {
				Pair p = (Pair)o;
				r = this.first == p.first && this.second == p.second;
			}
			return r;
		}

	}
}
