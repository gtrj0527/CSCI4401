import math
import time

start_time = time.time()
n = 12699515683626229
sqrt_n = int(math.ceil(math.sqrt(n)))
for i in range(2, sqrt_n+1):
	if n % i == 0:
		print ("%s is not a prime." % n,)
		break
else:
	print ("%s is a prime!" % n,)
end_time = time.time()
print ("This took %.2f seconds." % (end_time - start_time))
