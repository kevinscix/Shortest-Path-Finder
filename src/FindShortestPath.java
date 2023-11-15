import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * A program to find the shortest path from the start point to the exit point in a dungeon.
 *The program reads the input file from args[0] and outputs the path length found.
 *@author Kevin Wu kwu347
 */
public class FindShortestPath {
	/**
	 * The name of the input file.
	 */
	static String dungeonFileName;
	/**
	 * The current hexagon being analyzed.
	 */
	static Hexagon current;
	/**
	 * The main method of the program.
	 * Reads the input file from command line arguments and calls the findPath method to find the shortest path.
	 * @param args the command line arguments.
	 * @throws RuntimeException if no input file is specified.
	 * @throws FileNotFoundException if no file is found.
	 * @throws InvalidDungeonCharacterException if invalid character is found.
	 * @throws IOException if error with accessing file.
	 * @throws InvalidNeighbourIndexException if neighbour index is out of range or negative.
	 */
	public static void main (String[] args) {
		try {
			if (args.length < 1) {
				throw new Exception("No input file specified");
			}
			dungeonFileName = args[0];
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		try {
			findPath();
		}catch (FileNotFoundException e){
			System.out.println("FileNotFoundException: File not found");
		}catch (InvalidDungeonCharacterException e){
			System.out.println("Invalid character found in input file");
		}catch (IOException e){
			System.out.println("IOException: error with accessing file");
		}catch (InvalidNeighbourIndexException e){
			System.out.println("Neighbour index is out of range or negative");
		}
	}

	/**
	 * Checks if the given hexagon position has a dragon.
	 * @param hexPosition the hexagon to check.
	 * @return true if the hexagon has a dragon or its neighbour is a dragon, false otherwise.
	 */
	private static boolean dragon(Hexagon hexPosition) {
		if (hexPosition.isDragon()){
			return true;
		}
		else {
			for (int i = 0; i < 6; i++){
				Hexagon neighbour = hexPosition.getNeighbour(i);
				if(neighbour != null && !neighbour.isWall() && !neighbour.isMarkedDequeued() && neighbour.isDragon()) {
					return true;
				}    
			}    
		}
		return false;
	}

	/**
	 * The findPath method finds the shortest path from the starting point to the exit point
	 * It throws various exceptions if the file is not found, invalid characters are found in the file, or there is an error in accessing the file.
	 * @throws IOException if there is an error in accessing the input file
	 * @throws InvalidDungeonCharacterException if invalid characters are found in the input file
	 * @throws InvalidNeighbourIndexException if the neighbour index is out of range or negative
	 */
	private static void findPath() throws IOException{
		Dungeon dungeon = new Dungeon(dungeonFileName);
		DLPriorityQueue<Hexagon> prioQueue = new DLPriorityQueue<Hexagon>();
		boolean exitFound = false;
		prioQueue.add(dungeon.getStart(), 0.0);
		dungeon.getStart().markEnqueued();
		dungeon.getStart().setDistanceToStart(1);

		while(!prioQueue.isEmpty() && !exitFound) {
			current = prioQueue.removeMin();
			current.markDequeued();
			if(current.isExit()) {
				exitFound = true;
				break;
			}
			else {
				boolean updatedQueue = false;
				int D = current.getDistanceToStart() + 1;
				for (int i = 0; i < 6; i++) {
					Hexagon neighbour = current.getNeighbour(i);
					if (neighbour != null && !dragon(neighbour) && !neighbour.isWall() && !neighbour.isMarkedDequeued()){
						if (neighbour.getDistanceToStart() > D) {
							neighbour.setDistanceToStart(D);
							neighbour.setPredecessor(current);
							prioQueue.add(neighbour, (neighbour.getDistanceToExit(dungeon) + neighbour.getDistanceToStart()));
							updatedQueue = true;
						}    
						if (updatedQueue && neighbour.isMarkedEnqueued()) {
							prioQueue.updatePriority(neighbour, (neighbour.getDistanceToExit(dungeon) + neighbour.getDistanceToStart()));
						}
						if (!neighbour.isMarkedEnqueued()) {
							neighbour.markEnqueued();
						}
					}
				}		
			}	
		}
		if(exitFound == true) {
			System.out.println("Path of length " + current.getDistanceToStart() + " found");
		}
		else {
			System.out.println("No path found");
		}
	}
}