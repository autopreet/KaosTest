# KaosTest

## This project is under maintenance.
### I am working on a complete refactoring before I add new features. Code is liable to be broken at this time.


![Sonar Coverage](https://img.shields.io/sonar/coverage/autopreet_KaosTest/master?server=https%3A%2F%2Fsonarcloud.io)
![GitHub last commit](https://img.shields.io/github/last-commit/autopreet/KaosTest)
![GitHub all releases](https://img.shields.io/github/downloads/autopreet/KaosTest/total)

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/autopreet/KaosTest)
![GitHub](https://img.shields.io/github/license/autopreet/KaosTest)
![GitHub Repo stars](https://img.shields.io/github/stars/autopreet/KaosTest?style=social)

Have you ever wanted to re-use your functional tests to put a load on your system but never found a good way? Well, I went through the same conundrum and ended up with KaosTest.
The idea is pretty simple, as long as your test classes are atomic and are using the TestNG framework, we can run those test classes randomly, in N number of threads for a given duration.

## Usage

```implementation 'dev.manpreet:kaostest:1.0'```

## Providers

Instead of using simple parameters, we've used providers to allow significant enhancements which are planned. Please feel free to open a PR to suggest your own.

### Thread count providers

Either the implementations of ```dev.manpreet.kaostest.providers.ThreadCountProvider``` available in ```dev.manpreet.kaostest.providers.threadcount``` can be used. 

Let's see how:

```ThreadCountProvider threadCountProvider = new FixedThreadCountProvider(THREAD_COUNT);```

This will configure the fixed thread count provider with a count of THREAD_COUNT and therefore the executor will run with THREAD_COUNT tests classes in parallel until it stops.

```ThreadCountProvider threadCountProvider = new ThreadCountInRangeProvider(POLL_SECONDS, MIN_COUNT, MAX_COUNT);```

This will configure the ranged thread count provider which when called will return a random number from MIN_COUNT to MAX_COUNT (both inclusive). 
The POLL_SECONDS tells the test executor how frequently do you want to update the thread count.


### Duration count provider

We have only a single implementation of ```dev.manpreet.kaostest.providers.DurationProvider``` available in ```dev.manpreet.kaostest.providers.duration```.

```DurationProvider durationProvider = new FixedDurationProvider(DURATION_VALUE, DURATION_UNIT);```

This will configure the fixed duration provider with the specified duration. DURATION_UNIT is an instance of ```java.util.concurrent.TimeUnit```.
The test executor will poll this provider until it returns a false flag indicating the executor should stop.



## TestNG suite XML

As of now, we only allow you to define a set of tests using the ```<classes></classes>``` definition in the suite file and there should be only a single ```<test></test>``` defined.
Happily, you can add your own test listener classes using ```<listeners></listeners>``` in the suite as usual and these will be invoked for each run of each test.
As an example from our test, your suite XML should look similar to the below in structure with these guidelines:
```
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
<suite name="Fallback Tests">
    <listeners>
        <listener class-name="dev.manpreet.kaostest.listeners.TestListener"/>
    </listeners>

    <test name="Kaos Cases">
        <classes>
            <class name="dev.manpreet.kaostest.testclasses.TestClassA"/>
            <class name="dev.manpreet.kaostest.testclasses.TestClassB"/>
            <class name="dev.manpreet.kaostest.testclasses.more.TestClassC"/>
        </classes>
    </test>
</suite>
```


## Executing and analyzing results

```Runner runner = new Runner();```

```RunnerStore runnerStore = runner.runTests(SUITE_XML_PATH, THREAD_COUNT_PROVIDER, DURATION_PROVIDER);```

If you just want to give it a try, we have a default constructor available which will run the test classes specified in the suite XML for 5 minutes in 10 threads.

```RunnerStore runnerStore = runner.runTests(SUITE_XML_PATH);```


The runner store maintains execution time, total run count, pass count, fail count & skip count for the complete execution as well as at a per test class level.
You can analyze/put conditions on these based on your requirements.

At the end of the execution, you should see a summary in the logs from the runner store:
```Test Class 1
Total runs: 210
Passed: 70 (33.33%)	
Failed: 70 (33.33%)	
Skipped: 70 (33.33%)	
Total execution time: 73.43 seconds	
Average execution time: 0.35 seconds	
Average passed execution time: 1.05 seconds
Test Class 2
Total runs: 132
Passed: 132 (100.00%)	
Failed: 0 (0.00%)	
Skipped: 0 (0.00%)	
Total execution time: 9.08 seconds	
Average execution time: 0.07 seconds	
Average passed execution time: 0.07 seconds
Test Class 3
Total runs: 104
Passed: 104 (100.00%)	
Failed: 0 (0.00%)	
Skipped: 0 (0.00%)	
Total execution time: 292.58 seconds	
Average execution time: 2.81 seconds	
Average passed execution time: 2.81 seconds

Overall Summary:
Total runs: 446
Passed: 306 (68.61%)	
Failed: 70 (15.70%)	
Skipped: 70 (15.70%)	
Total execution time: 375.09 seconds	
Average execution time: 0.84 seconds	
Average passed execution time: 1.23 seconds
```

