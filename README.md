# PPAssignment3

# Problem 1

To run the program, navigate to the Q1 folder and enter "javac Test.java LazyList.java Counter.java and Presents.java". Then, enter "java Test". The program will output "Thank you's written in (time)ms", where time is equal to the time the program has taken to run and finish.
Each of the actions presented in the situation are possible through the implementation of the Lazy Linked List. In the application itself, only the add and remove operations are used, as the method followed for handling the thank you notes is for each servant to add a present from the bag and remove the present at the next available opportunity. The average execution time for this program is around 163ms.

# Problem 2

To run the program, navigate to the Q2 folder and enter "javac Test.java Rover.java". Then, enter "java Test". The program will output a list of the highest temperatures recorded, a list of the lowest temperatures recorded, and the 10 minute interval with the largest temperature difference, with the temperatures recorded in terms of Fahrenheit. The ConcurrentSkipListSet used for storing all temps has an average log(n) runtime for add, remove, and contains operations. The ConcurrentHashMaps for storing high and low temps for each minute have an O(1) runtime for get, put, and contains operations. All temperature data from each threads is stored using the concurrent data structures, which makes sure that there are no deadlocks occurring when trying to access the shared memory. As a result, the runtime of the application will be O(nlogn), where n is the number of temps to record per minute. This program simulates a minute as one second.
