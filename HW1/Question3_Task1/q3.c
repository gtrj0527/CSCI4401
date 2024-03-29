#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

/****************************
 * HW1, Q3, Task 1	    *
 * Force parent to wait for *
 *  child to die            *
 ****************************/
int main(){
	int i, userEntry, status;
	pid_t pid;
	FILE *output;

	output = fopen("output_q3.txt", "w");

	//get user input and print results to screen
	printf("Enter the number of times you'd like the fork loop to run: ");
	scanf("%d", &userEntry);	//Store the number the user enters
	printf("\n You entered %d, and your results will print to a text file titled output_q3.txt. \n", userEntry)
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
				
		//child process	displays its PID and its parent's PID	
		else if (pid == 0)
		{
			printf("Loop #%d \n", i+1);
			printf("I am a child and have no children. My process PID is %d, and my parent PID is %d \n", getpid(), getppid());
			//print results to output.txt
			fprintf(output, "Loop #%d \n", i+1);
			fprintf(output, "(Child) ";
			fprintf(output, "pid: %d ", getpid());
			fprintf(output, "ppid: %d \n\n", getppid());		}
		
		//parent sleeps until something happens and then displays its process PID and its parent's PID		
		else {
			waitpid(pid, &status, 0);	
			printf("Loop #%d \n", i+1);
			printf("I am a parent and have a child. My process PID is %d, and my parent PID is %d \n", getpid(), getppid());
			//print results to output.txt
			fprintf(output, "Loop #%d \n", i+1);
			fprintf(output, "(Parent) ";
			fprintf(output, "pid: %d ", getpid());
			fprintf(output, "ppid: %d \n\n", getppid());		}
			break;
	}
	fclose(output);
	return 0;	//0 because main defined as int
}
