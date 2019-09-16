#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

/****************************
 * HW1, Q2, Task 1	    *
 * Write C-language code    *
 * that does fork() calls   *
 * in a loop
 ****************************/
int main(){
	int i, userEntry, got_pid, status;
	pid_t pid;
	FILE *output;

	output = fopen("output.txt", "w");

	//get user input and print results to screen
	printf("Enter the number of times you'd like the fork loop to run: ");
	scanf("%d", &userEntry);	//Store the number the user enters
	printf("You entered %d, so the loop will run %d times. \n\n", userEntry, userEntry);

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
			fprintf(output, "I am a child and have no children. My process PID is %d, and my parent PID is %d \n", getpid(), getppid());
		}
		
		//parent displays its process PID and its parent's PID		
		else {	
			printf("Loop #%d \n", i+1);
			printf("I am a parent and have a child. My process PID is %d, and my parent PID is %d \n", getpid(), getppid());
			wait(NULL);
			//print results to output.txt
			fprintf(output, "Loop #%d \n", i+1);
			fprintf(output, "I am a parent and have a child. My process PID is %d, and my parent PID is %d \n", getpid(), getppid());
		}
		
	}
	fclose(output);
	return 0;	//0 because main defined as int
}


