# Sape Sports Football API Perf Test


## FootballPerfSimulation
Load Tests `Search Standings` by ramping up prescribed number of users for the given ramping duration and then maintains a steady load of users for the configurable steady state duration before ramping down

## To Run the test Locally
1. Navigate to gatling folder
2. Run: ./run_gatling.sh 
   
   -b (service base URL) [optional: defaults to "host.docker.internal:8080"]
   
   -t (total TPS) [optional: defaults to 2]
   
   -s (steady state duration in seconds) [optional: defaults to 300]
   
   -u (ramp up duration in seconds) [optional: defaults to 300]
   
   -d (ramp down duration in seconds) [optional: defaults to 300]
