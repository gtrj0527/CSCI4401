import math
import time
import multiprocessing

# n = number
# x = starting range
# y = ending range

# Function to determine if number is prime
def is_prime(n, x, y):
	for i in range(x, y):
		if n % i == 0:
			print("%s is not a prime number." % (n,))
			break
	else:
		print ("%s IS a prime number" % (n,))

# Process
if __name__ == '__main__':
	# Prime number from wolframalpha.com
	n = 12699515683626229

	# Get square root of the prime number to make the calculation faster	
	sqrt_n = int(math.ceil(math.sqrt(n)))

	# Need start time to determine total amount of time per process
	start_time = time.time()

	# Define the computations: Each will handle 1/2 the work.
	# First computation
	proc1 = multiprocessing.Process(target=is_prime, args=(n, 2, int(sqrt_n/2.0)))
	# Second computation
	proc2 = multiprocessing.Process(target=is_prime, args=(n, int(sqrt_n/2.0), sqrt_n + 1))

	# Start the processes
	proc1.start()
	proc2.start()

	# Join the processes after they execute
	proc1.join()
	proc2.join()

	# Need end time to determine total amount of time per process
	end_time = time.time()

	# Calculate the amount of time taken
	total_time = end_time - start_time

	print("This took %.2f seconds" % total_time)
