#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

/****************************
 * HW1, Q4     	            *
 * Make the code track &    *
 *  print "Level" it's on   *
 ****************************/
int main(){
	int i, userEntry;    //, got_pid, status;
	int level = 0;      //keeps track of what level of the tree the pid belongs to
	pid_t pid;
	FILE *output;

	output = fopen("q4.txt", "w");

	//get user input and print results to screen
	printf("Enter the number of times you'd like the fork loop to run: ");
	scanf("%d", &userEntry);	//Store the number the user enters
	printf("You entered %d, so the loop will run %d times. \n\n", userEntry, userEntry);

	//for loop through the number of iterations requested by the user
	for(i = 0; i < userEntry; i++)
	{
		//call the fork IOT mirror the current process
		pid = fork();		

		if(i==0)
		{
		    if(pid == -1)
		    {
		        perror("For failure");
		        break;
		    }
		    else if (pid != 0)
		    {
		        fprintf(output, "Root level \n");
		        fprintf(output, "Level: %d \n", level);
		        fprintf(output, "PID: %d \n", getpid());
		        fprintf(output, "Parent PID: %d \n", getppid());
		    }
		}

		//account for errors	
		if (pid == -1)
		{
			perror("Fork failure");
	    		break;
		}
				
		//child process	displays its PID and its parent's PID	
		else if (pid == 0)
		{
	    		level++;
			fprintf(output, "Level: %d \n", level);
	    		fprintf(output, "PID: %d \n", getpid());
	    		fprintf(output, "Parent PID: %d \n", getppid());
		}
		
		//wait for child processes to finish		
		else 
		{
			wait(NULL);
		}
	}
	fclose(output);
	return 0;	//0 because main defined as int
}
