echo off

set path=C:\dev\java\jdk-9-160\bin;%path%

rem java --module-path bin;lib -m junit/org.junit.runner.JUnitCore hoo.systemtest/com.iksgmbh.demo.hoo.test.HOOSystemTest

rem java -cp ./lib/*;./bin/* org.junit.runner.JUnitCore com.iksgmbh.demo.hoo.test.HOOSystemTest

echo Does not work. If you know why, let me know...

