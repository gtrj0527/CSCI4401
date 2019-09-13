#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

/****************************
 * Gabriela Swanson 2542788 *
 * CSCI 4401, Fall 2019     *
 * HW1, Q2, Task 1	    *
 ****************************/
int main(){
	int i, userEntry;
	pid_t pid;
	FILE *output;

	output = fopen("output.txt", "w");

	printf("Enter the number of times you'd like the fork loop to run: ");
	fprintf(output, "Enter the number of times you'd like the fork loop to run: \n");

	scanf("%d", &userEntry);	//Store the number the user enters

	printf("You entered %d, so the loop will run %d times. \n\n", userEntry, userEntry);

	fprintf(output, "You entered %d, so the loop will run %d times. \n\n", userEntry, userEntry);

	//for loop through the number of iterations requested by the user
	for(i = 0; i < userEntry; i++)
	{
		//call the fork IOT mirror the current process
		pid = fork();		

		//account for errors	
		if (pid == -1)
		{
			perror("Fork failure");
		}
		
		//display the process PID and its parent's PID		
		else {
			printf("Loop #%d \n", i);
			printf("The process PID is %d and this process' parent PID is %d \n", getpid(), getppid());
			wait(NULL);	//acct for parent moving faster than child

			//print results to output.txt
			fprintf(output, "Loop #%d \n", i);
			fprintf(output, "The process PID is %d and this process' parent PID is %d \n", getpid(), getppid());			
		}
		fclose(output);		//good practice to close
	}
	return 0;	//0 because main defined as int
}






	
